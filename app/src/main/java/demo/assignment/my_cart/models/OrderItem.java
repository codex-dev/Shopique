package demo.assignment.my_cart.models;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderItem extends Item implements Parcelable {
    public static final Creator<OrderItem> CREATOR = new Creator<OrderItem>() {
        @Override
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        @Override
        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };
    private String subTotal;

    public OrderItem(String productName, String imageUrl, int qty) {
        super(productName, imageUrl, qty);
    }

    protected OrderItem(Parcel in) {
        super(in);
        subTotal = in.readString();
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(subTotal);
    }
}
