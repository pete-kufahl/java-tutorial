package compose;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * demo for starting multiple tasks and using the data returned from ONE of them
 */
public class WeatherServiceFirstReport {
    record Weather(String server, String weather) {}

    public static void main(String[] args) {
        double ans = run(10);
        System.out.println("average first report runtime = " + ans);
    }

    public static double run(int iters) {
        Random random = new Random();
        List<Long> runtimes = new ArrayList<>();
        List<Supplier<Weather>> weatherTasks = buildWeatherTasks(random);

        for (int i = 0; i < iters; i++) {
            Instant begin = Instant.now();

            List<CompletableFuture<Weather>> futures = new ArrayList<>();
            for (var task: weatherTasks) {
                var future = CompletableFuture.supplyAsync(task);
                futures.add(future);
            }

            CompletableFuture<Object> future = CompletableFuture.anyOf(futures.toArray(CompletableFuture[]::new));
            future.thenAccept(System.out::println).join();

            Instant end = Instant.now();
            Duration dur = Duration.between(begin, end);
            runtimes.add(dur.toMillis());
        }
        return runtimes.stream().mapToLong(Long::longValue).average().orElse(0.0);
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
