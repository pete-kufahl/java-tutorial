package control;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class ControlComposeTasks {
    record Quotation(String server, int amount) {}
    record Weather(String server, String weather) {}
    record TravelPage(Quotation quotation, Weather weather) {}

    public static void main(String[] args) {
        run();
    }

    private static int quotationThreadIndex = 0;
    private static ThreadFactory quotationThreadFactory =
            r -> new Thread(r, "Quotation-" + quotationThreadIndex++);

    private static int weatherThreadIndex = 0;
    private static ThreadFactory weatherThreadFactory =
            r -> new Thread(r, "Weather-" + weatherThreadIndex++);

    private static int minThreadIndex = 0;
    private static ThreadFactory minThreadFactory =
            r -> new Thread(r, "Min-" + minThreadIndex++);

    public static void run() {
        Random random = new Random();
        ExecutorService quotationExecutor = Executors.newFixedThreadPool(4,
                quotationThreadFactory);
        ExecutorService weatherExecutor = Executors.newFixedThreadPool(4,
                weatherThreadFactory);
        ExecutorService minExecutor = Executors.newFixedThreadPool(1, minThreadFactory);

        List<Supplier<Weather>> weatherTasks = buildWeatherTasks(random);
        List<Supplier<Quotation>> quotationTasks = buildQuotationTasks(random);

        List<CompletableFuture<Weather>> weatherCFs = new ArrayList<>();
        for (Supplier<Weather> weatherTask : weatherTasks) {
            CompletableFuture<Weather> weatherCF =
                    CompletableFuture.supplyAsync(weatherTask, weatherExecutor);
            weatherCFs.add(weatherCF);
        }

        CompletableFuture<Weather> anyOfWeather = CompletableFuture
                .anyOf(weatherCFs.toArray(CompletableFuture[]::new))
                .thenApply(weather -> (Weather) weather);

        List<CompletableFuture<Quotation>> quotationCFs = new ArrayList<>();
        for (Supplier<Quotation> qt : quotationTasks) {
            CompletableFuture<Quotation> quotationCF =
                    CompletableFuture.supplyAsync(qt, quotationExecutor);
            quotationCFs.add(quotationCF);
        }

        CompletableFuture<Void> allOfQuotations = CompletableFuture
                .allOf(quotationCFs.toArray(CompletableFuture[]::new));

        CompletableFuture<Quotation> bestQuotationCF =
                allOfQuotations.thenApplyAsync(
                        v -> {
                            System.out.println("AllOf then apply " + Thread.currentThread());
                            return quotationCFs.stream()
                                    .map(CompletableFuture::join)
                                    .min(Comparator.comparing(Quotation::amount))
                                    .orElseThrow();
                        }, minExecutor);

        CompletableFuture<Void> done = bestQuotationCF.thenCompose(
                quotation -> anyOfWeather.thenApply(
                        weather -> new TravelPage(quotation, weather)))
                .thenAccept(System.out::println);
        done.join();

        quotationExecutor.shutdown();
        weatherExecutor.shutdown();
        minExecutor.shutdown();
    }

    private static List<Supplier<Quotation>> buildQuotationTasks(Random random) {
        Supplier<Quotation> fetchQuotationA = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
            System.out.println("Quotation A in thread " + Thread.currentThread());
            return new Quotation("Server A", random.nextInt(40, 60));
        };
        Supplier<Quotation> fetchQuotationB = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
            System.out.println("Quotation B in thread " + Thread.currentThread());
            return new Quotation("Server B", random.nextInt(30, 70));
        };
        Supplier<Quotation> fetchQuotationC = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
            System.out.println("Quotation C in thread " + Thread.currentThread());
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
            System.out.println("weather A running in " + Thread.currentThread());
            return new Weather("server A", "Clear");
        };
        Supplier<Weather> fetchWeatherB = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
            System.out.println("weather B running in " + Thread.currentThread());
            return new Weather("server B", "Cloudy");
        };
        Supplier<Weather> fetchWeatherC = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
            System.out.println("weather C running in " + Thread.currentThread());
            return new Weather("server C", "Rain");
        };
        return List.of(fetchWeatherA, fetchWeatherB, fetchWeatherC);
    }

    private static Quotation openFuture(Future<Quotation> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
