// AIDLMainMonitor.aidl
package com.ruiaa.timelock.common.bind.aidl;

import com.ruiaa.timelock.common.bind.aidl.Config;

// Declare any non-default types here with import statements

interface AIDLMainMonitor {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
  /*  void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);*/

    boolean saveConfig(in Config config);

    Config getConfig();
}
