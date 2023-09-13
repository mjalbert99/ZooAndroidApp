package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DayPlannerDao {
    @Insert
    long insert(DayPlannerItem dayPlannerItem);

    @Update
    int update(DayPlannerItem dayPlannerItem);

    @Delete
    int delete(DayPlannerItem dayPlannerItem);

    @Query("SELECT * FROM `activity_list` WHERE `index`= :index")
    DayPlannerItem get(long index);

    @Insert
    List<Long> insertAll(List<DayPlannerItem> dayPlannerItems);

   @Query("SELECT * FROM `activity_list` ORDER BY `index`")
    List<DayPlannerItem> getAll();

    @Query("SELECT * FROM `activity_list` ORDER BY `index`")
    LiveData<List<DayPlannerItem>> getAllLive();
}

