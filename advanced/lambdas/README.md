## lambdas
Demos of lambda functions and function references

* **sortnames** demos how a sorting function is made concise with a lambda expression
  * a *lambda expression* is an anonymous method
  * `(parameter list) -> { body }`
    * types of parameters are optional (but you cannot mix styles)
    * `()` means no parameters
    * parens unnecessary for a single parameter
    * `{}` not needed if the body is a single expression
  * lambdas implement functional interfaces
    * any interface that has exactly one abstract method, annotated with `@interface`
    * can assign a lambda to a functional interface, but not `var`
    * standard functional interfaces
      * `Function<T, R>` takes in a `T` value and returns an `R`
      * `Consumer<T>` takes in a `T`, does not return a value
      * `Supplier<T>` returns a `T`, does not take in a value
      * `Runnable` has no input or output
      * `BiFunction<T, U, R>` and `BiConsumer<T, U>` take 2 inputs
      * `Predicate<T>` takes in a `T`, returns a boolean
      * `UnaryOperator<T>` takes in a `T`, outputs a `T`
      * etc.
* **printnames** shows how lambdas must not modify captured local variables, and can often be used to follow the principle of functional programming (i.e. no side effects)
  * not always possible or even preferable to be purely functional (e.g. `Consumer`) but usually it maintains code readability
  * if there is a predefined static or instance method that does what the lambda is intended for, we can replace the lambda expression with a *method reference*
* **filewriter** shows how tasks that involve checked exceptions are not good candidates for lambda expressions.
  * wrapping it in an unchecked exception is possible, but better to just use a for-loop, etc.
* **products** demonstrates using the different varieties of method references
  * method references implement functional interfaces, like lambdas