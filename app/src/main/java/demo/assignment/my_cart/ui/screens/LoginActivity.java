package demo.assignment.my_cart.ui.screens;

import static demo.assignment.my_cart.ui.util.TextFormatter.getTrimmedText;
import static demo.assignment.my_cart.ui.util.TextFormatter.isNullOrEmpty;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import demo.assignment.my_cart.R;

public class LoginActivity extends CommonActivity {

    private TextInputEditText etUsername;
    private TextInputEditText etPassword;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dismissKeyboard(findViewById(R.id.login_activity), LoginActivity.this);

        if (isUserSignedIn()) {
            proceedToHome();
        } else {
            initViews();
            setEventListeners();
        }
    }

    private void initViews() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
    }

    private void setEventListeners() {
        btnSignIn.setOnClickListener(view -> {
            if (hasValidInput()) {
                signInUser();
            }
        });
    }

    private boolean hasValidInput() {
        if (isNullOrEmpty(etUsername)) {
            etUsername.requestFocus();
            etUsername.setError(getString(R.string.err_input_username));
            return false;
        }
        if (isNullOrEmpty(etPassword)) {
            etPassword.requestFocus();
            etPassword.setError(getString(R.string.err_input_password));
            return false;
        }
        if (!getTrimmedText(etUsername).equals("user") || !getTrimmedText(etPassword).equals("1234")) {
            Toast.makeText(this, getString(R.string.err_invalid_login_credentials), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        exitApp();
    }
}