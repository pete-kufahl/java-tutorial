package com.prk.printnames;

import java.util.ArrayList;
import java.util.List;

public class DemoPrintNames {
    public static void main(String[] args) {

        var names = List.of("Adam", "Eve", "Abraham", "Esau", "Ezekiel", "Joseph", "John");

        var count = 0;
        // violates the "effectively final" local variable
        //names.forEach(name -> System.out.println(++count + ': ' + name));

        // this is legal since we modify the state of whatever result points to, but not result itself
        // however, it is a lambda with a side effect -> not functional programming
        var result = new ArrayList<String>();
        names.forEach(name -> result.add(name.toUpperCase()));

        // here, the lambda is functional programming
        names.stream().map(name -> name.toUpperCase()).toList();

        // or, replace the lambda with a method reference
        names.forEach(System.out::println);
    }
}
