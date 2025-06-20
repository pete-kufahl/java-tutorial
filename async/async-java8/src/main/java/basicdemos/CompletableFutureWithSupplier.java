package basicdemos;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class CompletableFutureWithSupplier {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Supplier<String> supplier = () -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                // do nothing
            }
            return Thread.currentThread().getName();
        };
        CompletableFuture<String> future = CompletableFuture.supplyAsync(supplier, executor);

        // what matters is when we call this .obtrudeValue()
        future.obtrudeValue("too long!");

        String res = future.join();
        System.out.println("result = " + res);
        res = future.join();
        System.out.println("result = " + res);
        executor.shutdown();
    }
}
