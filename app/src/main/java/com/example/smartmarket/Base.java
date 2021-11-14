package com.example.smartmarket;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.main.MarketApp;

public class Base extends AppCompatActivity {

    public MarketApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (MarketApp) getApplication();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
