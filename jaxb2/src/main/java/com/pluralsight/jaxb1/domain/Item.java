package com.pluralsight.jaxb1.domain;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Item {
    @XmlElement
    private String name;
    @XmlElement
    private double price;

    public Item() {}
    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}