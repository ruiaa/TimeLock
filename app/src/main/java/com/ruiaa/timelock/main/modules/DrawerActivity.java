package com.ruiaa.timelock.main.modules;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.ruiaa.timelock.R;
import com.ruiaa.timelock.common.base.App;
import com.ruiaa.timelock.common.base.BaseActivity;
import com.ruiaa.timelock.common.utils.DoubleClickExit;
import com.ruiaa.timelock.common.utils.ResUtil;
import com.ruiaa.timelock.common.utils.ToastUtil;
import com.ruiaa.timelock.main.modules.about.ui.AboutActivity;
import com.ruiaa.timelock.main.modules.lock.ui.LockActivity;
import com.ruiaa.timelock.main.modules.setting.ui.SettingActivity;
import com.ruiaa.timelock.main.modules.usage.ui.UsageActivity;

/**
 * Created by ruiaa on 2016/10/2.
 */

public class DrawerActivity extends BaseActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getView();
        initDrawer();
        initToolbar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void getView(){
        navigationView=(NavigationView)findViewById(R.id.nav_main);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_main);
        toolbar=(Toolbar)findViewById(R.id.toolbar_main);
    }

    private void initDrawer() {
        navigationView.setNavigationItemSelectedListener((item -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            Intent intent;
            switch (item.getItemId()) {
                case R.id.drawer_lock: {
                    if (DrawerActivity.this instanceof LockActivity){
                        return false;
                    }
                    intent = new Intent(App.getAppContext(), LockActivity.class);
                    break;
                }
                case R.id.drawer_usage: {
                    if (DrawerActivity.this instanceof UsageActivity){
                        return false;
                    }
                    intent = new Intent(App.getAppContext(), UsageActivity.class);
                    break;
                }
                case R.id.drawer_setting: {
                    if (DrawerActivity.this instanceof SettingActivity){
                        return false;
                    }
                    intent = new Intent(App.getAppContext(), SettingActivity.class);
                    break;
                }
                case R.id.drawer_about: {
                    if (DrawerActivity.this instanceof AboutActivity){
                        return false;
                    }
                    intent = new Intent(App.getAppContext(), AboutActivity.class);
                    break;
                }
                default: {
                    return false;
                }
            }
            startActivity(intent);

            finish();

            return true;
        }));
    }

    private void initToolbar(){
        setToolbarTitle(toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void setToolbarTitle(Toolbar toolbar){
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (fragmentTurnBack()) {
            //成功返回上个界面
        } else if (!DoubleClickExit.check()) {
            ToastUtil.showShort(ResUtil.getString(R.string.toast_exit));
        } else {
            finish();
        }
    }

    public boolean fragmentTurnBack() {
        return false;
    }
}
