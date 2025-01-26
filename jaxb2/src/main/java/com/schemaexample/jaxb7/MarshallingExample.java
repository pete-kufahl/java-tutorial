package com.schemaexample.jaxb7;

import com.schemaexample.jaxb7.domain.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class MarshallingExample {
    /**
     * just commenting out the line that adds loyalty DOES NOT fill in the default value
     * JAXB uses the rules: null --> no XML element
     *      and: empty element --> default value
     */
    public static void main(String[] args) throws JAXBException {
        var purchaseOrder = createPurchaseOrder();
        var context = JAXBContext.newInstance(PurchaseOrder.class);

        var filepath = "src/main/resources/output.xml";
        marshall(context, purchaseOrder, filepath);

        unmarshall(context, filepath);
    }

    private static void marshall(JAXBContext context, PurchaseOrder purchaseOrder, String filepath) throws JAXBException {
        var marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // marshal to file
        File outputFile = new File("src/main/resources/output.xml");
        marshaller.marshal(purchaseOrder, outputFile);

        System.out.println("XML file created at: " + outputFile.getAbsolutePath());
    }

    private static void unmarshall(JAXBContext context, String filepath) throws JAXBException {
        // Specify the path to the generated XML file
        File xmlFile = new File("src/main/resources/output.xml");

        if (!xmlFile.exists()) {
            System.out.println("The XML file does not exist. Make sure to run the marshalling program first.");
            return;
        }

        // Create an Unmarshaller
        var unmarshaller = context.createUnmarshaller();

        // Unmarshal the XML file into a PurchaseOrder object
        var purchaseOrder = (PurchaseOrder) unmarshaller.unmarshal(xmlFile);

        // Print the unmarshalled object to verify correctness
        System.out.println("Customer Name: " + purchaseOrder.getCustomer().getName());

        // NullPointerException if loyalty does not have default value
        System.out.println("This is a " + purchaseOrder.getCustomer().printLoyalty() + " member!");

        System.out.println("Items:");
        for (Item item : purchaseOrder.getItems()) {
            System.out.println("  - " + item.getProductName() + ": $" + item.getPrice());
        }
        // System.out.println("Total Amount: $" + purchaseOrder.getTotalAmount());

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
