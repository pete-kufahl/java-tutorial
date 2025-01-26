package com.schemaexample.jaxb8.domain;

import jakarta.xml.bind.annotation.*;

import java.util.Date;
import java.util.List;

@XmlRootElement
@XmlType(name = "")     // more convenient to make the root element an anonymous type
public class PurchaseOrder {
    @XmlAttribute(required=true)
    private Date orderDate;

    @XmlElementWrapper(name = "items", required=true)  // outputs list as <Items>..<Item>..<Item>.. without java wrapper class
    @XmlElement(name = "item")
    private List<Item> items;

    @XmlElement(required=true)
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
