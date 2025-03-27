package compose;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class TravelPage1 {
    record TravelPage(Quotation quotation, Weather weather) {}
    record Quotation(String server, int amount) {}
    record Weather(String server, String weather) {}

    public static void main(String[] args) {
        double ans = run(10);
        System.out.println("average travel page compose runtime = " + ans);
    }

    public static double run(int iters) {
        Random random = new Random();
        List<Long> runtimes = new ArrayList<>();
        List<Supplier<Weather>> weatherTasks = buildWeatherTasks(random);
        List<Supplier<Quotation>> quotationTasks = buildTasks(random);

        for (int i = 0; i < iters; i++) {
            Instant begin = Instant.now();

            List<CompletableFuture<Weather>> weatherCFS = new ArrayList<>();
            for (Supplier<Weather> task : weatherTasks) {
                CompletableFuture<Weather> future = CompletableFuture.supplyAsync(task);
                weatherCFS.add(future);
            }

            List<CompletableFuture<Quotation>> quotationCFS = new ArrayList<>();
            for (Supplier<Quotation> task : quotationTasks) {
                CompletableFuture<Quotation> future = CompletableFuture.supplyAsync(task);
                quotationCFS.add(future);
            }

            CompletableFuture<Void> allOfQuotations =
                    CompletableFuture.allOf(quotationCFS.toArray(CompletableFuture[]::new));

            CompletableFuture<Quotation> bestQuotationCF =
                    allOfQuotations.thenApply(
                            v -> quotationCFS.stream()
                                    .map(CompletableFuture::join)
                                    .min(Comparator.comparing(Quotation::amount))
                                    .orElseThrow()
                            );

            // combine case
            CompletableFuture<TravelPage> pageCompletableFuture1 =
                    bestQuotationCF.thenCombine(
                            CompletableFuture.anyOf(weatherCFS.toArray(CompletableFuture[]::new))
                                    .thenApply(o -> (Weather) o), TravelPage::new);
            pageCompletableFuture1.thenAccept(System.out::println).join();

            // compose case
            CompletableFuture<TravelPage> pageCompletableFuture2 =
                    bestQuotationCF.thenCompose(
                            quotation ->
                                    CompletableFuture.anyOf(weatherCFS.toArray(CompletableFuture[]::new))
                                            .thenApply(o -> (Weather) o)
                                            .thenApply(weather -> new TravelPage(quotation, weather)));
            pageCompletableFuture2.thenAccept(System.out::println).join();

            Instant end = Instant.now();
            Duration dur = Duration.between(begin, end);
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

    private static List<Supplier<Weather>> buildWeatherTasks(Random random) {
        Supplier<Weather> fetchWeatherA = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
            return new Weather("Server A", "rainy");
        };
        Supplier<Weather> fetchWeatherB = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
            return new Weather("Server B", "mostly sunny");
        };
        Supplier<Weather> fetchWeatherC = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
            return new Weather("Server C", "clear");
        };
        return List.of(fetchWeatherA, fetchWeatherB, fetchWeatherC);
    }
}
