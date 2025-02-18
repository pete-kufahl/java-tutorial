package com.prk.recordpatterns;

import static com.prk.constructor.Validation.checkThat;

public record Product(long id, String name, String description) {

    // canonical constructor overridden with compact syntax
    public Product {
        checkThat(name != null && !name.isBlank(), "name must not be null or blank");
    }

    // additional constructor MUST FIRST refer back to the canonical constructor
    public Product(long id, String name) {
        this(id, name, null);
        // fields are all final, so there is no additional assignment logic
    }
}
