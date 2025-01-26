package com.schemaexample.jaxb7.domain;

import jakarta.xml.bind.annotation.XmlElement;

import java.math.BigDecimal;

public class Item {
    @XmlElement(required=true)
    private String productName;

    @XmlElement(required=true)
    private int quantity;

    @XmlElement(required=true)
    private BigDecimal price;

    @XmlElement(nillable = true)
    private String comment = "TEST";

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
