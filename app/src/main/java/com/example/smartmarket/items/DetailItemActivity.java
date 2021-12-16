package com.example.smartmarket.items;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.models.Items;
import com.example.smartmarket.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class DetailItemActivity extends AppCompatActivity {

    FloatingActionButton detail_item_backbtn, detail_item_cartbtn;
    ImageView detail_item_image;
    TextView detail_item_title, detail_item_des, detail_item_priceSell, detail_quantity_sold, detail_quantity;
    Button detail_item_btnbuy;

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

        String imgUrl = item.image;
        Picasso.with(this)
                .load(imgUrl)
                .into(detail_item_image);

        return true;
    }

    private void initElement() {
        detail_item_backbtn = (FloatingActionButton) findViewById(R.id.detail_item_backbtn);
        detail_item_cartbtn = (FloatingActionButton) findViewById(R.id.detail_item_cartbtn);
        detail_item_image = (ImageView) findViewById(R.id.detail_item_image);
        detail_item_title = (TextView) findViewById(R.id.detail_item_title);
        detail_item_des = (TextView) findViewById(R.id.detail_item_des);
        detail_item_priceSell = (TextView) findViewById(R.id.detail_item_priceSell);
        detail_quantity_sold = (TextView) findViewById(R.id.detail_quantity_sold);
        detail_quantity = (TextView) findViewById(R.id.detail_quantity);
        detail_item_btnbuy = (Button) findViewById(R.id.detail_item_btnbuy);
    }
}