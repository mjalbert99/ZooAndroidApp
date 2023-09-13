package com.example.myapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.app.Dialog;
import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowDialog;


@RunWith(AndroidJUnit4.class)
public class DisplayCompactListTest {

    @Test
    public void dialogTest() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        activity.findViewById(R.id.compactDayPlanner).performClick();

        Dialog dialog = ShadowDialog.getLatestDialog();
        assertTrue(dialog.isShowing());


    }
}