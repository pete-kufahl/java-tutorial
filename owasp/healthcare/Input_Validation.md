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