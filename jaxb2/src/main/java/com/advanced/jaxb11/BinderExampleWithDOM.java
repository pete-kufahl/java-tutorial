package com.advanced.jaxb11;

import com.advanced.jaxb10.domain.*;

import jakarta.xml.bind.*;
import org.w3c.dom.*;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;

/**
 * Unmarshal XML to a DOM (Node or Element).
 * Apply changes to the DOM (e.g., modify or add nodes).
 * Update the DOM back to a Java object using Binder.
 * Marshal the Java object to the updated XML.
 */
public class BinderExampleWithDOM {
    /**
     * benefits of the Binder approach:
     * DOM-Level Manipulation: You can easily manipulate the XML before or after unmarshal,
     *  using standard DOM techniques.
     * Java Object Representation: You can continue to work with Java objects
     *  (e.g., PurchaseOrderType) and then marshal them back into XML without worrying
     *   about @XmlRootElement on the class.
     * Preserving XML Structure: The approach preserves the structure and flexibility of
     *  XML while still benefiting from the power of JAXB for binding Java objects.
     * @param args
     */
    public static void main(String[] args) {
        try {
            // Step 1: Create JAXBContext
            JAXBContext context = JAXBContext.newInstance(PurchaseOrderType.class);

            // Step 2: Create Unmarshaller
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Step 3: Load XSD Schema for Validation
            SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            Schema schema = schemaFactory.newSchema(new File("src/main/resources/demo10/purchaseOrder.xsd"));
            unmarshaller.setSchema(schema);

            // Step 4: Parse XML into a DOM Document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("purchaseOrder.xml"));

            // Step 5: Use Binder to unmarshal the DOM into Java object (without requiring @XmlRootElement)
            Binder<Node> binder = context.createBinder();
            PurchaseOrderType order = (PurchaseOrderType) binder.unmarshal(document);

            // Step 6: Modify the Java Object (Add a new item)
            ItemType newItem = new ItemType();
            newItem.setName("Wireless Mouse");
            newItem.setPrice(new BigDecimal("25.99"));
            newItem.setQuantity(2);

            if (order.getItems() != null) {
                List<ItemType> itemList = order.getItems().getItem();
                itemList.add(newItem);
            }

            // Step 7: Modify the DOM (Add a new item in the XML)
            Element rootElement = document.getDocumentElement();
            Element newItemElement = document.createElement("item");
            newItemElement.setAttribute("name", "Wireless Mouse");
            newItemElement.setAttribute("price", "25.99");
            newItemElement.setAttribute("quantity", "2");

            rootElement.getElementsByTagName("items").item(0).appendChild(newItemElement);

            // Step 8: Marshal the updated Java Object back to XML (from Java object, not DOM directly)
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(order, new File("updatedPurchaseOrder.xml"));

            System.out.println("Updated XML written to 'updatedPurchaseOrder.xml'");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
