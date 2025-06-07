package com.prk;

import com.prk.order.Order;
import com.prk.order.OrderController;
import com.prk.product.ProductRepository;
import com.prk.user.User;
import com.prk.user.UserStatus;
import com.prk.user.UserController;

import java.time.LocalDateTime;
import java.util.List;

public class ClubApplication {
    public static void main(String[] args) {
        startup();
    }

    public static void startup() {
        // code that pretends to be a user and place an order
        User user = new User(4, "Pancho", "pancho@dogs.org", LocalDateTime.now(), UserStatus.PENDING);
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