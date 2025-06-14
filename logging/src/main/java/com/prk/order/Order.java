package com.prk.order;

import com.prk.product.Product;
import com.prk.user.User;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private long id;
    private User user;
    private List<Product> products;
    private LocalDateTime orderDateTime;
    private OrderStatus orderStatus;

    public Order() { }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order {");
        sb.append("id=").append(id);
        sb.append(", user=").append(user);
        sb.append(", products=").append(products);
        sb.append(", orderDateTime=").append(orderDateTime);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append('}');
        return sb.toString();
    }

    public Order(long id, User user, List<Product> products, LocalDateTime orderDateTime) {
        this.user = user;
        this.products = products;
        this.orderDateTime = orderDateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
