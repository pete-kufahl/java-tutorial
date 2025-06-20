package performance;

import model.User;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class DelayedStartExample {
    public static void main(String[] args) {

        // assign the delayed start to a particular thread
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Supplier<List<Long>> supplyIDs = () -> {
            sleep(200);
            return Arrays.asList(1L, 2L, 3L);
        };

        Function<List<Long>, List<User>> fetchUsers = ids -> {
            sleep(300);
            return ids.stream()
                    .map(User::new)
                    .toList();
        };

        Consumer<List<User>> displayer = users -> {
            System.out.println("in thread: " + Thread.currentThread().getName());
            users.forEach(System.out::println);
        };

        // "fake" CF for the delayed start
        CompletableFuture<Void> start = new CompletableFuture<>();

        // lambda argument for thenApply() requires us to use Supplier<>.get()
        CompletableFuture<List<Long>> supply = start.thenApply(nil -> supplyIDs.get());
        CompletableFuture<List<User>> fetch = supply.thenApply(fetchUsers);
        CompletableFuture<Void> display = fetch.thenAccept(displayer);

        // now, we have to actually trigger the start
        start.completeAsync(() -> null, executor); // use thread pool-1-thread-1
        // start.complete(null);   // use main thread

        sleep(1_000);
        System.out.println("Supply  : done = " + supply.isDone() +
                " exception = " + supply.isCompletedExceptionally());
        System.out.println("Fetch   : done = " + fetch.isDone() +
                " exception = " + fetch.isCompletedExceptionally());
        System.out.println("Display : done = " + display.isDone() +
                " exception = " + display.isCompletedExceptionally());

        executor.shutdown();
    }

    private static void sleep(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException _) {
        }
    }
}
