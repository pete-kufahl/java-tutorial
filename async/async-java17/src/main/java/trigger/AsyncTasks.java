package trigger;

import sync_vs_async.Quotation;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Supplier;

public class AsyncTasks {
    public static void main (String[] args) {
        double ans = run(1);
        System.out.println("average ASYNC runtime = " + ans);
    }

    public static double run(int iters) {
        Random random = new Random();

        Supplier<Quotation> fetchQuotationA = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return new Quotation("Server A", random.nextInt(40, 60));
        };

        Supplier<Quotation> fetchQuotationB = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return new Quotation("Server B", random.nextInt(30, 70));
        };

        Supplier<Quotation> fetchQuotationC = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return new Quotation("Server C", random.nextInt(40, 80));
        };

        var quotationTasks = List.of(fetchQuotationA, fetchQuotationB, fetchQuotationC);
        List<Long> runtimes = new ArrayList<>();

        for (int i = 0; i < iters; i++) {
            Instant begin = Instant.now();

            List<CompletableFuture<Quotation>> futures = new ArrayList<>();
            for (Supplier<Quotation> task : quotationTasks) {
                CompletableFuture<Quotation> future = CompletableFuture.supplyAsync(task);
                futures.add(future);
            }

            Collection<Quotation> quotations = new ConcurrentLinkedDeque<>();
            List<CompletableFuture<Void>> voids = new ArrayList<>();

            for (CompletableFuture<Quotation> future : futures) {
                future.thenAccept(System.out::println);
                CompletableFuture<Void> accept = future.thenAccept(quotations::add);
                voids.add(accept);
            }

            for (CompletableFuture<Void> v : voids) {
                v.join();
            }

            Quotation bestQuotation = quotations.stream()
                    .min(Comparator.comparing(Quotation::amount))
                    .orElseThrow();

            Instant end = Instant.now();
            Duration dur = Duration.between(begin, end);
            System.out.println("Best quotation [ASYNC] = " + bestQuotation + " (" + dur.toMillis() + " ms)");
            runtimes.add(dur.toMillis());
        }
        return runtimes.stream().mapToLong(Long::longValue).average().orElse(0.0);
    }
}
