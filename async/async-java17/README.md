# async-java17
tutorials for the course *Asynchronous Programming in Java*

### sync_vs_async
shows three tasks handled synchronously, concurrently and asynchronously
* *SynchronousTasks* uses Java 5 APIs `Executor` and `Future`
* *AsyncTasks* uses Java 8 API `CompletableFuture`
* there's little throughput difference between the two approaches, but the advantage in `CompletableFuture` lies in the ability to chain consequential effects to the tasks

### trigger
