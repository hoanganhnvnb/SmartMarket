package com.example.smartmarket.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.smartmarket.Base;
import com.example.smartmarket.R;

public class CartActivity extends Base {

    RecyclerView.Adapter cartItemsAdapter;
    RecyclerView cartItemsList;
    TextView cart_total;
    Button cart_payButton, cart_backButton;
    ScrollView scrollView2;
    TextView text_empty_cart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initView();
        initList();
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cartItemsList.setLayoutManager(linearLayoutManager);

        if (app.cartItems.isEmpty()) {
            text_empty_cart.setVisibility(View.VISIBLE);
            scrollView2.setVisibility(View.GONE);
        } else {
            text_empty_cart.setVisibility(View.GONE);
            scrollView2.setVisibility(View.VISIBLE);
        }

    }

    private void initView() {
        cartItemsList = (RecyclerView) findViewById(R.id.cart_rv);
        cart_total = (TextView) findViewById(R.id.cart_total);
        cart_payButton = (Button) findViewById(R.id.cart_payButton);
        cart_backButton = (Button) findViewById(R.id.cart_backButton);
        scrollView2 = (ScrollView) findViewById(R.id.scrollView2);
        text_empty_cart = findViewById(R.id.text_empty_cart);


        cart_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}