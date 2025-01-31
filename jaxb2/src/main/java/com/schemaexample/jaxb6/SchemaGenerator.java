package com.schemaexample.jaxb6;

import java.io.File;

import com.schemaexample.jaxb6.domain.PurchaseOrder;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

public class SchemaGenerator {
    public static void main(String[] args) throws Exception {
        // pass root element into context
        JAXBContext context = JAXBContext.newInstance(PurchaseOrder.class);
        context.generateSchema(new SchemaOutputResolver() {
            @Override
            public Result createOutput(String namespaceUri, String suggestedFileName) {
                File file = new File("other/schema.xsd");
                return new StreamResult(file);
            }
        });
        System.out.println("Schema generated as schema.xsd");
    }
}
