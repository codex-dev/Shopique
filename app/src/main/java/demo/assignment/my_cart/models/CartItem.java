package demo.assignment.my_cart.models;

public class CartItem extends Item {

    private int prodId;
    private double unitPrice;

    public CartItem(int prodId, String productName, String imageUrl, double unitPrice, int qty) {
        super(productName, imageUrl, qty);
        this.prodId = prodId;
        this.unitPrice = unitPrice;
    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

}
