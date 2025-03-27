# async-java17
tutorials for the course *Asynchronous Programming in Java*

### sync_vs_async
shows three tasks handled synchronously, concurrently and asynchronously
* *SynchronousTasks* uses Java 5 APIs `Executor` and `Future`
* *AsyncTasks* uses Java 8 API `CompletableFuture`
* there's little throughput difference between the two approaches, but the advantage in `CompletableFuture` lies in the ability to chain consequential effects to the tasks

### trigger
chain operations together to create a pipeline
* use `CompletableFuture` with `thenApply()` or `thenAccept()` to chain asynchronous tasks
  * avoid blocking the main thread
  * divide processing into small operations
  * chain them with the "CompletionStage" API
    * one task can trigger as many tasks as needed
* be careful to make the resources returned from one task available for the next task
  * debug with displays of containers: an empty container can mean the waiting thread has died before the supplier thread has finished
* use threadsafe collections (e.g. `ConcurrentLinkedDeque` instead of `ArrayList`) to avoid race conditions

### compose
splitting a result into multiple asynchronous tasks
* launch multiple tasks at once, but use the first available result: `CompletableFuture.anyOf()`
  * *WeatherServiceAnyOf* reports the result from either of two weather-reporting tasks
  * `anyOf()` has a vararg parameter, but a `CompletableFuture<Object>` return type that may require a casting operation
  * since only one worker thread needs to finish for `.join()`, console output sometimes shows that only one thread completed
* launch multiple tasks, when you need all the results: ``
  * *QuotationServiceBestPrice* gets data from 3 suppliers, calls `.allOf()` that returns `CompletableFuture<Void>`
    * `CompletableFuture<Void>` stores the finished state of the first `CompleableFuture<>` instances 
    * handle this return type with the Stream API
      * `Stream.of(..).map(CompletableFuture::join).min(...).orElseThrow()` inside a `.join()`
* composing the `CompletableFuture` instances