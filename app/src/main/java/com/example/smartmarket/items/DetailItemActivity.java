package com.example.smartmarket.items;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.models.Category;
import com.example.models.Items;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class DetailItemActivity extends Base {

    FloatingActionButton detail_item_backbtn, detail_item_cartbtn;
    ImageView detail_item_image;
    TextView detail_item_title, detail_item_des, detail_item_priceSell, detail_quantity_sold, detail_quantity;
    Button detail_item_btnbuy;
    TextView cat_detail, company_detail;

    private Items item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);

        initElement();
        getItemBundle();
    }

    private boolean getItemBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return false;
        }
        item = (Items) bundle.get("object_item");

        detail_item_title.setText(item.title);
        detail_item_des.setText(item.description);
        detail_item_priceSell.setText(String.valueOf(item.sellPrice));
        detail_quantity_sold.setText(String.valueOf(item.quantity_sold));
        detail_quantity.setText(String.valueOf(item.quantity));
        company_detail.setText(item.companyName);

        for (int i = 0; i < app.categories.size(); i++) {
            Category category = app.categories.get(i);
            if (item.category == category.id) {
                cat_detail.setText(category.title);
                break;
            }
        }

        String imgUrl = item.image;
        Picasso.with(this)
                .load(imgUrl)
                .into(detail_item_image);
        return true;
    }

    private void initElement() {
        detail_item_backbtn = (FloatingActionButton) findViewById(R.id.detail_item_backbtn);
        detail_item_image = (ImageView) findViewById(R.id.detail_item_image);
        detail_item_title = (TextView) findViewById(R.id.detail_item_title);
        detail_item_des = (TextView) findViewById(R.id.detail_item_des);
        detail_item_priceSell = (TextView) findViewById(R.id.detail_item_priceSell);
        detail_quantity_sold = (TextView) findViewById(R.id.detail_quantity_sold);
        detail_quantity = (TextView) findViewById(R.id.detail_quantity);
        cat_detail = findViewById(R.id.cat_detail);
        company_detail = findViewById(R.id.company_detail);

        detail_item_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}