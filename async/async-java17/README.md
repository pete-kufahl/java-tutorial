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
  * *WeatherServiceFirstReport* is another version of this, with 3 suppliers and for-loop
    * note that the vararg argument for `anyOf()` requires an array, so use the Java-8 pattern:
      * `futures.toArray(CompletableFuture[]::new)`
* launch multiple tasks, when you need all the results
  * *QuotationServiceBestPrice* gets data from 3 suppliers, calls `.allOf()` that returns `CompletableFuture<Void>`
    * `CompletableFuture<Void>` stores the finished state of the first `CompleableFuture<>` instances 
    * handle this return type with the Stream API
      * `Stream.of(..).map(CompletableFuture::join).min(...).orElseThrow()` inside a `.thenApply() ... .join()`
      * note that `.thenApply()` wants a function that has a void parameter, use lambda syntax
* composing the `CompletableFuture` instances
  * *TravelPage1* demos 2 patterns for composing a result from two asynchronous sources without blocking the main thread
    *  `.thenCombine()` is clearer, but the `.thenApply()` call is an argument and will always be called (even if something goes wrong with the first CF)
    *  `.thenCompose()` puts `.thenApply()` in the body of a lambda expression, which means it won't be called if something goes wrong with the first CF
  * *TravelPage2* is a cleaner version of the `.thenCompose()` case
    * coding the pattern is tricky, with casting from `CompletableFuture<Object>` needed before the `Comparator`

### control
the concurrency framework allows control over what thread gets assigned to a task
* by default, async tasks are executed in the common fork/join pool
  * one wait list per thread, and an inactive thread can take a task from another thread --> *work stealing*
  * threads can managed (some amount to I/O, network, databases, etc.)
* `CompletableFuture.method()` naming conventions:
  * if the method passed into a CF does not end in `...Async`, the task will be finished by the same thread that it began with
  * if the method ends in `...Async`, this is generally not the case
    * overloads a method with an `Executor` as a parameter
    * `Executor` is a functional interface that can be implemented by a lambda
    * common in GUI frameworks
* *ControlComposeTasks* demonstrates use of `...Async` methods to send tasks to specific thread pools

### exception_handling
reporting and recovering from errors
* when a task completes with an exception, the rest of the pipeline will also fail if the exception is not handled
* the `CompletableFuture` API offers several handling options
  * `.exceptionally()` returns a CF with default values (optional)
  * `whenComplete()` takes a BiConsumer and Exception (one should be null), is suited for tasks with no downstream tasks
  * `handle()` takes a BiFunction and Exception (one should be null)
* *ComposeTasksWithErrorHandling* shows recovery from exceptions thrown by two worker tasks