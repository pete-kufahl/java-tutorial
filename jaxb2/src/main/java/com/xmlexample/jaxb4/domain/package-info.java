@XmlAccessorType(XmlAccessType.FIELD)
@XmlSchemaType(type = Date.class, name = "date")    // output dates as date, not datetime
package com.xmlexample.jaxb4.domain;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlSchemaType;

import java.util.Date;