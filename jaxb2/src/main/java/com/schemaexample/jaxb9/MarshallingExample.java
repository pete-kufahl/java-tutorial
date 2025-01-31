package com.schemaexample.jaxb9;

import com.schemaexample.jaxb9.domain.PurchaseOrderType;
import java.io.File;
import java.math.BigDecimal;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

public class MarshallingExample {
    public static void main(String[] args) {
        try {
            // Create JAXB context for the generated class
            JAXBContext context = JAXBContext.newInstance(PurchaseOrderType.class);

            // Create a marshaller
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Create a PurchaseOrder object
            var order = new PurchaseOrderType();
            order.setOrderId(1001);
            order.setCustomerName("John Doe");
            order.setTotalAmount(BigDecimal.valueOf(250.75));

            // Marshal the object to an XML file
            File file = new File("purchaseOrder.xml");
            marshaller.marshal(order, file);

            // Print the XML to console
            marshaller.marshal(order, System.out);

            System.out.println("XML file 'purchaseOrder.xml' generated successfully.");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
