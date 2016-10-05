package com.ruiaa.timelock.main.modules.lock.ui;

import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;

import com.ruiaa.timelock.R;
import com.ruiaa.timelock.common.utils.AppUtils;
import com.ruiaa.timelock.common.utils.LogUtil;
import com.ruiaa.timelock.common.utils.ResUtil;
import com.ruiaa.timelock.main.modules.DrawerActivity;

public class LockActivity extends DrawerActivity {


    AppFragment appFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null){
            appFragment=AppFragment.newInstance();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_main,appFragment);
            transaction.commit();
        }

        setToolbarTitle(ResUtil.getString(R.string.lock));

        if (Build.VERSION.SDK_INT>=21){
            AppUtils.getUsagePermission(this);
        }
        LogUtil.i("onCreate#");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
