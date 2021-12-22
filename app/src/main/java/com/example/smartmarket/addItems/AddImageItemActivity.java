package com.example.smartmarket.addItems;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.api.ApiService;
import com.example.models.Items;
import com.example.models.MessageApi;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.example.smartmarket.RealPathUtil;
import com.example.smartmarket.dashboard.DashboardActivity;
import com.example.smartmarket.items.DetailAddToCartActivity;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddImageItemActivity extends Base {

    private static final int MY_REQUEST_CODE = 10;
    ImageView img_up;
    Button select_img;
    AppCompatButton reject_up, confirm_up;

    String barcode;

    Uri mUri;
    ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.v("shop", "onActivityResult");

                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }

                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            img_up.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image_item);

        initView();
        getBundleData();
    }

    private void getBundleData() {
        Intent intent = getIntent();
        barcode = intent.getStringExtra("barcode");
    }

    private void initView() {
        img_up = findViewById(R.id.img_up);
        select_img = findViewById(R.id.select_img);
        reject_up = findViewById(R.id.reject_up_img);
        confirm_up = findViewById(R.id.confirm_up_img);

        select_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickPermission();
            }
        });

        confirm_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUri == null) {
                    Toast.makeText(AddImageItemActivity.this, "Chưa chọn ảnh", Toast.LENGTH_SHORT).show();
                } else {
                    uploadImageApi();
                }
            }
        });

        reject_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddImageItemActivity.this, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void uploadImageApi() {
        String realPathImg = RealPathUtil.getRealPath(this, mUri);
        File file = new File(realPathImg);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        ApiService.apiService.addImage(Long.parseLong(barcode), body).enqueue(new Callback<MessageApi>() {
            @Override
            public void onResponse(Call<MessageApi> call, Response<MessageApi> response) {
                Log.v("shop", realPathImg);
                if (response.code() == 200) {
                    Log.v("shop", realPathImg);
                    Toast.makeText(AddImageItemActivity.this, "Thêm ảnh thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddImageItemActivity.this, DashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<MessageApi> call, Throwable t) {
                Log.v("shop", t.getMessage());
            }
        });
    }

    private void onClickPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select picture"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }

    }
}