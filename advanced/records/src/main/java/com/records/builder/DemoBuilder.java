package com.records.builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DemoBuilder {
    public static void main(String[] args) {
        var customer = new Customer(10L, "Mary Jane", "maryj@bbmail.com");
        var apples = new Product(10101L, "apples", "twisted apples");
        var bananas = new Product(102200L, "bananas", "ready to eat bananas");

        var order = Order.builder()
                .withId(202021L)
                .forCustomer(customer)
                .atDateTime(LocalDateTime.now())
                .addLine(new OrderLine(apples, 6, new BigDecimal("5.95")))
                .addLine(new OrderLine(bananas, 2, new BigDecimal("0.95")))
                .build();
        System.out.println(order);
    }
}
