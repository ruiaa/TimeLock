package com.ruiaa.timelock.main.modules.about.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ruiaa.timelock.R;
import com.ruiaa.timelock.main.modules.DrawerActivity;

public class AboutActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setToolbarTitle(Toolbar toolbar) {
        super.setToolbarTitle(toolbar);
        toolbar.setTitle(R.string.about);
    }
}
