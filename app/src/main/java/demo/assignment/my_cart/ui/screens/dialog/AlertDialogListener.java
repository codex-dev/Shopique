package demo.assignment.my_cart.ui.screens.dialog;

/**
 * Action listener of custom alert dialog to notify parent activity
 * after one of provided actions has been executed
 */
public interface AlertDialogListener {
    void onPositiveButtonClicked();
    void onNegativeButtonClicked();
}
