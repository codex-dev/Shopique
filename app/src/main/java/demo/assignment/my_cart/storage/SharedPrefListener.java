package demo.assignment.my_cart.storage;

/**
 * Shared preferences update listener to notify parent activity/fragment once write action
 * has been performed
 */
public interface SharedPrefListener {
    void onSuccess();

    void onError();
}
