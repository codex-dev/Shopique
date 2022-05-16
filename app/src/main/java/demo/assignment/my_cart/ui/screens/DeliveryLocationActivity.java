package demo.assignment.my_cart.ui.screens;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import demo.assignment.my_cart.R;
import demo.assignment.my_cart.ui.Constants;
import demo.assignment.my_cart.ui.screens.listeners.AppbarListener;

public class DeliveryLocationActivity extends CommonActivity implements AppbarListener {

    private TextView btnHome, btnWork;
    private Button btnNext;

    private String deliveryLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_location);
        setupActionbar(getString(R.string.delivery_location), true, false);
        dismissKeyboard(findViewById(R.id.delivery_location_activity), DeliveryLocationActivity.this);

        appBarClickListener = this;

        initViews();
        setEventListeners();
    }

    private void initViews() {
        btnHome = findViewById(R.id.btnHome);
        btnWork = findViewById(R.id.btnWork);
        btnNext = findViewById(R.id.btnNext);
    }

    private void setEventListeners() {
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deliveryLocation = Constants.HOME;
                updateView();
            }
        });

        btnWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deliveryLocation = Constants.WORK;
                updateView();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DeliveryLocationActivity.this, PaymentActivity.class);
                i.putExtras(getIntent());
                i.putExtra("del_location", deliveryLocation);
                startActivity(i);
            }
        });

        btnHome.performClick();
    }

    private void updateView() {
        boolean isHomeSelected = deliveryLocation.equalsIgnoreCase(Constants.HOME);

        btnHome.setTextColor(isHomeSelected ?
                ContextCompat.getColor(DeliveryLocationActivity.this, R.color.white) :
                ContextCompat.getColor(DeliveryLocationActivity.this, R.color.black)
        );
        btnHome.setBackground(isHomeSelected ?
                ContextCompat.getDrawable(DeliveryLocationActivity.this, R.drawable.bg_outline_selected) :
                ContextCompat.getDrawable(DeliveryLocationActivity.this, R.drawable.bg_outline)
        );
        setTextViewDrawableColor(btnHome, isHomeSelected ?
                ContextCompat.getDrawable(this, R.drawable.ic_home_white) :
                ContextCompat.getDrawable(this, R.drawable.ic_home_black)
        );

        btnWork.setTextColor(isHomeSelected ?
                ContextCompat.getColor(DeliveryLocationActivity.this, R.color.black) :
                ContextCompat.getColor(DeliveryLocationActivity.this, R.color.white)
        );
        btnWork.setBackground(isHomeSelected ?
                ContextCompat.getDrawable(DeliveryLocationActivity.this, R.drawable.bg_outline) :
                ContextCompat.getDrawable(DeliveryLocationActivity.this, R.drawable.bg_outline_selected)
        );
        setTextViewDrawableColor(btnWork, isHomeSelected ?
                ContextCompat.getDrawable(this, R.drawable.ic_work_black) :
                ContextCompat.getDrawable(this, R.drawable.ic_work_white)
        );
    }

    private void setTextViewDrawableColor(TextView textView, Drawable drawable) {
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null);
    }

    @Override
    public void onRightIconClicked() {

    }

    @Override
    public void onLeftIconClicked() {
        onBackPressed();
    }
}