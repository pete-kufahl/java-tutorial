package com.schemaexample.jaxb8;

import com.schemaexample.jaxb8.domain.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ObjectFactoryExample {
    private static final boolean CHOOSE_NIL = false;

    public static void main(String[] args) throws JAXBException {
        var purchaseOrder = createPurchaseOrder();
        var context = JAXBContext.newInstance(PurchaseOrder.class);

        var filepath = "src/main/resources/output8.xml";
        marshall(context, purchaseOrder, filepath);

        unmarshall(context, filepath);
    }

    private static void marshall(JAXBContext context, PurchaseOrder purchaseOrder, String filepath) throws JAXBException {
        var marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // marshall to file
        File outputFile = new File(filepath);
        marshaller.marshal(purchaseOrder, outputFile);

        System.out.println("XML file created at: " + outputFile.getAbsolutePath());
    }

    private static void unmarshall(JAXBContext context, String filepath) throws JAXBException {
        // Specify the path to the generated XML file
        File xmlFile = new File(filepath);

        if (!xmlFile.exists()) {
            System.out.println("The XML file does not exist. Make sure to run the marshalling program first.");
            return;
        }

        // Create an Unmarshaller
        var unmarshaller = context.createUnmarshaller();

        // Unmarshal the XML file into a PurchaseOrder object
        var purchaseOrder = (PurchaseOrder) unmarshaller.unmarshal(xmlFile);

        System.out.println("Customer Name: " + purchaseOrder.getCustomer().getName());

        // NullPointerException if loyalty does not have default value
        // System.out.println("This is a " + purchaseOrder.getCustomer().printLoyalty() + " member!");

        System.out.println("Items:");
        for (Item item : purchaseOrder.getItems()) {
            System.out.println("  - " + item.getProductName() + ": $" + item.getPrice());
            if (item.getComment() == null) {
                System.out.println("NO COMMENT");
            } else {
                System.out.println(item.getComment().getValue());
            }
        }

        System.out.println("Loyalty: " + purchaseOrder.getCustomer().getLoyalty());

    }

    private static PurchaseOrder createPurchaseOrder() {
        var purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderDate(new Date());

        var item1 = new Item();
        item1.setProductName("ballpoint pen");
        item1.setQuantity(20);
        item1.setPrice(new BigDecimal("8.95"));

        var fac = new ObjectFactory();
        item1.setComment(fac.createItemComment("blue ink"));

        var item2 = new Item();
        item2.setProductName("pencil");
        item2.setQuantity(10);
        item2.setPrice(new BigDecimal("2.95"));

        if (CHOOSE_NIL) {
            JAXBElement<String> comment = fac.createItemComment(null);
            comment.setNil(true);
            item2.setComment(comment); // results in the nil element in XML
        } else {
            item2.setComment(null); // results in no element in XML
        }

        purchaseOrder.setItems(List.of(item1, item2));

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
        // customer.setLoyalty(Loyalty.SILVER);
        return customer;
    }
}
