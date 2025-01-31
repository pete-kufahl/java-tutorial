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
<details>
<summary>steps</summary>
<ul>
  <li>before writing the marshalling and unmarshalling java classes, put the .xsd into the resources/demo9/</li>
  <li><code>mvn clean compile</code></li>
  <li>copy over the generated classes from the target/... directory, if needed</li>
  <li>fix the base data type <code>PurchaseOrderType</code> to have a root element: <code>@XmlRootElement(name = "PurchaseOrder")</code></li>
  <li>write the marshalling and unmarshalling classes</li>
  <li>marshalling -> generates a target XML file</li>
  <li>unmarshalling -> populates java objects from the XML file and the generated classes</li>
  <li>unmarshalling with validation -> uses the .xsd schema to validate the XML before unmarshalling</li>
</ul>
</details>

### advanced
* jaxb10 - validate against .xsd during both marshalling and unmarshalling
<details>
<summary>steps</summary>
<ul>
  <li>define .xsd in resources/demo10/</li>
  <li>modify pom.xml to use this .xsd and place the generated classes in a advanced.jaxb10.domain package</li>
  <li>copy generated classes in main/java/com/advanced/</li>
  <li>fix the base data type <code>PurchaseOrderType</code> to have a root element: <code>@XmlRootElement(name = "PurchaseOrder")</code></li>
  <li>enter code for, build and run MarshallingExample
    <ul>
      <li>this creates a PurchaseOrder object and validates it against the schema</li>
      <li>then, it generates XML</li>
    </ul>
  </li>
<li>enter code for, build and run UnmarshallingExample
  <ul>
      <li>like before, this reads in the XML and validates it against the schema</li>
  </ul>
  </li>
</ul>
</details>

* jaxb11 - use Binder to maintain a DOM tree
  * unmarshal an XML document into a DOM tree
  * apply some changes to the DOM (e.g., add an element)
  * bind the modified DOM back to Java objects using the Binder
    * avoids the `@XmlRootElement` exception: no direct marshalling of the DOM element: We marshal the Java object (`PurchaseOrderType`), which doesn’t require `@XmlRootElement`.
    * also, the Binder is only used for DOM to Java object transformation
      * The Binder is applied in this scenario where the XML is being manipulated at the DOM level, and we then convert it to a Java object. This avoids marshalling a DOM element without a root.


## caveats
* this was implemented on Java 17 SE JDK, which does not include JAXB in its standard distribution. JAXB was added as dependencies in maven.
* the tools schemagen and xjc are apparently no longer readily available
  * jaxb6 is a workaround for the lack of schemagen
  * the maven plugin `jaxb2-maven-plugin` replaces xjc
