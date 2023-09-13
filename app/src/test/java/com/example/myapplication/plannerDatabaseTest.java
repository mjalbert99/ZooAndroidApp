package com.example.myapplication;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class plannerDatabaseTest {
    private DayPlannerDao dao;
    private PlannerDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, PlannerDatabase.class)
                .allowMainThreadQueries()
                .build();
        dao = db.dayPlannerDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }



    @Test
    public void testInsert() {
        DayPlannerItem item1 = new DayPlannerItem("loins", "exhibit", "Loins", null,
                Arrays.asList("lions",
                "cats",
                "mammal",
                "africa"));
        DayPlannerItem item2 = new DayPlannerItem("elephant_odyssey", "exhibit",
                "Elephant Odyssy",null,
                Arrays.asList( "elephant",
                        "mammal",
                        "africa"));
        long id1 = dao.insert(item1);
        long id2 = dao.insert(item2);

        assertNotEquals(id1, id2);

    }

    @Test
    public void testGet() {
        DayPlannerItem insertedItem =  new DayPlannerItem("loins", "exhibit", "Loins",
                null, Arrays.asList("lions",
                        "cats",
                        "mammal",
                        "africa"));
        long index = dao.insert(insertedItem);

        DayPlannerItem item = dao.get(index);
        assertEquals(index, item.index);
        assertEquals(insertedItem.id, item.id);
        assertEquals(insertedItem.name, item.name);
        assertEquals(insertedItem.kind, item.kind);
        assertEquals(insertedItem.tags, item.tags);
    }

    @Test
    public void testUpdate() {
        DayPlannerItem item = new DayPlannerItem("loins", "exhibit", "Loins",
                null, Arrays.asList("lions",
                        "cats",
                        "mammal",
                        "africa"));

        long index = dao.insert(item);
        item = dao.get(index);
        item.name = "elephant_odyssey";
        int itemsUpdated = dao.update(item);
        assertEquals(1, itemsUpdated);

        item = dao.get(index);
        assertNotNull(item);
        assertEquals("elephant_odyssey", item.name);
    }

    public void testDelete() {
        DayPlannerItem item = new DayPlannerItem("loins", "exhibit", "Loins",
                null, Arrays.asList("lions",
                        "cats",
                        "mammal",
                        "africa"));
        long index = dao.insert(item);

        item = dao.get(index);
        int itemsDeleted = dao.delete(item);
        assertEquals(1, itemsDeleted);
        assertNull(dao.get(index));
    }



}
