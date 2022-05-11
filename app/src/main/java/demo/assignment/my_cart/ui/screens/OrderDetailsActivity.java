package demo.assignment.my_cart.ui.screens;

import android.os.Bundle;

import demo.assignment.my_cart.R;
import demo.assignment.my_cart.ui.screens.listeners.AppbarListener;

public class OrderDetailsActivity extends CommonActivity implements AppbarListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        setupActionbar(getString(R.string.order_details), true, false);

        appBarClickListener = this;
    }

    @Override
    public void onRightIconClicked() {

    }

    @Override
    public void onLeftIconClicked() {
        onBackPressed();
    }
}