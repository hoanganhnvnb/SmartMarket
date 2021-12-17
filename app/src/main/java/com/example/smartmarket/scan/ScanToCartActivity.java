package com.example.smartmarket.scan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.example.api.ApiService;
import com.example.models.Items;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.example.smartmarket.items.DetailAddToCartActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanToCartActivity extends Base {

    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_to_cart);
        CodeScannerView scannerView = findViewById(R.id.scanner_view_cart);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
            ApiService.apiService.getItemsByBarcode(Long.parseLong(result.getText())).enqueue(new Callback<Items>() {
                @Override
                public void onResponse(Call<Items> call, Response<Items> response) {
                    Items items = response.body();
                    if (items != null) {
                        Toast.makeText(ScanToCartActivity.this, "Succeed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ScanToCartActivity.this, DetailAddToCartActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("object_item", items);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ScanToCartActivity.this, "Khong thay hang hoa co barcode nay", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Items> call, Throwable t) {

                }
            });

        }));
        scannerView.setOnClickListener(v -> mCodeScanner.startPreview());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(ScanToCartActivity.this, new String[] {Manifest.permission.CAMERA}, 101);
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}