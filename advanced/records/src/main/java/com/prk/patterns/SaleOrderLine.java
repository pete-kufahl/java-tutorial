package com.prk.patterns;

import java.math.BigDecimal;

public record SaleOrderLine(Product product, int quantity, BigDecimal price) implements OrderLine {
}
