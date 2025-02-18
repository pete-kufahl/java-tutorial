package com.prk.sealed;

import java.math.BigDecimal;

public record DiscountOrderLine(String discountCode, BigDecimal amount) implements OrderLine {
}
