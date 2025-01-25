package com.pluralsight.jaxb5.domain;

import com.pluralsight.jaxb5.adaptors.ItemsAdaptor;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.util.Date;
import java.util.Map;

// changes jaxb4 domain so that the Map items is displayed in a custom manner

@XmlRootElement(name = "order")
public class PurchaseOrder {
    @XmlAttribute
    private Date orderDate;

    // jaxb calls ItemsAdaptor.marshall() to translate map into wrapper object, which is then marshalled
    @XmlJavaTypeAdapter(ItemsAdaptor.class)
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
