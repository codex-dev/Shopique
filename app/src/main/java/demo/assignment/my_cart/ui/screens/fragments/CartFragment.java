package demo.assignment.my_cart.ui.screens.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import demo.assignment.my_cart.R;
import demo.assignment.my_cart.ui.screens.HomeActivity;
import demo.assignment.my_cart.ui.screens.PaymentActivity;

public class CartFragment extends Fragment {

    private HomeActivity homeActivity;

    private Button btnCheckout;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        homeActivity = (HomeActivity) getActivity();
        if (homeActivity != null) {
            homeActivity.setupActionbar(getString(R.string.title_cart), false, false);
        }

        initViews(v);
        setEventListeners();
//        loadCart();
        // TODO show cart items list
        // show text if cart is empty

        return v;
    }

    private void initViews(View v) {
        btnCheckout = v.findViewById(R.id.btnCheckout);
    }

    private void setEventListeners() {
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(homeActivity, PaymentActivity.class);
                startActivity(i);
            }
        });
    }
}