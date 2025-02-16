package com.records.accessor;

public class DemoAccessor {
    public static void main(String[] args) {
        var customer = new Customer(55440, null, null);
        System.out.println("customer name is: " + customer.name());

        var copy = new Customer(customer.id(), customer.name(), customer.email());
        System.out.println("customer and copy equal? " + copy.equals(customer));

        // note that the automatically generated toString() does not use the (overridden) accessor method
        System.out.println(customer);
        System.out.println(copy);
    }
}
