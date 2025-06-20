package exceptions;

import model.User;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ExceptionallyDemo {
    public static void main(String[] args) {

        // when supplier throws exception,
        // supply CF shows than it completed exceptionally,
        // but not the fetch or display CFs
        Supplier<List<Long>> supplyIDs = () -> {
            sleep(200);
            throw new IllegalStateException("no data");
            // return Arrays.asList(1L, 2L, 3L);
        };

        Function<List<Long>, List<User>> fetchUsers = ids -> {
            sleep(300);
            return ids.stream()
                    .map(User::new)
                    .toList();
        };

        Consumer<List<User>> displayer = users -> {
            users.forEach(System.out::println);
        };

        // add another CF supplyErrorHandler to handle exception
        CompletableFuture<List<Long>> supply = CompletableFuture.supplyAsync(supplyIDs);
        CompletableFuture<List<Long>> supplyErrorHandler = supply.exceptionally(e -> List.of());

        // wire the fetch CF to the supplyErrorHandler CF
        CompletableFuture<List<User>> fetch = supplyErrorHandler.thenApply(fetchUsers);
        CompletableFuture<Void> display = fetch.thenAccept(displayer);

        sleep(1_000);
        // supply.join(); .. throws completion exception caused by the no-data exception

        System.out.println("Supply  : done = " + supply.isDone() +
                " exception = " + supply.isCompletedExceptionally());
        System.out.println("Fetch   : done = " + fetch.isDone() +
                " exception = " + fetch.isCompletedExceptionally());
        System.out.println("Display : done = " + display.isDone() +
                " exception = " + display.isCompletedExceptionally());
    }

    private static void sleep(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException _) {
        }
    }
}
