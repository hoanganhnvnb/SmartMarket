package com.example.smartmarket.Profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.OrderAdapter;
import com.example.api.ApiService;
import com.example.models.Order;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOrder extends Base {

    Button button;
    RecyclerView.Adapter adapter;
    RecyclerView order_list_rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);

        button = (Button)findViewById(R.id.btn_return_orderList);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }


        });

        initView();

    }

    private void initView() {
        order_list_rv = findViewById(R.id.order_list_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        order_list_rv.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ApiService.apiService.getHistoryOrder().enqueue(new Callback<ArrayList<Order>>() {
            @Override
            public void onResponse(Call<ArrayList<Order>> call, Response<ArrayList<Order>> response) {
                ArrayList<Order> orders = response.body();
                if (orders != null)
                {
                    app.historyOrder = orders;
                    adapter = new OrderAdapter(ListOrder.this,orders);
                    order_list_rv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Order>> call, Throwable t) {

            }
        });
    }


}