package com.example.client;

public class ClientApp {
    public static void main(String[] args) {
        // The generated service class (based on WSDL)
        // Create the service instance
        OrderService_Service service = new OrderService_Service();

        // Get the port (proxy to the SOAP endpoint)
        OrderService port = service.getOrderServicePort();

        // Call the SOAP method
        String result = port.placeOrder("Coffee", 3);

        // Print the result
        System.out.println("Server responded: " + result);
    }
}
