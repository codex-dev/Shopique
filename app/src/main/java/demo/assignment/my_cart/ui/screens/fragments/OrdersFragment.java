package demo.assignment.my_cart.ui.screens.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import demo.assignment.my_cart.R;
import demo.assignment.my_cart.ui.screens.HomeActivity;

public class OrdersFragment extends Fragment {

    private HomeActivity homeActivity;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_orders, container, false);
        homeActivity = (HomeActivity) getActivity();
        if (homeActivity != null) {
            homeActivity.setupActionbar(getString(R.string.title_orders), false, false);
        }
        return v;
    }

}