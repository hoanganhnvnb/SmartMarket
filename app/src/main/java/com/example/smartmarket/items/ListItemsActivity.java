package com.example.smartmarket.items;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.adapter.ListItemsAdapter;
import com.example.api.ApiService;
import com.example.models.Items;
import com.example.smartmarket.Base;
import com.example.smartmarket.Profile.Profile;
import com.example.smartmarket.Profile.UserList;
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
                Intent i = new Intent( ListItemsActivity.this, Profile.class);
                startActivity(i);
            }
        });
        CreateRVItems();
    }

    private void CreateRVItems() {
        LinearLayoutManager linearLayoutManagerPop =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewItem = (RecyclerView) findViewById(R.id.list_view_items);
        recyclerViewItem.setLayoutManager(linearLayoutManagerPop);

        ApiService.apiService.getAllItemsApi().enqueue(new Callback<ArrayList<Items>>() {
            @Override
            public void onResponse(Call<ArrayList<Items>> call, Response<ArrayList<Items>> response) {
                if (response.body() != null) {
                    app.items = response.body();
                    listItemsAdapter = new ListItemsAdapter(app.items);
                    recyclerViewItem.setAdapter(listItemsAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Items>> call, Throwable t) {

            }
        });
    }
}