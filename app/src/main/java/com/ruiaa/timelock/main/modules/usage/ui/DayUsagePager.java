package com.ruiaa.timelock.main.modules.usage.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruiaa.timelock.R;
import com.ruiaa.timelock.common.base.BaseFragment;


public class DayUsagePager extends BaseFragment {

    private static final String DATE_CODE="date";

    public DayUsagePager() {
        // Required empty public constructor
    }

    public static DayUsagePager newInstance(int date) {
        DayUsagePager fragment = new DayUsagePager();
        Bundle bundle=new Bundle();
        bundle.putInt(DATE_CODE,date);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            int date=getArguments().getInt(DATE_CODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.pager_day_usage, container, false);
    }

}
