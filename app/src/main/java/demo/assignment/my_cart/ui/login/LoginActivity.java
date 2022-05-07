package demo.assignment.my_cart.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import demo.assignment.my_cart.R;
import demo.assignment.my_cart.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private TextInputEditText etUsername;
    private TextInputEditText etPassword;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        etUsername = binding.etUsername;
        etPassword = binding.etPassword;
        btnSignIn = binding.btnSignIn;

        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasValidInput()) {
//                    loginViewModel.setUsername(getTrimmedText(etUsername));
                }
            }
        });

//        loginViewModel.getUsername().observe(this, etUsername::setText);
    }

    private boolean hasValidInput() {
        if (isNullOrEmpty(etUsername)) {
            etUsername.requestFocus();
            etUsername.setError(getString(R.string.err_input_username), getDrawable(R.drawable.ic_error));
            return false;
        }
        if (isNullOrEmpty(etPassword)) {
            etPassword.requestFocus();
            etPassword.setError(getString(R.string.err_input_password), getDrawable(R.drawable.ic_error));
            return false;
        }
        return true;
    }

    private boolean isNullOrEmpty(EditText et) {
        return et == null || getTrimmedText(et).isEmpty();
    }

    private String getTrimmedText(EditText et) {
        return et.getText().toString().trim();
    }
}