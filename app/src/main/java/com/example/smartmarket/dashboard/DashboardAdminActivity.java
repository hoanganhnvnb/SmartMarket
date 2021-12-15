package com.example.smartmarket.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.smartmarket.R;
import com.example.smartmarket.scan.ScanActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class DashboardAdminActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FloatingActionButton scanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard_admin);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view_admin);
        bottomNavigationView.setBackground(null);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home_admin:
                        System.out.println("Home");
                        break;
                    case R.id.menu_notification_admin:
                        System.out.println("Notification");
                        break;
                    case R.id.menu_list_item:
                        System.out.println("Cart");
                        break;
                    case R.id.menu_user_admin:
                        System.out.println("User");
                        break;
                }
                return true;
            }
        });

        scanButton = (FloatingActionButton) findViewById(R.id.dash_scan_btn_admin);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardAdminActivity.this, ScanActivity.class));
//                System.out.println("Home");
            }
        });
    }
}