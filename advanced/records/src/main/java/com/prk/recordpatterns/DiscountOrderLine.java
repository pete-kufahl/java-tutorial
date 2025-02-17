package com.prk.recordpatterns;

import java.math.BigDecimal;

public record DiscountOrderLine(String discountCode, BigDecimal amount) implements OrderLine {
}
