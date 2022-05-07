package demo.assignment.my_cart.repositories;

import java.util.ArrayList;

import demo.assignment.my_cart.models.Product;

public class ProductRepository {
    private static ProductRepository instance;
    private ArrayList<Product> listCartItems;

    private ProductRepository() {
        listCartItems = new ArrayList<>();
    }

    public static ProductRepository getInstance() {
        if (instance == null) {
            instance = new ProductRepository();
        }
        return instance;
    }

    // TODO: save cart badge count and cart item list to shared pref and retrieve

}
