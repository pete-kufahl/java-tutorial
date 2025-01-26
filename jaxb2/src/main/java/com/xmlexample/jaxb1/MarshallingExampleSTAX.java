package com.xmlexample.jaxb1;

import com.xmlexample.jaxb1.domain.*;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import java.io.StringWriter;
import java.util.Arrays;

public class MarshallingExampleSTAX {

    public static void main(String[] args) {
        try {
            // Create JAXB context for the PurchaseOrder class
            JAXBContext context = JAXBContext.newInstance(PurchaseOrder.class);

            // Create a JAXB Marshaller
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Create an XMLOutputFactory instance
            XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();

            // Use a StringWriter to collect the XML output as a string
            StringWriter stringWriter = new StringWriter();

            // Create an XMLStreamWriter from the output factory
            XMLStreamWriter xmlStreamWriter = outputFactory.createXMLStreamWriter(stringWriter);

            // Create sample data
            Customer customer = new Customer("Alice", "alice@example.com");
            Item item1 = new Item("Book", 19.99);
            Item item2 = new Item("Pen", 2.49);
            PurchaseOrder purchaseOrder = new PurchaseOrder(customer, Arrays.asList(item1, item2), 22.48);

            // Marshal the data to the StAX XMLStreamWriter
            marshaller.marshal(purchaseOrder, xmlStreamWriter);

            // Close the XMLStreamWriter
            xmlStreamWriter.close();

            // Print the generated XML
            System.out.println(stringWriter.toString());

        } catch (JAXBException | XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }
}
