package com.xmlexample.jaxb4;

import com.xmlexample.jaxb4.domain.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CollectorsExampleMap {
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

        Map<String, Item> items = new HashMap<>();
        items.put("100348", item1);
        items.put("235607", item2);
        purchaseOrder.setItems(items);

        var customer = createCustomer();
        purchaseOrder.setCustomer(customer);

        return purchaseOrder;
    }

    private static Customer createCustomer() {
        var customer = new Customer();
        customer.setName("George the Buyer");

        var address = new Address();
        address.setStreet("12 Main Street");
        address.setCity("Waukegan, IL");
        address.setPostalCode("60087");
        var country = new Country();
        country.setCode("USA");
        country.setName("United States of America");
        address.setCountry(country);

        customer.setShippingAddress(address);
        customer.setBillingAddress(address);
        customer.setLoyalty(Loyalty.SILVER);
        return customer;
    }
}
