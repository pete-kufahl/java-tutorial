package com.prk.wither;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DemoWither {
    public static void main(String[] args) {
        var maryjane = new Customer(10L, "Mary Jane", "maryj@bbmail.com");
        var apples = new Product(10101L, "apples", "twisted apples");
        var bananas = new Product(102200L, "bananas", "ready to eat bananas");

        var applesLine1 = new OrderLine(apples, 6, new BigDecimal("5.95"));
        var bananasLine1 = new OrderLine(bananas, 2, new BigDecimal("0.95"));

        // keep the Order's builder pattern from before
        var marysOrder = Order.builder()
                .withId(202021L)
                .forCustomer(maryjane)
                .atDateTime(LocalDateTime.now())
                .addLine(applesLine1)
                .addLine(bananasLine1)
                .build();

        System.out.println(marysOrder);

        var peter = new Customer(5544, "Peter", "pete@yahoo.com");

        // make new orderline objects from existing ones
        var applesLine2 = applesLine1.withQuantity(3).withPrice(new BigDecimal("3.25"));
        var bananasLine2 = bananasLine1.withQuantity(10).withPrice(new BigDecimal("4.05"));

        var petersOrder = Order.builder()
                .forCustomer(peter)
                .atDateTime(LocalDateTime.now())
                .addLine(applesLine2)
                .addLine(bananasLine2)
                .build();

        System.out.println("**************");
        System.out.println(petersOrder);


    }
}
