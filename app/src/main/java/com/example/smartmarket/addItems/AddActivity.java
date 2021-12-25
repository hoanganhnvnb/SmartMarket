package com.example.smartmarket.addItems;

import static android.widget.Toast.LENGTH_LONG;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.example.api.ApiService;
import com.example.models.Category;
import com.example.models.Items;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.example.smartmarket.RealPathUtil;
import com.example.smartmarket.items.UpdateDeleteCartItemActivity;
import com.example.smartmarket.scan.ScanActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends Base {

    private static final int MY_REQUEST_CODE = 10;
    TextView addEdit;

    EditText addName;
    TextView addBarcode;
    EditText addDescription;
    EditText add_Import_Price;
    EditText add_Sell_Price;
    EditText addQuantity;
    EditText add_Company;

    Button addAction;

    TextInputLayout categoryMenu;
    AutoCompleteTextView categoryItem;

    ArrayList<Category> arrayList_category;
    ArrayAdapter<Category> arrayAdapter_category;
    private int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        initView();

        getBundle();
    }

    private void getBundle() {
        Intent intent = getIntent();
        String barcode = intent.getStringExtra("barcode");
        addBarcode.setText(barcode);

        ApiService.apiService.getItemsByBarcode(Long.parseLong(barcode)).enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {
                if (response.code() == 200 && response.body() != null) {
                    Items itemEdit = response.body();
                    addEdit.setText("EDIT");
                    addAction.setText("EDIT");
                    addName.setText(itemEdit.title);
                    addDescription.setText(itemEdit.description);
                    add_Import_Price.setText(String.valueOf(itemEdit.importPrice));
                    add_Sell_Price.setText(String.valueOf(itemEdit.sellPrice));
                    addQuantity.setText(String.valueOf(itemEdit.quantity));
                    add_Company.setText(itemEdit.companyName);
                    categoryItem.setText(getCategoryById(itemEdit.category).title);
                    addAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            callEditItemApi();
                        }
                    });
                }
                else {
                    addEdit.setText("ADD");
                    addAction.setText("ADD");
                    addAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            callAddItemApi();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<Items> call, Throwable t) {
            }
        });
    }

    private Category getCategoryById(int id) {
        for (int i = 0; i < app.categories.size(); i++) {
            Category category = app.categories.get(i);
            if (category.id == id) {
                return category;
            }
        }
        return new Category();
    }

    private void initView() {
        addEdit=(TextView) findViewById(R.id.addEdit);
        addName=(EditText) findViewById(R.id.addName);
        addBarcode=(TextView) findViewById(R.id.addBarcode);
        addDescription=(EditText) findViewById(R.id.addDescription);
        add_Import_Price=(EditText) findViewById(R.id.add_Import_Price);
        add_Sell_Price=(EditText) findViewById(R.id.add_Sell_Price);
        addQuantity=(EditText) findViewById(R.id.addQuantity);
        add_Company=(EditText) findViewById(R.id.add_Company);
        addAction = (Button) findViewById(R.id.addAction);
        categoryMenu=(TextInputLayout) findViewById(R.id.categoryMenu);
        categoryItem=(AutoCompleteTextView) findViewById(R.id.categoryItem);

        arrayList_category = app.categories;
        arrayAdapter_category=new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, arrayList_category);
        categoryItem.setAdapter(arrayAdapter_category);
        categoryItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoryId = arrayAdapter_category.getItem(i).id;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        categoryItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                categoryId = arrayAdapter_category.getItem(i).id;
            }
        });
    }

    private void callAddItemApi() {
        long barcode = Long.parseLong(addBarcode.getText().toString());
        String title = addName.getText().toString();
        String description = addDescription.getText().toString();
        int category = categoryId;
        int importPrice = Integer.parseInt(add_Import_Price.getText().toString());
        int sellPrice = Integer.parseInt(add_Sell_Price.getText().toString());
        int quantity = Integer.parseInt(addQuantity.getText().toString());
        String companyName = add_Company.getText().toString();

        Items items = new Items(barcode, title, description, category, importPrice, sellPrice, quantity, companyName);
        ApiService.apiService.insertItem(items).enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {
                if (response.code() == 201) {
                    Toast.makeText(AddActivity.this, "Thêm hàng hóa vào kho thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddActivity.this, AddImageItemActivity.class);
                    intent.putExtra("barcode", addBarcode.getText().toString());
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Items> call, Throwable t) {

            }
        });
    }


    private void callEditItemApi() {
        long barcode = Long.parseLong(addBarcode.getText().toString());
        String title = addName.getText().toString();
        String description = addDescription.getText().toString();
        int category = Integer.parseInt(categoryItem.getText().toString());
        int importPrice = Integer.parseInt(add_Import_Price.getText().toString());
        int sellPrice = Integer.parseInt(add_Sell_Price.getText().toString());
        int quantity = Integer.parseInt(addQuantity.getText().toString());
        String companyName = add_Company.getText().toString();

        Items items = new Items(barcode, title, description, category, importPrice, sellPrice, quantity, companyName);

        ApiService.apiService.editItem(barcode, items).enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {

            }

            @Override
            public void onFailure(Call<Items> call, Throwable t) {

            }
        });
    }

}
