package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * UI component for Route Plan Activity
 */
public class RoutePlan extends AppCompatActivity {
    private DayPlannerDao dayPlannerDao;
    File visitedFile;
    Scanner reader;
    List<String> shortestPath;
    TextView routeView;
    ZooGraph zg;
    Coord currentLocation;
    String closestExhibit;
    List<String> visited;


    CompactDPList compactDPList = new CompactDPList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_plan);


    }

    public void onDismiss(DialogInterface dialogInterface)
    {
        System.out.println("Dismissed");
    }
    public void sendDirections(View view) {
        Intent intent = new Intent(this, Directions.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int btn = item.getItemId();

        if (btn == R.id.compactDayPlanner) {
            compactDPList.showDialog(RoutePlan.this, this,this,this);
        }


        return super.onOptionsItemSelected(item);
    }
//

   @Override
    public void onResume() {
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(this).edit();
        e.putString("last_activity", getClass().getSimpleName());
        e.commit();

        super.onResume();
       InitializeFile.initFiles(this);
       PlannerDatabase db = PlannerDatabase.getSingleton(this);
       dayPlannerDao = db.dayPlannerDao();
       List<DayPlannerItem> dayPlannerItems = dayPlannerDao.getAll();
       List<String> selectedExhibits = new ArrayList<>();
       String currEx = "";


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

       List<String> visited = DataInput.getVisitedArray(this);
       currEx = CoordToExhibit.closestExhibit( DataInput.getCurrentLocation(this) ).id;


       try {
           zg = new ZooGraph(this);
           String start = ZooGraph.START_ITEM.id;
           for (int i = 0; i < selectedExhibits.size(); i++) {
               if (visited.contains(selectedExhibits.get(i))) {
                   selectedExhibits.remove(i);
               }
           }

           shortestPath = zg.shortestPath(currEx, selectedExhibits);
           shortestPath.remove(0);
           for (int i = visited.size() - 1; i >= 0; i--) {
               shortestPath.add(0, visited.get(i));
           }
           routeView = findViewById(R.id.routeplanset);
           int counter = 1;
           String shortestPathText = "";
           for (String s : shortestPath) {

               if (s.equals(currEx) && counter <= shortestPath.size() ) {
                   shortestPathText += counter + ". " + zg.getIdName(s) + " (Here)\n";
               }
               else {
                   shortestPathText += counter + ". " + zg.getIdName(s) + "\n";
               }
               counter++;
           }

           shortestPathText += counter + ". " + zg.getIdName(start) +"\n";

           routeView.setText(shortestPathText);
       } catch (IOException ex) {
           ex.printStackTrace();
       }


    }

    public void onPause() {
        super.onPause();
        Log.d("last_Activity", "here4");
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(this).edit();
        e.putString("last_activity", "DayPlanner");
        System.out.println("Route Plan Paused");
        e.commit();
        finish();
//        Intent intent = new Intent(this,MainActivity.class);
    }
}