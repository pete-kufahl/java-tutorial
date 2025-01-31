package com.advanced.jaxb10;

import com.advanced.jaxb10.domain.*;
import javax.xml.XMLConstants;
import jakarta.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class UnmarshallingExample {
    public static void main(String[] args) {
        try {
            // Create JAXB context
            JAXBContext context = JAXBContext.newInstance(PurchaseOrderType.class);

            // Create unmarshaller
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Load XSD Schema for validation
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("src/main/resources/demo10/purchaseOrder.xsd"));
            unmarshaller.setSchema(schema);

            // error handler logs to a file
            unmarshaller.setEventHandler(event -> {
                try (FileWriter writer = new FileWriter("validation_errors.log", true)) {
                    String errorMessage = "Validation Error: " + event.getMessage() + "\n";
                    writer.write(errorMessage);
                    System.out.println(errorMessage); // Print to console as well
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false; // Stop processing on error
            });

            // Unmarshal XML to Java object
            File file = new File("purchaseOrder.xml");
            PurchaseOrderType order = (PurchaseOrderType) unmarshaller.unmarshal(file);

            System.out.println("Unmarshalled Java Object:");
            // Print Customer Name with a null check
            System.out.println("Customer Name: " + (order.getCustomerName() != null ? order.getCustomerName() : "N/A"));

            // Print Total Amount with a default value
            System.out.println("Total Amount: " + (order.getTotalAmount() != null ? order.getTotalAmount() : "0.00"));

            // Handle Items list safely
            if (order.getItems() != null && order.getItems().getItem() != null) {
                System.out.println("Items:");
                for (ItemType item : order.getItems().getItem()) {
                    System.out.println("- " + (item.getName() != null ? item.getName() : "Unnamed Item")
                            + " | Price: " + (item.getPrice() != null ? item.getPrice() : "0.00")
                            + " | Quantity: " + item.getQuantity());
                }
            } else {
                System.out.println("No items found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
