package com.prk.constructor;

import java.math.BigDecimal;

public record OrderLine(Product product, int quantity, BigDecimal price) {
}
