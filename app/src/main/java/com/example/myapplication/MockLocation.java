package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MockLocation extends AppCompatActivity {

    EditText latitude;
    EditText longitude;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_location);

        latitude =  findViewById(R.id.lat_txt);
        longitude =  findViewById(R.id.lng_txt);
    }

    public void onDoneClicked(View view) {
        try {
            String lat = latitude.getText().toString();
            String lng = longitude.getText().toString();

            if (lat.length() < 1 || lng.length() < 1 ) {
                finish();
                return;
            }
            else {
                FileOutputStream fileLoc = openFileOutput("location.txt", Context.MODE_PRIVATE);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter( fileLoc));

                String latlng = lat + "," + lng;
                String tempVisited = "";


                ZooData.VertexInfo closest = CoordToExhibit.closestExhibit(Coord.of(Double.parseDouble(lat),Double.parseDouble(lng)));
                if(!Directions.visited.contains(closest.id)) Directions.visited.add(closest.id);

                FileOutputStream fileVis = openFileOutput("visited.txt", Context.MODE_APPEND);
                BufferedWriter writeVis = new BufferedWriter(new OutputStreamWriter( fileVis));
                writeVis.append(",").append(closest.id);
                writeVis.close();


                writer.write(latlng);
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finish();
    }
}