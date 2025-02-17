package com.prk.product;

// accessor methods serve as the implementation of the interfaces
public record Product(long id, String name, String description) implements HasId, HasName {
}
