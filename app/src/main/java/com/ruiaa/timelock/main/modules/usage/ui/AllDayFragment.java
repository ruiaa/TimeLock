package com.ruiaa.timelock.main.modules.usage.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruiaa.timelock.R;
import com.ruiaa.timelock.common.base.BaseFragment;
import com.ruiaa.timelock.main.modules.usage.viewmodel.AllDayModel;


public class AllDayFragment extends BaseFragment {

    private AllDayModel allDayModel;

    public AllDayFragment() {
        // Required empty public constructor
    }

    public static AllDayFragment newInstance() {
        AllDayFragment fragment = new AllDayFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()==null){
            allDayModel=new AllDayModel(getActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_all_day, container, false);
    }

    @Override
    public void onDestroy() {
        allDayModel.destroy();
        super.onDestroy();
    }
}
