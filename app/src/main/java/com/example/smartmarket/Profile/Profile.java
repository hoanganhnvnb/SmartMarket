package com.example.smartmarket.Profile;



import com.example.main.MarketApp;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.example.smartmarket.dashboard.DashboardActivity;
import com.example.smartmarket.login.LoginActivity;
import com.example.smartmarket.items.ListItemsActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Profile extends Base {

    Button button1,button2,button3, logout_btn,button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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

    }

}