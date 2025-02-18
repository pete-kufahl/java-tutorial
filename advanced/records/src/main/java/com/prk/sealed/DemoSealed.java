package com.prk.sealed;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DemoSealed {
    // add a new type of Orderline to model discounts
    // relate the existing OrderLine record --> SaleOrderLine to the new DiscountOrderLine
    //. by using a sealed interface OrderLine
    public static void main(String[] args) {

        var product1 = new Product(10101L, "apples", "twisted apples");
        var product2 = new Product(10332L, "bread", "Limpa rye bread");

        var customer = new Customer(533550L, "Georg the Buyer", "georg@gmail.com");

        var lines = new ArrayList<OrderLine>();
        lines.add(new SaleOrderLine(product1, 6, new BigDecimal("7.02")));
        lines.add(new SaleOrderLine(product2, 2, new BigDecimal("2.75")));

        // add the discount
        lines.add(new DiscountOrderLine("saver club", new BigDecimal("2.00")));

        var order = new Order(2157574L, customer, LocalDateTime.now(), lines);
        System.out.println(order);

        var orderService = new OrderService();
        var total = orderService.calculateTotal(order);
        System.out.println(order.lines().size() + " items.");
        System.out.println(total + " total cost");

    }
}
