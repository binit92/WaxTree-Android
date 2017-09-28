package com.waxtree.waxtree.data;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by inbkumar01 on 9/28/2017.
 */

public class WaxTreeApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /*Enable disk persistence */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
