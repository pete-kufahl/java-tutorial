package triggering_upon_completion;

import model.Email;
import model.User;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class MultibranchBoth {
    public static void main(String[] args) {
        ExecutorService executor1 = Executors.newSingleThreadExecutor();

        Supplier<List<Long>> supplyIDs = () -> {
            sleep(200);
            return Arrays.asList(1L, 2L, 3L);
        };

        Function<List<Long>, CompletableFuture<List<User>>> fetchUsers = ids -> {
            sleep(300);
            Supplier<List<User>> userSupplier = () ->
                    ids.stream()
                        .map(User::new)
                        .toList();
            return CompletableFuture.supplyAsync(userSupplier);
        };

        Function<List<Long>, CompletableFuture<List<Email>>> fetchEmails = ids -> {
            sleep(305);
            Supplier<List<Email>> emailSupplier = () ->
                    ids.stream()
                            .map(Email::new)
                            .toList();
            return CompletableFuture.supplyAsync(emailSupplier);
        };

        Consumer<List<User>> displayer = users -> users.forEach(System.out::println);

        CompletableFuture<List<Long>> idsFuture = CompletableFuture.supplyAsync(supplyIDs);

        // fetch the users after getting the ids
        CompletableFuture<List<User>> usersFuture = idsFuture.thenCompose(fetchUsers);
        // fetch the emails after getting the ids
        CompletableFuture<List<Email>> emailFuture = idsFuture.thenCompose(fetchEmails);

        usersFuture.thenAcceptBoth(emailFuture,
                (users, emails) -> {
                    System.out.println(users.size() + " --- " + emails.size());
                });
        sleep(1_000);
        executor1.shutdown();
    }

    private static void sleep(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException _) {
        }
    }
}
