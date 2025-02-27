package com.prk.commandline.model;

import java.util.Map;

import static com.prk.commandline.Validation.checkThat;

public record Customer (long id, String name, String email) {

    public static final Map<Long, Customer> Customers_By_Id = Map.of(
            5005L, new Customer(5005L, "Taylor Reiner", "taylor.reiner@seers.com"),
            5166L, new Customer(5166L, "Reiner Knizia", "reiner@tigrisundeuphrat.net"),
            5201L, new Customer(5121L, "Stefan Dorra", "sdorra@valletta.de"),
            5444L, new Customer(5444L, "Taiki Shinzawa", "tshinzawa@americanbookshop.org")
    );

    public Customer {
        checkThat(name != null && !name.isBlank(), "name must not be null or blank");
        checkThat(email != null && !email.isBlank(), "email address must not be null or blank");
    }
}
