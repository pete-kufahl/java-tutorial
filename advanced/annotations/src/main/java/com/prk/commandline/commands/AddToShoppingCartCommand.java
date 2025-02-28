package com.prk.commandline.commands;

import com.prk.commandline.Command;
import com.prk.commandline.SessionState;
import com.prk.commandline.UserInput;
import com.prk.commandline.model.OrderLine;
import com.prk.commandline.model.Product;

import java.math.BigDecimal;

@Command(value = "add", order = 30, description = "add products to shopping cart")
public class AddToShoppingCartCommand implements CommandExecutor {

    @Override
    public void execute(SessionState sessionState, UserInput userInput) {
        if (userInput.arguments().length != 2) {
            System.out.println("usage: add <quantity> <product id>");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(userInput.arguments()[0]);
        } catch (NumberFormatException nfe) {
            System.out.println("Not a valid quantity: " + userInput.arguments()[0]);
            return;
        }

        long productId;
        try {
            productId = Long.parseLong(userInput.arguments()[1]);
        } catch (NumberFormatException nfe) {
            System.out.println("Not a valid product Id: " + userInput.arguments()[1]);
            return;
        }

        var product = Product.Products_By_Id.get(productId);
        if (product == null) {
            System.out.println("unknown product id: " + productId);
            System.out.println("try 'inventory' to list available products");
            return;
        }

        var amount = product.price().multiply(BigDecimal.valueOf(quantity));
        sessionState.getOrderBuilder().addLine(new OrderLine(product, quantity, amount));
        System.out.printf("add to your shopping cart: %d x %s - $ %s%n", quantity, product.name(), amount);
    }
}
