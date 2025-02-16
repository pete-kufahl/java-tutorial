# advanced
advanced java features, following the Pluralsight course *Java SE Advanced Language Features* and using Java 23

## records
record class demos

* **rec** declare record class
* **accessor** override record accessor
	* this can create unexpected effects when making a copy of the object
* **hash** override the `hashCode` and `equals` methods
	* follow rule that these methods are overridden in tandem
* **constructor** override canonical constructor
	* could be motivated by a record having a (mutable) container as a field
	* can also validate inputs to the constructor
	* compact constructor: eliminate argument list and non-custom assignments
	* additional constructors must always call the canonical constructor first
	* can add logic with a static factory method
* **builder** use the builder pattern to create records
	* private class inside the record
	* design setters to make the record creation more intuitive
* **wither** use "wither" methods to make modified copies of records
	* makes code more intuitive, but be careful not to make extra copies




