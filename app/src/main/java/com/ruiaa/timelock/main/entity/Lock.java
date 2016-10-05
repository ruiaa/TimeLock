package com.ruiaa.timelock.main.entity;

import android.content.ContentValues;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.ruiaa.timelock.BR;
import com.ruiaa.timelock.common.consts.AppCode;
import com.ruiaa.timelock.common.consts.SqlField;


/**
 * Created by ruiaa on 2016/9/30.
 */

public class Lock extends BaseObservable {

    private String packageName;
    private int order=-1;

    private int startTime=0;
    private int finishTime=0;
    private int type= AppCode.LOCK_TYPE_FORBIDDEN;

    private boolean monRepeat=false;
    private boolean tueRepeat=false;
    private boolean wedRepeat=false;
    private boolean thuRepeat=false;
    private boolean friRepeat=false;
    private boolean satRepeat=false;
    private boolean sunRepeat=false;


    //新建
    public Lock(String packageName){
        this.packageName=packageName;
    }

    //数据库读取
    public Lock(String packageName,int order){
        this.packageName=packageName;
        this.order=order;
    }

    //
    //
    public ContentValues toContentValues(Lock lock){
        boolean weekRepeat=lock.getMonRepeat()||lock.getTueRepeat()||lock.getWedRepeat()
                ||lock.getThuRepeat() ||lock.getFriRepeat() ||lock.getSatRepeat()||lock.getSunRepeat();

        ContentValues contentValues;
        contentValues = new ContentValues();
        contentValues.put(SqlField.PACKAGE, lock.getPackageName());

        contentValues.put(SqlField.LOCK_TYPE, lock.getType());
        contentValues.put(SqlField.LOCK_START_TIME, lock.getStartTime());
        contentValues.put(SqlField.LOCK_FINISH_TIME, lock.getFinishTime());

        contentValues.put(SqlField.LOCK_REPEAT, weekRepeat);
        contentValues.put(SqlField.REPEAT_MONDAY, lock.getMonRepeat());
        contentValues.put(SqlField.REPEAT_TUESDAY, lock.getTueRepeat());
        contentValues.put(SqlField.REPEAT_WEDNESDAY, lock.getWedRepeat());
        contentValues.put(SqlField.REPEAT_THURSDAY, lock.getThuRepeat());
        contentValues.put(SqlField.REPEAT_FRIDAY, lock.getFriRepeat());
        contentValues.put(SqlField.REPEAT_SATURDAY, lock.getSatRepeat());
        contentValues.put(SqlField.REPEAT_SUNDAY, lock.getSunRepeat());

        return contentValues;
    }

    public void copySettingTo(Lock target){
        target.setStartTime(startTime);
        target.setFinishTime(finishTime);
        target.setType(type);
        target.setMonRepeat(monRepeat);
        target.setTueRepeat(tueRepeat);
        target.setWedRepeat(wedRepeat);
        target.setThuRepeat(thuRepeat);
        target.setFriRepeat(friRepeat);
        target.setSatRepeat(satRepeat);
        target.setSunRepeat(sunRepeat);
    }

    //
    //

    public String getPackageName() {
        return packageName;
    }

    public int getOrder() {
        return order;
    }

    @Bindable
    public int getStartTime() {
        return startTime;
    }

    public Lock setStartTime(int startTime) {
        this.startTime = startTime;
        notifyPropertyChanged(BR.startTime);
        return this;
    }

    @Bindable
    public int getFinishTime() {
        return finishTime;
    }

    public Lock setFinishTime(int finishTime) {
        this.finishTime = finishTime;
        notifyPropertyChanged(BR.finishTime);
        return this;
    }

    @Bindable
    public int getType() {
        return type;
    }

    public Lock setType(int type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
        return this;
    }

    @Bindable
    public boolean getMonRepeat() {
        return monRepeat;
    }

    public Lock setMonRepeat(boolean monRepeat) {
        this.monRepeat = monRepeat;
        notifyPropertyChanged(BR.monRepeat);
        return this;
    }

    @Bindable
    public boolean getTueRepeat() {
        return tueRepeat;
    }

    public Lock setTueRepeat(boolean tueRepeat) {
        this.tueRepeat = tueRepeat;
        notifyPropertyChanged(BR.tueRepeat);
        return this;
    }

    @Bindable
    public boolean getWedRepeat() {
        return wedRepeat;
    }

    public Lock setWedRepeat(boolean wedRepeat) {
        this.wedRepeat = wedRepeat;
        notifyPropertyChanged(BR.wedRepeat);
        return this;
    }

    @Bindable
    public boolean getThuRepeat() {
        return thuRepeat;
    }

    public Lock setThuRepeat(boolean thuRepeat) {
        this.thuRepeat = thuRepeat;
        notifyPropertyChanged(BR.thuRepeat);
        return this;
    }

    @Bindable
    public boolean getFriRepeat() {
        return friRepeat;
    }

    public Lock setFriRepeat(boolean friRepeat) {
        this.friRepeat = friRepeat;
        notifyPropertyChanged(BR.friRepeat);
        return this;
    }

    @Bindable
    public boolean getSatRepeat() {
        return satRepeat;
    }

    public Lock setSatRepeat(boolean satRepeat) {
        this.satRepeat = satRepeat;
        notifyPropertyChanged(BR.satRepeat);
        return this;
    }

    @Bindable
    public boolean getSunRepeat() {
        return sunRepeat;
    }

    public Lock setSunRepeat(boolean sunRepeat) {
        this.sunRepeat = sunRepeat;
        notifyPropertyChanged(BR.sunRepeat);
        return this;
    }

    public boolean getWeekRepeat(){
        return monRepeat||tueRepeat||wedRepeat||thuRepeat||friRepeat||satRepeat||sunRepeat;
    }

}
