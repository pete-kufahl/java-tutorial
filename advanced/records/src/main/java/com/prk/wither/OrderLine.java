package com.prk.wither;

import java.math.BigDecimal;

public record OrderLine(Product product, int quantity, BigDecimal price) {

    // add "wither" methods to make a modified copy of an existing record
    //. they can make code more readable since the copying is done inside the record implementations
    //. however, they can make code very inefficient if misused
    //. e.g. make Order.withLine() to add extra OrderLine objects
    //.     this produces code that makes intermediate copies of List<OrderLine> because this field
    //.     is made unmodifiable. Similar to the problem with concatenating strings inside a loop.

    public OrderLine withQuantity(int newQuantity) {
        return new OrderLine(this.product, newQuantity, this.price);
    }

    public OrderLine withPrice(BigDecimal newPrice) {
        return new OrderLine(this.product, this.quantity, newPrice);
    }
}
