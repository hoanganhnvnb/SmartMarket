package com.example.smartmarket.scan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.example.api.ApiService;
import com.example.models.MessageApi;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.example.smartmarket.addItems.AddActivity;
import com.example.smartmarket.dashboard.DashboardActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanQRPayActivity extends Base {

    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrpay);
        CodeScannerView scannerView = findViewById(R.id.scanner_view_pay);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
            ApiService.apiService.paidOrder(Integer.parseInt(result.getText()), true).enqueue(new Callback<MessageApi>() {
                @Override
                public void onResponse(Call<MessageApi> call, Response<MessageApi> response) {
                    if (response.code() == 200) {
                        Toast.makeText(ScanQRPayActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ScanQRPayActivity.this, DashboardActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else if (response.code() == 400) {
                        Toast.makeText(ScanQRPayActivity.this, "Giỏ hàng rỗng", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<MessageApi> call, Throwable t) {

                }
            }); }
        ));
        scannerView.setOnClickListener(v -> mCodeScanner.startPreview());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(com.example.smartmarket.scan.ScanQRPayActivity.this, new String[] {Manifest.permission.CAMERA}, 101);
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