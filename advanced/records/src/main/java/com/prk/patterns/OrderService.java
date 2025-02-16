package com.prk.patterns;

import java.math.BigDecimal;

public class OrderService {
    // scope of pattern variables?
    //. here, it's the block following the if-statement
    //. because we know the pattern-matching expression evaluates to true there
    public BigDecimal calculateTotal(Order order) {
        var total = BigDecimal.ZERO;

        for (OrderLine line : order.lines()) {
            // "SaleOrderLine sale" is pattern matching: the type and new variable
            if (line instanceof SaleOrderLine sale) {
                total = total.add(sale.price());
            } else if (line instanceof DiscountOrderLine discount) {
                total = total.subtract(discount.amount());
            }
        }
        return total;
    }

    // using scope of pattern variable in a different way
    //. here the scope of discount is AFTER the if-block because there, the pattern-matching
    //.  expression evaluates to true
    public BigDecimal calculateTotalDiscount(Order order) {
        var total = BigDecimal.ZERO;

        for (OrderLine line : order.lines()) {
            if (!(line instanceof DiscountOrderLine discount)) {
                continue;
            }
            total = total.add(discount.amount());
        }
        return total;
    }

    // here, the scope of "sale" is after the && because the compiler knows the
    //. relevant pattern matching expression evaluated to true
    public boolean hasSaleWithAmountGreaterThan(Order order, BigDecimal limit) {
        for (OrderLine line : order.lines()) {
            if (line instanceof SaleOrderLine sale && sale.price().compareTo(limit) > 0) {
                return true;
            }
        }
        return false;
    }

    // use switch expression to make calculateTotal() more concise
    // can switch() on type with pattern-matching
    //  a switch statement does nothing if there's no match,
    //  but a switch expression must always evaluate to a value
    //. OrderLine is a sealed interface, so the switch expression can be used
    //.  and have exhaustive checking
    public BigDecimal calculateTotalV2(Order order) {
        var total = BigDecimal.ZERO;

        for (OrderLine line : order.lines()) {
            var netAmount = switch(line) {
                case SaleOrderLine sale -> sale.price();
                case DiscountOrderLine discount -> discount.amount().negate();
            };
            total = total.add(netAmount);
        }
        return total;
    }

    // use pattern matching to conditionally format OrderLine
    public String formatOrderLines(Order order) {
        var sb = new StringBuilder();
        for (OrderLine line : order.lines()) {
            var text = switch (line) {
                // when-clause keeps the switching logic and format logic separate
                //. put the "when" condition first because the switch matches the first pattern that matches
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

