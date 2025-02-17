package com.prk.pair;

import java.util.function.BiFunction;

public record Pair<T, U>(T first, U second) {

    // static method cannot access enclosing record's type params T, U
    public static <V, W> Pair<V, W> of (V first, W second) {
        return new Pair<>(first, second);
    }

    // non-static wither methods

    public Pair<T, U> withFirst(T newFirst) {
        return Pair.of(newFirst, second);
    }

    public Pair<T, U> withSecond(U newSecond) {
        return Pair.of(first, newSecond);
    }

    // map method takes a BiFunction as an argument
    // accesses T, U as arguments (which it can), returns a Pair with possibly two different types V, W
    public <V, W> Pair<V, W> map(BiFunction<T, U, Pair<V, W>> mapper) {
        return mapper.apply(first, second);
    }
}
