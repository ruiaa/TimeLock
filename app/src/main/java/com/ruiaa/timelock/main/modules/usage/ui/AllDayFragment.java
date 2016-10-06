package com.ruiaa.timelock.main.modules.usage.ui;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruiaa.timelock.R;
import com.ruiaa.timelock.common.base.BaseFragment;
import com.ruiaa.timelock.common.utils.DataConvert;
import com.ruiaa.timelock.common.utils.ResUtil;
import com.ruiaa.timelock.databinding.FragmentAllDayBinding;
import com.ruiaa.timelock.main.modules.usage.viewmodel.AllDayModel;


public class AllDayFragment extends BaseFragment {

    private AllDayModel allDayModel;
    private UsageActivity usageActivity;

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
        allDayModel=new AllDayModel(getActivity());
        usageActivity=(UsageActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentAllDayBinding fragmentAllDayBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_all_day,container,false);
        View view=fragmentAllDayBinding.getRoot();

        ViewPager viewPager=(ViewPager)view.findViewById(R.id.pager_all_day);
        viewPager.setAdapter(new AllDayAdapter(allDayModel,this));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                usageActivity.setToolbarTitle(ResUtil.getString(R.string.usage)+"  "+ DataConvert.date(allDayModel.getDateList().get(position)));
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }


    @Override
    public void onDestroy() {
        allDayModel.destroy();
        allDayModel=null;
        super.onDestroy();
    }
}
