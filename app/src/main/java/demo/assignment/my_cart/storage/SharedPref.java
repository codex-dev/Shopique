package demo.assignment.my_cart.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    public static final String MY_PREFERENCES = "my_prefs";
    public static final String CART_PRODUCT_COUNT = "cart_product_count";

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public void SharedPref(Context context){
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(MY_PREFERENCES,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
}
