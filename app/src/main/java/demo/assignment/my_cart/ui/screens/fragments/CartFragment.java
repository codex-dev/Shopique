package demo.assignment.my_cart.ui.screens.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

import demo.assignment.my_cart.R;
import demo.assignment.my_cart.models.CartItem;
import demo.assignment.my_cart.ui.adapters.CartItemAdapter;
import demo.assignment.my_cart.ui.screens.DeliveryLocationActivity;
import demo.assignment.my_cart.ui.screens.HomeActivity;
import demo.assignment.my_cart.ui.screens.listeners.CartUpdateListener;

public class CartFragment extends Fragment {

    private HomeActivity homeActivity;

    private RecyclerView rvCart;
    private LinearLayout lytBottom;
    private TextView tvEmpty, tvTotalAmount;
    private Button btnCheckout;

    private String totalPayableAmount;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        homeActivity = (HomeActivity) getActivity();
        if (homeActivity != null) {
            homeActivity.setupActionbar(getString(R.string.title_cart), false, false);
        }

        initViews(v);
        setEventListeners();
        loadCart();

        return v;
    }

    private void initViews(View v) {
        rvCart = v.findViewById(R.id.rvCartItems);
        tvEmpty = v.findViewById(R.id.tvEmpty);
        lytBottom = v.findViewById(R.id.lytBottom);
        tvTotalAmount = v.findViewById(R.id.tvTotalAmount);
        btnCheckout = v.findViewById(R.id.btnCheckout);
    }

    private void setEventListeners() {
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(homeActivity, DeliveryLocationActivity.class);
                i.putExtra("total_amount", totalPayableAmount);
                startActivity(i);
            }
        });
    }

    private void loadCart() {
        List<CartItem> list = Collections.list(Collections.enumeration(homeActivity.getCartItems().values()));
        if (list.size() > 0) {
            CartItemAdapter cartItemAdapter = new CartItemAdapter(homeActivity, list, new CartUpdateListener() {
                @Override
                public void onCartUpdated(boolean hasItems, String totalAmount) {
                    // once cart has been updated, and still has at least one item in it, new total amount will be shown to the user
                    if (hasItems) {
                        totalPayableAmount = totalAmount;
                        tvTotalAmount.setText(MessageFormat.format("{0} ${1}", getString(R.string.total_amount), totalAmount));
                        showEmptyCartMsg(false);
                    } else {
                        showEmptyCartMsg(true);
                    }
                }
            });
            rvCart.setAdapter(cartItemAdapter);
            rvCart.setLayoutManager(new LinearLayoutManager(homeActivity));
        } else {
            //if cart item list is empty, "cart is empty" message will be shown to the user
            showEmptyCartMsg(true);
        }
    }

    private void showEmptyCartMsg(boolean isEmpty) {
        lytBottom.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        rvCart.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        tvEmpty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }
}