package com.prk.patterns;

import java.math.BigDecimal;

public record DiscountOrderLine(String discountCode, BigDecimal amount) implements OrderLine {
}
