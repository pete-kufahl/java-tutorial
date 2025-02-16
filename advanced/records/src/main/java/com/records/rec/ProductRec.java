package com.records.rec;

// immutable class declared as record

public record ProductRec (long id, String name, String description) {
    // compiler translates this into a final class
    //. with private final fields for the components

    // generates a constructor that initializes the fields

    // generates accessor methods that don't match the Bean syntax:
    // public long id() { return this.id; }
    //  etc.

    public boolean hasDescription() {
        return description != null && !description.isBlank();
    }

    // instance fields are not allowed, e.g. private int value;
    // but static fields are.
    private static int VALUE = 23;
}
