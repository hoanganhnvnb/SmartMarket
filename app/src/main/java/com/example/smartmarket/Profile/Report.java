package com.example.smartmarket.Profile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapter.PopularAdapter;
import com.example.adapter.UserAdapter;
import com.example.api.ApiService;
import com.example.models.Items;
import com.example.models.User;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Report extends Base {

    Button report_backButton;
    RecyclerView.Adapter itemAdapter,userAdapter;
    RecyclerView hangMoi,khachQuen;
    TextView total_nhanvien,report_quantityuser,total_nhap,total_thu,total_sauthue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        report_backButton = (Button)findViewById(R.id.report_backButton);
        report_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        initView();

        getAPI();


    }

    private void getAPI() {
        ApiService.apiService.getAllItemsApi().enqueue(new Callback<ArrayList<Items>>() {
            @Override
            public void onResponse(Call<ArrayList<Items>> call, Response<ArrayList<Items>> response) {
                ArrayList<Items> items = response.body();
                if (items != null) {
                    itemAdapter = new PopularAdapter(Report.this,items);
                    hangMoi.setAdapter(itemAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Items>> call, Throwable t) {

            }
        });

        ApiService.apiService.getAllUser().enqueue((new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                ArrayList<User> users = response.body();
                if (users != null) {
                    userAdapter = new UserAdapter(users);
                    khachQuen.setAdapter(userAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

            }
        }));

        ApiService.apiService.getReport().enqueue(new Callback<com.example.models.Report>() {
            @Override
            public void onResponse(Call<com.example.models.Report> call, Response<com.example.models.Report> response) {
                com.example.models.Report report = response.body();
                if (report != null) {
                    total_nhap.setText(String.valueOf(report.total_price_import));
                    total_thu.setText(String.valueOf(report.total_price_sell));
                    total_sauthue.setText(String.valueOf(report.total_price_sell - report.total_price_import));
                    report_quantityuser.setText(String.valueOf(report.new_cus));
                    total_nhanvien.setText(String.valueOf(report.count_nv));
                }
            }

            @Override
            public void onFailure(Call<com.example.models.Report> call, Throwable t) {

            }
        });
    }

    private void initView() {

        hangMoi = findViewById(R.id.hangMoi);
        khachQuen = findViewById(R.id.khachQuen);
        total_nhanvien = findViewById(R.id.total_nhanvien);
        total_nhap = findViewById(R.id.total_nhap);
        report_quantityuser = findViewById(R.id.report_quantityuser);
        total_thu = findViewById(R.id.total_thu);
        total_sauthue = findViewById(R.id.total_sauthue);

        LinearLayoutManager linearLayoutManageritem = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManageruser = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

        hangMoi.setLayoutManager(linearLayoutManageritem);
        khachQuen.setLayoutManager(linearLayoutManageruser);
    }





}