package demo.assignment.my_cart.models;

public class Item {
    private String productName;
    private String imageUrl;
    private int qty;

    public Item(String productName, String imageUrl, int qty) {
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.qty = qty;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
