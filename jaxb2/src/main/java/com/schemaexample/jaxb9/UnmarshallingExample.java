package com.schemaexample.jaxb9;

import com.schemaexample.jaxb9.domain.PurchaseOrderType;
import jakarta.xml.bind.*;
import java.io.File;

public class UnmarshallingExample {
    public static void main(String[] args) {
        try {
            // Create JAXB context for the generated class
            JAXBContext context = JAXBContext.newInstance(PurchaseOrderType.class);

            // Create an unmarshaller
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Unmarshal XML to Java object
            File file = new File("purchaseOrder.xml");
            PurchaseOrderType order = (PurchaseOrderType) unmarshaller.unmarshal(file);

            // Print the contents of the Java object
            System.out.println("Unmarshalled Java Object:");
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Customer Name: " + order.getCustomerName());
            System.out.println("Total Amount: " + order.getTotalAmount());

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
