package com.prk.commandline.model;

import java.util.Map;

import static com.prk.commandline.Validation.checkThat;

public record Customer (long id, String name, String email) {

    public static final Map<Long, Customer> Customers_By_Id = Map.of(
            5L, new Customer(5L, "Taylor Reiner", "taylor.reiner@seers.com"),
            6L, new Customer(6L, "Reiner Knizia", "reiner@tigrisundeuphrat.net"),
            1L, new Customer(1L, "Stefan Dorra", "sdorra@valletta.de"),
            4L, new Customer(4L, "Taiki Shinzawa", "tshinzawa@americanbookshop.org")
    );

    public Customer {
        checkThat(name != null && !name.isBlank(), "name must not be null or blank");
        checkThat(email != null && !email.isBlank(), "email address must not be null or blank");
    }
}
