package com.pluralsight.jaxb5.adaptors;

import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;

public class ItemsWrapper {
    private List<ItemValue> items;

    public ItemsWrapper() {}

    public ItemsWrapper(List<ItemValue> items) {
        this.items = items;
    }

    @XmlElement(name = "item")
    public List<ItemValue> getItems() { return items; }

    public void setItems(List<ItemValue> items) {
        this.items = items;
    }
}
