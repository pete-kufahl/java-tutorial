package com.schemaexample.jaxb8.domain;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

public class Country {
    @XmlAttribute
    private String code;

    @XmlElement(required=true)  // forces output as simple type (one less level in tree)
    private String name;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
