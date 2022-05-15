package demo.assignment.my_cart.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

import demo.assignment.my_cart.models.CartItem;
import demo.assignment.my_cart.ui.util.TextFormatter;

public class SharedPref {
    public static final String MY_PREFERENCES = "my_prefs";
    public static final String CART_BADGE_COUNT = "cart_product_count";
    public static final String LOGGED_IN = "logged_in";
    private static final String CART_DETAILS = "cart_details";

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

    public HashMap<Integer, CartItem> getCartDetails() {
        String detailsJson = sharedPreferences.getString(CART_DETAILS, "");
        return getHashMapFromJson(detailsJson);
    }

    public void setCartDetails(HashMap<Integer, CartItem> cartDetails, SharedPrefListener listener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                listener.onSuccess();
                sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
            }
        });

        String cartDetailsJson = getJsonFromHashMap(cartDetails);
        editor.putString(CART_DETAILS, cartDetailsJson);
        editor.apply();
    }

    private HashMap<Integer, CartItem> getHashMapFromJson(String detailsJson) {
        if (!TextFormatter.isNullOrEmpty(detailsJson)) {
            HashMap<Integer, CartItem> hashMap = new Gson().fromJson(
                    detailsJson, new TypeToken<HashMap<Integer, CartItem>>() {
                    }.getType()
            );
            return hashMap;
        }
        return new HashMap<>();
    }

    private String getJsonFromHashMap(HashMap<Integer, CartItem> cartDetails) {
        if (cartDetails != null && !cartDetails.isEmpty()) {
            return new Gson().toJson(cartDetails);
        }
        return "";
    }
}

