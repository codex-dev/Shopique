package demo.assignment.my_cart.ui.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import demo.assignment.my_cart.R;
import demo.assignment.my_cart.ui.screens.listeners.AppbarListener;

public class DeliveryLocationActivity extends CommonActivity implements AppbarListener {

    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_location);
        setupActionbar(getString(R.string.delivery_location), true, false);
        dismissKeyboard(findViewById(R.id.delivery_location_activity), DeliveryLocationActivity.this);

        initViews();
        setEventListeners();
    }

    private void initViews() {
        btnNext = findViewById(R.id.btnNext);
    }

    private void setEventListeners() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DeliveryLocationActivity.this, PaymentActivity.class);
                i.putExtras(getIntent());
                startActivity(i);
            }
        });
    }

    @Override
    public void onRightIconClicked() {

    }

    @Override
    public void onLeftIconClicked() {
        onBackPressed();
    }
}