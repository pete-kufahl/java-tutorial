package com.schemaexample.jaxb6.domain;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"name", "loyalty", "shippingAddress", "billingAddress"})
public class Customer {
    @XmlElement(required=true)
    private String name;

    @XmlElement(required=true)
    private Address shippingAddress;

    @XmlElement(required=true)
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
