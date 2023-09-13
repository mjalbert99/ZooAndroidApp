package com.example.myapplication;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {DayPlannerItem.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class PlannerDatabase  extends RoomDatabase {
    private static PlannerDatabase singleton =null;

    public abstract DayPlannerDao dayPlannerDao();

    public synchronized static PlannerDatabase getSingleton(Context context){
        if(singleton == null){
            singleton = PlannerDatabase.makeDatabase(context);
        }
        return singleton;

    }

    private static PlannerDatabase makeDatabase(Context context){
        return Room.databaseBuilder(context,PlannerDatabase.class,"todo_app.db")
                .allowMainThreadQueries()
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(()->{
                            List<DayPlannerItem> items = DayPlannerItem
                                    .loadJSON(context, "sample_node_info.json");
                            //getSingleton(context).dayPlannerDao().insertAll(items);
                        });
                    }
                })
                .build();
    }

    @VisibleForTesting
    public static void injectTestDatabase(PlannerDatabase testDatabase) {
        if (singleton != null) {
            singleton.close();
        }
        singleton = testDatabase;
    }


}
