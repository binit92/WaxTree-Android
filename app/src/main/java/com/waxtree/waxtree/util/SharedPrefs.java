package com.waxtree.waxtree.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by inbkumar01 on 9/24/2017.
 */

public class SharedPrefs {

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = SharedPrefs.class.getCanonicalName();
    private static SharedPrefs preferences ;

    private SharedPrefs(Context context){
        sharedPreferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
    }

    public static SharedPrefs getInstance(Context context){
        if(preferences ==null){
            preferences = new SharedPrefs(context);
        }
        return preferences;
    }

    public void setProperty(String key, String value){
        Editor edit = sharedPreferences.edit();
        edit.putString(key,value);
        edit.apply();
    }

    public String getProperty(String key) {
        return sharedPreferences.getString(key, null);
    }
}
