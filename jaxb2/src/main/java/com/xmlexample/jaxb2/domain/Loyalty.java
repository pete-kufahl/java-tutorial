package com.xmlexample.jaxb2.domain;

import jakarta.xml.bind.annotation.*;

// makes xml output values as integers, instead of, say, SILVER
@XmlEnum(Integer.class)
public enum Loyalty {
    @XmlEnumValue("10") BRONZE,
    @XmlEnumValue("20") SILVER,
    @XmlEnumValue("30") GOLD
}
