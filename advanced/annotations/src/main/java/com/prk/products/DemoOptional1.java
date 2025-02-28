package com.prk.products;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

public class DemoOptional1 {
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        // using method that returns Optional
        Optional<Product> optionalProduct = findProductById(98L);

        // translating a nullable return type into an Optional
        Optional<Product> optionalProduct1 = Optional.ofNullable(getProductById(99L));

        // check if Optional contains a value
        if (optionalProduct.isPresent()) {
            var product = optionalProduct.get();
            System.out.println("found product: " + product);
        } else {
            System.out.println("product not found");
        }

        // orElse() returns a specific value of the Optional is empty
        //. that value can be null to back to nullable elements
        Product product2 = optionalProduct.orElse(new Product(27L,
                "chocolate milk", new BigDecimal("3.99")));
        System.out.println("Product: " + product2);

        // alternatively, use orElseGet(), which takes a supplier
        Product product3 = optionalProduct.orElseGet(() -> Product.PRODUCTS
                .get(RANDOM.nextInt(Product.PRODUCTS.size())));
        System.out.println("Dennis bought: " + product3);
    }

    // find() method that returns null if product not found
    private static Product getProductById(long id) {
        for (Product product : Product.PRODUCTS) {
            if (product.id() == id) {
                return product;
            }
        }
        return null;
    }

    // find() method that uses Optional
    private static Optional<Product> findProductById(long id) {
        for (Product product : Product.PRODUCTS) {
            if (product.id() == id) {
                // Optional.of<>() is a generic method; its type parameter is inferred here
                return Optional.of(product);
            }
        }
        // type argument is inferred here as well
        return Optional.empty();
    }
}
