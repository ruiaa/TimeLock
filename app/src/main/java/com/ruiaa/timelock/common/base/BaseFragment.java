package com.ruiaa.timelock.common.base;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;

/**
 * Created by ruiaa on 2016/9/29.
 */

public class BaseFragment extends Fragment {
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    private FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
        fragmentManager=getFragmentManager();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    public void openNewFragment(Fragment thisFragment, Fragment newFragment, @IdRes int id) {
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.detach(thisFragment);
        fragmentTransaction.add(id, newFragment);
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

    public boolean fragmentTurnBack() {
        if(fragmentManager.getBackStackEntryCount()!=0){
            fragmentManager.popBackStack();
            return true;
        }else {
            return false;
        }
    }

}
