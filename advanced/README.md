# advanced
advanced java features, following the Pluralsight course *Java SE Advanced Language Features* and using Java 23

## records
record class demos

* **rec** declare record class
* **accessor** override record accessor
	* this can create unexpected effects when making a copy of the object
* **hash** override the `hashCode` and `equals` methods
	* follow rule that these methods are overridden in tandem
* **constructor** override constructor
	* could be motivated by a record having a (mutable) container as a field
	* can also validate inputs to the constructor

