package com.prk.products;

import java.math.BigDecimal;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class DemoProducts {
    public static void main(String[] args) {
        var products = List.of(
                new Product(1011L, "Apples", new BigDecimal("2.25")),
                new Product(1022L, "Oranges", new BigDecimal("3.99")),
                new Product(10334L, "Cheese", new BigDecimal("7.95")),
                new Product(10000L, "Bread", new BigDecimal("2.95"))
        );

        // static method reference
        // isExpensive matches the signature of Predicate<>()
        var expensiveProducts = products.stream().filter(DemoProducts::isExpensive).toList();

        // instance method reference
        expensiveProducts.forEach(System.out::println);

        // Product::price is also an instance method reference, even though we are not referring to a particular
        //. product object, but the context determines which object
        //. same thing happens with BigDecimal::add
        var totalPrice = products.stream().map(Product::price).reduce(BigDecimal.ZERO, BigDecimal::add);

        // TreeSet::new is a constructor reference, implements Supplier<>
        var productNames = products.stream().map(Product::name).collect(Collectors.toCollection(TreeSet::new));
    }

    static boolean isExpensive(Product product) {
        return product.price().compareTo(new BigDecimal("4.00")) >= 0;
    }
}

record Product(long id, String name, BigDecimal price) {}