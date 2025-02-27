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
  * 