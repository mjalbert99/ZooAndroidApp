package com.example.myapplication;
//SQLite
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@Entity(tableName = "activity_list")
public class DayPlannerItem{
    @PrimaryKey(autoGenerate = true)
    public long index;

    @NonNull
    public String id;
    public String kind;
    public String name;
    public String group_id;
    public List<String> tags;
    //public String lat;
    //public String lng;

    DayPlannerItem(@NonNull String id, String kind, String name, String group_id, List<String> tags){
        this.id = id;
        this.kind = kind;
        this.name = name;
        this.group_id = group_id;
        this.tags = tags;

    }


    public static List<DayPlannerItem> loadJSON(Context context, String path){
        try {
            InputStream input = context.getAssets().open(path);
            Reader reader = new InputStreamReader(input);
            Gson gson = new Gson();
            Type type = new TypeToken<List<DayPlannerItem>>(){}.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public String toString() {
        return "DayPlannerItem{" +
                "id=" + id +
                ", kind=" + kind +
                ", parent_id=" + group_id +
                ", name=" +name +
                ", tags =" + tags +
                //", lat=" + lat +
                //", long=" + lng +
                '}';
    }

}


