package com.example.smartmarket.Profile;



import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.example.smartmarket.dashboard.DashboardActivity;
import com.example.smartmarket.items.ListItemsActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Profile extends Base {

    Button button1,button2,button3, button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        button1 = (Button)findViewById(R.id.btn_return);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( Profile.this, DashboardActivity.class);
                startActivity(i);
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

        button4 = (Button)findViewById(R.id.btn_list_items);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( Profile.this, ListItemsActivity.class);
                startActivity(i);
            }
        });
    }
}