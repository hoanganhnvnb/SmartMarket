package com.example.main;

import android.app.Application;
import android.util.Log;

public class MarketApp extends Application {

    @Override
    public void onCreate (){
        super.onCreate();
        Log.v("Market", "Marketapp created");
    }
}
