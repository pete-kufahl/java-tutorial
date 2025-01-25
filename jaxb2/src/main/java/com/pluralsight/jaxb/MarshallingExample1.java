package com.pluralsight.jaxb;

import com.pluralsight.jaxb.domain.Customer;
import com.pluralsight.jaxb.domain.Item;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import com.pluralsight.jaxb.domain.PurchaseOrder;

import java.util.Arrays;

public class MarshallingExample1 {
    public static void main(String... args) {
        Customer customer = new Customer("John Doe", "john.doe@example.com");
        Item item1 = new Item("Laptop", 1200.50);
        Item item2 = new Item("Mouse", 25.75);
        PurchaseOrder order = new PurchaseOrder(customer, Arrays.asList(item1, item2), 1226.25);

        try {
            // Create JAXB context
            JAXBContext context = JAXBContext.newInstance(PurchaseOrder.class);

            // Create a marshaller
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Marshal the data to System.out
            marshaller.marshal(order, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
