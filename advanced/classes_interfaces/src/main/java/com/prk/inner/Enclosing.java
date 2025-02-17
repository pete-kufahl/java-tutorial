package com.prk.inner;

public class Enclosing {

    private String name = "Joe Smith";

    // an inner class can access the members of its enclosing class
    // in this case, this.new()
    public void createInner() {
        var inner = new Inner();
    }

    class Inner {
        private String name = "Pancho Gonzales";

        void run() {
            System.out.println(name);   // Inner.name, via shadowing

            // access enclosing member (non-static context):
            System.out.println(Enclosing.this.name);
        }
    }
}
