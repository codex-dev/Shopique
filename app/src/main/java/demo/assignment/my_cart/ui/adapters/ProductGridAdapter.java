package demo.assignment.my_cart.ui.adapters;

import static demo.assignment.my_cart.ui.util.TextFormatter.isNullOrEmpty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;

import com.bumptech.glide.Glide;

import java.text.MessageFormat;
import java.util.ArrayList;

import demo.assignment.my_cart.R;
import demo.assignment.my_cart.models.Product;
import demo.assignment.my_cart.ui.screens.ProductDetailsActivity;

public class ProductGridAdapter extends ArrayAdapter<Product> {

    private final Context context;
    private final int resource;

    public ProductGridAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        Product product = getItem(position);
        ImageView ivProduct = listItemView.findViewById(R.id.ivProduct);
        TextView tvTitle = listItemView.findViewById(R.id.tvTitle);
        TextView tvPrice = listItemView.findViewById(R.id.tvPrice);
        TextView tvRating = listItemView.findViewById(R.id.tvRating);
        AppCompatImageButton btnAddToCart = listItemView.findViewById(R.id.btnAddToCart);

        if (!isNullOrEmpty(product.getImage())) {
            Glide.with(context)
                    .load(product.getImage())
                    .error(R.drawable.img_placeholder)
                    .into(ivProduct);
        }
        tvTitle.setText(product.getTitle());
        tvPrice.setText(MessageFormat.format("${0}", product.getPrice()));
        tvRating.setText(String.valueOf(product.getRating().getRate()));
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO save item to shared pref cart list
                // TODO increase cart item count
            }
        });

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("product", product);
                context.startActivity(intent);
            }
        });

        return listItemView;
    }
}
