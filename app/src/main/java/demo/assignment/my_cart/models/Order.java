package demo.assignment.my_cart.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Order implements Parcelable {

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
    private int orderId;
    private String placedDateTime;
    private List<OrderItem> orderItems;
    private String orderStatus;
    private String deliveryLocation;
    private String totalAmount;

    public Order(String placedDateTime, List<OrderItem> orderItems, String orderStatus, String deliveryLocation, String totalAmount) {
        this.placedDateTime = placedDateTime;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
        this.deliveryLocation = deliveryLocation;
        this.totalAmount = totalAmount;
    }

    protected Order(Parcel in) {
        orderId = in.readInt();
        placedDateTime = in.readString();
        orderItems = in.createTypedArrayList(OrderItem.CREATOR);
        orderStatus = in.readString();
        deliveryLocation = in.readString();
        totalAmount = in.readString();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getPlacedDateTime() {
        return placedDateTime;
    }

    public void setPlacedDateTime(String placedDateTime) {
        this.placedDateTime = placedDateTime;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(orderId);
        parcel.writeString(placedDateTime);
        parcel.writeTypedList(orderItems);
        parcel.writeString(orderStatus);
        parcel.writeString(deliveryLocation);
        parcel.writeString(totalAmount);
    }
}
