package demo.assignment.my_cart.ui.products;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import demo.assignment.my_cart.models.Product;

public class ProductsViewModel extends ViewModel {

    private MutableLiveData<List<Product>> listProducts;
    private MutableLiveData<List<String>> listCategories;

    public LiveData<List<Product>> getProducts() {
        if(listProducts == null){
            listProducts = new MutableLiveData<>();
            fetchProducts();
        }
        return listProducts;
    }

    private void fetchProducts() {

    }
}