package com.prk.products;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public record Product(long id, String name, BigDecimal price) {

    public static final List<Product> PRODUCTS = Arrays.asList(
            new Product(1, "Milk", new BigDecimal("2.49")),
            new Product(2, "Bread", new BigDecimal("1.99")),
            new Product(3, "Eggs (12-pack)", new BigDecimal("3.99")),
            new Product(4, "Butter", new BigDecimal("2.79")),
            new Product(5, "Cheese", new BigDecimal("4.99")),
            new Product(6, "Apples (1 lb)", new BigDecimal("1.49")),
            new Product(7, "Bananas (1 lb)", new BigDecimal("0.99")),
            new Product(8, "Orange Juice (1L)", new BigDecimal("3.49")),
            new Product(9, "Chicken Breast (1 lb)", new BigDecimal("5.99")),
            new Product(10, "Ground Beef (1 lb)", new BigDecimal("6.49")),
            new Product(11, "Rice (2 lb)", new BigDecimal("2.99")),
            new Product(12, "Pasta (1 lb)", new BigDecimal("1.69")),
            new Product(13, "Tomato Sauce", new BigDecimal("2.19")),
            new Product(14, "Potatoes (5 lb)", new BigDecimal("3.29")),
            new Product(15, "Onions (3 lb)", new BigDecimal("2.59")),
            new Product(16, "Carrots (2 lb)", new BigDecimal("1.99")),
            new Product(17, "Yogurt (500g)", new BigDecimal("2.89")),
            new Product(18, "Cereal", new BigDecimal("4.49")),
            new Product(19, "Coffee (250g)", new BigDecimal("7.99")),
            new Product(20, "Tea (20 bags)", new BigDecimal("3.29")));
}
