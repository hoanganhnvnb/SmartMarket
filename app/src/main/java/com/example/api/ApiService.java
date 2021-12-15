package com.example.api;

import com.example.main.MarketApp;
import com.example.models.Cart;
import com.example.models.Category;
import com.example.models.Items;
import com.example.models.MessageApi;
import com.example.models.Token;
import com.example.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request().newBuilder().build();
            if (MarketApp.userIsLogin()) {
                request = request.newBuilder().header("Authorization", "Bearer " + MarketApp.token.access).build();
            }
            return chain.proceed(request);
        }
    });

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://18.220.110.46:8000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
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


    // USER API----------------------------------------------------------
    // register user
    @POST("user/api/users")
    Call<MessageApi> registerUser(@Body User user);

    // get all user
    @GET("user/api/users")
    Call<ArrayList<User>> getAllUser();

    //login
    @POST("user/api/login")
    Call<Token> loginUser(@Body User user);

    // CART API-----------------------------------------------------------
    @POST("cart/api/carts")
    Call<Cart> createCart();
}
