package com.ruiaa.timelock.main.modules.usage.viewmodel;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.ruiaa.timelock.common.utils.LogUtil;
import com.ruiaa.timelock.main.modules.usage.ui.DayUsageView;

import java.util.List;

/**
 * Created by ruiaa on 2016/10/5.
 */

public class AllDayPagerAdapter extends PagerAdapter {

    List<DayUsageView> viewList;

    public AllDayPagerAdapter(List<DayUsageView> viewList) {
        this.viewList = viewList;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position).getViewInit(container));
        LogUtil.i("instantiateItem--");
        return viewList.get(position).getView();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position).getView());
        LogUtil.i("destroyItem--");
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
