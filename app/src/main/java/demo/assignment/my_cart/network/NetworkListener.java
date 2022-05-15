package demo.assignment.my_cart.network;

public interface NetworkListener {
    void onSuccess(String response);

    void onError(String error);
}
