package com.example.smartmarket.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.adapter.CartItemHistoryAdapter;
import com.example.adapter.CartItemOrderAdapter;
import com.example.api.ApiService;
import com.example.models.CartItems;
import com.example.models.Items;
import com.example.models.Order;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.example.smartmarket.cart.OrderActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryOrderDetailActivity extends Base {

    TextView order_name_user_detail, order_id_detail, order_is_complete_detail, order_date_detail, pay_method_detail, sum_order_detail;

    RecyclerView.Adapter adapter;
    RecyclerView order_rv_detail;

    Button order_backButton_detail;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order_detail);

        initView();
        getBundleOrder();
    }

    private void getBundleOrder() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        order = (Order) bundle.get("object_order");

        order_name_user_detail.setText(app.mUser.first_name + " " + app.mUser.last_name);

        order_id_detail.setText(String.valueOf(order.id));
        if (order.is_completed) {
            order_is_complete_detail.setText("Đã thanh toán");
        } else {
            order_is_complete_detail.setText("Chưa thanh toán");
        }

        ApiService.apiService.getCartItems(order.cart).enqueue(new Callback<ArrayList<CartItems>>() {
            @Override
            public void onResponse(Call<ArrayList<CartItems>> call, Response<ArrayList<CartItems>> response) {
                ArrayList<CartItems> cartItems = response.body();
                if (cartItems != null) {
                    adapter = new CartItemHistoryAdapter(HistoryOrderDetailActivity.this, cartItems);
                    order_rv_detail.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CartItems>> call, Throwable t) {

            }
        });
        sum_order_detail.setText(String.valueOf(order.order_total));
        order_date_detail.setText(convertDateTime(order.create_at));
    }

    private String convertDateTime(String date){
        String[] parts = date.split("T",2);
        return parts[0];
    }

    private void initView() {
        order_name_user_detail = findViewById(R.id.order_name_user_detail);
        order_id_detail = findViewById(R.id.order_id_detail);
        order_is_complete_detail = findViewById(R.id.order_is_complete_detail);
        order_date_detail = findViewById(R.id.order_date_detail);
        pay_method_detail = findViewById(R.id.pay_method_detail);
        sum_order_detail = findViewById(R.id.sum_order_detail);
        order_backButton_detail = findViewById(R.id.order_backButton_detail);

        order_backButton_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        order_rv_detail = findViewById(R.id.order_rv_detail);
        order_rv_detail.setLayoutManager(linearLayoutManager);
    }
}