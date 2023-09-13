package com.example.myapplication;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataInput {

    public static final String LOCATION_FILE_NAME = "location.txt";
    public static final String VISITED_FILE_NAME = "visited.txt";


    public static List<String> getVisitedArray(Context context) {

        List<String> visited = new ArrayList<>();
        try {
            FileInputStream visitedFile = context.openFileInput(VISITED_FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader( visitedFile) );
            String line = reader.readLine();
            if (line != null) {
                String[] visitedExhibits = line.split(",");
                reader.close();
                for (int i = 0; i < visitedExhibits.length; i++) {
                    if (!visited.contains(visitedExhibits[i])) visited.add(visitedExhibits[i]);
                }
            }

        } catch(IOException e) {
            e.printStackTrace();
        }

        return visited;
    }

    public static Coord getCurrentLocation(Context context) {
        Coord c;
        try {
            FileInputStream locationFile = context.openFileInput(LOCATION_FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader( locationFile) );
            String retStr = reader.readLine();
            reader.close();
            if (retStr == null) {
                c = Coord.of( Double.parseDouble(ZooGraph.START_LAT), Double.parseDouble(ZooGraph.START_LNG) );
            }
            else {
                String[] loc = retStr.split(",");
                ZooData.VertexInfo start = CoordToExhibit.closestExhibit( Coord.of(Double.parseDouble(loc[0]), Double.parseDouble(loc[1])) );
                c = Coord.of( Double.parseDouble( start.lat ), Double.parseDouble(start.lng) );
            }
            return c;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
