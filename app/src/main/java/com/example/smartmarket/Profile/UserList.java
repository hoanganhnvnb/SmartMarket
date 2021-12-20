package com.example.smartmarket.Profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.UserAdapter;
import com.example.api.ApiService;
import com.example.models.User;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserList extends Base {

    public RecyclerView.Adapter userAdapter;
    public RecyclerView recyclerViewUser;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        button = (Button)findViewById(R.id.btn_return_userList);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        createRVUser();
    }

    private void createRVUser() {
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewUser = (RecyclerView) findViewById(R.id.user_list_rv);
        recyclerViewUser.setLayoutManager(linearLayoutManager);

        ApiService.apiService.getAllUser().enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                ArrayList<User> users = response.body();
                if (users != null) {
                    app.users = users;
                    userAdapter = new UserAdapter(app.users);
                    recyclerViewUser.setAdapter(userAdapter);
                }
        }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

            }
        });
    }
}