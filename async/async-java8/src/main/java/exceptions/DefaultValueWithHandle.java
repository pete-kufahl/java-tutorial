package exceptions;

import model.User;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class DefaultValueWithHandle {
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

        //  in the case of an exception, it does not get passed to the next CF (fetch)
        CompletableFuture<List<Long>> supplyErrorHandler = supply.handle((ids, ex) -> {
            if (ex != null) {
                System.out.println(ex.getMessage());
                return List.of();
            } else {
                return ids;
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
