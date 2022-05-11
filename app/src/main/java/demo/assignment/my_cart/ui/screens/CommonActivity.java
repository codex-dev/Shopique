package demo.assignment.my_cart.ui.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import demo.assignment.my_cart.R;
import demo.assignment.my_cart.storage.SharedPref;
import demo.assignment.my_cart.ui.screens.listeners.AppbarListener;

public class CommonActivity extends AppCompatActivity {

    protected AppbarListener appBarClickListener;
    private SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = new SharedPref(this);
    }

    public void dismissKeyboard(View view, Activity activity) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(activity);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                dismissKeyboard(innerView, activity);
            }
        }
    }

    protected void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager
                    .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void setupActionbar(String title, boolean showLeftIcon, boolean showRightIcon) {
        Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.appbar_action);
        View actionbarView = getSupportActionBar().getCustomView();

        ImageView ivLeft = actionbarView.findViewById(R.id.ivLeft);
        ImageView ivRight = actionbarView.findViewById(R.id.ivRight);
        TextView tvTitle = actionbarView.findViewById(R.id.tvTitle);

        tvTitle.setPadding(0, 0, 0, 0);
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setText(title);

        ivLeft.setVisibility(showLeftIcon ? View.VISIBLE : View.GONE);
        ivRight.setVisibility(showRightIcon ? View.VISIBLE : View.GONE);
        ivLeft.setOnClickListener(view -> appBarClickListener.onLeftIconClicked());
        ivRight.setOnClickListener(view -> appBarClickListener.onRightIconClicked());
    }

    protected void signInUser() {
        sharedPref.setLoggedIn(true);
        proceedToHome();
    }

    protected void signOutUser() {
        sharedPref.setLoggedIn(false);
        gotoLogin();
    }

    protected boolean isUserSignedIn() {
        return sharedPref.isLoggedIn();
    }

    protected void proceedToHome() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }

    private void gotoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    protected void exitApp() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}