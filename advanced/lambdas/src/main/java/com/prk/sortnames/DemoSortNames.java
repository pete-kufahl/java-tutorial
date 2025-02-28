package com.prk.sortnames;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DemoSortNames {
    // declare a list of names and sort them by length
    public static void main(String[] args) {
        
        var names1 = new ArrayList<>(List.of("Adam", "Eve", "Abraham", "Esau", "Ezekiel", "Joseph", "John"));
        sortWithInnerClass(names1);
        System.out.println(names1);

        var names2 = new ArrayList<>(List.of("Adam", "Eve", "Abraham", "Esau", "Ezekiel", "Joseph", "John"));
        // using lambda
        names2.sort((o1, o2) -> Integer.compare(o1.length(), o2.length()));
        System.out.println(names2);
    }

    private static void sortWithInnerClass(ArrayList<String> names) {
        // using anonymous class
        names.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        });
    }
}