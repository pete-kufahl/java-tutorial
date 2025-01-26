package com.schemaexample.jaxb6.domain;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

// makes xml output values as integers, instead of, say, SILVER
@XmlEnum(Integer.class)
public enum Loyalty {
    @XmlEnumValue("10") BRONZE,
    @XmlEnumValue("20") SILVER,
    @XmlEnumValue("30") GOLD
}
