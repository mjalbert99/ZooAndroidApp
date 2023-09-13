package com.example.myapplication;


import android.content.Intent;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;

import java.util.List;
import java.util.Locale;

@RunWith(RobolectricTestRunner.class)
public class SearchBarTest {

    @Test
    public void searchBarLaunched(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.findViewById(R.id.action_search).performClick();

        Intent expectedIntent = new Intent(activity, SearchActivity.class);
        Intent actual = Shadows.shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void searchResultLowerCase(){
        SearchActivity activity = Robolectric.setupActivity(SearchActivity.class);

        activity.adapter.filter("lions");
        int filterNum = activity.adapter.filterTest();
        int num = 0;
        for(DayPlannerItem s : activity.listJson){
            if(s.name.toLowerCase(Locale.ROOT).equals("lions")) num++;
        }
        assertEquals(filterNum,num);

    }

    @Test
    public void searchResultUpperCase(){
        SearchActivity activity = Robolectric.setupActivity(SearchActivity.class);

        activity.adapter.filter("LIONS");
        int filterNum = activity.adapter.filterTest();
        int num = 0;
        for(DayPlannerItem s : activity.listJson){
            if(s.name.toLowerCase(Locale.ROOT).equals("lions")) num++;
        }
        assertEquals(filterNum,num);

    }
    @Test
    public void searchResultTags(){
        SearchActivity activity = Robolectric.setupActivity(SearchActivity.class);

        activity.adapter.filter("africa");
        int filterNum = activity.adapter.filterTest();
        int num = 0;
        for(DayPlannerItem s : activity.listJson){
            if(s.tags.contains("africa")) num++;
        }

        assertEquals(filterNum,num);

    }

    @Test
    public void searchResultAutoUpdate(){
        SearchActivity activity = Robolectric.setupActivity(SearchActivity.class);

        activity.adapter.filter("afri");
        int filterNum = activity.adapter.filterTest();
        int num = 0;
        for(DayPlannerItem s : activity.listJson){
            if(s.tags.contains("africa")) num++;
        }
        assertEquals(filterNum,num);

    }

}
