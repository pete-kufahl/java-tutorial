package com.prk.commandline.commands;

import com.prk.commandline.Command;
import com.prk.commandline.SessionState;
import com.prk.commandline.UserInput;
import com.prk.commandline.model.OrderLine;

import java.math.BigDecimal;
import java.util.List;

@Command(value = "showcart", order = 40, description = "show what's in the shopping cart")
public class ShowCartCommand implements CommandExecutor {

    @Override
    public void execute(SessionState sessionState, UserInput userInput) {
        List<OrderLine> orderLines = sessionState.getOrderBuilder().getLines();
        if (orderLines.isEmpty()) {
            System.out.println("the shopping cart is empty");
        } else {
            int index = 1;
            var total = BigDecimal.ZERO;
            for (OrderLine orderLine : orderLines) {
                var product = orderLine.product();
                System.out.printf("[%3d] [%6d] %-10s x %3d $ %6s - %s%n",
                        index++,
                        product.id(),
                        product.name(),
                        orderLine.quantity(),
                        orderLine.amount(),
                        product.description());
                total = total.add(orderLine.amount());
            }
            System.out.printf("            Total amount:       $ %6s%n", total);
        }
    }
}
