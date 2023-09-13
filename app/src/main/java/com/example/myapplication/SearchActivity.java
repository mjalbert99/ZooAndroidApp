package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.TypeConverters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


//Source: https://developer.android.com/guide/topics/search/search-dialog
@TypeConverters({Converters.class})
public class SearchActivity extends AppCompatActivity  implements SearchView.OnQueryTextListener {

    ListView list;
    SearchListViewAdapter adapter;
    SearchView editSearch;
    ArrayList<DayPlannerItem> arrayList = new ArrayList<>();
    List<DayPlannerItem> listJson;


    CompactDPList compactDPList = new CompactDPList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listJson = DayPlannerItem.loadJSON(this,"sample_node_info.json");
        list = findViewById(R.id.listview);

        for (DayPlannerItem s: listJson) {

            if(s.kind.equals("exhibit")) {
                arrayList.add(s);
            }

        }
        adapter = new SearchListViewAdapter(this,arrayList);
        list.setAdapter(adapter);
        editSearch= findViewById(R.id.search);
        editSearch.setOnQueryTextListener(this);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              //  Toast.makeText(SearchActivity.this,
             //           (CharSequence) arrayList.get(i).name + " added to day planner", Toast.LENGTH_LONG).show();
                insertItem(arrayList.get(i));

                SharedPreferences pref=getSharedPreferences("hold", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putInt("target",1);
                Log.d("holdInfo","set target search=1 ");
                edit.commit();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int btn = item.getItemId();

        if (btn == R.id.compactDayPlanner) {
            compactDPList.showDialog(SearchActivity.this, this,this,this);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filter(s);
        return false;
    }

    public void insertItem( DayPlannerItem dayPlannerItem){
        InsertItem insertItem = new InsertItem();
        insertItem.execute(dayPlannerItem);
    }

    class InsertItem extends AsyncTask<DayPlannerItem, Void,Void> {
        @Override
        protected Void doInBackground(DayPlannerItem... dayPlannerItems) {
            List<DayPlannerItem> items = PlannerDatabase.getSingleton(getApplicationContext())
                    .dayPlannerDao().getAll();
            for ( int i = 0; i < items.size(); i++) {
                if (items.get(i).name.equals(dayPlannerItems[0].name)){
                    return null;
                }
            }
            PlannerDatabase.getSingleton(getApplicationContext())
                    .dayPlannerDao()
                    .insert(dayPlannerItems[0]);
            return null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(this).edit();
        e.putString("last_activity", getClass().getSimpleName());
        e.commit();
    }
//
    public void onPause() {
        super.onPause();
        Log.d("last_Activity", "here2");
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(this).edit();
        e.putString("last_activity", "MainActivity");
        e.commit();
        finish();
//        Intent intent = new Intent(this,MainActivity.class);
    }


}



//Video on how to add item to database
// https://www.youtube.com/watch?v=VCyAlQvjCK4 (Insert using singleton pattern)