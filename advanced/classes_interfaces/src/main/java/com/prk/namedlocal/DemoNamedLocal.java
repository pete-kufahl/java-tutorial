package com.prk.namedlocal;

public class DemoNamedLocal {
    public static void main(String[] args) {

        var encl = new Enclosing();
        encl.enclosingMethod(30, 50);
    }
}
