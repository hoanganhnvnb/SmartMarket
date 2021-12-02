package com.example.smartmarket.dashboard;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.models.Cart;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.example.smartmarket.cart.CartActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class DashboardActivity extends Base {

    public static ArrayList<Cart> cartArrayList;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton scanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setBackground(null);
        Anhxa();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        System.out.println("Home");
                        break;
                    case R.id.menu_notification:
                        System.out.println("Notification");
                        break;
                    case R.id.menu_cart:
                        System.out.println("Cart");
                        startActivity(new Intent(DashboardActivity.this, CartActivity.class));
                        break;
                    case R.id.menu_user:
                        System.out.println("User");
                        break;
                }
                return true;
            }
        });

        scanButton = (FloatingActionButton) findViewById(R.id.dash_scan_btn);
    }

    private void Anhxa() {
        if(cartArrayList != null) {

        } else {
            cartArrayList = new ArrayList<>();
        }
    }

    private void CartAddButton() {

    }

}

