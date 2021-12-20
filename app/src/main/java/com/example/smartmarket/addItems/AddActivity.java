package com.example.smartmarket.addItems;

import static android.widget.Toast.LENGTH_LONG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api.ApiService;
import com.example.models.Items;
import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends Base {

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

    ArrayList<String> arrayList_category;
    ArrayAdapter<String> arrayAdapter_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

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

        arrayList_category = new ArrayList<>();
        arrayList_category.add("Food");
        arrayList_category.add("Toy");
        arrayList_category.add("Drink");
        arrayList_category.add("Tool");

        arrayAdapter_category=new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_item, arrayList_category);
        categoryItem.setAdapter(arrayAdapter_category);

        categoryItem.setThreshold(1);

        Intent intent = getIntent();
        String barcode = intent.getStringExtra("barcode");
        addBarcode.setText(barcode);

        ApiService.apiService.getItemsByBarcode(Long.parseLong(barcode)).enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {
                if (response.code() == 200) {
                    addEdit.setText("EDIT");
                    addAction.setText("EDIT");
                    addName.setText(response.body().title);
                    addDescription.setText(response.body().description);
                    add_Import_Price.setText(response.body().importPrice);
                    add_Sell_Price.setText(response.body().sellPrice);
                    addQuantity.setText(response.body().quantity);
                    add_Company.setText(response.body().companyName);
                    categoryItem.setText(response.body().category);
                }
                else {
                    addEdit.setText("ADD");
                    addAction.setText("ADD");
                }
            }

            @Override
            public void onFailure(Call<Items> call, Throwable t) {

            }
        });

        addAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddActivity.this, "Done", LENGTH_LONG).show();
            }
        });
    }
}
