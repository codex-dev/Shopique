package demo.assignment.my_cart.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    public static final String MY_PREFERENCES = "my_prefs";
    public static final String CART_PRODUCT_COUNT = "cart_product_count";
    public static final String LOGGED_IN = "logged_in";

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SharedPref(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoggedIn(boolean isLoggedIn) {
        editor.putString(LOGGED_IN, isLoggedIn ? "Y" : "N");
        editor.apply();
    }

    public boolean isLoggedIn() {
       String status =  sharedPreferences.getString(LOGGED_IN,"N");
       return status.equals("Y");
    }
}
