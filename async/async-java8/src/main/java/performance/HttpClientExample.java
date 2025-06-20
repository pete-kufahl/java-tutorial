package performance;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class HttpClientExample {

    public static void main(String[] args) {

        // demonstrates the delayed-start pattern.
        //  this is a solution to the problem of creating a chain of expensive tasks.
        //  for example, we don't want to move data from one thread to another
        //  so, we can chain tasks on the same thread and use a delayed start to
        //    trigger the chain
        // if we didn't want to do this, we could just call sendAsync()
        //  directly (see comment below)

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://www.example.com"))
                .build();

        CompletableFuture<Void> start = new CompletableFuture<>();
        CompletableFuture<HttpResponse<String>> future =
                start.thenCompose(nil -> client.sendAsync(request,
                        HttpResponse.BodyHandlers.ofString()));

        // Trigger the chain
        start.complete(null);

        /*
         * or, to just send the request immediately without using start:
         * CompletableFuture<HttpResponse<String>> future =
         *         client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
         */

        // optionally, block for the result
        future.thenAccept(response -> {
            System.out.println("Status code: " + response.statusCode());
            System.out.println("Body: " + response.body());
        }).join();
    }
}
