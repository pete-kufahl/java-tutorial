package com.prk.staticnested;

public class DemoStaticNested {
    public static void main(String[] args) {
        var nested1 = new Enclosing.Nested();
        nested1.run();;
    }
}