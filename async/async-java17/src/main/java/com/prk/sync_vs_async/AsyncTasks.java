package com.prk.sync_vs_async;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class AsyncTasks {
    public static void main (String[] args) {
        double ans = run(10);
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

            List<Quotation> quotations = new ArrayList<>();
            for (CompletableFuture<Quotation> future : futures) {
                Quotation quotation = future.join();
                quotations.add(quotation);
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
