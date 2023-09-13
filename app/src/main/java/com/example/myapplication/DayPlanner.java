package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class DayPlanner extends AppCompatActivity {

    public RecyclerView recyclerView;
    private  DayPlannerViewModel viewModel;
    private DayPlannerItem dayPlannerItem;
    private TextView itemCount;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_planner_list);

        viewModel = new ViewModelProvider(this)
                .get(DayPlannerViewModel.class);
        DayPlannerAdapter adapter = new DayPlannerAdapter();
        adapter.setOnDeleteClickedHandler(viewModel::deleteItem);
        viewModel.getDayPlannerList().observe(this, adapter::setDayPlannerListItem);

        //Updatable counter
        viewModel.getDayPlannerList().observe(this, DayPlanner->{
            this.itemCount = this.findViewById(R.id.count_items);
            count = viewModel.listCount();
            this.itemCount.setText(String.valueOf(count));
        });

        recyclerView = findViewById(R.id.dayplanner_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        Button clearBtn = (Button)findViewById(R.id.clearDayPlannerBtn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.deleteAllItem();
                SharedPreferences pref=getSharedPreferences("hold",Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putInt("target",1);
                Log.d("holdInfo","set target planner=1 ");
                edit.commit();
            }
        });

        InitializeFile.initFiles(this);


    }

    public void onBackPressed(){
        Intent intent = new Intent(this,MainActivity.class);
    }

    public void sendRoutePlan(View view) {
        Intent intent = new Intent(this, RoutePlan.class);
        startActivity(intent);
    }
//
    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(this).edit();
        e.putString("last_activity", getClass().getSimpleName());
        e.commit();
    }

    public void onPause() {
        super.onPause();
        Log.d("last_Activity", "here2");
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(this).edit();
        e.putString("last_activity", "MainActivity");
        e.commit();
        finish();
//        Intent intent = new Intent(this,MainActivity.class);
    }



}
