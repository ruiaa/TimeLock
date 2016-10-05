package com.ruiaa.timelock.main.modules.usage.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruiaa.timelock.R;
import com.ruiaa.timelock.main.modules.usage.viewmodel.AllDayModel;

/**
 * Created by ruiaa on 2016/10/5.
 */

public class DayUsageView {

    private AllDayModel allDayModel;
    private int date;
    private View view;

    public DayUsageView(AllDayModel allDayModel, int date) {
        this.allDayModel = allDayModel;
        this.date = date;
    }

    public View getViewInit(ViewGroup container){
        view= LayoutInflater.from(allDayModel.getContext()).inflate(R.layout.pager_day_usage,container,false);
        RecyclerView recyclerView= (RecyclerView)view.findViewById(R.id.list_day_usage);
        recyclerView.setAdapter(allDayModel.getDayUsageAdapter(date));
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        return view;
    }

    public View getView() {
        return view;
    }
}
