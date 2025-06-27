package com.example.service;

import com.example.model.MenuItem;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import java.util.List;

@WebService(targetNamespace = "http://service.example.com/", name = "OrderService")
public interface OrderService {

    @WebMethod
    String placeOrder(@WebParam(name = "item") String item, @WebParam(name = "quantity") int quantity);

    @WebMethod
    List<MenuItem> getMenu();

    @WebMethod
    double getOrderTotal(@WebParam(name = "orderId") String orderId);

    @WebMethod
    String addItemToOrder(@WebParam(name = "orderId") String orderId,
                          @WebParam(name = "item") String itemName,
                          @WebParam(name = "quantity") int quantity);

    @WebMethod
    String removeItemFromOrder(@WebParam(name = "orderId") String orderId,
                               @WebParam(name = "item") String itemName);

    @WebMethod
    double completeOrder(@WebParam(name = "orderId") String orderId,
                         @WebParam(name = "taxRate") double taxRate,
                         @WebParam(name = "tipAmount") double tipAmount);
}
