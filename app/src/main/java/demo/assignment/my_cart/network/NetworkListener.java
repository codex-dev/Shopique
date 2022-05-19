package demo.assignment.my_cart.network;

/**
 * Send api responses back to parent fragment once received
 */
public interface NetworkListener {
    void onSuccess(String response);

    void onError(String error);
}
