package com.prk.commandline.commands;

import com.prk.commandline.Command;
import com.prk.commandline.SessionState;
import com.prk.commandline.UserInput;
import com.prk.commandline.model.Customer;

@Command(value = "login", order = 10, description = "login to identify who's shopping")
public class LoginCommand implements CommandExecutor {

    @Override
    public void execute(SessionState sessionState, UserInput userInput) {
        if (sessionState.getCustomer() != null) {
            System.out.println("already logged in as: " + sessionState.getCustomer().name());
            return;
        }

        if (userInput.arguments().length != 1) {
            System.out.println("usage: login <customer id>");
            return;
        }

        long customerId;
        try {
            customerId = Long.parseLong(userInput.arguments()[0]);
        } catch (NumberFormatException nfe) {
            System.out.println("not a valid customer id: " + userInput.arguments()[0]);
            return;
        }

        var customer = Customer.Customers_By_Id.get(customerId);
        if (customer == null) {
            System.out.println("unknown customer id: " + customerId);
            return;
        }

        sessionState.setCustomer(customer);
        System.out.println("welcome, " + customer.name());
    }
}
