package com.example.api;

import com.example.models.Category;
import com.example.models.Items;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiService {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://18.220.110.46:8000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    // CATEGORY API-----------------------------------------------------
    // get all category Api
    @GET("category/api/categories")
    Call<ArrayList<Category>> getAllCategoryApi();


    // ITEMS API--------------------------------------------------------
    // get popular items Api
    @GET("items/api/items/popular")
    Call<ArrayList<Items>> getPopularItemsApi();

    //get all items Api
    @GET("items/api/items")
    Call<ArrayList<Items>> getAllItemsApi();
}
