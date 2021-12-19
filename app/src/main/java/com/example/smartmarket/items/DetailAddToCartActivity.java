package com.example.smartmarket.items;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api.ApiService;
import com.example.main.MarketApp;
import com.example.models.CartItems;
import com.example.models.Items;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.example.smartmarket.cart.CartActivity;
import com.example.smartmarket.dashboard.DashboardActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailAddToCartActivity extends Base {

    FloatingActionButton detail_add_backbtn;
    ImageView detail_add_image;
    TextView detail_add_title, detail_add_des, detail_add_priceSell, detail_add_quantity_sold, detail_add_quantity_i, detail_add_quantity;
    Button detail_addtocart;
    ImageView detail_add_minus, detail_add_plus;

    private Items item;
    private int quantityItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_add_to_cart);

        this.quantityItem = 1;
        initView();
        getItemsBundle();
    }

    private boolean getItemsBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return false;
        }
        item = (Items) bundle.get("object_item");

        detail_add_title.setText(item.title);
        detail_add_des.setText(item.description);
        detail_add_priceSell.setText(String.valueOf(item.sellPrice));
        detail_add_quantity_sold.setText(String.valueOf(item.quantity_sold));
        detail_add_quantity_i.setText(String.valueOf(item.quantity));

        String imgUrl = MarketApp.API_ROOT_URL + item.image;
        Picasso.with(this)
                .load(imgUrl)
                .into(detail_add_image);

        return true;
    }

    private void initView() {
        detail_add_backbtn = findViewById(R.id.detail_add_backbtn);
        detail_add_image = findViewById(R.id.detail_add_image);
        detail_add_title = findViewById(R.id.detail_add_title);
        detail_add_des = findViewById(R.id.detail_add_des);
        detail_add_priceSell = findViewById(R.id.detail_add_priceSell);
        detail_add_quantity_sold = findViewById(R.id.detail_add_quantity_sold);
        detail_add_quantity_i = findViewById(R.id.detail_add_quantity_i);
        detail_add_quantity = findViewById(R.id.detail_add_quantity);
        detail_addtocart = findViewById(R.id.detail_addtocart);
        detail_add_minus = findViewById(R.id.detail_add_minus);
        detail_add_plus = findViewById(R.id.detail_add_plus);
        
        detail_add_quantity.setText(String.valueOf(quantityItem));
        
        detail_add_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickMinus();
            }
        });
        
        detail_add_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickPlus();
            }
        });
        
        detail_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAddToCart();
            }
        });

        detail_add_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void clickAddToCart() {
        ApiService.apiService.insertCartItem(app.mCart.id, item.id, quantityItem).enqueue(new Callback<CartItems>() {
            @Override
            public void onResponse(Call<CartItems> call, Response<CartItems> response) {
                Toast.makeText(DetailAddToCartActivity.this, "Succeed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DetailAddToCartActivity.this, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<CartItems> call, Throwable t) {

            }
        });
    }

    private void clickPlus() {
        if (this.quantityItem + 1 > item.quantity) {
            Toast.makeText(DetailAddToCartActivity.this, "Số lượng đạt tối đa", Toast.LENGTH_SHORT).show();
        } else {
            this.quantityItem = this.quantityItem + 1;
            detail_add_quantity.setText(String.valueOf(quantityItem));
        }
    }

    private void clickMinus() {
        if (this.quantityItem - 1 < 0) {
            Toast.makeText(DetailAddToCartActivity.this, "Số lượng đạt tối thiểu", Toast.LENGTH_SHORT).show();
        } else {
            this.quantityItem = this.quantityItem - 1;
            detail_add_quantity.setText(String.valueOf(quantityItem));
        }
    }
}