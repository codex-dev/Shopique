package demo.assignment.my_cart.ui.screens.fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import demo.assignment.my_cart.R;
import demo.assignment.my_cart.models.Product;
import demo.assignment.my_cart.network.NetworkListener;
import demo.assignment.my_cart.network.NetworkService;
import demo.assignment.my_cart.ui.adapters.ProductGridAdapter;
import demo.assignment.my_cart.ui.screens.HomeActivity;
import demo.assignment.my_cart.ui.util.TextFormatter;

/**
 * Fragment to display and filter products using given product categories
 */
public class ProductsFragment extends Fragment {

    private static final String TAG = "ProductsFragment : ";
    private HomeActivity homeActivity;
    private ProductGridAdapter productGridAdapter;
    private ArrayList<Product> listProducts;
    private ChipGroup cgCategories;
    private GridView gvProducts;
    private ProgressBar progressBar;
    private LinearLayout lytNoConnection;
    private Button btnRetry;

    private Gson gson;
    private NetworkService networkService;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_products, container, false);

        homeActivity = (HomeActivity) getActivity();
        if (homeActivity != null) {
            homeActivity.setupActionbar(getString(R.string.title_products), false, true);
        }

        listProducts = new ArrayList<>();
        networkService = NetworkService.getInstance();
        gson = new Gson();

        initViews(v);
        loadProductCategories();
        loadProducts();
        setEventListeners();

        return v;
    }

    private void initViews(View v) {
        cgCategories = v.findViewById(R.id.cgCategories);
        gvProducts = v.findViewById(R.id.gvProducts);
        progressBar = v.findViewById(R.id.progress_circular);
        lytNoConnection = v.findViewById(R.id.lytNoConnection);
        btnRetry = v.findViewById(R.id.btnRetry);
    }

    private void setEventListeners() {
        cgCategories.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                String selectedCategory = null;

                for (int i = 1; i <= group.getChildCount(); i++) {
                    Chip chip = group.findViewById(i);

                    // update chip text and background colors based on user selection
                    if (checkedId == chip.getId()) {
                        selectedCategory = chip.getText().toString();

                        chip.setTextColor(getResources().getColor(R.color.white));
                        chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(homeActivity, R.color.blue_default)));
                    } else {
                        chip.setTextColor(getResources().getColor(R.color.black));
                        chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(homeActivity, R.color.light_grey)));
                    }
                }

                if (selectedCategory != null)
                    loadProductsByCategory(selectedCategory.toLowerCase());
            }
        });

        // retry loading products and categories once the internet connection has been restored
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadProductCategories();
                loadProducts();
                lytNoConnection.setVisibility(View.GONE);
            }
        });
    }

    /**
     * Load product categories from api request
     */
    private void loadProductCategories() {
        showProgressbar(true);

        networkService.getProductCategories(new NetworkListener() {
            @Override
            public void onSuccess(String response) {
                homeActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showProgressbar(false);

                        try {
                            ArrayList<String> listCategories = new ArrayList<>();
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                String category = jsonArray.getString(i);
                                listCategories.add(category);

                                Chip chip = new Chip(homeActivity);
                                chip.setId(i + 1);
                                chip.setText(TextFormatter.capitalizeFirstLetter(category));
                                chip.setCheckable(true);
                                chip.setCheckedIconVisible(false);

                                cgCategories.addView(chip);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, error);
                homeActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showProgressbar(false);
                        lytNoConnection.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }

    /**
     * Load products from api request
     */
    private void loadProducts() {
        listProducts.clear();
        showProgressbar(true);

        networkService.getAllProducts(new NetworkListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Product product = gson.fromJson(jsonArray.getString(i), Product.class);
                        listProducts.add(product);
                    }

                    homeActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showProgressbar(false);

                            productGridAdapter = new ProductGridAdapter(homeActivity, R.layout.item_product, listProducts);
                            gvProducts.setAdapter(productGridAdapter);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, error);
                homeActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showProgressbar(false);
                        lytNoConnection.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }

    /**
     * Filter products by category from api request
     */
    private void loadProductsByCategory(String selectedCategory) {
        listProducts.clear();
        showProgressbar(true);
        networkService.getProductsByCategory(selectedCategory, new NetworkListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Product product = gson.fromJson(jsonArray.getString(i), Product.class);
                        listProducts.add(product);
                    }

                    homeActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showProgressbar(false);

                            productGridAdapter = new ProductGridAdapter(homeActivity, R.layout.item_product, listProducts);
                            gvProducts.setAdapter(productGridAdapter);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, error);
                homeActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showProgressbar(false);
                        lytNoConnection.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }

    private void showProgressbar(boolean isVisible) {
        progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}