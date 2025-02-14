package com.prk.constructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.prk.constructor.Validation.checkThat;

public record Order(long id, Customer customer, LocalDateTime dateTime, List<OrderLine> lines) {

    // since Order has a mutable collection, we should override the canonical constructor to prevent
    //.  immutability from being broken at runtime
    // also can validate fields at this point (using utility Validation.checkThat() )

    public Order(long id, Customer customer, LocalDateTime dateTime, List<OrderLine> lines) {
        checkThat(customer != null, "customer must not be null");
        checkThat(dateTime != null, "dateTime must not be null");
        checkThat(lines != null && !lines.isEmpty(), "lines must not be null or empty");

        this.id = id;
        this.customer = customer;
        this.dateTime = dateTime;
        this.lines = List.copyOf(lines);    // defensive unmodifiable copy
    }

    // rules:
    // - the canonical constructor's access level cannot be more restrictive than that of the record itself
    //      - cannot hide by making it private
    // - cannot throw checked exceptions, but can throw an unchecked exception
}
