package com.pluralsight.jaxb5.adaptors;

import com.pluralsight.jaxb5.domain.Item;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;

import java.math.BigDecimal;

@XmlType(propOrder = {"productName", "quantity", "price", "comment"})
public class ItemValue {
    private String productCode;
    private Item item;

    public ItemValue() { this.item = new Item(); }

    public ItemValue(String productCode, Item item) {
        this.productCode = productCode;
        this.item = item;
    }

    @XmlAttribute
    public String getProductCode() { return productCode; }

    public void setProductCode(String productCode) { this.productCode = productCode; }

    public String getProductName() { return item.getProductName(); }

    public void setProductName(String productName) { item.setProductName(productName); }

    public int getQuantity() { return item.getQuantity(); }

    public void setQuantity(int quantity) { item.setQuantity(quantity); }

    public BigDecimal getPrice() { return item.getPrice(); }

    public void setPrice(BigDecimal price) { item.setPrice(price); }

    public String getComment() { return item.getComment(); }

    public void setComment(String comment) { item.setComment(comment); }

    public Item toItem() { return item; }


}
