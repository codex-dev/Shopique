package demo.assignment.my_cart.services;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkService {
    private static NetworkService networkService;
    private final OkHttpClient okHttpClient;

    private NetworkService() {
        this.okHttpClient = new OkHttpClient();
    }

    public static NetworkService getInstance() {
        if (networkService == null) {
            networkService = new NetworkService();
        }
        return networkService;
    }

    public void getAllProducts(NetworkListener listener) {
        String url = "https://fakestoreapi.com/products";

        Request request = new Request.Builder()
                .url(url)
                .build();

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

    public void getProductCategories(NetworkListener listener) {
        String url = "https://fakestoreapi.com/products/category";

        Request request = new Request.Builder()
                .url(url)
                .build();

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

    public void getProductsOfCategory(String category, NetworkListener listener) {
        String url = "https://fakestoreapi.com/products/category/" + category;

        Request request = new Request.Builder()
                .url(url)
                .build();

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
