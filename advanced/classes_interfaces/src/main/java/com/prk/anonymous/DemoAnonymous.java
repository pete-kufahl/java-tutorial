package com.prk.anonymous;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DemoAnonymous {
    public static void main(String[] args) {

        // for example, ArrayList and Comparator
        var names = new ArrayList<>(List.of("Ford", "Carter", "Reagan", "Bush", "Clinton"));

        // implement the interface Comparator with a new class,
        //  overriding the abstract compare() method
        // note that since we are implementing the Comparator interface, the parens are empty:
        //. that is because interfaces do not have constructors
        // however, when we extend a class with an anonymous class, the parens will have the
        //. parameter list of the superclass' constructor
        names.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        });

        System.out.println(names);

        // replace the anon class with a lambda
        // since Comparator is a functional interface
        names.sort(Comparator.comparingInt(String::length));
    }
}
