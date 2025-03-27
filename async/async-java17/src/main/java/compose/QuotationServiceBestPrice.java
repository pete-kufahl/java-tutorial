package compose;

import sync_vs_async.Quotation;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * demo for reading multiple tasks and comparing the information returned from ALL of them
 */
public class QuotationServiceBestPrice {
    public static void main(String[] args) {
        double ans = run(10);
        System.out.println("average best price runtime = " + ans);
    }

    public static double run(int iters) {
        Random random = new Random();
        List<Long> runtimes = new ArrayList<>();
        List<Supplier<Quotation>> quotationTasks = buildTasks(random);

        for (int i = 0; i < iters; i++) {
            Instant begin = Instant.now();
            List<CompletableFuture<Quotation>> quotationCFS = new ArrayList<>();
            for (Supplier<Quotation> task : quotationTasks) {
                CompletableFuture<Quotation> future = CompletableFuture.supplyAsync(task);
                quotationCFS.add(future);
            }
            CompletableFuture<Void> allOfReturn = CompletableFuture.allOf(quotationCFS.toArray(CompletableFuture[]::new));

            Quotation bestQuotation = allOfReturn.thenApply(
                    v -> quotationCFS.stream()
                            .map(CompletableFuture::join)
                            .min(Comparator.comparing(Quotation::amount))
                            .orElseThrow()
                    ).join();

            Instant end = Instant.now();
            Duration dur = Duration.between(begin, end);
            System.out.println("Best quotation = " + bestQuotation + " (" + dur.toMillis() + " ms)");
            runtimes.add(dur.toMillis());
        }
        return runtimes.stream().mapToLong(Long::longValue).average().orElse(0.0);
    }

    private static List<Supplier<Quotation>> buildTasks(Random random) {
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
        return List.of(fetchQuotationA, fetchQuotationB, fetchQuotationC);
    }
}
