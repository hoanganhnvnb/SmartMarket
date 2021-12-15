package com.example.smartmarket.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adapter.CategoryAdapter;
import com.example.adapter.PopularAdapter;
import com.example.api.ApiService;
import com.example.api.CategoryApi;
import com.example.api.ItemsApi;
import com.example.models.Cart;
import com.example.models.Category;
import com.example.models.Items;
import com.example.smartmarket.Base;
import com.example.smartmarket.Profile.Profile;
import com.example.smartmarket.Profile.UserProfile;
import com.example.smartmarket.R;
import com.example.smartmarket.cart.CartActivity;
import com.example.smartmarket.scan.ScanActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends Base {

    public RecyclerView.Adapter categoryAdapter;
    public RecyclerView recyclerViewCategory;

    public RecyclerView.Adapter popularAdapter;
    public RecyclerView recyclerViewPopular;

    public static ArrayList<Cart> cartArrayList;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton scanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        createRVDash();

        createBottomNav();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void createRVDash() {
        LinearLayoutManager linearLayoutManagerCat =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewCategory = (RecyclerView) findViewById(R.id.dash_category_rv);
        recyclerViewCategory.setLayoutManager(linearLayoutManagerCat);

        LinearLayoutManager linearLayoutManagerPop =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewPopular = (RecyclerView) findViewById(R.id.dash_popular_rv);
        recyclerViewPopular.setLayoutManager(linearLayoutManagerPop);

        ApiService.apiService.getAllCategoryApi().enqueue(new Callback<ArrayList<Category>>() {
            @Override
            public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                Toast.makeText(DashboardActivity.this, "Call Category Api Succeed", Toast.LENGTH_SHORT);

                if (response.body() != null) {
                    app.categories = response.body();
                    categoryAdapter = new CategoryAdapter(app.categories);
                    recyclerViewCategory.setAdapter(categoryAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Call Category Api ERROR", Toast.LENGTH_SHORT);
            }
        });

        ApiService.apiService.getPopularItemsApi().enqueue(new Callback<ArrayList<Items>>() {
            @Override
            public void onResponse(Call<ArrayList<Items>> call, Response<ArrayList<Items>> response) {
                if (response.body() != null) {
                    app.itemsPopular = response.body();
                    popularAdapter = new PopularAdapter(app.itemsPopular);
                    recyclerViewPopular.setAdapter(popularAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Items>> call, Throwable t) {

            }
        });
    }

    private void createBottomNav() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setBackground(null);
        Mapping();
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
                        startActivity(new Intent(DashboardActivity.this, Profile.class));
                        break;
                }
                return true;
            }
        });

        scanButton = (FloatingActionButton) findViewById(R.id.dash_scan_btn);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, ScanActivity.class));
            }
        });
    }

    private void Mapping() {
        if(cartArrayList != null) {

        } else {
            cartArrayList = new ArrayList<>();
        }
    }
}

