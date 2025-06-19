package triggering_upon_completion;

import triggering_upon_completion.model.Email;
import triggering_upon_completion.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class MultibranchEither {
    public static void main(String[] args) {
        ExecutorService executor1 = Executors.newSingleThreadExecutor();

        Supplier<List<Long>> supplyIDs = () -> {
            sleep(200);
            return Arrays.asList(1L, 2L, 3L);
        };

        Function<List<Long>, CompletableFuture<List<User>>> fetchUsers1 = ids -> {
            sleep(150);
            Supplier<List<User>> userSupplier = () ->
                    ids.stream()
                            .map(User::new)
                            .toList();
            return CompletableFuture.supplyAsync(userSupplier);
        };

        Function<List<Long>, CompletableFuture<List<User>>> fetchUsers2 = ids -> {
            sleep(5000);
            Supplier<List<User>> userSupplier = () ->
                    ids.stream()
                            .map(User::new)
                            .toList();
            return CompletableFuture.supplyAsync(userSupplier);
        };

        Consumer<List<User>> displayer = users -> users.forEach(System.out::println);

        CompletableFuture<List<Long>> idsFuture = CompletableFuture.supplyAsync(supplyIDs);

        // fetch the users after getting the ids
        CompletableFuture<List<User>> usersFuture1 = idsFuture.thenComposeAsync(fetchUsers1);
        // fetch the emails after getting the ids
        CompletableFuture<List<User>> usersFuture2 = idsFuture.thenComposeAsync(fetchUsers2);

        usersFuture1.thenRun(() -> System.out.println("Users 1"));
        usersFuture2.thenRun(() -> System.out.println("Users 2"));

        usersFuture1.acceptEither(usersFuture2, displayer);
        sleep(6_000);
        executor1.shutdown();

    }

    private static void sleep(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException _) {
        }
    }
}
