package com.prk.recordpatterns;

import java.math.BigDecimal;

public record SaleOrderLine(Product product, int quantity, BigDecimal price) implements OrderLine {
}
