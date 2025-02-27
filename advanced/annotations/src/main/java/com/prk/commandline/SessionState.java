package com.prk.commandline;

import com.prk.commandline.model.Customer;
import com.prk.commandline.model.Order;

public class SessionState {

    private Customer customer;
    private Order.Builder orderBuilder = Order.builder();
    private boolean finished;

    public Customer getCustomer() { return customer; }

    public void setCustomer(Customer customer) { this.customer = customer; }

    public Order.Builder getOrderBuilder() { return this.orderBuilder; }

    public void setOrderBuilder(Order.Builder orderBuilder) { this.orderBuilder = orderBuilder; }

    public boolean isFinished() { return finished; }

    public void setFinished(boolean finished) { this.finished = finished; }
}
