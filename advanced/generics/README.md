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
