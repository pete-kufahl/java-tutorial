package com.prk.printlines;

import java.util.HashMap;
import java.util.Map;

public class DemoPrintlines {

    static void printLines(String... lines) {
        for (String line : lines) {
            System.out.println(line);
        }
    }

    // parameterize function arguments by following convention for in-parameters, out-parameters
    // warning of possible heap pollution
    @SafeVarargs
    static <T, U> void putIntoMap(Map<? super T, ? super U> map, Pair<? extends T, ? extends U>... pairs) {
        for (var pair : pairs) {
            map.put(pair.first(), pair.second());
        }
    }

    public static void main(String[] args) {
        printLines("Hello World", "How are you?");

        var map = new HashMap<Integer, String>();
        // this gives possible unchecked exception
        putIntoMap(map, new Pair<>(1, "one"), new Pair<>(2, "two"));
    }
}
