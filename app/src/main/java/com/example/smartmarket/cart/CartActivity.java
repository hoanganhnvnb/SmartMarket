package com.example.smartmarket.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.adapter.CartItemAdapter;
import com.example.api.ApiService;
import com.example.models.CartItems;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends Base {

    RecyclerView.Adapter cartItemsAdapter;
    RecyclerView cartItemsList;
    TextView cart_total;
    Button cart_payButton, cart_backButton;
    NestedScrollView scrollView2;
    TextView text_empty_cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initView();
        initList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ApiService.apiService.getCartItems(app.mCart.id).enqueue(new Callback<ArrayList<CartItems>>() {
            @Override
            public void onResponse(Call<ArrayList<CartItems>> call, Response<ArrayList<CartItems>> response) {
                ArrayList<CartItems> cartItems = response.body();
                if (cartItems != null) {
                    app.cartItems = cartItems;
                    cartItemsAdapter = new CartItemAdapter(CartActivity.this, cartItems);
                    cartItemsList.setAdapter(cartItemsAdapter);
                    int total = 0;
                    for (int i = 0; i < app.cartItems.size(); i++) {
                        CartItems cartItem = app.cartItems.get(i);
                        total = total + cartItem.quantity * cartItem.items.sellPrice;
                    }
                    cart_total.setText(String.valueOf(total));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CartItems>> call, Throwable t) {

            }
        });
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cartItemsList.setLayoutManager(linearLayoutManager);
    }

    private void initView() {
        cartItemsList = (RecyclerView) findViewById(R.id.cart_rv);
        cart_total = (TextView) findViewById(R.id.cart_total);
        cart_payButton = (Button) findViewById(R.id.cart_payButton);
        cart_backButton = (Button) findViewById(R.id.cart_backButton);
        scrollView2 = (NestedScrollView) findViewById(R.id.scrollView2);
        text_empty_cart = findViewById(R.id.text_empty_cart);

        cart_payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickPay();
            }
        });


        cart_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (app.cartItems.isEmpty() || app.cartItems == null) {
            text_empty_cart.setVisibility(View.VISIBLE);
            cartItemsList.setVisibility(View.GONE);
        } else {
            text_empty_cart.setVisibility(View.GONE);
            cartItemsList.setVisibility(View.VISIBLE);
        }
    }

    private void onClickPay() {
        startActivity(new Intent(this, OrderActivity.class));
    }
}