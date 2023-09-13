package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchListViewAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private final List<DayPlannerItem> exhibitsList;
    private final ArrayList<DayPlannerItem> arrayList;
    List<DayPlannerItem> listJson;

    public SearchListViewAdapter(Context context, List<DayPlannerItem> exhibitsList){
        this.context = context;
        this.exhibitsList = exhibitsList;
        inflater = LayoutInflater.from(this.context);
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(exhibitsList);
        this.listJson = DayPlannerItem.loadJSON(context,"sample_node_info.json");
    }

    public static class ViewHolder{TextView name;}

    @Override
    public int getCount() {
        return exhibitsList.size();
    }

    @Override
    public Object getItem(int i) {
        return exhibitsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.search_view, null);
            holder.name = view.findViewById(R.id.name);
            view.setTag(holder);
        }
        else{
            holder= (ViewHolder) view.getTag();
        }
        holder.name.setText(exhibitsList.get(i).name);
        return view;
    }

    public void filter(String text){
        text = text.toLowerCase(Locale.getDefault());
        exhibitsList.clear();
        if(text.length() == 0){
            exhibitsList.addAll(arrayList);
        }
        else{
            for(DayPlannerItem x : listJson){
                String allTags = x.tags.toString();
                if((x.name.toLowerCase(Locale.getDefault()).contains(text) ||
                    allTags.contains(text) )&& x.kind.equals("exhibit")){
                    exhibitsList.add(x);
                    notifyDataSetChanged();
                }
               if(exhibitsList.size() == 0){
                    exhibitsList.clear();
                    notifyDataSetChanged();
                }
            }
        }
    }

    public int filterTest(){return exhibitsList.size();}
}
