package com.example.smartmarket.Search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.adapter.PopularAdapter;
import com.example.adapter.SearchItemAdapter;
import com.example.api.ApiService;
import com.example.models.Items;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.example.smartmarket.dashboard.DashboardActivity;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends Base {
    public RecyclerView.Adapter searchItemsAdapter;
    public RecyclerView recyclerViewItems;

    public RecyclerView.Adapter searchCatAdapter;
    public RecyclerView recyclerViewCat;

    public RecyclerView.Adapter searchComAdapter;
    public RecyclerView recyclerViewCom;

    public TextView search_text_head;
    public Button searchback;
    String search_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        search_text = intent.getStringExtra("search_text");

        search_text_head = findViewById(R.id.search_text_head);
        search_text_head.setText("Kết quả cho: " + search_text);

        searchback = findViewById(R.id.searchback);
        searchback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        LinearLayoutManager linearLayoutManagerItems =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewItems = (RecyclerView) findViewById(R.id.search_items_rv);
        recyclerViewItems.setLayoutManager(linearLayoutManagerItems);

        LinearLayoutManager linearLayoutManagerCat =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewCat = (RecyclerView) findViewById(R.id.search_category_rv);
        recyclerViewCat.setLayoutManager(linearLayoutManagerCat);

        LinearLayoutManager linearLayoutManagerCom =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewCom = (RecyclerView) findViewById(R.id.search_company_rv);
        recyclerViewCom.setLayoutManager(linearLayoutManagerCom);

    }

    @Override
    protected void onResume() {
        super.onResume();

        ApiService.apiService.searchItemByItemTitle(search_text).enqueue(new Callback<ArrayList<Items>>() {
            @Override
            public void onResponse(Call<ArrayList<Items>> call, Response<ArrayList<Items>> response) {
                ArrayList<Items> items = response.body();
                if (items != null) {
                    searchItemsAdapter = new SearchItemAdapter(SearchActivity.this, items);
                    recyclerViewItems.setAdapter(searchItemsAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Items>> call, Throwable t) {

            }
        });

        ApiService.apiService.searchItemByCategoryTitle(search_text).enqueue(new Callback<ArrayList<Items>>() {
            @Override
            public void onResponse(Call<ArrayList<Items>> call, Response<ArrayList<Items>> response) {
                ArrayList<Items> items = response.body();
                if (items != null) {
                    searchCatAdapter = new SearchItemAdapter(SearchActivity.this, items);
                    recyclerViewCat.setAdapter(searchCatAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Items>> call, Throwable t) {

            }
        });


        ApiService.apiService.searchItemByCompany(search_text).enqueue(new Callback<ArrayList<Items>>() {
            @Override
            public void onResponse(Call<ArrayList<Items>> call, Response<ArrayList<Items>> response) {
                ArrayList<Items> items = response.body();
                if (items != null) {
                    searchComAdapter = new SearchItemAdapter(SearchActivity.this, items);
                    recyclerViewCom.setAdapter(searchComAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Items>> call, Throwable t) {

            }
        });
    }
}