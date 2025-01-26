package com.schemaexample;

import java.io.File;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import com.schemaexample.domain.PurchaseOrder;

public class SchemaGenerator {
    public static void main(String[] args) throws Exception {
        JAXBContext context = JAXBContext.newInstance(PurchaseOrder.class);
        context.generateSchema(new SchemaOutputResolver() {
            @Override
            public Result createOutput(String namespaceUri, String suggestedFileName) {
                File file = new File("schema.xsd");
                return new StreamResult(file);
            }
        });
        System.out.println("Schema generated as schema.xsd");
    }
}
