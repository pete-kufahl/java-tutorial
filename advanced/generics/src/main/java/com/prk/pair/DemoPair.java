package com.prk.pair;

public class DemoPair {
    public static void main(String[] args) {
        var p1 = Pair.<Integer, String> of(1, "one");
        System.out.println(p1);

        // use type inference
        var p2 = Pair.of(2, "two");
        System.out.println(p2);

        // map function to swap left and right
        var p3 = p2.map((left, right) -> Pair.of(right, left));
        System.out.println(p3);

        // or, without type inference:
        var p4 = p2.<String, Integer>map((Integer left, String right) -> Pair.<String, Integer> of(right, left));
        System.out.println(p4);
    }
}
