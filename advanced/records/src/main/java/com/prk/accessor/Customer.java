package com.prk.accessor;

public record Customer(long id, String name, String email) {

    // can override the accessor method generated for the record
    @Override
    public String name() {
        return name != null && !name.isBlank() ? name : "anonymous";
    }
}
