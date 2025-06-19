package triggering_upon_completion;

import triggering_upon_completion.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


public class ChainComposeAccept {
    public static void main(String[] args) {
        ExecutorService executor1 = Executors.newSingleThreadExecutor();
        ExecutorService executor2 = Executors.newSingleThreadExecutor();

        Supplier<List<Long>> supplyIDs = () -> {
            sleep(200);
            return Arrays.asList(1L, 2L, 3L);
        };

        // input: list of Long (ids)
        // output: list of User
        Function<List<Long>, CompletableFuture<List<User>>> fetchUsers = ids -> {
            sleep(300);
            System.out.println("function is currently running in " + Thread.currentThread().getName());
            Supplier<List<User>> userSupplier = () -> {
                System.out.println("currently running in " + Thread.currentThread().getName());
                return ids.stream()
                        .map(User::new)
                        .toList();
            };
            return CompletableFuture.supplyAsync(userSupplier, executor2);
        };

        Consumer<List<User>> displayer = users -> {
            System.out.println("running in " + Thread.currentThread().getName());
            users.forEach(System.out::println);
        };

        CompletableFuture<List<Long>> future = CompletableFuture.supplyAsync(supplyIDs);
        future.thenComposeAsync(fetchUsers, executor2)
                .thenAcceptAsync(displayer, executor1);
        sleep(1000);
        executor1.shutdown();
        executor2.shutdown();
    }

    private static void sleep(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException _) {
        }
    }
}
