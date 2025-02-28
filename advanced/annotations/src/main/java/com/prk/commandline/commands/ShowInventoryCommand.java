package com.prk.commandline.commands;

import com.prk.commandline.Command;
import com.prk.commandline.SessionState;
import com.prk.commandline.UserInput;
import com.prk.commandline.model.Product;

import java.util.Comparator;

@Command(value = "inventory", order = 20, description = "list available products")
public class ShowInventoryCommand implements CommandExecutor {

    @Override
    public void execute(SessionState sessionState, UserInput userInput) {
        System.out.println("available products:");
        Product.Products_By_Id.values().stream()
                .sorted(Comparator.comparing(Product::id))
                .forEach(product -> System.out.printf("[%6d] %-10s $ %6s - %s%n",
                        product.id(),
                        product.name(),
                        product.price(),
                        product.description()));
    }
}
