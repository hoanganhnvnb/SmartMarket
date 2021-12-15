package com.example.smartmarket.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.api.ApiService;
import com.example.models.MessageApi;
import com.example.models.User;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.example.smartmarket.dashboard.DashboardAdminActivity;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends Base {

    Button callLoginBtn;
    Button signup_btn;
    TextInputLayout signup_name;
    TextInputLayout signup_username;
    TextInputLayout signup_email;
    TextInputLayout signup_password;
    TextInputLayout signup_confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        callLoginBtn = (Button) findViewById(R.id.signup_call_login);
        signup_btn = (Button) findViewById(R.id.signup_btn);

        signup_name = (TextInputLayout) findViewById(R.id.signup_name);
        signup_username = (TextInputLayout) findViewById(R.id.signup_username);
        signup_email = (TextInputLayout) findViewById(R.id.signup_email);
        signup_password = (TextInputLayout) findViewById(R.id.signup_password);
        signup_confirm_password = (TextInputLayout) findViewById(R.id.signup_confirm_password);

        callLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    public void register() {
        String name = signup_name.getEditText().getText().toString().trim();
        String username = signup_username.getEditText().getText().toString().trim();
        String email = signup_email.getEditText().getText().toString().trim();
        String password = signup_password.getEditText().getText().toString().trim();
        String cnfpassword = signup_confirm_password.getEditText().getText().toString().trim();

        if (password.equals(cnfpassword) && password.length()>=6 && cnfpassword.length()>=6
            && username.length()>4) {
            User userRegister = new User(username, email, password, name, "");

            ApiService.apiService.registerUser(userRegister).enqueue(new Callback<MessageApi>() {
                @Override
                public void onResponse(Call<MessageApi> call, Response<MessageApi> response) {
                    MessageApi messageApi = response.body();
                    signup_email.getEditText().setText("");
                    signup_password.getEditText().setText("");
                    signup_confirm_password.getEditText().setText("");
                    Toast.makeText(SignupActivity.this, messageApi.message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<MessageApi> call, Throwable t) {
                    Toast.makeText(SignupActivity.this, "Register Fail", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Invalid form data", Toast.LENGTH_SHORT).show();
        }
    }
}