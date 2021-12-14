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

import com.example.adapter.CategoryAdapter;
import com.example.adapter.PopularAdapter;
import com.example.api.CategoryApi;
import com.example.api.ItemsApi;
import com.example.models.Cart;
import com.example.models.Category;
import com.example.models.Items;
import com.example.smartmarket.Base;
import com.example.smartmarket.Profile.UserProfile;
import com.example.smartmarket.R;
import com.example.smartmarket.cart.CartActivity;
import com.example.smartmarket.scan.ScanActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

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
        new GetAllCategoryTask(this).execute("category/api/categories");
        new GetPopularItemsTask(this).execute("items/api/items/popular");
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
                        startActivity(new Intent(DashboardActivity.this, UserProfile.class));
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


    // Category Task
    private class GetAllCategoryTask extends AsyncTask<String, Void, ArrayList<Category>> {
        protected ProgressDialog dialog;
        protected Context context;

        public GetAllCategoryTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog = new ProgressDialog(context, 1);
            this.dialog.setMessage("Retrieving Category List");
            this.dialog.show();
        }

        @Override
        protected ArrayList<Category> doInBackground(String... params) {
            try {
                Log.v("shop", "Market App Getting All Category");
                return (ArrayList<Category>) CategoryApi.getAll((String) params[0]);
            } catch (Exception e) {
                Log.v("shop", "ERROR: " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Category> result) {
            super.onPostExecute(result);

            // use result here
            app.categories = result;
            categoryAdapter = new CategoryAdapter(app.categories);
            recyclerViewCategory.setAdapter(categoryAdapter);


            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }


    // Item Task
    private class GetPopularItemsTask extends AsyncTask<String, Void, ArrayList<Items>> {
        protected Context context;

        public GetPopularItemsTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Items> doInBackground(String... params) {
            try {
                Log.v("shop", "Market App Getting 10 Popular Items");
                return (ArrayList<Items>) ItemsApi.getAll((String) params[0]);
            } catch (Exception e) {
                Log.v("shop", "ERROR: " + e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Items> result) {
            super.onPostExecute(result);

            // use result here
            app.itemsPopular = result;
            popularAdapter = new PopularAdapter(app.itemsPopular);
            recyclerViewPopular.setAdapter(popularAdapter);
        }
    }

}

