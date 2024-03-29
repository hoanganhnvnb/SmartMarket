package com.example.smartmarket.items;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.ListItemsAdapter;
import com.example.api.ApiService;
import com.example.models.Items;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListItemsActivity extends Base {

    public RecyclerView.Adapter listItemsAdapter;
    public RecyclerView recyclerViewItem;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        button = (Button)findViewById(R.id.btn_return_userList);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        CreateRVItems();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ApiService.apiService.getAllItemsApi().enqueue(new Callback<ArrayList<Items>>() {
            @Override
            public void onResponse(Call<ArrayList<Items>> call, Response<ArrayList<Items>> response) {
                if (response.body() != null) {
                    app.items = response.body();
                    listItemsAdapter = new ListItemsAdapter(ListItemsActivity.this ,app.items);
                    recyclerViewItem.setAdapter(listItemsAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Items>> call, Throwable t) {

            }
        });
    }

    private void CreateRVItems() {
        LinearLayoutManager linearLayoutManagerPop =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewItem = (RecyclerView) findViewById(R.id.list_view_items);
        recyclerViewItem.setLayoutManager(linearLayoutManagerPop);
    }
}