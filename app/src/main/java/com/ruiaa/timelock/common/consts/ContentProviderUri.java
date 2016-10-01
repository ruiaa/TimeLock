package com.ruiaa.timelock.common.consts;

import android.net.Uri;

/**
 * Created by ruiaa on 2016/9/30.
 */
public final class ContentProviderUri {
    public static final String CONTENT="com.ruiaa.timelock.controller.DataProvider";
    public static final String CONTENT_URI="content://com.ruiaa.timelock.controller.DataProvider/";

    public static final String TABLE_LOCK="lock";
    public static final String TABLE_APP_INFO="appInfo";

    public static Uri getLockTableUri(){
        return getUri(TABLE_LOCK);
    }

    public static Uri getAppInfoTable(){
        return  getUri(TABLE_APP_INFO);
    }

    public static Uri getUri(String lastUri) {
        return Uri.parse(CONTENT_URI + lastUri);
    }
}
