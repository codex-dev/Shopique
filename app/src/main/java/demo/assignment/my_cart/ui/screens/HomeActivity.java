package demo.assignment.my_cart.ui.screens;

import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import demo.assignment.my_cart.R;
import demo.assignment.my_cart.ui.screens.listeners.AppbarListener;

public class HomeActivity extends CommonActivity implements AppbarListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        appBarClickListener = this;

        BottomNavigationView navView = findViewById(R.id.nav_view);
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
//        Toast.makeText(this, "Logout Clicked", Toast.LENGTH_SHORT).show();
        signOutUser();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        exitApp();
    }
}