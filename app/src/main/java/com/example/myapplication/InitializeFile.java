package com.example.myapplication;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class InitializeFile {

    public static void initFiles(Context c) {

        ExhibitData d = new ExhibitData(c);
        CoordToExhibit co = new CoordToExhibit(c);
        try {
            ZooGraph z = new ZooGraph(c);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileInputStream locationFile = null;


        try {
            locationFile = c.openFileInput("location.txt");
        }  catch (IOException e) {
            try {
                FileOutputStream createFile = c.openFileOutput("location.txt", MODE_PRIVATE);
                createFile.close();
                locationFile = c.openFileInput("location.txt");
            } catch (IOException e2) {
                e2.printStackTrace();
            }

        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader( locationFile) );
            String retStr = reader.readLine();
            reader.close();
            if(retStr == null) {
                FileOutputStream fileLoc = c.openFileOutput("location.txt", MODE_PRIVATE);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter( fileLoc));
                double lat = Double.parseDouble(ZooGraph.START_LAT);
                double lng = Double.parseDouble(ZooGraph.START_LNG);
                String latlng = lat + "," + lng;
                writer.write(latlng);
                writer.close();
                fileLoc.close();
                locationFile.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            locationFile = c.openFileInput("visited.txt");
            locationFile.close();
        } catch (IOException e) {
            try {
                FileOutputStream createFile = c.openFileOutput("visited.txt", MODE_PRIVATE);
                createFile.close();
                createFile = c.openFileOutput("visited.txt", MODE_APPEND);
                BufferedWriter writeVis = new BufferedWriter(new OutputStreamWriter( createFile ));
                writeVis.write(ZooGraph.START_ITEM.id);
                writeVis.close();
                createFile.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }
}
