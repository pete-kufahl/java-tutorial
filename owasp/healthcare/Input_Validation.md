# Input Validation
filter input so that only well-formed data passes into the application

## Whitelisting
whitelisting allows only pre-approved data/operations
* compare to *blacklisting,* where known bad entities are blocked but unkown threats can still pass through
* embodies *deny-by-default*
* implementation:
  * validate input against allowed patterns to prevent attacks
  * whitelist file extensions for uploading
  * limit class loading and API access reduces the attack surface against an app (particularly useful for microservices)
* **healthcare-server - PatientResource - addNotes**
  * whitelist of allowed patterns is streamed against the input (turned into a regex by `Pattern.compile`)
  * test with api:
    * `GET http://localhost:8080/patients` to retrieve patient ids
    * `POST http://localhost:8080/patients/{id}/notes` to test whitelisted notes
      * header: `Content-Type: text/plain`
      * body: e.g., `Admitted` for a 200 response
* challenges:
  * whitelists have to be maintained and updated, costing in overhead
  * balance security against app performance

## Boundary Checking


## Setup
* run PatientServer
* run PatientRetriever to seed the H2 database