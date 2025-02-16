package com.prk.constructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DemoConstructors {
    public static void main(String[] args) {

        var product1 = new Product(10101L, "apples", "twisted apples");
        var product2 = new Product(10332L, "bread", "Limpa rye bread");

        var customer = new Customer(533550L, "Georg the Buyer", "georg@gmail.com");

        var lines = new ArrayList<OrderLine>();
        lines.add(new OrderLine(product1, 6, new BigDecimal("7.02")));
        lines.add(new OrderLine(product2, 2, new BigDecimal("2.75")));

        var order = new Order(2157574L, customer, LocalDateTime.now(), lines);
        System.out.println(order);
        System.out.println(order.lines().size() + " items.");

        // try to add to the list
        try {
            var newline = new OrderLine(new Product(102200L, "bananas", "ready to eat bananas"),
                    34, new BigDecimal("1.99"));
            order.lines().add(newline);
            System.out.println(order);
            System.out.println(order.lines().size() + " items.");
        } catch (UnsupportedOperationException ex) {
            System.out.println("attempt at adding to Order::lines field threw exception!");
        } finally {
            System.out.println("still " + order.lines().size() + " items.");
        }
    }
}
