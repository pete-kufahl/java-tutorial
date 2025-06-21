package com.example.service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService (serviceName = "OrderService")
public class OrderService {
    @WebMethod
    public String placeOrder(@WebParam(name = "item") String item, @WebParam(name = "quantity") int quantity) {
        return "Order received: " + quantity + " of " + item;
    }
}
