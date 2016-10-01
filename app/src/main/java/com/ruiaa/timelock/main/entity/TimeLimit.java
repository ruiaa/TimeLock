package com.ruiaa.timelock.main.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.ruiaa.timelock.BR;


/**
 * Created by ruiaa on 2016/9/30.
 */

public class TimeLimit extends BaseObservable {

    public static final int A_DAY=86400;//24*60*60

    private String packageName;

    private boolean limitState=true;
    private int monLimit=A_DAY;
    private int tueLimit=A_DAY;
    private int wedLimit=A_DAY;
    private int thuLimit=A_DAY;
    private int friLimit=A_DAY;
    private int satLimit=A_DAY;
    private int sunLimit=A_DAY;

    public TimeLimit(String packageName){
        this.packageName=packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    @Bindable
    public boolean getLimitState() {
        return limitState;
    }

    public TimeLimit setLimitState(boolean limitState) {
        this.limitState = limitState;
        notifyPropertyChanged(BR.limitState);
        return this;
    }

    @Bindable
    public int getMonLimit() {
        return monLimit;
    }

    public TimeLimit setMonLimit(int monLimit) {
        this.monLimit = monLimit;
        notifyPropertyChanged(BR.monLimit);
        return this;
    }

    @Bindable
    public int getTueLimit() {
        return tueLimit;
    }

    public TimeLimit setTueLimit(int tueLimit) {
        this.tueLimit = tueLimit;
        notifyPropertyChanged(BR.tueLimit);
        return this;
    }

    @Bindable
    public int getWedLimit() {
        return wedLimit;
    }

    public TimeLimit setWedLimit(int wedLimit) {
        this.wedLimit = wedLimit;
        notifyPropertyChanged(BR.wedLimit);
        return this;
    }

    @Bindable
    public int getThuLimit() {
        return thuLimit;
    }

    public TimeLimit setThuLimit(int thuLimit) {
        this.thuLimit = thuLimit;
        notifyPropertyChanged(BR.thuLimit);
        return this;
    }

    @Bindable
    public int getFriLimit() {
        return friLimit;
    }

    public TimeLimit setFriLimit(int friLimit) {
        this.friLimit = friLimit;
        notifyPropertyChanged(BR.friLimit);
        return this;
    }

    @Bindable
    public int getSatLimit() {
        return satLimit;
    }

    public TimeLimit setSatLimit(int satLimit) {
        this.satLimit = satLimit;
        notifyPropertyChanged(BR.satLimit);
        return this;
    }

    @Bindable
    public int getSunLimit() {
        return sunLimit;
    }

    public TimeLimit setSunLimit(int sunLimit) {
        this.sunLimit = sunLimit;
        notifyPropertyChanged(BR.sunLimit);
        return this;
    }
}
