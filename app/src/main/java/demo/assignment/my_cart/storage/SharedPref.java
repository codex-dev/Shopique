package demo.assignment.my_cart.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.LinkedHashMap;
import java.util.TreeMap;

import demo.assignment.my_cart.models.CartItem;
import demo.assignment.my_cart.models.Order;
import demo.assignment.my_cart.ui.util.TextFormatter;

public class SharedPref {
    public static final String MY_PREFERENCES = "my_prefs";
    public static final String CART_BADGE_COUNT = "cart_product_count";
    public static final String LOGGED_IN = "logged_in";
    private static final String CART_DETAILS = "cart_details";
    private static final String ORDERS = "orders";

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SharedPref(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public boolean isLoggedIn() {
        String status = sharedPreferences.getString(LOGGED_IN, "N");
        return status.equals("Y");
    }

    public void setLoggedIn(boolean isLoggedIn, SharedPrefListener listener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                listener.onSuccess();
                sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
            }
        });

        editor.putString(LOGGED_IN, isLoggedIn ? "Y" : "N");
        editor.apply();
    }

    public int getCartBadgeCount() {
        return sharedPreferences.getInt(CART_BADGE_COUNT, 0);
    }

    public void setCartBadgeCount(int count, SharedPrefListener listener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                listener.onSuccess();
                sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
            }
        });

        editor.putInt(CART_BADGE_COUNT, count);
        editor.apply();
    }

    public LinkedHashMap<Integer, CartItem> getCartDetails() {
        String json = sharedPreferences.getString(CART_DETAILS, "");
        return getCartItemsFromJson(json);
    }

    public TreeMap<Integer, Order> getOrders() {
        String json = sharedPreferences.getString(ORDERS, "");
        return getOrdersFromJson(json);
    }

    public void setOrders(TreeMap<Integer, Order> tm, SharedPrefListener listener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                listener.onSuccess();
                sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
            }
        });

        String json = getJsonForOrders(tm);
        editor.putString(ORDERS, json);
        editor.apply();
    }

    public void setCartDetails(LinkedHashMap<Integer, CartItem> hm, SharedPrefListener listener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                listener.onSuccess();
                sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
            }
        });

        String json = getJsonForCartItems(hm);
        editor.putString(CART_DETAILS, json);
        editor.apply();
    }

    public void resetCart(SharedPrefListener listener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                listener.onSuccess();
                sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
            }
        });

        editor.putString(CART_DETAILS, "");
        editor.apply();
    }

    private LinkedHashMap<Integer, CartItem> getCartItemsFromJson(String json) {
        if (!TextFormatter.isNullOrEmpty(json)) {
            LinkedHashMap<Integer, CartItem> hashMap = new Gson().fromJson(
                    json, new TypeToken<LinkedHashMap<Integer, CartItem>>() {
                    }.getType()
            );
            return hashMap;
        }
        return new LinkedHashMap<>();
    }

    private TreeMap<Integer, Order> getOrdersFromJson(String json) {
        if (!TextFormatter.isNullOrEmpty(json)) {
            TreeMap<Integer, Order> treeMap = new Gson().fromJson(
                    json, new TypeToken<TreeMap<Integer, Order>>() {
                    }.getType()
            );
            return treeMap;
        }
        return new TreeMap<>();
    }

    private String getJsonForCartItems(LinkedHashMap<Integer, CartItem> hm) {
        if (hm != null && !hm.isEmpty()) {
            return new Gson().toJson(hm);
        }
        return "";
    }

    private String getJsonForOrders(TreeMap<Integer, Order> tm) {
        if (tm != null && !tm.isEmpty()) {
            return new Gson().toJson(tm);
        }
        return "";
    }
}

