package demo.assignment.my_cart.ui.screens;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Locale;

import demo.assignment.my_cart.R;
import demo.assignment.my_cart.ui.screens.listeners.AppbarListener;
import demo.assignment.my_cart.ui.util.TextFormatter;

public class PaymentActivity extends CommonActivity implements AppbarListener {

    private TextInputEditText etCHName, etCardNo, etExpDate, etCVC;
    private Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        setupActionbar(getString(R.string.payment_method), true, false);

        appBarClickListener = this;

        initViews();
        setEventListeners();
    }

    private void initViews() {
        etCHName = findViewById(R.id.etCHName);
        etCardNo = findViewById(R.id.etCardNo);
        etExpDate = findViewById(R.id.etExpDate);
        etCVC = findViewById(R.id.etCVC);
        btnPay = findViewById(R.id.btnPay);
    }

    private void setEventListeners() {
        etExpDate.setOnFocusChangeListener((view, hasFocus) -> {
            etExpDate.setHint("");

            if (hasFocus) {
                if (TextFormatter.isNullOrEmpty(etExpDate)) {
                    etExpDate.setHint("mm/yy");
                }
                hideSoftKeyboard(this);
                showDatePicker();
            }
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(PaymentActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String selectedDM = MessageFormat.format("{0}/{1}",
                        String.format(Locale.getDefault(), "%02d", month),
                        String.format(Locale.getDefault(), "%02d", year % 100));
                etExpDate.setText(selectedDM);
            }
        }, year, month, day);

        datePickerDialog.setTitle(R.string.select_expiry_date);
        datePickerDialog.setOnCancelListener(dialogInterface -> moveFocusToCVC());
        datePickerDialog.setOnDismissListener(dialogInterface -> moveFocusToCVC());
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void moveFocusToCVC() {
        etExpDate.clearFocus();
        etCVC.requestFocus();
    }

    @Override
    public void onRightIconClicked() {

    }

    @Override
    public void onLeftIconClicked() {
        onBackPressed();
    }
}