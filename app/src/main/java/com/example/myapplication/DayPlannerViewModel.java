package com.example.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DayPlannerViewModel extends AndroidViewModel {
    private LiveData<List<DayPlannerItem>> dayPlannerList;
    private final DayPlannerDao dayPlannerDao;

    public DayPlannerViewModel(@NonNull Application application) {
        super(application);
        Context context = getApplication().getApplicationContext();
        PlannerDatabase db = PlannerDatabase.getSingleton(context);
        dayPlannerDao = db.dayPlannerDao();
    }

    public LiveData<List<DayPlannerItem>> getDayPlannerList() {
        if (dayPlannerList == null) {
            loadUsers();
        }
        return dayPlannerList;
    }

    private void loadUsers() {
        dayPlannerList = dayPlannerDao.getAllLive();
    }

    public void createAnimalItem(DayPlannerItem animal) {
        dayPlannerDao.insert(animal);
    }

    public int listCount() {
        return dayPlannerDao.getAll().size();
    }

    public void deleteItem(DayPlannerItem dayPlannerItem) {
        SharedPreferences pref= getApplication().getSharedPreferences("hold",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt("target",1);
        Log.d("holdInfo","set target delete=1 ");
        edit.commit();
        dayPlannerDao.delete(dayPlannerItem);

    }

    public void deleteAllItem(){
        for( DayPlannerItem dpi : dayPlannerList.getValue()){
            deleteItem(dpi);
        }
    }
}
