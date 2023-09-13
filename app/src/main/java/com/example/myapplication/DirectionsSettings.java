package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class DirectionsSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions_settings);
    }

    public void setDetailed(View view) {
        Directions.detailedDirections = true;
        finish();
    }

    public void setBrief(View view) {
        Directions.detailedDirections = false;
        finish();
    }


}