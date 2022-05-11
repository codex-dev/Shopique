package demo.assignment.my_cart.ui.screens.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import demo.assignment.my_cart.R;

public class CustomDialog extends Dialog implements View.OnClickListener {

    private Activity activity;

    public CustomDialog(@NonNull Context context) {
        super(context);

        activity = (Activity) context;
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_custom);

//        findViewById(R.id.)
    }

    @Override
    public void onClick(View view) {

    }
}
