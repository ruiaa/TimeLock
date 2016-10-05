package com.ruiaa.timelock.main.modules.usage.ui;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.ruiaa.timelock.R;
import com.ruiaa.timelock.common.utils.LogUtil;
import com.ruiaa.timelock.common.utils.ResUtil;
import com.ruiaa.timelock.main.modules.DrawerActivity;

public class UsageActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null){
            AllDayFragment allDayFragment= AllDayFragment.newInstance();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_main,allDayFragment);
            transaction.commit();
        }

        setToolbarTitle(ResUtil.getString(R.string.usage));

        LogUtil.i("onCreate--");
    }
}
