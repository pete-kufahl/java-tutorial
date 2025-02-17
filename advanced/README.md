# advanced
advanced java features, following the Pluralsight course *Java SE Advanced Language Features* and using Java 23

## records
Demonstrations of record classes, sealed classes and pattern-matching

## classes_interfaces
Demonstrations of advanced techniques for using classes and interfaces

* **staticnested** static nested classes
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
* **namedlocal** named local types
	* when a (local) class is declared inside a method, its methods have access to the parameters and local variables of the enclosing method
		* however, they must be "effectively" final
	* enclosing data members are also accessible by inner class methods, and they do not have to be effectively final
	* shadowed local variables are not accessible by inner class methods
	* rules about static vs. instance variables apply for the enclosing variables, parameters
	* there is no way to make an inner class `static`
	* also, cannot make inner class `private` but its methods can be declared `private`
	* uses include temporary data structures only relevant to the enclosing class
* **anonymous** nameless local classes




