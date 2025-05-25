# json
examples used for the PluralSight course **Java JSON Fundamentals**
* a large data file is used via symbolic link
  * download citylots.json from https://github.com/zemirco/sf-city-lots-json/blob/master/citylots.json
  * `ln -s ~/Downloads/citylots.json src/main/resources/citylots.json`
* `..Consumer` packages feature Jackson API demos
  * basic "demo" classes with main() functions
  * Servlet classes injected into a basic Jetty server

## demos
alternative approaches for producing JSON programmatically
* `String.format` commands
* the Generator API from Jackson
* the Bindings API from Jackson

## domConsumer
consuming JSON with the DOM api, which handles every field individually as part of an in-memory document

## bindingConsumer
consuming JSON with the Bindings API
### basic
when the JSON file and java POJO match field names, organization
### advanced
handling immutable POJO classes and transformations for name mismatches and list/map representations

## streamingConsumer
consuming JSON with the streaming API
### Benchmark test
* compares streaming versus binding api - execution speed
* `>  java -jar target/json-1.0-SNAPSHOT.jar JsonBenchmark.streaming` to run
* streaming about 20% faster
### Memory Consumption test
* streaming versus binding api
* streaming uses about 70% less memory on the heap

## versioning
* **ApiEvolution** demonstrates how the different APIs can be sensitive/insensitive to schema changes if the changed fields are not used in the extraction code
  * DOM and binding apis require all fields in the JSON input to be accounted for in the POJO
  * streaming api did not require DTO changes

## jersey
integration of JSON with JAX-RS and Jersey
* note that adding the jersey dependencies required a purge of the local repositories and clean install


