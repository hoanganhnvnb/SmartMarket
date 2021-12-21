package com.example.main;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import com.example.models.Cart;
import com.example.models.CartItems;
import com.example.models.Category;
import com.example.models.Items;
import com.example.models.Notify;
import com.example.models.Token;
import com.example.models.User;

import java.util.ArrayList;


public class MarketApp extends Application {

    public static final String SHARED_PREFERENCE_TOKEN = "Token";
    public static final String CHANNEL_ID = "push_notification_id";
    public static final String API_ROOT_URL = "http://ec2-18-118-101-193.us-east-2.compute.amazonaws.com:8000";

    public ArrayList<Category> categories = new ArrayList<>();
    public ArrayList<Items> itemsPopular = new ArrayList<>();
    public ArrayList<User>  users = new ArrayList<>();
    public ArrayList<Items> items = new ArrayList<>();
    public ArrayList<CartItems> cartItems = new ArrayList<>();
    public ArrayList<Notify> notifies = new ArrayList<>();

    public User mUser;
    public static Token token;
    public Cart mCart;

    @Override
    public void onCreate (){
        super.onCreate();
        Log.v("Market", "Marketapp created");
        
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(CHANNEL_ID, "push_notification", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    public static boolean userIsLogin() {
        if (token == null) {
            return false;
        }
        return true;
    }
}
