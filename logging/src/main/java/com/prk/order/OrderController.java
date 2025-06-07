package com.prk.order;

import com.prk.user.User;

import java.util.List;

public class OrderController {
    private static final OrderService service = new OrderService();

    // get all
    public List<Order> getAllOrders() {
        return service.getAllOrders();
    }

    // get all, by user
    public List<Order> getAllOrdersByUser(User user) {
        return service.getAllOrdersByUser(user);
    }

    // post
    public boolean addOrder(Order order) {
        return service.addOrder(order);
    }

    // delete
    public boolean deleteOrder(Order order) {
        return service.deleteOrder(order);
    }
}
