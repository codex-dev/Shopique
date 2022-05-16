package demo.assignment.my_cart.ui.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.MessageFormat;
import java.util.TreeMap;

import demo.assignment.my_cart.R;
import demo.assignment.my_cart.models.Order;
import demo.assignment.my_cart.models.OrderItem;
import demo.assignment.my_cart.storage.SharedPrefListener;
import demo.assignment.my_cart.ui.screens.listeners.AppbarListener;

public class OrderDetailsActivity extends CommonActivity implements AppbarListener {

    private Order order;
    private TextView tvOrderId, tvOrderStatus, tvDateTime, tvDelLocation, tvTotalAmount;
    private LinearLayout lytOrderItems;
    private Button btnScanQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        appBarClickListener = this;
        String orderJson = getIntent().getStringExtra("order");
        order = new Gson().fromJson(orderJson, Order.class);
        setupActionbar(getString(R.string.order) + " #" + order.getOrderId(), true, false);

        initViews();
        setValues();
        setActionListeners();
    }

    private void initViews() {
        tvOrderId = findViewById(R.id.tvOrderId);
        tvOrderStatus = findViewById(R.id.tvOrderStatus);
        tvDateTime = findViewById(R.id.tvDateTime);
        tvDelLocation = findViewById(R.id.tvDelLocation);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        lytOrderItems = findViewById(R.id.lytOrderItems);
        btnScanQR = findViewById(R.id.btnScanQR);
    }

    private void setValues() {
        tvOrderId.setText(MessageFormat.format("#{0}", order.getOrderId()));

        switch (order.getOrderStatus()) {
            case "PENDING":
                btnScanQR.setVisibility(View.VISIBLE);
                tvOrderStatus.setText(getResources().getString(R.string.pending));
                tvOrderStatus.setTextColor(ContextCompat.getColor(this, R.color.status_pending));
                break;
            case "RECEIVED":
                btnScanQR.setVisibility(View.GONE);
                tvOrderStatus.setText(getResources().getString(R.string.received));
                tvOrderStatus.setTextColor(ContextCompat.getColor(this, R.color.status_received));
        }

        tvDateTime.setText(order.getPlacedDateTime());
        tvDelLocation.setText(order.getDeliveryLocation());
        tvTotalAmount.setText(MessageFormat.format("${0}", order.getTotalAmount()));

        for (OrderItem orderItem : order.getOrderItems()) {
            LinearLayout childLayout = new LinearLayout(this);
            childLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            childLayout.setOrientation(LinearLayout.HORIZONTAL);
            childLayout.setWeightSum(3);

            LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f);

            TextView tvProductName = new TextView(this);
            tvProductName.setLayoutParams(tvParams);
            tvProductName.setText(orderItem.getProductName());
            tvProductName.setTextColor(ContextCompat.getColor(this, R.color.default_label_color));
            tvProductName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tvProductName.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_underline));
            tvProductName.setMaxLines(1);
            tvProductName.setGravity(Gravity.START);

            TextView tvQty = new TextView(this);
            tvQty.setLayoutParams(tvParams);
            tvQty.setText(MessageFormat.format("x{0}", orderItem.getQty()));
            tvQty.setTextColor(ContextCompat.getColor(this, R.color.default_label_color));
            tvQty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tvQty.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_underline));
            tvQty.setGravity(Gravity.CENTER);

            TextView tvSubTotal = new TextView(this);
            tvSubTotal.setLayoutParams(tvParams);
            tvSubTotal.setText(MessageFormat.format("${0}", orderItem.getSubTotal()));
            tvSubTotal.setTextColor(ContextCompat.getColor(this, R.color.default_label_color));
            tvSubTotal.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            tvSubTotal.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_underline));
            tvSubTotal.setGravity(Gravity.END);

            childLayout.addView(tvProductName);
            childLayout.addView(tvQty);
            childLayout.addView(tvSubTotal);
            lytOrderItems.addView(childLayout);
        }
    }

    private void setActionListeners() {
        btnScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(OrderDetailsActivity.this);
                intentIntegrator.setPrompt("Scan QR Code");
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
                String result = intentResult.getContents();
                int oId = Integer.parseInt(result.split("#")[1]);

                // if order id equals to the id in qr code, mark the order as received
                if (order.getOrderId() == oId) {
                    TreeMap<Integer, Order> tm = getOrders();
                    tm.get(order.getOrderId()).setOrderStatus("RECEIVED");

                    updateOrders(tm, new SharedPrefListener() {
                        @Override
                        public void onSuccess() {
                            btnScanQR.setVisibility(View.GONE);
                            tvOrderStatus.setText(getResources().getString(R.string.received));
                            tvOrderStatus.setTextColor(ContextCompat.getColor(OrderDetailsActivity.this, R.color.status_received));
                            Toast.makeText(OrderDetailsActivity.this, "Order has been marked as RECEIVED", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError() {

                        }
                    });
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRightIconClicked() {

    }

    @Override
    public void onLeftIconClicked() {
        onBackPressed();
    }
}