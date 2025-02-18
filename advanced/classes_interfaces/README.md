## classes_interfaces
Demonstrations of advanced techniques for using classes and interfaces

### nested classes
nested or inner classes should be named when they are potentially used for more than one instance, but could be anonymous (or lambdas) when are only used once per instance of the enclosing class

* **static_nested** static nested classes
	* used when we need a type exclusively used by another class
	* e.g. Builder pattern
* **inner** non-static nested classes
	* accessing the enclosing members from the inner class requires `this`
	* used for similar reasons as with static nested classes
		* need to access enclosing class from a non-static context
		* e.g. Iterator within ArrayList
	* *also note:* nested interfaces, records and enums
		* these are all implicitly static
		* also, anything inside an enclosing interface is implicitly static
		* i.e., there are no "inner classes" inside interfaces
* **named_local** named local types
	* when a (local) class is declared inside a method, its methods have access to the parameters and local variables of the enclosing method
		* however, they must be "effectively" final
	* enclosing data members are also accessible by inner class methods, and they do not have to be effectively final
	* shadowed local variables are not accessible by inner class methods
	* rules about static vs. instance variables apply for the enclosing variables, parameters
	* there is no way to make an inner class `static`
	* also, cannot make inner class `private` but its methods can be declared `private`
	* uses include temporary data structures only relevant to the enclosing class
* **anonymous** nameless local classes
	* follows all the rules of named local classes
	* but, there's no way to define the constructor of an anonymous class

### interfaces
*interfaces* define a common contract for implementing classes, while *abstract classes* provide a way to share implementation code

* **modern_interface** shows how default methods are added to interfaces
	* these allow methods to be added to interfaces that already have compiled subclasses
	* interface methods have to implemented somewhere, but the default method provides a way to maintain backward compatibility
		* it does make interfaces more like abstract classes
	* `private` methods can be added to interfaces to share code between default methods
		* cannot be accessed by subclasses
	* `static` methods can also be added to interfaces
		* can also be made `private` to limit access
* **static_initializer_blox** static initializer blocks
	* provides the space for code used to initialize static member variables 
	* e.g. configuring a class instance using properties from a file
	* roughly, these blocks are executed the first time when the class is used
* **instance_initializer_blox** instance initializer blocks
	* a way to initialize class variables outside constructors
	* rarely used, but useful when we want to share code between multiple constructors (but, they can also call each other with `this()`)
	