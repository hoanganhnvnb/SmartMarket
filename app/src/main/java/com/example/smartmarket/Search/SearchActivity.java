package com.example.smartmarket.Search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import com.example.adapter.PopularAdapter;
import com.example.api.ApiService;
import com.example.models.Items;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.example.smartmarket.dashboard.DashboardActivity;

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

    public SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        String search_text = intent.getStringExtra("search_text");

        searchView = (SearchView) findViewById(R.id.searchView_main);
        searchView.setQueryHint(search_text);

        LinearLayoutManager linearLayoutManagerItems =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewItems = (RecyclerView) findViewById(R.id.search_items_rv);
        recyclerViewItems.setLayoutManager(linearLayoutManagerItems);

        LinearLayoutManager linearLayoutManagerCat =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewCat = (RecyclerView) findViewById(R.id.search_items_rv);
        recyclerViewCat.setLayoutManager(linearLayoutManagerItems);

        LinearLayoutManager linearLayoutManagerCom =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewCom = (RecyclerView) findViewById(R.id.search_items_rv);
        recyclerViewCom.setLayoutManager(linearLayoutManagerItems);

        searchItemsAdapter = new PopularAdapter(SearchActivity.this, app.items);
        recyclerViewItems.setAdapter(searchItemsAdapter);

        searchCatAdapter = new PopularAdapter(SearchActivity.this, app.items);
        recyclerViewCat.setAdapter(searchCatAdapter);

        searchComAdapter = new PopularAdapter(SearchActivity.this, app.items);
        recyclerViewCom.setAdapter(searchComAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}