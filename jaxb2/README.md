# JAXB Tutorial
This project contains packages that correspond to the lessons in the *Working with XML in Java Using JAXB 2* course on Pluralsight.

## contents
### xml-example
* jaxb1 - marshalling / unmarshalling using JAXB, StAX
* jaxb2 - JAXB annotations
* jaxb3 - dealing with List collections
* jaxb4 - dealing with Map collections
* jaxb5 - converting a java Map to an XML List with an Adapter

### schemaexample
* jaxb6 - generate a .xsd schema
* jaxb7 - marshalling with nillable elements
* jaxb8 - more control over nillable behavior with ObjectFactory
* jaxb9 - generate classes from a schema
  * redone with maven plugin and simple .xsd in src/main/resources/
  * steps:
    * before writing the marshalling and unmarshalling java classes,
    * put the .xsd into the resources/
    * `mvn clean compile`
    * copy over the generated classes from the target/... directory, if needed
    * fix the base data type `PurchaseOrderType` to have a root element: `@XmlRootElement(name = "PurchaseOrder")`
    * write the marshalling and unmarshalling classes
    * marshalling -> generates a target XML file
    * unmarshalling -> populates java objects from the XML file and the generated classes
    * unmarshalling with validation -> uses the .xsd schema to validate the XML before unmarshalling
### advanced
* jaxb10 - 

## caveats
* this was implemented on Java 17 SE JDK, which does not include JAXB in its standard distribution. JAXB was added as dependencies in maven.
* the tools schemagen and xjc are apparently no longer readily available
  * jaxb6 is a workaround for the lack of schemagen
  * the maven plugin `jaxb2-maven-plugin` replaces xjc
