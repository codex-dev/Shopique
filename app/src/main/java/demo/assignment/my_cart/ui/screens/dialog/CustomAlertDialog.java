package demo.assignment.my_cart.ui.screens.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import demo.assignment.my_cart.R;

public class CustomAlertDialog extends DialogFragment {

    private Context context;
    private AlertDialogListener listener;
    private int title, message, positiveButtonText, negativeButtonText, icon;

    public CustomAlertDialog(Context context, DialogType dialogType, AlertDialogListener listener) {
        this.context = context;
        this.listener = listener;

        switch (dialogType) {
            case SIGN_OUT:
                title = R.string.sign_out;
                icon = R.drawable.ic_alert;
                message = R.string.are_you_sure_sign_out;
                positiveButtonText = R.string.yes;
                negativeButtonText = R.string.no;
                break;
            case EXIT_APP:
                title = R.string.exit_app;
                icon = R.drawable.ic_alert;
                message = R.string.are_you_sure_exit_app;
                positiveButtonText = R.string.yes;
                negativeButtonText = R.string.no;
                break;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle(title).setIcon(icon)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onPositiveButtonClicked();
                    }
                })
                .setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        listener.onNegativeButtonClicked();
                        dismiss();
                    }
                })
                .setCancelable(false);

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
