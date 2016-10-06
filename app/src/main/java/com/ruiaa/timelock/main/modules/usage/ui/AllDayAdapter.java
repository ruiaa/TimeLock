package com.ruiaa.timelock.main.modules.usage.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruiaa.timelock.R;
import com.ruiaa.timelock.main.modules.usage.viewmodel.AllDayModel;

import java.util.List;

/**
 * Created by ruiaa on 2016/10/5.
 */

public class AllDayAdapter extends PagerAdapter {

    private AllDayFragment allDayFragment;
    private AllDayModel allDayModel;
    private List<Integer> dateList;

    private LayoutInflater mInflater;

    public AllDayAdapter(AllDayModel allDayModel, AllDayFragment allDayFragment) {
        this.allDayModel = allDayModel;
        this.allDayFragment = allDayFragment;
        dateList=allDayModel.getDateList();
        mInflater=LayoutInflater.from(allDayFragment.getActivity());
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewDataBinding binding=DataBindingUtil.inflate(mInflater, R.layout.pager_day_usage,container,true);
        RecyclerView recyclerView= (RecyclerView)binding.getRoot().findViewById(R.id.list_day_usage);
        recyclerView.setAdapter(new DayUsageAdapter(dateList.get(position),allDayModel,allDayFragment));
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        return binding;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(((ViewDataBinding)object).getRoot());
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {

    }

    @Override
    public int getCount() {
        return dateList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==((ViewDataBinding)object).getRoot();
    }
}
