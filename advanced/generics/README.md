## generics
Custom generic types and methods

### generic interface and implementation

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
* **bounded_tree** example to add restrictions to the type parameters (to **tree**)
  * when the specification `T extends Comparable<T>` is added to the interface, it requires that a similar change be made in the implementations
    * e.g. `<T extends Comparable<T>> implements TreeNode<T>` where `T` refers to two type parameters with the same name
* **product** example demonstrates multiple bounds to a type parameter
  * use interfaces to make sort-extract method non-type-specific
    * *ProductDemo*
    * use &-syntax on left side of declaration: `<T extends HasId & HasName>`
    * change value parameter to use `T`
    * in implementation, change method references to interface methods
  * can only specify the intersection of bounds (no `|` used here)
    * actually, java does not support union types