# advanced
advanced java features, following the Pluralsight course *Java SE Advanced Language Features* and using Java 23

## records
Demonstrations of record classes, sealed classes and pattern-matching

* **rec** declare record class
* **accessor** override record accessor
	* this can create unexpected effects when making a copy of the object
* **hash** override the `hashCode` and `equals` methods
	* follow rule that these methods are overridden in tandem
* **constructor** override canonical constructor
	* *Order*
	* could be motivated by a record having a (mutable) container as a field
	* can also validate inputs to the constructor
	* compact constructor: eliminate argument list and non-custom assignments
	* additional constructors must always call the canonical constructor first
	* can add logic with a static factory method
* **builder** use the builder pattern to create records
	* *Order*
	* private class inside the record
	* design setters to make the record creation more intuitive
* **wither** use "wither" methods to make modified copies of records
	* *OrderLine*
	* makes code more intuitive, but be careful not to make extra copies
* **sealed** use a `sealed` interface to make limited subtypes of a "record"
	* *OrderLine, OrderService*
	* think of `sealed` as an `enum` for types instead of values
	* even though (because they cannot be subtypes) records are never sealed classes, they can work well with sealed interfaces
* **patterns** uses pattern-matching to eliminate type-casting operations
	* *OrderService*
	* a pattern variable can be used where its pattern-matching expression is known by the compiler to be true
	* pattern-matching and sealer interface enables a switch() expression to switch on type with exhaustive checking
		* the compile breaks if a new subtype is unaccounted for
	* add a `when` clause to add to the switching logic for additional conditions without cluttering the code after the `->`
* **recordpatterns** extends pattern-matching for records by enabling variables to be initialized to the fields of the matching pattern
	* *OrderService*
	* Java 21: must name a variable for every parameter in the canonical constructor
	* Java 22: can use an _ for unneeded variables
	* record patterns can also be nested; this provides access to records within records without calling the accessor methods
		* using _ for some variables in the "outer" record seemed to confuse the compiler

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




