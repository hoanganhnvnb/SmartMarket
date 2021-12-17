package com.example.smartmarket.dashboard;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.CategoryAdapter;
import com.example.adapter.PopularAdapter;
import com.example.api.ApiService;
import com.example.models.Cart;
import com.example.models.Category;
import com.example.models.Items;
import com.example.models.User;
import com.example.smartmarket.Base;
import com.example.smartmarket.Profile.Profile;
import com.example.smartmarket.R;
import com.example.smartmarket.cart.CartActivity;
import com.example.smartmarket.scan.ScanActivity;
import com.example.smartmarket.scan.ScanToCartActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

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
    TextView dash_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dash_username = (TextView) findViewById(R.id.dash_username);

        createRVDash();

        createBottomNav();

    }

    private void createCart() {
        if (app.mCart != null) {
            if (app.mCart.active) {
                return;
            }
        }
        ApiService.apiService.getActiveCart().enqueue(new Callback<Cart>() {
            @Override
            public void onResponse(Call<Cart> call, Response<Cart> response) {
                Cart cart = response.body();
                if (cart != null) {
                    app.mCart = cart;
                }
            }

            @Override
            public void onFailure(Call<Cart> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        createCart();

        if (!app.itemsPopular.isEmpty()) {
            popularAdapter = new PopularAdapter(DashboardActivity.this, app.itemsPopular);
            recyclerViewPopular.setAdapter(popularAdapter);
        } else {
            ApiService.apiService.getPopularItemsApi().enqueue(new Callback<ArrayList<Items>>() {
                @Override
                public void onResponse(Call<ArrayList<Items>> call, Response<ArrayList<Items>> response) {
                    if (response.body() != null) {
                        app.itemsPopular = response.body();
                        popularAdapter = new PopularAdapter(DashboardActivity.this, app.itemsPopular);
                        recyclerViewPopular.setAdapter(popularAdapter);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Items>> call, Throwable t) {

                }
            });
        }
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

        if (!app.categories.isEmpty()) {
            categoryAdapter = new CategoryAdapter(app.categories);
            recyclerViewCategory.setAdapter(categoryAdapter);
        } else {
            ApiService.apiService.getAllCategoryApi().enqueue(new Callback<ArrayList<Category>>() {
                @Override
                public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                    if (response.body() != null) {
                        app.categories = response.body();
                        categoryAdapter = new CategoryAdapter(app.categories);
                        recyclerViewCategory.setAdapter(categoryAdapter);
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                    Toast.makeText(DashboardActivity.this, "Call Category Api ERROR", Toast.LENGTH_SHORT).show();
                }
            });
        }

        ApiService.apiService.getMyUser().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (user != null) {
                    app.mUser = user;
                    dash_username.setText("Chào " + user.username +"!");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void createBottomNav() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setBackground(null);
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
                if (app.mUser.is_superuser) {
                    startActivity(new Intent(DashboardActivity.this, ScanActivity.class));
                } else {
                    startActivity(new Intent(DashboardActivity.this, ScanToCartActivity.class));
                }
            }
        });
    }



}

