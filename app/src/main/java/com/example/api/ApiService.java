package com.example.api;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.main.MarketApp;
import com.example.models.Cart;
import com.example.models.CartItems;
import com.example.models.Category;
import com.example.models.Items;
import com.example.models.MessageApi;
import com.example.models.Notify;
import com.example.models.Token;
import com.example.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request().newBuilder().build();
            if (MarketApp.userIsLogin()) {
                request = request.newBuilder().header("Authorization", "Bearer " +
                        MarketApp.token.access).build();
            }
            return chain.proceed(request);
        }
    });

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl(MarketApp.API_ROOT_URL + "/")
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

    //get items by barcode
    @GET("items/api/items/get/{barcode}")
    Call<Items> getItemsByBarcode(@Path("barcode") long barcode);

    @Multipart
    @POST("items/api/items")
    Call<Items> insertItem(@Part("barcode") RequestBody barcode,
                           @Part("title") RequestBody title,
                           @Part("description") RequestBody des,
                           @Part("category") RequestBody category,
                           @Part("image") Multipart image,
                           @Part("importPrice") RequestBody importPrice,
                           @Part("sellPrice") RequestBody sellPrice,
                           @Part("quantity") RequestBody quantity,
                           @Part("companyName") RequestBody companyName);

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

    @FormUrlEncoded
    @POST("user/api/token/refresh")
    Call<Token> refreshToken(@Field("refresh") String refresh);

    // get user login
    @GET("user/api/info")
    Call<User> getMyUser();

    @GET("user/api/info")
    Call<User> getMyUser(@Header("Authorization") String token);

    @FormUrlEncoded
    @PUT("user/api/device_token")
    Call<User> updateRegistrationToken(@Header("Authorization") String tokenAuth,
                                       @Field("token") String token);

    // CART API-----------------------------------------------------------
    @POST("cart/api/carts")
    Call<Cart> createCart();

    @GET("cart/api/carts/cart_active")
    Call<Cart> getActiveCart();

    // CART ITEMS API-----------------------------------------------------
    @GET("cart/api/cart_items/cart/{cart_id}")
    Call<ArrayList<CartItems>> getCartItems(@Path("cart_id") int cart_id);

    @FormUrlEncoded
    @POST("cart/api/cart_items")
    Call<CartItems> insertCartItem(@Field("cart") int cartId,
                                   @Field("items") int itemId,
                                   @Field("quantity") int quantity);

    // update cartItem
    @FormUrlEncoded
    @PUT("cart/api/cart_items/{id}")
    Call<MessageApi> updateCartItem(@Path("id") int id,
                                    @Field("cart") int cart_id,
                                    @Field("items") int item_id,
                                    @Field("quantity") int qty);

    //delete cartItem
    @DELETE("cart/api/cart_items/{id}")
    Call<MessageApi> deleteCartItem(@Path("id") int id);


    // NOTIFICATION API--------------------------------------------------------------
    @GET("notification/api/notifications")
    Call<ArrayList<Notify>> getAllNotifyInfo();

    @PUT("notification/api/notifications/{id}")
    Call<MessageApi> markIsReadNotify(@Path("id") int id);

}
