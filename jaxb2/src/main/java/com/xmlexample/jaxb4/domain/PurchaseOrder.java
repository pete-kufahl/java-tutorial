package com.xmlexample.jaxb4.domain;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Date;
import java.util.Map;

// changes jaxb2 domain so that items are not individually output as <Items/> elements
// instead, make a map<String,Item> <Items/> and populate it with <Item/>

@XmlRootElement(name = "order")
public class PurchaseOrder {
    @XmlAttribute
    private Date orderDate;

    @XmlElementWrapper(name = "items")  // outputs items as map with <Key/>, <Value/> fields
    @XmlElement(name = "item")
    private Map<String, Item> items;

    private Customer customer;
    private String comment;

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate=orderDate; }

    public Map<String, Item> getItems() { return items; }
    public void setItems(Map<String, Item> items) { this.items=items; }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
