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
import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.example.smartmarket.addItems.AddActivity;

public class ScanActivity extends Base {

    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
                    Intent intent = new Intent(ScanActivity.this, AddActivity.class);
                    intent.putExtra("barcode", result.getText());
                    startActivity(intent);
                }
                ));
        scannerView.setOnClickListener(v -> mCodeScanner.startPreview());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(com.example.smartmarket.scan.ScanActivity.this, new String[] {Manifest.permission.CAMERA}, 101);
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

