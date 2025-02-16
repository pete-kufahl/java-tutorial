package com.prk.accessor;

public record Customer(long id, String name, String email) {

    // can override the accessor method generated for the record
    @Override
    public String name() {
        return name != null && !name.isBlank() ? name : "anonymous";
    }

    // rules:
    // - the return type of the overridden accessor must match the type of the component
    // - cannot throw checked exceptions (i.e. throws clause)
}
