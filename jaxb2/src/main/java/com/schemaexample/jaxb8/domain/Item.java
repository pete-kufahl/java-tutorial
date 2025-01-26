package com.schemaexample.jaxb8.domain;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;

import java.math.BigDecimal;

public class Item {
    @XmlElement(required=true)
    private String productName;

    @XmlElement(required=true)
    private int quantity;

    @XmlElement(required=true)
    private BigDecimal price;

    // JAXBElement<> confers null behavior in XML.
    // xml element not present  <-----> java field set to "null"
    // xml element set to "nil" <-----> java field set to JAXBElement with nil == true
    // attrib matches XmlElementDecl is ObjectFactory
    @XmlElementRef(namespace = "http://www.prk.org/ps-jxb", name = "comment", required = false)
    private JAXBElement<String> comment;

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

    public JAXBElement<String> getComment() {
        return comment;
    }

    public void setComment(JAXBElement<String> comment) {
        this.comment = comment;
    }
}
