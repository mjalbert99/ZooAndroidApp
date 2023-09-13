package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Activity;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends AppCompatActivity {

   /* public static Dialog dialog;
    private  DayPlannerViewModel viewModel;
    private Button backBtn;*/
    CompactDPList compactDPList= new CompactDPList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String lastActivity = PreferenceManager.getDefaultSharedPreferences(this).getString("last_activity", "");
        if (lastActivity.equals("Directions")) {
            startActivity(new Intent(this, Directions.class));
        } else if (lastActivity.equals("RoutePlan")) {
            startActivity(new Intent(this, RoutePlan.class));
        } else if (lastActivity.equals("SearchActivity")) {
            startActivity(new Intent(this, SearchActivity.class));
        } else if (lastActivity.equals("DayPlanner")) {
            startActivity(new Intent(this, DayPlanner.class));
        }



        InitializeFile.initFiles(this);


    }

 /*   //Resume on last closed activity
    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences prefs = getSharedPreferences("X", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }*/

    /** Called when the user touches the button */
    public void sendDayPlanner(View view) {
        Intent intent = new Intent(this, DayPlanner.class);
        startActivity(intent);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_bar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.compactDayPlanner:

                //showDialog(MainActivity.this);
                compactDPList.showDialog(MainActivity.this, this,this,this);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
/*
    private void showDialog(Activity activity) {
        dialog= new Dialog(activity);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.compactlist);

        this.backBtn= dialog.findViewById(R.id.Cbutton);
        this.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        viewModel = new ViewModelProvider(this)
                .get(DayPlannerViewModel.class);

        DayPlannerAdapter adapter = new DayPlannerAdapter();
        adapter.setOnDeleteClickedHandler(viewModel::deleteItem);
        viewModel.getDayPlannerList().observe(this, adapter::setDayPlannerListItem);

        RecyclerView recyclerView = dialog.findViewById(R.id.dialog_recycler_view);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        dialog.show();
    }*/

/*
* https://demonuts.com/android-custom-dialog/#re
*  Creating a dialog that takes in our recyclerview and displays it
*/


}