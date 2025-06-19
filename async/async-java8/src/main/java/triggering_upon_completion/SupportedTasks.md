# CompletableFuture Supported Tasks
for chaining methods, depending on the type of method that gets executed after a completable future is run

| class    | example                          | method           | CF method     |
|----------|----------------------------------|------------------|---------------|
| Runnable | `() -> logger.info("user read")` | `void run()`     | `thenRun()`   |
| Consumer | `n -> logger.info(n + "users")`  | `void accept(T)` | `thenAccept()`|
| Function | `id -> database.getUser(id)`     | `V apply(U)`     | `thenApply()` |

