package com.example.smartmarket.cart;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import androidx.appcompat.widget.Toolbar;

import com.example.adapter.CartAdapter;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.example.smartmarket.dashboard.DashboardActivity;

import java.text.DecimalFormat;

public class CartActivity extends Base {
    ListView lvCart;
    ImageView emptyCart;
    TextView txtTotal;
    Button btnBuy;
    androidx.appcompat.widget.Toolbar toolbarCart;
    CartAdapter cartAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Mapping();
        ActionToolbar();
        CheckEmptyCart();
        EventUtil();
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarCart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarCart.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void EventUtil() {
        long total = 0;
        for(int i = 0; i < DashboardActivity.cartArrayList.size(); i++) {
            total += DashboardActivity.cartArrayList.get(i).getPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat(("###,###,###"));
        txtTotal.setText(decimalFormat.format(total));
    }


    private void CheckEmptyCart() {
        if(DashboardActivity.cartArrayList.size() <= 0) {
            cartAdapter.notifyDataSetChanged();
            emptyCart.setVisibility(View.VISIBLE);
            lvCart.setVisibility(View.INVISIBLE);
        } else {
            cartAdapter.notifyDataSetChanged();
            emptyCart.setVisibility(View.INVISIBLE);
            lvCart.setVisibility(View.VISIBLE);
        }
    }

    private void Mapping() {
        lvCart = (ListView) findViewById(R.id.listviewCart);
        emptyCart = (ImageView) findViewById(R.id.empty_cart_img);
        txtTotal = (TextView) findViewById(R.id.textviewTotal);
        btnBuy = (Button) findViewById(R.id.button_buy);
        toolbarCart = (Toolbar) findViewById(R.id.toolbarCart);
        cartAdapter = new CartAdapter(CartActivity.this, DashboardActivity.cartArrayList);
        lvCart.setAdapter(cartAdapter);
    }
}