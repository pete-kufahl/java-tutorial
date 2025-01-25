package com.xmlexample.jaxb1.domain;
import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PurchaseOrder {
    @XmlElement
    private Customer customer;

    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    private List<Item> items;

    @XmlElement
    private double totalAmount;

    public PurchaseOrder() {}
    public PurchaseOrder(Customer customer, List<Item> items, double totalAmount) {
        this.customer = customer;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}