package com.prk.sealed;

import java.math.BigDecimal;

// this interface does nothing except related the two record classes here
public sealed interface OrderLine permits SaleOrderLine, DiscountOrderLine {

}

