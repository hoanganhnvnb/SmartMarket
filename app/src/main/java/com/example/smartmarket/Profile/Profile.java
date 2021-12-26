package com.example.smartmarket.Profile;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.main.MarketApp;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.example.smartmarket.items.ListItemsActivity;
import com.example.smartmarket.login.LoginActivity;

public class Profile extends Base {

    Button button1,button2,button3, logout_btn,button5,button6, button7,btn_report;
    TextView username_profile_menu, email_profile_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        email_profile_menu = findViewById(R.id.email_profile_menu);
        username_profile_menu = findViewById(R.id.username_profile_menu);
        username_profile_menu.setText(app.mUser.username);
        email_profile_menu.setText(app.mUser.email);

        button1 = (Button)findViewById(R.id.btn_return);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        button2 = (Button)findViewById(R.id.btn_toProfile);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( Profile.this, UserProfile.class);
                startActivity(i);
            }
        });


        button3 = (Button)findViewById(R.id.btn_user_list);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( Profile.this, UserList.class);
                startActivity(i);


            }
        });

        logout_btn = (Button)findViewById(R.id.btn_logout);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( getApplicationContext(), LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                SharedPreferences tokenShared = getSharedPreferences(MarketApp.SHARED_PREFERENCE_TOKEN, MODE_PRIVATE);
                SharedPreferences.Editor editorToken = tokenShared.edit();
                editorToken.clear();
                editorToken.apply();
                app.mUser = null;
                app.mCart = null;
                app.cartItems = null;
                app.items = null;
                startActivity(i);
                Toast.makeText(Profile.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        button5 = (Button)findViewById(R.id.btn_support);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( Profile.this, support.class);
                startActivity(i);
            }
        });


        button6 = (Button)findViewById(R.id.btn_list_items);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( Profile.this,ListItemsActivity.class);
                startActivity(i);
            }
        });

        button7 = (Button)findViewById(R.id.btn_hoadon);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( Profile.this, ListOrder.class);
                startActivity(i);
            }
        });

        btn_report = (Button)findViewById(R.id.btn_report);
        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( Profile.this, Report.class);
                startActivity(i);
            }
        });

        if (app.mUser.is_superuser) {
            button7.setVisibility(View.GONE);
        } else {
            button3.setVisibility(View.GONE);
            button6.setVisibility(View.GONE);
            btn_report.setVisibility(View.GONE);
        }

    }

}