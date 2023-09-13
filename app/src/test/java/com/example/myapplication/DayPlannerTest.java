package com.example.myapplication;

import static org.junit.Assert.assertEquals;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
@RunWith(RobolectricTestRunner.class)
public class DayPlannerTest {

    @Test
    public void dayPlannerLaunched(){
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.findViewById(R.id.dayPlannerButton).performClick();

        Intent expectedIntent = new Intent(activity, DayPlanner.class);
        Intent actual = Shadows.shadowOf(RuntimeEnvironment.application).getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());


    }

}
