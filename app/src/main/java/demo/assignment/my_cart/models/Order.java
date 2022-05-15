package demo.assignment.my_cart.models;

import java.util.List;

enum OrderStatus {
    PLACED, DISPATCHED, DELIVERED
}

public class Order {
    private String orderReference;
    private String placedDateTime;
    private List<OrderItem> orderItems;
    private OrderStatus orderStatus;
}
