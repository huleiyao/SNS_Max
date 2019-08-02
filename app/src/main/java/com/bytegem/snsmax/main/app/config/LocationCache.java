package com.bytegem.snsmax.main.app.config;

import android.os.Environment;

public class LocationCache {
    public static String LOCATION = Environment.getExternalStorageDirectory() + "/snsmax";
    public static String IMAGE_LOCATION = LOCATION + "/image";
    public static String VIDEO_LOCATION = LOCATION + "/video";
}
