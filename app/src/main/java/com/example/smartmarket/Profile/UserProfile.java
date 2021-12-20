package com.example.smartmarket.Profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.google.android.material.textfield.TextInputEditText;

public class UserProfile extends Base {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        button = (Button)findViewById(R.id.button_return);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        TextView full_name = findViewById(R.id.full_name);
        TextInputEditText fullname_profile = findViewById(R.id.fullname_profile);
        TextInputEditText email_profile = findViewById(R.id.email_profile);
        TextView email = findViewById(R.id.email_12);

        full_name.setText(app.mUser.username);
        email.setText(app.mUser.email);
        fullname_profile.setText(app.mUser.first_name + app.mUser.last_name);
        email_profile.setText(app.mUser.email);


    }



}