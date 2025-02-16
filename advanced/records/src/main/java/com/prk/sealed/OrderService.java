package com.prk.sealed;

import java.math.BigDecimal;

public class OrderService {
    public BigDecimal calculateTotal(Order order) {
        var total = BigDecimal.ZERO;

        for (OrderLine line : order.lines()) {
            if (line instanceof SaleOrderLine sale) {
                total = total.add(sale.price());
            } else if (line instanceof DiscountOrderLine discount) {
                total = total.subtract(discount.amount());
            }
        }
        return total;
    }
}
