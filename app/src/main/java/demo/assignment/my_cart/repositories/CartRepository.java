package demo.assignment.my_cart.repositories;

import java.util.ArrayList;
import java.util.List;

import demo.assignment.my_cart.models.CartItem;
import demo.assignment.my_cart.models.Product;

public class CartRepository {
    private static CartRepository instance;
    private final ArrayList<CartItem> listCartItems;

    private CartRepository() {
        listCartItems = new ArrayList<>();
    }

    public static CartRepository getInstance() {
        if (instance == null) {
            instance = new CartRepository();
        }
        return instance;
    }

    //TODO: retrieve products, categories and filtered products from api

    public List<Product> getAllProducts(String response) {
        return new ArrayList<Product>();
    }

    public List<String> getProductCategories(String response) {
        return new ArrayList<>();
    }
//
//    public List

}
