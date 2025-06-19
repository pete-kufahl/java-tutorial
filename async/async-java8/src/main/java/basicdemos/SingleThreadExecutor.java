package basicdemos;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutor {

    public static void main(String[] args) throws InterruptedException {
        // launch a task
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Runnable task = () -> {
            System.out.println("i am running asynchronously in the thread " +
                    Thread.currentThread().getName());
        };
        CompletableFuture.runAsync(task);
        Thread.sleep(100); // do something while the task executes
        executor.shutdown();
    }
}
