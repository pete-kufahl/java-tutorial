# json
examples used for the PluralSight course **Java JSON Fundamentals**
* a large data file is used via symbolic link
  * download citylots.json from https://github.com/zemirco/sf-city-lots-json/blob/master/citylots.json
  * `ln -s ~/Downloads/citylots.json src/main/resources/citylots.json`
* demos are done with both basic "demo" classes with main() functions, and with Servlet classes injected into a basic Jetty server

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
