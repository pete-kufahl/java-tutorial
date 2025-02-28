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
