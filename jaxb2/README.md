# JAXB Tutorial
This project contains packages that correspond to the lessons in the *Working with XML in Java Using JAXB 2* course on Pluralsight.

## contents
### xmlexample packages
* jaxb1 - marshalling / unmarshalling using JAXB, StAX
* jaxb2 - JAXB annotations
* jaxb3 - dealing with List collections
* jaxb4 - dealing with Map collections
* jaxb5 - converting a java Map to an XML List with an Adapter

### schemaexample packages
* jaxb6 - generate a ,xsd schema
* jaxb7 - marshalling with nillable elements
* jaxb8 - more control over nillable behavior with ObjectFactory

## caveats
* this was implemented on Java 17 SE JDK, which does not include JAXB in its standard distribution. JAXB was added as dependencies in maven.
* the tools schemagen and xjc are apparently no longer readily available
  * jaxb6 is a workaround for the lack of schemagen
