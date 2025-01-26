package com.xmlexample.jaxb1;
import com.xmlexample.jaxb1.domain.*;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UnmarshallingExample1 {

    public static void main(String[] args) {

        try {
            // Load the XML file from the resources directory using ClassLoader
            InputStream inputStream = UnmarshallingExample1.class.getClassLoader().getResourceAsStream("po1.xml");
            if (inputStream == null) {
                System.out.println("XML file not found.");
                return;
            }

            // Create JAXB context for PurchaseOrder
            JAXBContext context = JAXBContext.newInstance(PurchaseOrder.class);

            // Create an Unmarshaller
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Read the XML content into a String
            String xmlData = new Scanner(inputStream, StandardCharsets.UTF_8).useDelimiter("\\A").next();
            xmlData = xmlData.replaceFirst("(<\\?xml.*?\\?>)", ""); // strip out the xml declaration

            // PurchaseOrder purchaseOrder = (PurchaseOrder) unmarshaller.unmarshal(inputStream);

            // Unmarshal the XML data into a PurchaseOrder object
            StringReader reader = new StringReader(xmlData);
            PurchaseOrder purchaseOrder = (PurchaseOrder) unmarshaller.unmarshal(reader);

            // Print the unmarshalled object
            System.out.println("Customer Name: " + purchaseOrder.getCustomer().getName());
            System.out.println("Customer Email: " + purchaseOrder.getCustomer().getEmail());
            System.out.println("Items:");
            for (Item item : purchaseOrder.getItems()) {
                System.out.println("  - " + item.getName() + ": $" + item.getPrice());
            }
            System.out.println("Total Amount: $" + purchaseOrder.getTotalAmount());

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
