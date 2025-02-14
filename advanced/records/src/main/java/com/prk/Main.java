package com.prk;

import com.prk.accessor.Customer;
import com.prk.hash.DuplCustomer;
import com.prk.rec.ProductRec;

public class Main {
    public static void main(String[] args) {
        System.out.printf("Hello, World from java %d\n", getVersion());

        var product = new ProductRec(101012, "pen", "ball point pen");
        System.out.println("Product has description: " + (product.hasDescription() ?
                product.description() : "NONE"));

        var customer = new Customer(55440, null, null);
        System.out.println("customer name is: " + customer.name());

        var copy = new Customer(customer.id(), customer.name(), customer.email());
        System.out.println("customer and copy equal? " + copy.equals(customer));

        // note that the automatically generated toString() does not use the (overridden) accessor method
        System.out.println(customer);
        System.out.println(copy);

        var dupl1 = new DuplCustomer(5544, "Peter", "pete@yahoo.com");
        var dupl2 = new DuplCustomer(5544, "Fritz", "fritz@hotmail.com");
        System.out.println("Peter and Fritz the same DuplCustomer? " + dupl2.equals(dupl1));
        System.out.println(dupl1 + " " + dupl1.hashCode());
        System.out.println(dupl2 + " " + dupl2.hashCode());
    }

    private static int getVersion() {
        String version = System.getProperty("java.version");
        if(version.startsWith("1.")) {
            version = version.substring(2, 3);
        } else {
            int dot = version.indexOf(".");
            if(dot != -1) { version = version.substring(0, dot); }
        } return Integer.parseInt(version);
    }
}