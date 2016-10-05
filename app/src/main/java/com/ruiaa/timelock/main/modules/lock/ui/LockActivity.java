package com.ruiaa.timelock.main.modules.lock.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ruiaa.timelock.R;
import com.ruiaa.timelock.common.utils.AppUtils;
import com.ruiaa.timelock.common.utils.LogUtil;
import com.ruiaa.timelock.main.modules.DrawerActivity;

public class LockActivity extends DrawerActivity {

    FragmentManager fragmentManager;
    AppFragment appFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager=this.getFragmentManager();
        if (savedInstanceState==null){
            appFragment=AppFragment.newInstance();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.add(R.id.fragment_main,appFragment);
            transaction.commit();
        }

        if (Build.VERSION.SDK_INT>=21){
            AppUtils.getUsagePermission(this);
        }
        LogUtil.i("onCreate#");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setToolbarTitle(Toolbar toolbar) {
        super.setToolbarTitle(toolbar);
        toolbar.setTitle(R.string.lock);
    }


    public void openFragment(Fragment thisFragment,Fragment newFragment) {
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.detach(thisFragment);
        fragmentTransaction.add(R.id.fragment_main, newFragment);
        //???fragmentTransaction.replace(R.id.fragment_main,newFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    public void reShow(Fragment fragment) {
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.detach(fragment);
        fragmentTransaction.attach(fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean fragmentTurnBack() {
        if(fragmentManager.getBackStackEntryCount()!=0){
            fragmentManager.popBackStack();
            return true;
        }else {
            return false;
        }
    }
}
