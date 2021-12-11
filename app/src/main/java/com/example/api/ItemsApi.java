package com.example.api;

import android.util.Log;

import com.example.models.Items;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ItemsApi {
    public static List<Items> getAll(String call) {
        String json = Rest.get(call);
        Type collectionType = new TypeToken<List<Items>>(){}.getType();

        return new Gson().fromJson(json, collectionType);
    }

    public static Items get(String call, String id) {
        String json = Rest.get(call + "/" + id);
        Log.v("donate", "JSON RESULT: " + json);
        Type objType = new TypeToken<Items>(){}.getType();

        return new Gson().fromJson(json, objType);
    }

    public static String deleteAll(String call) {
        return Rest.delete(call);
    }

    public static String delete(String call, String id) {
        return Rest.delete(call + "/" + id);
    }

    public static String insert(String call, Items donation) {
        Type objType = new TypeToken<Items>(){}.getType();
        String json = new Gson().toJson(donation, objType);

        return Rest.post(call, json);
    }
}
