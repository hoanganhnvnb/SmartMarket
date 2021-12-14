package com.example.api;

import android.util.Log;

import com.example.models.Category;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CategoryApi {
    public static List<Category> getAll(String call) {
        String json = Rest.get(call);
        Type collectionType = new TypeToken<List<Category>>(){}.getType();

        return new Gson().fromJson(json, collectionType);
    }

    public static Category get(String call, String id) {
        String json = Rest.get(call + "/" + id);
        Log.v("shop", "JSON RESULT: " + json);
        Type objType = new TypeToken<Category>(){}.getType();

        return new Gson().fromJson(json, objType);
    }

    public static String deleteAll(String call) {
        return Rest.delete(call);
    }

    public static String delete(String call, String id) {
        return Rest.delete(call + "/" + id);
    }

    public static String insert(String call, Category category) {
        Type objType = new TypeToken<Category>(){}.getType();
        String json = new Gson().toJson(category, objType);

        return Rest.post(call, json);
    }
}
