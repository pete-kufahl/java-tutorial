package com.prk.hash;

public class DemoHashEquals {
    public static void main(String[] args) {
        var dupl1 = new DuplCustomer(5544, "Peter", "pete@yahoo.com");
        var dupl2 = new DuplCustomer(5544, "Fritz", "fritz@hotmail.com");
        System.out.println("Peter and Fritz the same DuplCustomer? " + dupl2.equals(dupl1));
        System.out.println(dupl1 + " " + dupl1.hashCode());
        System.out.println(dupl2 + " " + dupl2.hashCode());
    }
}
