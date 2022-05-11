package demo.assignment.my_cart.services;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkService {
    private static NetworkService networkService;
    private final OkHttpClient okHttpClient;

    private NetworkService() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);

        okHttpClient = builder.build();
    }

    public static NetworkService getInstance() {
        if (networkService == null) {
            networkService = new NetworkService();
        }
        return networkService;
    }

    public void getAllProducts(NetworkListener listener) {
        String url = "https://fakestoreapi.com/products";

        initiateRequest(url, listener);
    }

    public void getProductCategories(NetworkListener listener) {
        String url = "https://fakestoreapi.com/products/categories";

        initiateRequest(url, listener);
    }

    public void getProductsByCategory(String category, NetworkListener listener) {
        String url = "https://fakestoreapi.com/products/category/" + category;

        initiateRequest(url, listener);
    }

    private void initiateRequest(String url, NetworkListener listener) {
        Request request = new Request.Builder().url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onSuccess(response.body().string());
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                listener.onError(e.getMessage());
            }
        });
    }
}
