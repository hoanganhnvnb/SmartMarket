package com.example.main;

import android.app.Application;
import android.util.Log;

import com.example.models.Category;
import com.example.models.Items;

import java.util.ArrayList;


public class MarketApp extends Application {

    public ArrayList<Category> categories = new ArrayList<>();
    public ArrayList<Items> itemsPopular = new ArrayList<>();

    @Override
    public void onCreate (){
        super.onCreate();
        Log.v("Market", "Marketapp created");
    }
}
