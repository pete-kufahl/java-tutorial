package com.xmlexample.jaxb2.domain;

import jakarta.xml.bind.annotation.*;

import java.util.Date;
import java.util.List;

// somewhat different than jaxb1.domain, to demonstrate attributes used in a different way

@XmlRootElement(name = "order") // => this class or enum maps to an xml element at the root level
public class PurchaseOrder {
    @XmlAttribute       // => data output as attribute instead of field
//    @XmlSchemaType(name = "date")   // => print out at date instead of datetime
    private Date orderDate;

    private List<Item> items;
    private Customer customer;
    private String comment;

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate=orderDate; }

    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items=items; }

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
