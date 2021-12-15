package com.example.smartmarket.Profile;

import androidx.appcompat.app.AppCompatActivity;
import com.example.smartmarket.R;
import com.example.smartmarket.dashboard.DashboardActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserList extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        button = (Button)findViewById(R.id.btn_return_userList);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( UserList.this, Profile.class);
                startActivity(i);
            }
        });
    }
}