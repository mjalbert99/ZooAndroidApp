package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;


public class DayPlannerAdapter extends RecyclerView.Adapter<DayPlannerAdapter.ViewHolder> {

    private List<DayPlannerItem> dayPlannerItems = Collections.emptyList();
    private Consumer<DayPlannerItem> onDeleteClicked;



    public void setDayPlannerListItem(List<DayPlannerItem> newDayPlannerItem){
        this.dayPlannerItems.clear();
        this.dayPlannerItems = newDayPlannerItem;
        notifyDataSetChanged();
    }

    public void setOnDeleteClickedHandler(Consumer<DayPlannerItem> onDeleteClicked){
        this.onDeleteClicked = onDeleteClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.activity_day_planner, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setDayPlannerItem(dayPlannerItems.get(position));
    }

    @Override
    public int getItemCount() {
        return dayPlannerItems.size();
    }

//    @Override
//    public long getItemId(int position){return dayPlannerItems.get(position).id;}


    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;
        private DayPlannerItem dayPlannerItem;
        private final TextView addRemovebtn;
        private TextView count;

        private ViewHolder(@NonNull View itemView){
            super(itemView);
            this.textView = itemView.findViewById(R.id.animal);
            this.addRemovebtn = itemView.findViewById(R.id.add_remove);
            this.count = itemView.findViewById(R.id.count_items);



            this.addRemovebtn.setOnClickListener(view->{
                if(onDeleteClicked == null) return;
                onDeleteClicked.accept(dayPlannerItem);

            });
        }

        public DayPlannerItem getDayPlannerItem(){return dayPlannerItem;}

        public void setDayPlannerItem(DayPlannerItem dayPlannerItem){
            this.dayPlannerItem = dayPlannerItem;
            this.textView.setText(dayPlannerItem.name);

        }

    }

}