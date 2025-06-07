package com.prk.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductRepository {
    public static List<Product> getDummyDataList() {
        Product p1 = new Product(1, "liquid chalk", 10);
        Product p2 = new Product(2, "climbing boots", 100);
        Product p3 = new Product(3, "backpack", 60);

        Product[] products = { p1, p2, p3};
        return new ArrayList<>(Arrays.asList(products));
    }
}
