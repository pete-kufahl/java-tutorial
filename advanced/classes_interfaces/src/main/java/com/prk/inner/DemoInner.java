package com.prk.inner;

public class DemoInner {
    public static void main(String[] args) {

        // to instantiate an inner class, must first instantiate the enclosing class
        var enclosing = new Enclosing();
        var inner = enclosing.new Inner();

        inner.run();
    }
}
