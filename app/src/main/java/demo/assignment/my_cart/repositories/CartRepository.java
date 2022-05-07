package demo.assignment.my_cart.repositories;

import java.util.ArrayList;

import demo.assignment.my_cart.models.CartItem;

public class CartRepository {
    private static CartRepository instance;
    private ArrayList<CartItem> listCartItems;

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

}
