package demo.assignment.my_cart.models;

public class OrderItem extends Item {
    private String subTotal;

    public OrderItem(String productName, String imageUrl, int qty) {
        super(productName, imageUrl, qty);
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }
}
