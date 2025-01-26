package com.schemaexample.jaxb8.domain;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;

import javax.xml.namespace.QName;

public class ObjectFactory {

    // attrib specifies what the element is for
    @XmlElementDecl(namespace = "http://www.prk.org/ps-jxb", name="comment", scope=Item.class)
    public JAXBElement<String> createItemComment(String comment) {
        return new JAXBElement<>(new QName("http://www.prk.org/ps-jxb", "comment"),
                String.class,
                Item.class,
                comment);
    }
}
