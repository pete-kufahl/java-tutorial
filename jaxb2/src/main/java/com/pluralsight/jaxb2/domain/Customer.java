package com.pluralsight.jaxb2.domain;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"name", "loyalty", "shippingAddress", "billingAddress"})
public class Customer {
    private String name;

    @XmlElement(name = "shipToAddress")
    private Address shippingAddress;

    @XmlElement(name = "billToAddress")
    private Address billingAddress;

    private Loyalty loyalty;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Loyalty getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(Loyalty loyalty) {
        this.loyalty = loyalty;
    }
}
