## generics
Custom generic types and methods

### custom generic interface and implementation

* **tree** binary tree example
  * two types of nodes (interface `TreeNode`): inner nodes and leaf nodes, which have or don't have child nodes
    * *generic types* define generic interface, 2 generic classes
  * the parameter `T` is the *type parameter*
* when we use the generic types, fill parameter with *type arguments* to *instantiate* the generic type to create a *parameterized type*
  * *instantiate* in this case does not mean only "create a new object" (at the value level), also means "instantiating at the type level"
  * i.e., a *generic type* is a type with *type parameters,* which is instantiated into a *parameterized type* by supplying *type arguments* to fill in its type parameters
    * e.g. `TreeNode<T>` --> `TreeNode<Integer>`
  * cannot use primitive types as type arguments (use `Integer` instead of `int`)
  * anonymous inner classes, enums and exception classes cannot have type parameters
  * use cases:
    * generic data structures
    * code reuse, e.g. generic abstract superclasses

### generic methods
* **pair** record example with 2 type parameters
  * uses a static method to instantiate a record
    * `Pair.of()` is a static method and does not have access to parameters of the enclosing type (`T, U`), so it needs two new type parameters (`V, W`)
    * `of` method preceded by `.<V, W>` syntax
      * or, can use compiler's type inference use `.of()`
  * non-static wither methods use `T, U`, since they call a particular instance
  * a map method uses `T, U` and returns something with new types `V, W`
    * takes in a `BiFunction<T, U, R>` to do so

### bounded type parameters
