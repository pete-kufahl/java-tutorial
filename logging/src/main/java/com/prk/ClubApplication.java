package com.prk;

import com.prk.order.Order;
import com.prk.order.OrderController;
import com.prk.product.ProductRepository;
import com.prk.user.User;
import com.prk.user.UserStatus;
import com.prk.user.UserController;
import com.prk.util.LoggingUtil;

import java.time.LocalDateTime;
import java.util.List;

public class ClubApplication {
    public static void main(String[] args) {
        LoggingUtil.initLogManager();
        try_add_user_add_order();
    }

    public static void try_add_user_add_order() {
        // code that pretends to be a user and place an order
        User user = new User(4, "Anne", "anne.cleves@royal.uk", LocalDateTime.now(), UserStatus.PENDING);
        var userController = new UserController();
        userController.addUser(user);

        Order order = new Order(10, user,
                List.of(ProductRepository.getDummyDataList().get(0)),
                LocalDateTime.of(2025, 6, 5, 0, 0));
        var orderController = new OrderController();
        orderController.addOrder(order);

        System.out.println("after startup, users: " + userController.getAllUsers());
        System.out.println("after startup, orders: " + orderController.getAllOrders());
    }
}