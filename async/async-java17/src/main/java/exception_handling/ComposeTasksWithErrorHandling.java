package exception_handling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class ComposeTasksWithErrorHandling {
    record Quotation(String server, int amount) {}
    record Weather(String server, String weather) {}
    record TravelPage(Quotation quotation, Weather weather) {}

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        Random random = new Random();

        List<Supplier<Weather>> weatherTasks = buildWeatherTasks(random);
        List<Supplier<Quotation>> quotationTasks = buildQuotationTasks(random);

        List<CompletableFuture<Weather>> weatherCFs = new ArrayList<>();
        for (Supplier<Weather> weatherTask : weatherTasks) {
            CompletableFuture<Weather> weatherCF =
                    CompletableFuture.supplyAsync(weatherTask)
                            .exceptionally(e -> {
                                System.out.println("exception = " + e);
                                return new Weather("Unknown", "Unknown");
                            });
            weatherCFs.add(weatherCF);
        }

        CompletableFuture<Weather> anyOfWeather = CompletableFuture
                .anyOf(weatherCFs.toArray(CompletableFuture[]::new))
                .thenApply(weather -> (Weather) weather);

        List<CompletableFuture<Quotation>> quotationCFs = new ArrayList<>();
        for (Supplier<Quotation> quotationTask : quotationTasks) {
            CompletableFuture<Quotation> quotationCF =
                    CompletableFuture.supplyAsync(quotationTask)
                            .handle((quotation, exception) -> {
                                if (exception == null) { return quotation; }
                                else {
                                    System.out.println("exception = " + exception);
                                    return new Quotation("Unknown", 1_000);
                                }
                            });
            quotationCFs.add(quotationCF);
        }

        CompletableFuture<Void> allOfQuotations = CompletableFuture
                .allOf(quotationCFs.toArray(CompletableFuture[]::new));

        CompletableFuture<Quotation> bestQuotationCF =
                allOfQuotations.thenApply(
                        v -> {
                            return quotationCFs.stream()
                                    .map(CompletableFuture::join)
                                    .min(Comparator.comparing(Quotation::amount))
                                    .orElseThrow();
                        });

        CompletableFuture<Void> done = bestQuotationCF.thenCompose(
                quotation -> anyOfWeather.thenApply(
                        weather -> new TravelPage(quotation, weather)))
                .thenAccept(System.out::println);
        done.join();
    }

    private static List<Supplier<Quotation>> buildQuotationTasks(Random random) {
        Supplier<Quotation> fetchQuotationA = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
            throw new RuntimeException(new IOException("Quotation Server A unavailable"));
            //return new Quotation("Server A", random.nextInt(40, 60));
        };
        Supplier<Quotation> fetchQuotationB = () -> {
            try {
                Thread.sleep(random.nextInt(10, 20));
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
            // System.out.println("Quotation B in thread " + Thread.currentThread());
            return new Quotation("Server B", random.nextInt(30, 70));
        };
        Supplier<Quotation> fetchQuotationC = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
            // System.out.println("Quotation C in thread " + Thread.currentThread());
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
            // System.out.println("weather A running in " + Thread.currentThread());
            return new Weather("server A", "Clear");
        };
        Supplier<Weather> fetchWeatherB = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
            // System.out.println("weather B running in " + Thread.currentThread());
            throw new RuntimeException(new IOException("weather server B unavailable"));
            // return new Weather("server B", "Cloudy");
        };
        Supplier<Weather> fetchWeatherC = () -> {
            try {
                Thread.sleep(random.nextInt(80, 120));
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
            // System.out.println("weather C running in " + Thread.currentThread());
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
