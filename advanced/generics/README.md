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

### inheritance and wildcards
* **animal** example to show invariance of generic types
  * if two types S and T are related so that S implements/extends T, generic containers of these types do not have this relationship
* a *wildcard* is a way to refer to a family of types
  * `?` is an unbounded wildcard
  * `? extends SomeType` upper bounded wildcard, refers to `SomeType` and its subtypes
  * `? super SomeType` lower bounded wildcard, refers to `SomeType` and its super-types
  * whereas `List<T>` --> `List<String>` is a *concrete parameterized type*, `List<?>` creates a *wildcard parameterized type*
    * wildcard parameterized type is not fully defined, matches a family of parameterized types
    * `List<?>` matches `List<String>`, `List<Integer>` and so on
    * `List<? extends Animal>` matches `List<Cat>`, `List<Dog>` and `List<Animal>`
* java supports subtyping; this enables *wildcard capture*
  * however, the wildcard stands for a particular, but ***unknown*** type
  * therefore, we cannot add anything to a list of wildcard types
* uses for wildcards
  * defining methods that take parameters of parameterized types
    * avoids unnecessary restrictions created by concrete parameterized types
    * e.g. `Collections.copy()` enables `copy(animals, dogs)`
      * `public static <T> void copy(List<? super T> dest, List<? extends T> src)`
    * upper bounded wildcards `<? extends T>` useful for in-parameters (where data is read)
    * lower bounded wildcards `<? super T>` useful for out-parameters (where data is written)
    * no wildcard parameter that is both for in- and out-parameters, just use `<T>`
    * unbounded wildcards `<?>` useful for methods that do not change the state of the parameter, like `size()`
  * e.g. `Stream.flatMap()`
    * `<R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper)`
      * `T` type parameter of the Stream that flatMap is called on
      * `R` type parameter of the Stream that flatMap returns
      * the `Function` mapper takes `? super T` is input (lower-bounded!)
        * and uses a lower-bounded wildcard as its output
      * but these wildcards are used as output, and input of `flatMap` itself
  * in general, avoid using wildcards in the return type of a method
    * withholding type information from the method caller will makes things harder