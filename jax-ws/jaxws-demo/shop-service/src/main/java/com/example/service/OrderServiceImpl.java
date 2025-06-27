package com.example.service;

import com.example.model.MenuItem;
import com.example.model.Order;
import com.example.model.OrderItem;

import jakarta.jws.WebService;
import java.util.*;

@WebService(
        serviceName = "OrderService",
        portName = "OrderServicePort",
        targetNamespace = "http://service.example.com/",
        endpointInterface = "com.example.service.OrderService"
)
public class OrderServiceImpl implements OrderService {

    private final Map<String, Order> orderMap = new HashMap<>();
    private final List<MenuItem> menu = List.of(
        new MenuItem("Coffee", 3.0),
        new MenuItem("Bagel", 2.5),
        new MenuItem("Muffin", 2.0),
        new MenuItem("Tea", 2.5)
    );

    @Override
    public String placeOrder(String item, int quantity) {
        String orderId = UUID.randomUUID().toString();
        Order order = new Order();
        order.setId(orderId);
        order.getItems().add(new OrderItem(item, quantity));
        orderMap.put(orderId, order);
        return orderId;
    }

    @Override
    public List<MenuItem> getMenu() {
        return menu;
    }

    @Override
    public double getOrderTotal(String orderId) {
        Order order = orderMap.get(orderId);
        if (order == null) return 0.0;
        return order.getItems().stream()
                .mapToDouble(i -> i.getQuantity() * getPrice(i.getName()))
                .sum();
    }

    @Override
    public String addItemToOrder(String orderId, String itemName, int quantity) {
        Order order = orderMap.get(orderId);
        if (order == null) return "Order not found";
        order.getItems().add(new OrderItem(itemName, quantity));
        return "Item added";
    }

    @Override
    public String removeItemFromOrder(String orderId, String itemName) {
        Order order = orderMap.get(orderId);
        if (order == null) return "Order not found";
        order.getItems().removeIf(i -> i.getName().equalsIgnoreCase(itemName));
        return "Item removed";
    }

    @Override
    public double completeOrder(String orderId, double taxRate, double tipAmount) {
        double subtotal = getOrderTotal(orderId);
        return subtotal + (subtotal * taxRate) + tipAmount;
    }

    private double getPrice(String itemName) {
        return menu.stream()
                .filter(i -> i.getName().equalsIgnoreCase(itemName))
                .map(MenuItem::getPrice)
                .findFirst()
                .orElse(0.0);
    }
}
