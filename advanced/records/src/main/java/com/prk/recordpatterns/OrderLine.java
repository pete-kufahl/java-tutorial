package com.prk.recordpatterns;

// this interface does nothing except related the two record classes here
public sealed interface OrderLine permits SaleOrderLine, DiscountOrderLine {

}

