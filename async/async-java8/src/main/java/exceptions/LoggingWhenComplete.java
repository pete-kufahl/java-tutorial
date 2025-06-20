package exceptions;

import model.User;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class LoggingWhenComplete {
    public static void main(String[] args) {

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

        CompletableFuture<List<Long>> supply = CompletableFuture.supplyAsync(supplyIDs);

        // define a BiConsumer for the whenComplete()
        CompletableFuture<List<Long>> supplyErrorHandler = supply.whenComplete((ids, ex) -> {
           if (ex != null) {
               System.out.println(ex.getMessage());
               // the exception is passed along to fetch, which completes exceptionally
           } else {
               // even with no code in this block, a successful result from supply
               //   gets returned to fetch ---v
           }
        });

        CompletableFuture<List<User>> fetch = supplyErrorHandler.thenApply(fetchUsers);
        CompletableFuture<Void> display = fetch.thenAccept(displayer);

        sleep(1_000);
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
