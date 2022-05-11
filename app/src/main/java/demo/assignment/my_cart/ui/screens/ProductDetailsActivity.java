package demo.assignment.my_cart.ui.screens;

import static demo.assignment.my_cart.ui.util.TextFormatter.isNullOrEmpty;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.MessageFormat;

import demo.assignment.my_cart.R;
import demo.assignment.my_cart.models.Product;
import demo.assignment.my_cart.ui.screens.listeners.AppbarListener;

public class ProductDetailsActivity extends CommonActivity implements AppbarListener {

    private ImageView ivProduct;
    private TextView tvTitle, tvRating, tvRateCount, tvDescription, tvQty, tvPrice;
    private Button btnPlus, btnMinus, btnAddToCart;

    private int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        setupActionbar(getString(R.string.product_details), true, false);

        appBarClickListener = this;

        initViews();
        setValues();
        setEventListeners();
    }

    private void initViews() {
        ivProduct = findViewById(R.id.ivProduct);
        tvTitle = findViewById(R.id.tvTitle);
        tvPrice = findViewById(R.id.tvPrice);
        tvRating = findViewById(R.id.tvRating);
        tvRateCount = findViewById(R.id.tvRateCount);
        tvDescription = findViewById(R.id.tvDescription);
        btnPlus = findViewById(R.id.btnPlus);
        tvQty = findViewById(R.id.tvQty);
        btnMinus = findViewById(R.id.btnMinus);
        btnAddToCart = findViewById(R.id.btnAddToCart);
    }

    private void setValues() {
        Product product = getIntent().getParcelableExtra("product");

        if (!isNullOrEmpty(product.getImage())) {
            Glide.with(this).load(product.getImage()).into(ivProduct);
        }

        tvTitle.setText(product.getTitle());
        tvPrice.setText(MessageFormat.format("${0}", product.getPrice()));
        tvRating.setText(String.valueOf(product.getRating().getRate()));
        tvRateCount.setText(MessageFormat.format("({0} {1})", product.getRating().getCount(), getString(R.string.lbl_reviews_count)));
        tvDescription.setText(product.getDescription());
        updateQty();
    }

    private void updateQty() {
        tvQty.setText(String.valueOf(quantity));
    }

    private void setEventListeners() {
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity < 10) {
                    quantity++;
                    updateQty();
                }
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 0) {
                    quantity--;
                    updateQty();
                }
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity > 0) {
//                    if(item is already added to cart){
//                      update qty
//                    }
                    // TODO add item to sharedPref with qty
                } else {
                    Toast.makeText(ProductDetailsActivity.this, "Quantity cannot be 0", Toast.LENGTH_SHORT).show();
                }
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