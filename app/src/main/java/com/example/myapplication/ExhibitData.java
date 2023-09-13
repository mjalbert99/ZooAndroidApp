package com.example.myapplication;

import android.content.Context;

import java.io.IOException;
import java.util.Map;

public class ExhibitData {
    public static Map<String, ZooData.VertexInfo> vInfo;

    public ExhibitData(Context context) {
        try {
            vInfo = ZooData.loadVertexInfoJSON("sample_node_info.json", context);
        } catch (IOException e) {
            System.out.println("Weird io exception");
        }

    }

    public static double getLat(String name) {
        return Double.parseDouble( vInfo.get(name).lat) ;
    }
    public static double getLng(String name) {
        return Double.parseDouble( vInfo.get(name).lng) ;
    }
}
