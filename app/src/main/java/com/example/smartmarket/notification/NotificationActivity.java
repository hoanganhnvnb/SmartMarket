package com.example.smartmarket.notification;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.ListNotifiesAdapter;
import com.example.api.ApiService;
import com.example.models.Notify;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends Base {

    public RecyclerView.Adapter listNotifiesAdapter;
    public RecyclerView recyclerViewNotify;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notifies);

        button = (Button)findViewById(R.id.btn_return_dashboard);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        CreateRVNotifies();
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        ApiService.apiService.getAllNotifyInfo().enqueue(new Callback<ArrayList<Notify>>() {
            @Override
            public void onResponse(Call<ArrayList<Notify>> call, Response<ArrayList<Notify>> response) {
                if (response.body() != null) {
                    app.notifies = response.body();
                    listNotifiesAdapter = new ListNotifiesAdapter(NotificationActivity.this, app.notifies);
                    recyclerViewNotify.setAdapter(listNotifiesAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Notify>> call, Throwable t) {

            }
        });
    }


    private void CreateRVNotifies() {
        LinearLayoutManager linearLayoutManagerPop =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerViewNotify = (RecyclerView) findViewById(R.id.list_view_notifies);
        recyclerViewNotify.setLayoutManager(linearLayoutManagerPop);
    }
}
