package com.example.model;

import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "OrderItem")
public class OrderItem {
    private String name;
    private int quantity;

    public OrderItem() {}
    public OrderItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
