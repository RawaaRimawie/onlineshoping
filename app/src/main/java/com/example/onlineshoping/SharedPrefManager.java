package com.example.onlineshoping;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPrefManager {
    private static final String PREF_NAME = "ShopPrefs";
    private static final String KEY_PRODUCTS = "products";
    private static final String KEY_CART = "cart";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public SharedPrefManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
        gson = new Gson();
    }

    public void saveProductList(ArrayList<Product> list) {
        String json = gson.toJson(list);
        editor.putString(KEY_PRODUCTS, json);
        editor.apply();
    }

    public ArrayList<Product> getProductList() {
        String json = prefs.getString(KEY_PRODUCTS, null);
        Type type = new TypeToken<ArrayList<Product>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void saveCart(ArrayList<Product> cart) {
        String json = gson.toJson(cart);
        editor.putString(KEY_CART, json);
        editor.apply();
    }

    public ArrayList<Product> getCart() {
        String json = prefs.getString(KEY_CART, null);
        Type type = new TypeToken<ArrayList<Product>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void clearCart() {
        editor.remove(KEY_CART).apply();
    }
}

