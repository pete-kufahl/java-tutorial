package com.pluralsight.jaxb2;

// 2 ways to look at domain model classes
// 1. access fields directly
// 2. access as JavaBeans properties

// two kinds of types
// 1. value types (java) --> simple types (XML)
// 2. class types (java) --> complex types (XML)

import com.pluralsight.jaxb2.domain.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class AnnotationsExample1 {
    public static void main(String[] args) throws JAXBException {
        var purchaseOrder = createPurchaseOrder();

        var context = JAXBContext.newInstance(PurchaseOrder.class);
        var marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // marshal to string
        var writer = new StringWriter();
        marshaller.marshal(purchaseOrder, writer);
        var xml = writer.toString();

        System.out.println(xml);

        // unmarshal from the string
        var unmarshaller = context.createUnmarshaller();
        var result = (PurchaseOrder) unmarshaller.unmarshal(new StringReader(xml));

        System.out.println("Purchase order for: " + result.getCustomer().getName());
    }

    private static PurchaseOrder createPurchaseOrder() {
        var purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderDate(new Date());

        var item1 = new Item();
        item1.setProductName("ballpoint pen");
        item1.setQuantity(20);
        item1.setPrice(new BigDecimal("8.95"));
        item1.setComment("blue ink");

        var item2 = new Item();
        item2.setProductName("pencil");
        item2.setQuantity(10);
        item2.setPrice(new BigDecimal("2.95"));

        purchaseOrder.setItems(List.of(item1, item2));

        var customer = new Customer();
        customer.setName("George the Buyer");

        var address = new Address();
        address.setStreet("12 Main Street");
        address.setCity("Waukegan, IL");
        address.setPostalCode("60087");
        address.setCountry("USA");

        customer.setShippingAddress(address);
        customer.setBillingAddress(address);
        customer.setLoyalty(Loyalty.SILVER);

        purchaseOrder.setCustomer(customer);

        return purchaseOrder;
    }
}
