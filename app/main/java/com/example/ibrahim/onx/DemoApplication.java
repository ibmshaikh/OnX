package com.example.ibrahim.onx;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by ibrahim on 10/17/2017.
 */

public class DemoApplication extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();

        // the following line is important
        Fresco.initialize(this);
    }
}