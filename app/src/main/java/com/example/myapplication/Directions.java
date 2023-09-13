package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * UI component for Directions Activity
 */
public class Directions extends AppCompatActivity {
    private DayPlannerDao dayPlannerDao;
    private ScrollingMovementMethod scroll;
    public static boolean detailedDirections;
    boolean showingDirections;
    int currentExhibit;

    Coord currentLocation;
    String closestExhibit;
    static int target;
    public static List<String> shortestPath;
    public static List<String> selectedExhibits;

    TextView directionsView;
    Button prevButton;
    Button nextButton;
    Button continueButton;
    Button skipButton;
    Button settingsButton;
    Button mockButton;

    public static List<String> visited;

    ZooGraph zg;


    //Adding for restore state
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            //Save currentExhibit

            SharedPreferences pref=getSharedPreferences("hold",Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = pref.edit();
            edit.putInt("target",target);
            Log.d("holdInfo","textwatcher "+ target );
            edit.commit();
        }
    };

    //End
    // new comment!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        SharedPreferences pref=getSharedPreferences("hold",Context.MODE_PRIVATE);

        PlannerDatabase db = PlannerDatabase.getSingleton(this);
        dayPlannerDao = db.dayPlannerDao();
        List<DayPlannerItem> dayPlannerItems = dayPlannerDao.getAll();
        selectedExhibits = new ArrayList<>();
        visited = new ArrayList<>();



        for(DayPlannerItem d : dayPlannerItems) {
            if (d.group_id != null) {
                if (selectedExhibits.contains(d.group_id)) {
                    continue;
                }
                else {
                    selectedExhibits.add(d.group_id);
                }
            }
            else {
                selectedExhibits.add(d.id);
            }

        }

        try {
            FileInputStream visitedFile = openFileInput("visited.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader( visitedFile) );
            String line = reader.readLine();
            if (line != null) {
                String[] visitedExhibits = line.split(",");
                reader.close();
                for (int i = 0; i < visitedExhibits.length; i++) {
                    if(!visited.contains(visitedExhibits[i])) visited.add(visitedExhibits[i]);
                }
                /*
                for (int i = 0; i < selectedExhibits.size(); i++) {
                    if ( visited.contains(selectedExhibits.get(i)) ) {
                        selectedExhibits.remove(i);
                    }
                }

                 */
            }else{
                FileOutputStream fileVis = openFileOutput("visited.txt", Context.MODE_PRIVATE);
                BufferedWriter writeVis = new BufferedWriter(new OutputStreamWriter( fileVis));
                writeVis.write(ZooGraph.START_ITEM.id);
                writeVis.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            FileInputStream locationFile = openFileInput("location.txt");
            System.out.println("Directory printed: " + getFilesDir() );
            BufferedReader reader = new BufferedReader(new InputStreamReader( locationFile) );
            String retStr = reader.readLine();
            reader.close();
            if (retStr == null) {
                currentLocation = Coord.of( Double.parseDouble(ZooGraph.START_LAT), Double.parseDouble(ZooGraph.START_LNG) );
                closestExhibit = ZooGraph.START_ITEM.id;
            }
            else {
                String[] loc = retStr.split(",");
                ZooData.VertexInfo start = CoordToExhibit.closestExhibit( Coord.of(Double.parseDouble(loc[0]), Double.parseDouble(loc[1])) );
                currentLocation = Coord.of( Double.parseDouble( start.lat ), Double.parseDouble(start.lng) );
                closestExhibit = start.id;
            }

        } catch (IOException e) {
            currentLocation = Coord.of( Double.parseDouble(ZooGraph.START_LAT), Double.parseDouble(ZooGraph.START_LNG) );
            closestExhibit = ZooGraph.START_ITEM.id;
        }
        try {
            zg = new ZooGraph(this);
            if(!visited.contains(ZooGraph.START_ITEM.id)) visited.add(ZooGraph.START_ITEM.id);

            selectedExhibits.remove(closestExhibit);
            shortestPath = zg.shortestPath(closestExhibit, selectedExhibits);
            shortestPath.add(ZooGraph.START_ITEM.id);

            target = pref.getInt("target",1);

            scroll = new ScrollingMovementMethod();
            directionsView = findViewById(R.id.directionsSet);
            directionsView.setMovementMethod(null);
            //System.out.println(directionsView.getMovementMethod() );
            nextButton = findViewById(R.id.next_btn);
            prevButton = findViewById(R.id.prev_btn);
            skipButton = findViewById(R.id.skip_btn);
            continueButton = findViewById(R.id.continue_btn);
            settingsButton = findViewById(R.id.settings_btn);
            mockButton = findViewById(R.id.change_loc_btn);
            detailedDirections = false;
            showingDirections = false;
            previewExhibit(directionsView);
        } catch (IOException e) {
            e.printStackTrace();
        }


        int tar= pref.getInt("target",1);
        int currEx = pref.getInt("currExhibit",0);

        Log.d("holdInfo","resume "+ tar+ " - "+ target);

    }

    public void showEndAlert(View view) throws IOException {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("No more exhibits! \n"
                        + "Click back to return to Route Plan.")
                .setTitle("Error");

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void sendSettings(View view) {
        Intent intent = new Intent(this, DirectionsSettings.class);
        startActivity(intent);

    }

    public void sendMock(View view) {
        SharedPreferences pref=getSharedPreferences("hold",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt("target",1);
        Log.d("holdInfo","set target directions=1 ");
        edit.commit();
        Intent intent = new Intent(this, MockLocation.class);
        startActivity(intent);

    }

    public void sendRoute(View view) {
        Intent intent = new Intent(this, RoutePlan.class);
        startActivity(intent);
    }
    public void previousExhibit(View view) throws IOException {
        if (target > 0) {
            target--;
        }

        previewPrevExhibit(view);
    }

    public void nextPressed(View view) throws IOException {
        if (target < shortestPath.size() )
        {
            target++;
        }

        previewExhibit(view);
    }
    public void previewExhibit(View view) throws IOException {

        Log.d("holdInfo","preview "+ target);
        showingDirections = false;
        if (target >= shortestPath.size() || target < 0) {
            showEndAlert(view);
            return;
        }

        nextButton.setVisibility(View.INVISIBLE);
        prevButton.setVisibility(View.INVISIBLE);


        String origin = closestExhibit;
        String dest = shortestPath.get(target);
        String formatted = "Starting from " + zg.getIdName(origin) + "\n\n";
        formatted += "\nYour next exhibit is " + zg.getIdName(dest) + "\n\n";
        formatted += "\nDo you wish to proceed?\n";
        directionsView.setText(formatted);
        directionsView.setMovementMethod(null);

        directionsView.addTextChangedListener(textWatcher);

        skipButton.setVisibility(View.VISIBLE);
        continueButton.setVisibility(View.VISIBLE);

    }

    public void previewPrevExhibit(View view) throws IOException {
        showingDirections = false;
        if (target >= shortestPath.size() || target < 0) {
            showEndAlert(view);
            return;
        }

        nextButton.setVisibility(View.INVISIBLE);
        prevButton.setVisibility(View.VISIBLE);


        String origin = closestExhibit;
        String dest = shortestPath.get(target);
        String formatted = "Starting from " + zg.getIdName(origin) + "\n\n";
        formatted += "\nYour next exhibit is " + zg.getIdName(dest) + "\n\n";
        formatted += "\nDo you wish to proceed?\n";
        directionsView.setText(formatted);
        directionsView.setMovementMethod(null);

        skipButton.setVisibility(View.INVISIBLE);
        continueButton.setVisibility(View.VISIBLE);
    }


    public void continuePressed(View view) throws IOException {
        showingDirections = false;
        skipButton.setVisibility(View.INVISIBLE);
        continueButton.setVisibility(View.INVISIBLE);
        nextButton.setVisibility(View.VISIBLE);
        prevButton.setVisibility(View.VISIBLE);
        advanceDirections(view);
    }

    public void skipPressed(View view) throws IOException {
        showingDirections = false;
        if (currentExhibit == shortestPath.size() - 2) {
            advanceDirections(view);
            return;
        }
        else if (currentExhibit >= shortestPath.size() - 1) {
            showEndAlert(view);
            return;
        }
        shortestPath.remove(currentExhibit+1);
        previewExhibit(view);

    }

    /**
     * Repopulate Directions with text-based directions for the next pair of exhibits
     * @param view the View where this method is being called
     */
    public void advanceDirections(View view) throws IOException {
        showingDirections = false;
        if (target >= shortestPath.size() ) {
            showEndAlert(view);
            return;
        }
        showDirections(view);

    }

    public void showDirections(View view) {
        showingDirections = true;
        String origin = closestExhibit;
        String dest = shortestPath.get(target);
        List<String> currentDirections;
        if (detailedDirections) {
            currentDirections = zg.directions(origin, dest);
        }
        else {
            currentDirections = zg.briefDirections(origin, dest);
        }
        String formatted = "Starting from " + zg.getIdName(origin) + "\n\n";
        for(String str : currentDirections) {
            formatted += str;
        }

        formatted += "\nYou have now reached " + zg.getIdName(dest) + "!\n";
        //visited.add(dest);
        directionsView.setText(formatted);
        directionsView.setMovementMethod(scroll);

    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("Visited: " + visited);
        {
            try {
            FileInputStream locationFile = openFileInput("location.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(locationFile));
            String retStr = reader.readLine();
            reader.close();
            if (retStr == null) {
                currentLocation = Coord.of(Double.parseDouble(ZooGraph.START_LAT), Double.parseDouble(ZooGraph.START_LNG));
                closestExhibit = ZooGraph.START_ITEM.id;
            } else {
                String[] loc = retStr.split(",");
                ZooData.VertexInfo start = CoordToExhibit.closestExhibit(Coord.of(Double.parseDouble(loc[0]), Double.parseDouble(loc[1])));
                currentLocation = Coord.of(Double.parseDouble(start.lat), Double.parseDouble(start.lng));
                closestExhibit = start.id;
            }
            System.out.println("Old shortest Path: " + shortestPath);
            //List<String> oldPath = shortestPath;

                if(!visited.contains(ZooGraph.START_ITEM.id)) visited.add(ZooGraph.START_ITEM.id);
                List<String> tempShortestPath = new ArrayList<>();
                List<String> temp = new ArrayList<>();
                System.out.println("Selected Exhibits: " + selectedExhibits);
                for(String str : selectedExhibits) {
                    if(!visited.contains(str)) temp.add(str);
                }
                tempShortestPath = zg.shortestPath(closestExhibit, temp);

                tempShortestPath.remove(closestExhibit);
                System.out.println("Middle shortest Path: " + tempShortestPath);
                for(int i = visited.size() - 1; i >= 0; i--) {
                    tempShortestPath.add(0, visited.get(i));
                }
                tempShortestPath.add(ZooGraph.START_ITEM.id);
                int tempTarget = visited.size();

                System.out.println("New shortest Path: " + tempShortestPath);
                System.out.println("Old Target " + shortestPath.get(target));
                System.out.println("New Target " + tempShortestPath.get(tempTarget));

                if(tempShortestPath.get(tempTarget) != shortestPath.get(target)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setMessage("Click OK to Replan Route.")
                            .setTitle("Replan Route?");
                    List<String> finalTempShortestPath = tempShortestPath;
                    builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            shortestPath = finalTempShortestPath;
                            target = tempTarget;
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }

            System.out.println("New shortest Path: " + shortestPath);
            //System.out.println("Vs old: " + oldPath);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (showingDirections) {
            showDirections(directionsView);

        } else {
            try {
                previewExhibit(directionsView);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    //Restores to activity from killed
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(this).edit();
        e.putString("last_activity", getClass().getSimpleName());
        e.commit();

    }//

    public void onPause(){
        super.onPause();
        SharedPreferences pref=getSharedPreferences("hold",Context.MODE_PRIVATE);
        int tar= pref.getInt("target",1);

        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(this).edit();
        e.putString("last_activity", "MainActivity");
        e.commit();

        Log.d("holdInfo","pause "+ tar);
    }




}