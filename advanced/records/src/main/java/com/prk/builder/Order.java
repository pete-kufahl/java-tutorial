package com.prk.builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record Order(long id, Customer customer, LocalDateTime dateTime, List<OrderLine> lines) {

    public Order {
        lines = List.copyOf(lines);    // defensive unmodifiable copy
    }

    // generating the builder pattern requires some boilerplate...
    //  - static getter for the builder itself
    //  - static Builder class definition
    //      - private fields with public setters that all return this
    //      - a public build() that calls the canonical constructor of the record

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private long id;
        private Customer customer;
        private LocalDateTime dateTime;
        private List<OrderLine> lines = new ArrayList<>();

        public Builder withId(long id) {
            this.id = id;
            return this;
        }

        public Builder forCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder atDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder addLine(OrderLine line) {
            this.lines.add(line);
            return this;
        }

        public Order build() {
            return new Order(id, customer, dateTime, lines);
        }
    }
}
