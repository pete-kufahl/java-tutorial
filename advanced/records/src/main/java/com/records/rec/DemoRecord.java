package com.records.rec;

public class DemoRecord {
    public static void main(String[] args) {
        var product = new ProductRec(101012, "pen", "ball point pen");
        System.out.println("Product has description: " + (product.hasDescription() ?
                product.description() : "NONE"));
    }
}
