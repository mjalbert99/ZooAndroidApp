package com.example.myapplication;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.ContentInfo;
import android.view.View;
import android.widget.Button;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CompactDPList {
    public static Dialog dialog;
    private  DayPlannerViewModel viewModel;
    private Button backBtn;



    public void showDialog(Activity activity, ViewModelStoreOwner owner, LifecycleOwner lifecycleOwner, Context context) {
        dialog = new Dialog(activity);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.compactlist);

        this.backBtn = dialog.findViewById(R.id.Cbutton);
        this.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        viewModel = new ViewModelProvider(owner)
                .get(DayPlannerViewModel.class);

        DayPlannerAdapter adapter = new DayPlannerAdapter();
        adapter.setOnDeleteClickedHandler(viewModel::deleteItem);
        viewModel.getDayPlannerList().observe(lifecycleOwner, adapter::setDayPlannerListItem);

        RecyclerView recyclerView = dialog.findViewById(R.id.dialog_recycler_view);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        dialog.show();
    }
}