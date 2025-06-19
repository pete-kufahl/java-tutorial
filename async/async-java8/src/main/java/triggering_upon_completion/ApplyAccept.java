package triggering_upon_completion;

import triggering_upon_completion.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


public class ApplyAccept {
    // 1. supplyIDs (async, use CF.supplyAsync())
    // 2. fetchUsers (sync, use cf.thenApply())
    // 3. displayer (sync, use cf.thenAccept())
    public static void main(String[] args) {

        Supplier<List<Long>> supplyIDs = () -> {
            sleep(200);
            return Arrays.asList(1L, 2L, 3L);
        };

        // input: list of Long (ids)
        // output: list of User
        Function<List<Long>, List<User>> fetchUsers = ids -> {
            sleep(300);
            return ids.stream()
                    .map(User::new)
                    .toList();
        };

        Consumer<List<User>> displayer = users -> {
            users.forEach(System.out::println);
        };

        CompletableFuture<List<Long>> future = CompletableFuture.supplyAsync(supplyIDs);
        future.thenApply(fetchUsers)
                .thenAccept(displayer);

        // invoke join() to make the main thread wait for the future to complete
        System.out.println(future.join());
    }

    private static void sleep(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException _) {
        }
    }
}
