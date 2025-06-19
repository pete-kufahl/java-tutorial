package basicdemos;

import java.util.concurrent.CompletableFuture;

public class SimpleCompletableFuture {
    /**
     * as opposed to the executor pattern, we can just launch the task, and not wait
     * for it to complete
     */
    public static void main(String[] args) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        // Runnable does not take a parameter, returns nothing
        Runnable task = () -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException iex) {
                // do nothing
            }
            future.complete(null);
        };
        CompletableFuture.runAsync(task);
        Void res = future.join();
        System.out.println("We are done");
    }
}
