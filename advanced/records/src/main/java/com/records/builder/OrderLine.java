package com.records.builder;

import java.math.BigDecimal;

public record OrderLine(Product product, int quantity, BigDecimal price) {
}
