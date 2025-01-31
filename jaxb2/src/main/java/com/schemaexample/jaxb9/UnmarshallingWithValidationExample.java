package com.schemaexample.jaxb9;

import com.schemaexample.jaxb9.domain.PurchaseOrderType;
import javax.xml.XMLConstants;
import jakarta.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

public class UnmarshallingWithValidationExample {
    public static void main(String[] args) {
        try {
            // Create JAXB context for the generated class
            JAXBContext context = JAXBContext.newInstance(PurchaseOrderType.class);

            // Create an unmarshaller
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Load the XSD schema for validation
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("src/main/resources/purchaseOrder.xsd"));

            // Set the schema on the unmarshaller for validation
            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(event -> {
                System.out.println("Validation Error: " + event.getMessage());
                return false;  // Stop processing if there's an error
            });

            // Unmarshal XML to Java object
            // var filename = "invalidPO.xml"; // missing customer name field
            var filename = "purchaseOrder.xml";
            File file = new File(filename);
            PurchaseOrderType order = (PurchaseOrderType) unmarshaller.unmarshal(file);

            // Print the contents of the Java object
            System.out.println("Unmarshalled Java Object:");
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Customer Name: " + order.getCustomerName());
            System.out.println("Total Amount: " + order.getTotalAmount());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
