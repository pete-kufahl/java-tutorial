package com.prk.commandline.model;

import java.math.BigDecimal;
import java.util.Map;

import static com.prk.commandline.Validation.checkThat;

public record Product(long id, String name, String description, BigDecimal price) {

    public static Map<Long, Product> Products_By_Id = Map.of(
            10101L, new Product(10101L, "Apples", "twisted red apples from Winesburg", new BigDecimal("0.60")),
            10220L, new Product(10220L, "Bread", "Limpa rye bread", new BigDecimal("5.95")),
            10033L, new Product(10033L, "Oranges", "blood oranges", new BigDecimal("7.00"))
    );

    public Product {
        checkThat(name != null && !name.isBlank(), "name must not null or blank");
        checkThat(price != null, "price must not be null");
    }
}
