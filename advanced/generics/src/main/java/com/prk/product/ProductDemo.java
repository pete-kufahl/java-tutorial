package com.prk.product;

import java.util.Comparator;
import java.util.List;

public class ProductDemo {
    // takes a list of products, sorts them by id, returns a list of names of the sorted products
    public static void main(String [] args) {
        var products = List.of(
                new Product(10102L, "bread", "whole wheat"),
                new Product(10333L, "cheese", "smoked gouda"),
                new Product(10123L, "apples", "twisted apples"),
                new Product(10009L, "queso", "Mexican cheese for chili con carne")
        );
        var names = sortByIdExtractNames(products);
        System.out.println(names);
    }

    // non-generic method
    /*
    static List<String> sortByIdExtractNames(List<Product> list) {
        return list.stream()
                .sorted(Comparator.comparing(Product::id))
                .map(Product::name)
                .toList();
    }
    */

    // method that works for anything that implements HasId and HasName
    static <T extends HasId & HasName> List<String> sortByIdExtractNames(List<T> list) {
        return list.stream()
                .sorted(Comparator.comparing(HasId::id))
                .map(HasName::name)
                .toList();
    }
}
