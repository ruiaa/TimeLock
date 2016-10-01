package com.ruiaa.timelock.monitor.event;

/**
 * Created by ruiaa on 2016/9/30.
 */

public class ScreenChangeEvent {

    private boolean screenIsLight;
    public ScreenChangeEvent(boolean screenIsLight){
        this.screenIsLight=screenIsLight;
    }

    public boolean isScreenIsLight() {
        return screenIsLight;
    }
}
