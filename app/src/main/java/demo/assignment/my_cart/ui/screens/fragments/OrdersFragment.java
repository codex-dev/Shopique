package demo.assignment.my_cart.ui.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import demo.assignment.my_cart.R;
import demo.assignment.my_cart.models.Order;
import demo.assignment.my_cart.ui.adapters.OrderAdapter;
import demo.assignment.my_cart.ui.screens.HomeActivity;

public class OrdersFragment extends Fragment {

    private HomeActivity homeActivity;

    private RecyclerView rvOrders;
    private TextView tvEmpty;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_orders, container, false);
        homeActivity = (HomeActivity) getActivity();
        if (homeActivity != null) {
            homeActivity.setupActionbar(getString(R.string.title_orders), false, false);
        }

        initViews(v);
        loadOrders();
        return v;
    }

    private void initViews(View v) {
        rvOrders = v.findViewById(R.id.rvOrders);
        tvEmpty = v.findViewById(R.id.tvEmpty);
    }

    private void loadOrders() {
        List<Order> list = Collections.list(Collections.enumeration(homeActivity.getOrders().values()));
        if (list.size() > 0) {
            OrderAdapter orderAdapter = new OrderAdapter(homeActivity, list);
            rvOrders.setAdapter(orderAdapter);
            rvOrders.setLayoutManager(new LinearLayoutManager(homeActivity));
        } else {
            //if cart item list is empty, "cart is empty" message will be shown to the user
            showEmptyCartMsg(true);
        }
    }

    private void showEmptyCartMsg(boolean isEmpty) {
        rvOrders.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        tvEmpty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }

}