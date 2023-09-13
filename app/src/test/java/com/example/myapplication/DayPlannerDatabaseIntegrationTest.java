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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Ordering;

import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DayPlannerDatabaseIntegrationTest {
    PlannerDatabase testDb;
    DayPlannerDao dayPlannerDao;


    public static void forceLayout(RecyclerView recyclerView){
        recyclerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        recyclerView.layout(0,0,1080,2280);
    }

    @Before
    public void resetDatabase(){
        Context context = ApplicationProvider.getApplicationContext();
        testDb = Room.inMemoryDatabaseBuilder(context, PlannerDatabase.class)
                .allowMainThreadQueries()
                .build();
        PlannerDatabase.injectTestDatabase(testDb);

        List<DayPlannerItem> items = DayPlannerItem.loadJSON(context, "sample_node_info.json");
        dayPlannerDao = testDb.dayPlannerDao();
        dayPlannerDao.insertAll(items);


    }

    // test line
    @Test
    public void testDeleteItem(){
        ActivityScenario<DayPlanner> scenario
                = ActivityScenario.launch(DayPlanner.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {
            List<DayPlannerItem> beforeDayPlannerList = dayPlannerDao.getAll();

            RecyclerView recyclerView = activity.recyclerView;
            RecyclerView.ViewHolder firstVH = recyclerView.findViewHolderForAdapterPosition(0);
            assertNotNull(firstVH);
            long index = firstVH.getItemId();

            View deleteButton = firstVH.itemView.findViewById(R.id.add_remove);
            deleteButton.performClick();

            List<DayPlannerItem> afterDayPlannerList = dayPlannerDao.getAll();
            assertEquals(beforeDayPlannerList.size()-1, afterDayPlannerList.size());

            DayPlannerItem editedItem = dayPlannerDao.get(index);
            assertNull(editedItem);
        });

    }
    @Test
    public void testAddItem(){
        ActivityScenario<DayPlanner> scenario
                = ActivityScenario.launch(DayPlanner.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {
                    List<DayPlannerItem> beforeDayPlannerList = dayPlannerDao.getAll();

                    RecyclerView recyclerView = activity.recyclerView;
                    RecyclerView.ViewHolder firstVH = recyclerView.findViewHolderForAdapterPosition(0);
                    assertNotNull(firstVH);
                    long index = firstVH.getItemId();

                    View deleteButton = firstVH.itemView.findViewById(R.id.add_remove);
                    deleteButton.performClick();

                    List<DayPlannerItem> afterDayPlannerList = dayPlannerDao.getAll();
                    assertEquals(beforeDayPlannerList.size() - 1, afterDayPlannerList.size());
        });

        ActivityScenario<SearchActivity> scenario1
                = ActivityScenario.launch(SearchActivity.class);
        scenario1.moveToState(Lifecycle.State.CREATED);
        scenario1.moveToState(Lifecycle.State.STARTED);
        scenario1.moveToState(Lifecycle.State.RESUMED);

        scenario1.onActivity(activity1 -> {
            List<DayPlannerItem> beforeDayPlannerList2 = dayPlannerDao.getAll();
            ListView  list = activity1.findViewById(R.id.listview);
            int position = 0;
//works
            list.performItemClick(list.getChildAt(position),position,list.getItemIdAtPosition(position));
            List<DayPlannerItem> afterDayPlannerList2 = dayPlannerDao.getAll();
            //comment
            assertEquals(beforeDayPlannerList2.size(), afterDayPlannerList2.size());
        });
//comment
    }


@Test
public void testCountItem(){
    ActivityScenario<DayPlanner> scenario
            = ActivityScenario.launch(DayPlanner.class);
    scenario.moveToState(Lifecycle.State.CREATED);
    scenario.moveToState(Lifecycle.State.STARTED);
    scenario.moveToState(Lifecycle.State.RESUMED);

    scenario.onActivity(activity -> {
        List<DayPlannerItem> DayPlannerList = dayPlannerDao.getAll();

        TextView count = activity.findViewById(R.id.count_items);

        assertEquals(String.valueOf(DayPlannerList.size()), count.getText());
    });

}


}
