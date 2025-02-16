package com.prk.sealed;

import java.math.BigDecimal;

public record SaleOrderLine(Product product, int quantity, BigDecimal price) implements OrderLine {
}
