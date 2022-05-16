package demo.assignment.my_cart.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.MessageFormat;
import java.util.List;

import demo.assignment.my_cart.R;
import demo.assignment.my_cart.models.Order;
import demo.assignment.my_cart.models.OrderStatus;
import demo.assignment.my_cart.ui.screens.OrderDetailsActivity;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private final List<Order> listOrders;
    private final Context context;

    public OrderAdapter(Context context, List<Order> items) {
        this.context = context;
        this.listOrders = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // inflate the custom layout
        View itemView = inflater.inflate(R.layout.item_order, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = listOrders.get(position);

        holder.tvOrderId.setText(MessageFormat.format("#{0}", order.getOrderId()));

        switch (order.getOrderStatus()) {
            case PENDING:
                holder.tvOrderStatus.setText(OrderStatus.PENDING.name());
                holder.tvOrderStatus.setTextColor(ContextCompat.getColor(context, R.color.status_pending));
                break;
            case RECEIVED:
                holder.tvOrderStatus.setText(OrderStatus.RECEIVED.name());
                holder.tvOrderStatus.setTextColor(ContextCompat.getColor(context, R.color.status_received));
        }

        holder.tvDateTime.setText(order.getPlacedDateTime());
        holder.tvDelLocation.setText(order.getDeliveryLocation());
        holder.tvTotalAmount.setText(MessageFormat.format("${0}", order.getTotalAmount()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, OrderDetailsActivity.class);
                i.putExtra("order", order);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOrders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvOrderId, tvOrderStatus, tvDateTime, tvDelLocation, tvTotalAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvDelLocation = itemView.findViewById(R.id.tvDelLocation);
            tvTotalAmount = itemView.findViewById(R.id.tvTotalAmount);
        }
    }
}
