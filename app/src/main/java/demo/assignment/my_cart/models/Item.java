package demo.assignment.my_cart.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
    private String productName;
    private String imageUrl;
    private int qty;

    public Item(String productName, String imageUrl, int qty) {
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.qty = qty;
    }

    protected Item(Parcel in) {
        productName = in.readString();
        imageUrl = in.readString();
        qty = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productName);
        parcel.writeString(imageUrl);
        parcel.writeInt(qty);
    }
}
