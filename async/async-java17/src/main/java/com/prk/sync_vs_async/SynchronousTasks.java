package com.prk.sync_vs_async;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class SynchronousTasks {
    public static void main (String[] args) {
        double ans = run(10);
        System.out.println("average SYNC runtime = " + ans);
    }

    public static double run(int iters) {
        Random random = new Random();

        Callable<Quotation> fetchQuotationA = () -> {
            Thread.sleep(random.nextInt(80, 120));
            return new Quotation("Server A", random.nextInt(40, 60));
        };
        Callable<Quotation> fetchQuotationB= () -> {
            Thread.sleep(random.nextInt(80, 120));
            return new Quotation("Server B", random.nextInt(30, 70));
        };
        Callable<Quotation> fetchQuotationC = () -> {
            Thread.sleep(random.nextInt(80, 120));
            return new Quotation("Server C", random.nextInt(40, 80));
        };

        var quotationTasks = List.of(fetchQuotationA, fetchQuotationB, fetchQuotationC);
        List<Long> runtimes = new ArrayList<>();

        for (int i = 0; i < iters; i++) {
            Instant begin = Instant.now();
            Quotation bestQuotation = quotationTasks.stream()
                    .map(SynchronousTasks::fetchQuotation)
                    .min(Comparator.comparing(Quotation::amount))
                    .orElseThrow();
            Instant end = Instant.now();
            Duration dur = Duration.between(begin, end);
            System.out.println("Best quotation [SYNC ] = " + bestQuotation + " (" + dur.toMillis() + " ms)");
            runtimes.add(dur.toMillis());
        }
        return runtimes.stream().mapToLong(Long::longValue).average().orElse(0.0);
    }

    private static Quotation fetchQuotation(Callable<Quotation> task) {
        try {
            return task.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
