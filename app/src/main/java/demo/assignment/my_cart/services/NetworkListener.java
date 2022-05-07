package demo.assignment.my_cart.services;

public interface NetworkListener {
    void onSuccess(String response);

    void onError(String error);
}
