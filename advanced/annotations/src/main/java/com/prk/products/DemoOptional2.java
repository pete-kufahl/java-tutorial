package com.prk.products;

import java.util.*;

public class DemoOptional2 {
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {

        Optional<Product> optionalProduct = findProductById(10L);

        // .ifPresent() takes a Consumer that is called if the Optional is not empty
        optionalProduct.ifPresent(product -> System.out.println("Found product: " + product));

        // .ifPresentOrElse() takes a Consumer and a Runnable
        optionalProduct.ifPresentOrElse(
                product -> System.out.println("Found product: " + product),
                () -> System.out.println("Product not found"));

        // Optional also has .filter(), .map(), .flatMap()
        String text = optionalProduct.map(Product::name).orElse("Product not found");
        System.out.println(text);

        // e.g. filter + map
        Set<Long> discountedProductIds = Set.of(12L, 10L, 55L, 122L, 2L);
        System.out.println(optionalProduct
                .filter(product -> discountedProductIds.contains(product.id()))
                .map(product -> "discounted product: " + product.name())
                .orElse("no discount found"));

        // for a stream of Optionals, can use Stream.flatMap() with Optional.stream()
        //. to convert to a stream of values. The empty Optionals will be filtered out
        System.out.println("\nlisting products matching randomly generated ids ....");
        Set<Long> productIds = new HashSet<>();
        for (int i = 0; i < Product.PRODUCTS.size() * 0.8; i++) {
            long val = RANDOM.nextLong(Product.PRODUCTS.size() * 2L);
            productIds.add(val);
        }
        List<Product> listedProducts = productIds.stream()
            .map(DemoOptional2::findProductById)    // stream of Optional<Product>
            .flatMap(Optional::stream)
            .toList();
        listedProducts.forEach(System.out::println);
    }

    // compared to DemoOptional1, this implementation is more compact with streams
    //. note Streams.findFirst() returns an Optional
    private static Optional<Product> findProductById(long id) {
        return Product.PRODUCTS.stream()
                .filter(product -> product.id() == id)
                .findFirst();
    }
}
