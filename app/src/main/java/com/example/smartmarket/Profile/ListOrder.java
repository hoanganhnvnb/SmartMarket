package com.example.smartmarket.Profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.smartmarket.Base;
import com.example.smartmarket.R;

public class ListOrder extends Base {

    Button button;

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


    }
}