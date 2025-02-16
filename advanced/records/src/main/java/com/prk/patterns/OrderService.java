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
}

