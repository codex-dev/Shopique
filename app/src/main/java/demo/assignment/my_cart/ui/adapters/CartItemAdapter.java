package demo.assignment.my_cart.ui.adapters;

import static demo.assignment.my_cart.ui.util.TextFormatter.isNullOrEmpty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import demo.assignment.my_cart.R;
import demo.assignment.my_cart.models.CartItem;
import demo.assignment.my_cart.storage.SharedPrefListener;
import demo.assignment.my_cart.ui.screens.CommonActivity;
import demo.assignment.my_cart.ui.screens.listeners.CartUpdateListener;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {

    private final List<CartItem> listItems;
    private final Context context;
    private final CommonActivity commonActivity;
    private final CartUpdateListener listener;
    private double totalAmount;

    public CartItemAdapter(Context context, List<CartItem> items, CartUpdateListener listener) {
        this.context = context;
        this.listItems = items;
        this.listener = listener;

        commonActivity = (CommonActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // inflate the custom layout
        View itemView = inflater.inflate(R.layout.item_cart, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem cartItem = listItems.get(position);
        final int[] quantity = {cartItem.getQty()};
        double subTotal = cartItem.getQty() * cartItem.getUnitPrice();

        // when dataset change has been notified, reset total amount before recalculating total amount
        if (holder.getAdapterPosition() == 0) {
            totalAmount = 0;
        }
        totalAmount += subTotal;

        if (!isNullOrEmpty(cartItem.getImageUrl())) {
            Glide.with(context)
                    .load(cartItem.getImageUrl())
                    .error(R.drawable.img_placeholder)
                    .into(holder.ivProductImage);
        }

        holder.tvProductTitle.setText(cartItem.getProductName());
        holder.tvQuantity.setText(String.valueOf(cartItem.getQty()));
        holder.tvSubTotal.setText(String.format(Locale.getDefault(), "$%.02f", subTotal));

        // after calculating subtotal of last cart item, notify cart fragment to show total amount
        if (getItemCount() > 0 && holder.getAdapterPosition() == getItemCount() - 1) {
            listener.onCartUpdated(true, String.format(Locale.getDefault(), "%.02f", totalAmount));
        }

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity[0] < 10) {
                    quantity[0] += 1;
                    updateQuantity(holder.getAdapterPosition(), cartItem.getProdId(), quantity[0]);
                }
            }
        });

        holder.btnDeduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity[0] > 1) {
                    quantity[0] -= 1;
                    updateQuantity(holder.getAdapterPosition(), cartItem.getProdId(), quantity[0]);
                }
            }
        });

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(holder.getAdapterPosition(), cartItem.getProdId());
            }
        });
    }

    private void removeItem(int position, int prodId) {
        //update shared preference
        LinkedHashMap<Integer, CartItem> hashMap = commonActivity.getCartItems();
        hashMap.remove(prodId);

        commonActivity.updateCartItems(hashMap, new SharedPrefListener() {
            @Override
            public void onSuccess() {
                listItems.remove(position);
                if (getItemCount() == 0) {
                    // if all items are removed, notify cart fragment to show empty cart message
                    listener.onCartUpdated(false, "0");
                }
//                notifyItemRemoved(position);

                // if the item list is not empty, it will update the recyclerview
                notifyDataSetChanged();
            }

            @Override
            public void onError() {

            }
        });
    }

    private void updateQuantity(int position, int prodId, int newQty) {
        // update shared preference
        LinkedHashMap<Integer, CartItem> hashMap = commonActivity.getCartItems();
        CartItem item = hashMap.get(prodId);
        if (item != null) {
            item.setQty(newQty);
        }
        commonActivity.updateCartItems(hashMap, new SharedPrefListener() {
            @Override
            public void onSuccess() {
                listItems.get(position).setQty(newQty);
//                notifyItemChanged(position);

                // update recyclerview to show modified data
                notifyDataSetChanged();
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvProductTitle, tvQuantity, tvSubTotal, btnAdd, btnDeduct;
        ImageView ivProductImage, btnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductTitle = itemView.findViewById(R.id.tvProductTitle);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvSubTotal = itemView.findViewById(R.id.tvSubTotal);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            btnDeduct = itemView.findViewById(R.id.btnDeduct);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}
