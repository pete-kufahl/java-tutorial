# JAX-WS SOAP Web Service & Client Example (Gradle + Jakarta EE + GlassFish)

This guide walks you through setting up a simple online shop SOAP web service using JAX-WS and a corresponding client.

---

## 🧰 Tools & Technologies

- Java 17
- Jakarta EE 10
- Eclipse GlassFish 7
- JAX-WS (Metro)
- Gradle

---

## 📁 Project Structure

```
jaxws-demo/
├── settings.gradle
├── build.gradle (root)
├── shop-service/
│   └── build.gradle
├── shop-client/
│   └── build.gradle
```

---

## ✅ Step-by-Step Instructions

### Step 1: Define the Web Service Interface

`OrderService.java`

```java
@WebService(targetNamespace = "http://service.example.com/")
public interface OrderService { ... }
```

### Step 2: Implement the Service

`OrderServiceImpl.java`

```java
@WebService(endpointInterface = "com.example.service.OrderService")
public class OrderServiceImpl implements OrderService { ... }
```

### Step 3: Gradle Setup (shop-service)

Add dependencies and WAR plugin.

```groovy
plugins {
    id 'war'
}
dependencies {
    implementation 'jakarta.jws:jakarta.jws-api:3.0.0'
    implementation 'jakarta.xml.bind:jakarta.xml.bind-api:4.0.0'
}
```

---

### Step 4: Build the WAR

```bash
./gradlew :shop-service:war
```

---

### Step 5: Deploy to GlassFish

```bash
~/glassfish7/bin/asadmin deploy shop-service/build/libs/shop-service-1.0.0.war
```

To undeploy:

```bash
~/glassfish7/bin/asadmin undeploy shop-service-1.0.0
```

---

### Step 6: Check the WSDL

Visit:

```
http://localhost:8080/shop-service-1.0.0/OrderService?wsdl
```

---

### Step 7: Generate Client from WSDL

Add a custom Gradle task in `shop-client/build.gradle`:

```groovy
task generateSoapClient(type: JavaExec) {
    mainClass.set("com.sun.tools.ws.WsImport")
    args = [
        "-s", "$buildDir/generated-sources/wsdl",
        "-p", "com.example.client",
        "http://localhost:8080/shop-service-1.0.0/OrderService?wsdl"
    ]
}
```

Run the task:

```bash
./gradlew :shop-client:generateSoapClient
```

---

### Step 8: Create a ClientApp

```java
public class ClientApp {
    public static void main(String[] args) {
        OrderService_Service service = new OrderService_Service();
        OrderService port = service.getOrderServicePort();
        // invoke methods on the port...
    }
}
```

---

## ✅ Service Features to Add

- `getMenu() : List<MenuItem>`
- `addItemToOrder(orderId, item, qty)`
- `removeItemFromOrder(orderId, item)`
- `getOrderTotal(orderId)`
- `completeOrder(orderId, taxRate, tipAmount)`

Use XML-annotated POJOs (`MenuItem`, `Order`, etc.) and return types that are JAXB-compatible.

---

## 🔍 Verify Deployment

```bash
~/glassfish7/bin/asadmin list-applications --long
```

Then hit the endpoint in browser or curl:

```bash
curl http://localhost:8080/shop-service-1.0.0/OrderService?wsdl
```

---

