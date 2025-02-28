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
