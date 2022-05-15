package demo.assignment.my_cart.ui.screens.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class ProductsFragment extends Fragment {

    private static final String TAG = "ProductsFragment : ";
    private HomeActivity homeActivity;
    private ProductGridAdapter productGridAdapter;
    private ArrayList<Product> listProducts;
    private ArrayList<String> listCategories;
    private ChipGroup cgCategories;
    private GridView gvProducts;
    private ProgressBar progressBar;

    private Gson gson;
    private NetworkService networkService;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_products, container, false);

        homeActivity = (HomeActivity) getActivity();
        if (homeActivity != null) {
            homeActivity.setupActionbar(getString(R.string.title_products), false, true);
        }

        listProducts = new ArrayList<>();
        listCategories = new ArrayList<>();
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
    }

    private void setEventListeners() {
        cgCategories.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Chip checkedChip = group.findViewById(checkedId);
                String selectedCategory = checkedChip.getText().toString();

                loadProductsByCategory(selectedCategory.toLowerCase());
            }
        });
    }

    private void loadProductCategories() {
        listCategories.clear();
        showProgressbar(true);

        networkService.getProductCategories(new NetworkListener() {
            @Override
            public void onSuccess(String response) {
                homeActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showProgressbar(false);

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                String category = jsonArray.getString(i);
                                listCategories.add(category);

                                Chip chip = new Chip(homeActivity);
                                chip.setId(i + 1);
                                chip.setText(TextFormatter.capitalizeFirstLetter(category));
                                chip.setCheckable(true);

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
                        Toast.makeText(homeActivity, getString(R.string.err_loading_product_categories), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

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
                        Toast.makeText(homeActivity, getString(R.string.err_loading_products), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

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
                        Toast.makeText(homeActivity, getString(R.string.err_loading_products), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void showProgressbar(boolean isVisible) {
        progressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}