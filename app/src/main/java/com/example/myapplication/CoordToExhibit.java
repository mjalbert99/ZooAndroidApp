package com.example.myapplication;


import static java.sql.Types.DOUBLE;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CoordToExhibit {

    public static List<ZooData.VertexInfo> exhibits;
    public static final double LAT_TO_FT = 363843.57;
    public static final double LNG_TO_FT = 307515.50;

    public CoordToExhibit(Context context) {
        exhibits = new ArrayList<ZooData.VertexInfo>();
        for (Map.Entry<String,ZooData.VertexInfo > m : ExhibitData.vInfo.entrySet() ) {
            ZooData.VertexInfo entry = m.getValue();
            if( (entry.lat != null && entry.lng != null ) ) {
                exhibits.add(entry);
            }
        }
    }

    public static ZooData.VertexInfo closestExhibit (Coord loc) {
        ZooData.VertexInfo closestEx = null;
        double minDistance = Double.MAX_VALUE;
        for (ZooData.VertexInfo d : exhibits) {
            double lat = Double.parseDouble(d.lat);
            double lng = Double.parseDouble(d.lng);
            double distance = distanceBetween(loc, Coord.of(lat, lng));
            if ( distance < minDistance) {
                closestEx = d;
                minDistance = distance;
            }
        }

        return closestEx;
    }

    public static double distanceBetween(Coord coord1, Coord coord2) {

        double lng1 = coord1.lng * LNG_TO_FT;
        double lng2 = coord2.lng * LNG_TO_FT;

        double lat1 = coord1.lat * LAT_TO_FT;
        double lat2 = coord2.lat * LAT_TO_FT;
        return Math.abs( Math.sqrt( (lng2 - lng1) * (lng2 - lng1) + (lat2 - lat1) * (lat2 - lat1)) );
    }


}
