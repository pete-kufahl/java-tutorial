package compose;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class WeatherServiceAnyOf {
    record Weather(String server, String weather) {}

    public static void main(String[] args) {
        double ans = run(10);
        System.out.println("average weather runtime = " + ans);
    }

    public static double run(int iters) {

        Supplier<Weather> fetchWeatherA = () -> {
            try {
                Thread.sleep(11);
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
            return new Weather("Server A", "rainy");
        };
        Supplier<Weather> fetchWeatherB = () -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ie) {
                throw new RuntimeException(ie);
            }
            return new Weather("Server B", "mostly sunny");
        };

        List<Long> runtimes = new ArrayList<>();
        for (int i = 0; i < iters; i++) {
            Instant begin = Instant.now();

            CompletableFuture<Weather> taskA = CompletableFuture.supplyAsync(fetchWeatherA);
            CompletableFuture<Weather> taskB = CompletableFuture.supplyAsync(fetchWeatherB);

            CompletableFuture.anyOf(taskA, taskB)
                    .thenAccept(System.out::println)
                    .join();

            System.out.println("taskA = " + taskA);
            System.out.println("taskB = " + taskB);

            Instant end = Instant.now();
            Duration dur = Duration.between(begin, end);
            runtimes.add(dur.toMillis());
        }
        return runtimes.stream().mapToLong(Long::longValue).average().orElse(0.0);
    }

}
