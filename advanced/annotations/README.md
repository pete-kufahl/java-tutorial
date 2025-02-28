# annotations
demos of annotations, Optional classes and try-with-resource cases

### annotations have three main usecases
* provide additional information to the compiler
  * `@Override`, `@Deprecated`, `@SupressWarnings`
* an *annotation processor* processes annotations at compile time, frequently generating additional code
  * *lombok* for data classes
  * *immutables* for immutable properties
  * *mapstruct* for special mapping/transformer operations
* annotations can also be processed at runtime
  * *Spring, Springboot, Jakarta EE*
  * *Jackson*, *JUnit*
  
### declaration annotations vs. type annotations
declaration annotations are far more common; they declare something about a particular variable
* type annotations require special tools, like *Checker*

### custom annotations
* **commandline** demos an interactive program with user-supplied commands
  * **@Command**: syntax for defining interfaces is reused for defining `@Interface` annotations
    * elements can be added, but `String value();` is not really a method
      * e.g. using `@Command(value = "login")` with a key, value for required element
    * element types are limited to primitives, string, enum, etc.
    * element values must be compile-time constants
    * can define defaults for elements, but must not be null
    * annotations cannot be generic, or be extensions of other annotations
      * exception: Spring framework, where `@Service` etc. are essentially `@Component` subtypes
  * meta-annotations are annotations applied to other annotations
    * `@Target`: where the annotation can be used, e.g. `TYPE`: classes only
    * `@Retention`: how long an annotation lives
    * `@Documented`: appears in the javadoc of the target
    * `@Inherited`: during class reflection, subclasses of the target appear as if they have the annotation
    * `@Repeatable`: annotation can be used more than once on the same element
      * still must define a second "container" annotation: **@Commands**

### Optional interface helps avoid null pointer exceptions (NPEs)
  * `Optional<T>` is a container for a type `T` that is never null
  * `Optional` is an immutable value class
    * value objects with the same value are interchangeable -> they should not be used for synchronization
  * designed to be return type for methods, not really as a field
    * not serializable
  * **products** demonstrates use cases of `Optional` with a list of records
    * **DemoOptional1** shows basic usage
      * type inference keeps code compact, as with `Optional.of<>()` and `Optional.empty<>()`
      * state of optionals can be checked with `.isPresent()` and `isEmpty()`
        * `.get()` without checking state is risky, can throw exception
        * `.orElseThrow()` is the same function, named more explicitly
    * **DemoOptional2** shows how `Optional` facilitates functional programming
      * stream operations like `findFirst()` return `Optional`
      * Optional API has several stream-like operations
      * use of `flatMap(Optional::stream)` to remove empty results

### Try-with-resources works with the AutoClosable interface
  * error-handling code with resources (files, etc.) can be very verbose, especially when nested exceptions can occur and more than one `finally` blocks are involved
  * **twofiles** demos multiple resources declared in the same try-block
    * takes away the burden of careful multiscope error-handling
  * the *resource* in this context implements `AutoCloseable`
    * files
    * network connections
    * MongoDB, JDBC connections
    * can use *var* in the resources statement
  * try-with-resources does not need a catch- or finally- block
    * **DemoAutoCloseable** shows rules of try-resource procedures
      * if exception is thrown in the try-block, resources are closed before the catch-block is executed
    * if an exception is thrown in the process of closing a resource, that exception is "suppressed"
      * prevents this exception from interfering with the stacktrace of the primary exception (which is presumably more interesting)
  * **tempdirectory** shows a practical usecase for implementing an `AutoCloseable` resource
    * **TempDirectory** allows the client to create a temporary directory (random name with prefix)
      * this directory is destroyed when the resource is closed
      * **close()** uses `Files.walkFileTree()`, which uses the Visitor pattern
      * demo uses two resources: the **TempDirectory** object (first) and a `BufferedWriter` (second, initialized with the first resource)
      