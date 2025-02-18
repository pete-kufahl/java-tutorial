package com.prk.recordpatterns;

import java.math.BigDecimal;

public class OrderService {

    // rewrite calculateTotal using record patterns
    public BigDecimal calculateTotal(Order order) {
        var total = BigDecimal.ZERO;

        for (OrderLine line : order.lines()) {
            // if the pattern matches, the left side of each case statement initializes variables
            //.  matching the fields of the records ...
            //. ... and the right side can use these variables.
            // Java 21: must list a variable for every record field in the pattern
            // Java 22+: can use _ for unneeded variables
            var netAmount = switch (line) {
                case SaleOrderLine(_, _, var amount) -> amount;
                case DiscountOrderLine(_, var amount) -> amount.negate();
            };
            total = total.add(netAmount);
        }
        return total;
    }

    // use record pattern-matching to handle nested records
    public String formatShippingAddress(Customer customer) {
        // switch on the customer object
        return switch (customer) {
            case Customer(var id, var name, var email, Address(var street, var houseNumber, var city, var country)) ->
                String.format("%s%n%s %s%n%s%n%s%n", name, street, houseNumber, city, country);
        };
    }



    public String formatOrderLines(Order order) {
        var sb = new StringBuilder();
        for (OrderLine line : order.lines()) {
            var text = switch (line) {
                case SaleOrderLine sale when sale.quantity() == 1 ->
                        String.format("%-14s %8s%n", sale.product().name(), sale.price());
                case SaleOrderLine sale ->
                    String.format("%-14s (%3d) %8s%n", sale.product().name(), sale.quantity(), sale.price());
                case DiscountOrderLine discount ->
                    String.format("Discount %-8s    %8s%n", discount.discountCode(), discount.amount().negate());
            };
            sb.append(text);
        }
        return sb.toString();
    }
}

