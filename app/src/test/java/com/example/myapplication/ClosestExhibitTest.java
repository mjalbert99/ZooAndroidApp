package com.example.myapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.checkerframework.checker.units.qual.C;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Ordering;

import java.util.Arrays;
import java.util.List;
import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class ClosestExhibitTest {

    @Test
    public void ClosestExhibitTest() throws IOException {
        Context context = ApplicationProvider.getApplicationContext();
        ZooGraph zooGraph;
        {
            try {
                zooGraph = new ZooGraph(context);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Coord coord = new Coord(32.73459618734685, -117.14936);
        ExhibitData ed = new ExhibitData(context);
        CoordToExhibit cte = new CoordToExhibit(context);
        assertEquals("entrance_exit_gate", cte.closestExhibit(coord).id);
    }

}
