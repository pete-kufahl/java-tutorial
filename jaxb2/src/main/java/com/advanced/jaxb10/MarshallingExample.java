package com.advanced.jaxb10;

import com.advanced.jaxb10.domain.*;
import javax.xml.XMLConstants;
import jakarta.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MarshallingExample {
    public static void main(String[] args) {
        try {
            // Create JAXB context
            JAXBContext context = JAXBContext.newInstance(PurchaseOrderType.class);

            // Create marshaller
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Load XSD Schema for validation
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("src/main/resources/demo10/purchaseOrder.xsd"));
            marshaller.setSchema(schema);

            // Create PurchaseOrder object
            PurchaseOrderType order = new PurchaseOrderType();
            order.setCustomerName("Alice Smith");
            order.setTotalAmount(new BigDecimal("99.99"));

            // Create Items list
            ItemsType items = new ItemsType();
            List<ItemType> itemList = new ArrayList<>();

            // Item 1
            ItemType item1 = new ItemType();
            item1.setName("Laptop Sleeve");
            item1.setPrice(new BigDecimal("29.99"));
            item1.setQuantity(1);
            itemList.add(item1);

            // Item 2
            ItemType item2 = new ItemType();
            item2.setName("Wireless Mouse");
            item2.setPrice(new BigDecimal("49.99"));
            item2.setQuantity(1);
            itemList.add(item2);

            items.getItem().addAll(itemList);
            order.setItems(items);

            // marshaller will log errors before marshalling
            marshaller.setEventHandler(event -> {
                try (FileWriter writer = new FileWriter("validation_errors.log", true)) {
                    String errorMessage = "Marshalling Validation Error: " + event.getMessage() + "\n";
                    writer.write(errorMessage);
                    System.out.println(errorMessage); // Print to console as well
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false; // Stop processing on error
            });

            // Marshal to XML file
            File file = new File("purchaseOrder.xml");
            marshaller.marshal(order, file);

            // Print XML to console
            marshaller.marshal(order, System.out);
            System.out.println("XML file 'purchaseOrder.xml' generated successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
