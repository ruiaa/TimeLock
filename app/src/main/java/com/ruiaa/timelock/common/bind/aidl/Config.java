package com.ruiaa.timelock.common.bind.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ruiaa on 2016/9/30.
 */

public class Config implements Parcelable {

    private boolean bootCompleted = true;
    private String todayDate = "0";
    private float monitorInterval = 0.1f;
    private int interceptInterval = 60;
    private String launcherPkg = "0";
    private boolean allLockOpen = true;

    public Config(){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bootCompleted ? 1 : 0);
        dest.writeString(todayDate);
        dest.writeFloat(monitorInterval);
        dest.writeInt(interceptInterval);
        dest.writeString(launcherPkg);
        dest.writeInt(allLockOpen ? 1 : 0);
    }

    public static final Parcelable.Creator<Config> CREATOR = new Creator<Config>() {

        @Override
        public Config createFromParcel(Parcel source) {
            Config config=new Config();
            config.bootCompleted=source.readInt()==1?true:false;
            config.todayDate=source.readString();
            config.monitorInterval=source.readFloat();
            config.interceptInterval=source.readInt();
            config.launcherPkg=source.readString();
            config.allLockOpen=source.readInt()==1?true:false;
            return config;
        }

        @Override
        public Config[] newArray(int size) {
            return new Config[size];
        }
    };
}
