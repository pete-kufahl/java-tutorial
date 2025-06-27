package com.example.client;

import java.util.List;

public class ClientApp {
    public static void main(String[] args) {

        test2();
    }

    private static void test1() {
        // Create the service instance
        OrderService_Service service = new OrderService_Service();

        // Get the port (proxy to the SOAP endpoint)
        OrderService port = service.getOrderServicePort();

        // Call the SOAP method
        String result = port.placeOrder("Coffee", 3);

        // Print the result
        System.out.println("Server responded: " + result);
    }

    private static void test2() {
        OrderService_Service service = new OrderService_Service();// generated class
        OrderService port = service.getOrderServicePort();

        // Get menu
        List<MenuItem> menu = port.getMenu();
        menu.forEach(item -> System.out.println(item.getName() + ": $" + item.getPrice()));

        // Place order
        String orderId = port.placeOrder("Coffee", 2);
        port.addItemToOrder(orderId, "Muffin", 1);

        double subtotal = port.getOrderTotal(orderId);
        double total = port.completeOrder(orderId, 0.07, 2.0);

        System.out.println("Subtotal: $" + subtotal);
        System.out.println("Total (with tax + tip): $" + total);
    }
}
