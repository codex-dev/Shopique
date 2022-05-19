package demo.assignment.my_cart.ui.screens;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import demo.assignment.my_cart.R;
import demo.assignment.my_cart.ui.screens.listeners.AppbarListener;

/**
 * Hosting activity of 3 fragments which display products, cart and orders list respectively.
 */
public class HomeActivity extends CommonActivity implements AppbarListener {

    private static final String TAG = "HomeActivity";
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        appBarClickListener = this;

        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_products, R.id.navigation_cart, R.id.navigation_orders)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public void onLeftIconClicked() {

    }

    @Override
    public void onRightIconClicked() {
        signOutUser(TAG);
    }

    @Override
    public void onBackPressed() {
        exitApp(TAG);
    }
}