package com.example.smartmarket.login;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.example.smartmarket.dashboard.DashboardActivity;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends Base {

    ImageView login_logo;
    TextView login_title, login_subtitle;
    TextInputLayout login_username, login_password;
    Button login_forget_pass, login_btn, login_call_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        // define view val
        login_logo = (ImageView) findViewById(R.id.login_logo);
        login_title = (TextView) findViewById(R.id.login_title);
        login_subtitle = (TextView) findViewById(R.id.login_subtitle);
        login_username = (TextInputLayout) findViewById(R.id.login_username);
        login_password = (TextInputLayout) findViewById(R.id.login_password);
        login_forget_pass = (Button) findViewById(R.id.login_forget_pass);
        login_btn = (Button) findViewById(R.id.login_btn);
        login_call_signup = (Button) findViewById(R.id.login_call_signup);

        // To Signup screen
        login_call_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toSignupTrans();
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
            }
        });
    }

    public void toSignupTrans() {
        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(login_logo, "trans_image");
        pairs[1] = new Pair<View, String>(login_title, "trans_text");
        pairs[2] = new Pair<View, String>(login_subtitle, "trans_subtitle");
        pairs[3] = new Pair<View, String>(login_username, "trans_username");
        pairs[4] = new Pair<View, String>(login_password, "trans_password");
        pairs[5] = new Pair<View, String>(login_btn, "trans_btn");
        pairs[6] = new Pair<View, String>(login_call_signup, "trans_login_signup");

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pairs);
        startActivity(new Intent(this, SignupActivity.class), options.toBundle());
    }
}