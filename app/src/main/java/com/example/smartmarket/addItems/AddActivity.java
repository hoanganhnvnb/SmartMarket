package com.example.smartmarket.addItems;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.smartmarket.Base;
import com.example.smartmarket.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class AddActivity extends Base {

    TextInputLayout categoryMenu;
    AutoCompleteTextView categoryItem;

    ArrayList<String> arrayList_category;
    ArrayAdapter<String> arrayAdapter_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

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

    }
}
