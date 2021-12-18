package com.example.smartmarket.login;


import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.api.ApiService;
import com.example.main.MarketApp;
import com.example.models.MessageApi;
import com.example.models.Token;
import com.example.models.User;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.example.smartmarket.dashboard.DashboardActivity;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Base {

    ImageView login_logo;
    TextView login_title, login_subtitle;
    TextInputLayout login_username, login_password;
    Button login_forget_pass, login_btn, login_call_signup;
    private long backTime;
    private Toast backToast;

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
                login();
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[] {Manifest.permission.CAMERA}, 101);
        }

        checkTokenAccess();
    }

    private void checkTokenAccess() {
        SharedPreferences tokenShared = getSharedPreferences(MarketApp.SHARED_PREFERENCE_TOKEN, MODE_PRIVATE);
        SharedPreferences.Editor editorToken = tokenShared.edit();
        String token = tokenShared.getString("token", null);
        String refresh = tokenShared.getString("refresh", null);
        if (token != null && refresh != null) {
            ApiService.apiService.getMyUser("Bearer " + token).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.code() == 401) {
                        refreshToken();
                    } else if (response.code() == 200) {
                        MarketApp.token = new Token(token, refresh);
                        app.mUser = response.body();
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        finish();
                    }
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                }
            });
        }
    }

    private void refreshToken() {
        SharedPreferences tokenShared = getSharedPreferences(MarketApp.SHARED_PREFERENCE_TOKEN, MODE_PRIVATE);
        SharedPreferences.Editor editorToken = tokenShared.edit();
        String refresh = tokenShared.getString("refresh", null);
        ApiService.apiService.refreshToken(refresh).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.code() == 200) {
                    editorToken.putString("token", response.body().access);
                    editorToken.apply();
                    MarketApp.token = new Token(response.body().access, refresh);
                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {

            }
        });
    }


    public void login() {
        SharedPreferences tokenShared = getSharedPreferences(MarketApp.SHARED_PREFERENCE_TOKEN, MODE_PRIVATE);
        SharedPreferences.Editor editorToken = tokenShared.edit();
        String username = login_username.getEditText().getText().toString();
        String pass = login_password.getEditText().getText().toString();

        User userLogin = new User();
        userLogin.username = username;
        userLogin.password = pass;

        ApiService.apiService.loginUser(userLogin).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Token token = response.body();
                if (token != null) {
                    Toast.makeText(LoginActivity.this, "Login succeed", Toast.LENGTH_SHORT).show();
                    MarketApp.token = token;

                    editorToken.putString("token", token.access);
                    editorToken.putString("refresh", token.refresh);
                    editorToken.apply();
                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login Fail", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {

        if (backTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(LoginActivity.this, "Nhấn back thêm một lần nữa để thoát", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backTime = System.currentTimeMillis();
    }
}