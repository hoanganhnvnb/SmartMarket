package com.example.smartmarket.dashboard;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;

public class ScanActivity extends Base {

    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> Toast.makeText(ScanActivity.this, result.getText(), Toast.LENGTH_LONG).show()));
        scannerView.setOnClickListener(v -> mCodeScanner.startPreview());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(ScanActivity.this, new String[] {Manifest.permission.CAMERA}, 101);
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
