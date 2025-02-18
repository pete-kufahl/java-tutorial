package com.prk.static_nested;

public class DemoStaticNested {
    public static void main(String[] args) {
        var nested1 = new Enclosing.Nested();
        nested1.run();
    }
}