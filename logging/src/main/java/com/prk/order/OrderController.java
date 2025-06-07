package com.prk.order;

import com.prk.user.User;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderController {
    private static final OrderService service = new OrderService();
    private static final Logger LOGGER = Logger.getLogger(OrderController.class.getName());

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

        LOGGER.log(Level.FINE, "at post endpoint for order: " + order.toString());
        return service.addOrder(order);
    }

    // delete
    public boolean deleteOrder(Order order) {
        return service.deleteOrder(order);
    }
}
