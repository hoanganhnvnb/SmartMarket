package com.example.smartmarket.items;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.ListItemCatAdapter;
import com.example.api.ApiService;
import com.example.models.Category;
import com.example.models.Items;
import com.example.smartmarket.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemsByCategoryActivity extends AppCompatActivity {

    RecyclerView.Adapter adapter;
    RecyclerView list_cat_rv;
    ArrayList<Items> items;

    TextView text_empty_list;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_by_category);

        button = (Button)findViewById(R.id.list_catBack);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        list_cat_rv = findViewById(R.id.list_cat_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        list_cat_rv.setLayoutManager(linearLayoutManager);

        text_empty_list = findViewById(R.id.text_empty_list);

        getBundleCat();
    }

    private void getBundleCat() {

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        Category category = (Category) bundle.get("object_cat");

        ApiService.apiService.getItemByCategory(category.id).enqueue(new Callback<ArrayList<Items>>() {
            @Override
            public void onResponse(Call<ArrayList<Items>> call, Response<ArrayList<Items>> response) {
                items = response.body();
                if (items != null) {
                    adapter = new ListItemCatAdapter(ItemsByCategoryActivity.this, items);
                    list_cat_rv.setAdapter(adapter);
                    if (items != null) {
                        list_cat_rv.setVisibility(View.VISIBLE);
                        text_empty_list.setVisibility(View.GONE);
                    } else {
                        list_cat_rv.setVisibility(View.GONE);
                        text_empty_list.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Items>> call, Throwable t) {

            }
        });

    }
}