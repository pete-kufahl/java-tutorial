# Input Validation
filter input so that only well-formed data passes into the application
### Setup
* run PatientServer
* run PatientRetriever to seed the H2 database

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
verify that any index or pointer into memory does not exceed allocated bounds
* buffer overflows have been exploited to execute arbitrary code
* Java provides collections that protect against overflows, but relying on built-in mechanisms is insufficient in secure applcations
  * check length of arrays before accessing them to proactively avoid IndexOutOfBounds exceptions
  * validate collection size with size()
  * ensure buffer and string bounds are checked
* **healthcare-server - PatientResource - addNotes**
  * define boundaries for user inputs (10 to 500 characters), provides length-based validation
* simpler than pattern whitelisting, but still a good measure against buffer overflow and injection attacks
* best practices:
  * perform early, i.e. before rest of application logic
  * centralize validation, leverage Java APIs that inherently perform these checks
  * include boundary checks in testing (unit ,fuzz, penetration)

## Character Escaping
substitute special characters with safe sequences when parsing input
* *input sanitization*
* prevents injection attacks where special characters can cause unintended actions in input parsing
  * SQL injection, XSS, etc.
* in Java
  * escape characters in HTML and XML
  * for SQL statements, use prepared statements or JDBC interface to avoid SQL injection
  * encode URLs with encoded special characters
* **healthcare-server - PatientResource - addNotes**
  * leverage apache.commons.StringEscapeUtils
    * `escapeJson()` escapes JSON characters, which Jackson already does
    * HTML escape: for displaying in web apps — `escapeHtml4()`
    * XML escape: for XML output — `escapeXml10()`
    * Java escape: for storing in code/logs — `escapeJava()`
* best practices:
  * match escaping strategy to specific context
  * utilize libraries whenever possible for automatic escaping, keep these updated
  * combine with validation (whitelist and/or boundary checking)
  * differentiate escaping from encoding appropriately

## Numeric Validation
check numeric input (range, type and format) against specific constraints
* prevents overflow errors, secures against numeric attacks
* in Java
  * prevent type mismatches (integer vs. float)
  * validate against set ranges (esp. precision and scale in financial applications)
  * use `BigInteger` and `BigDecimal` to handle large and high-precision numbers
* **healthcare-server - PatientResource - addNotes**
  * use `Integer.parseInt()` to enforce that the input is a valid integer
* best practices:
  * use libraries for simplified validation (apache commons, hibernate)
  * implement custom validation logic for specific rules
  * enhance UX with client-side validation
  * id fields may require uniqueness
  * consider internationalization issues

## Checking for Other Potentially Dangerous Characters
demo with **healthcare-server - PatientResource - addNotes**
### Null Bytes
null bytes can be used by attackers to bypass security measures, should be explicitly checked against
* Java interaction with C/C++ poses risks: C considers the string termination character a null byte
* in Java
  * validate input data (bound for eternal systems or the filesystem) to exclude null bytes
  * ensure boundary checks on data buffers
  * use `String.contains("\\0")`
* monitor Java-native code interoperability

### New Line Characters
newline characters can be leveraged in certain exploits, e.g. log injection, HTTP response splitting
* newlines can disrupt text-based formats
  * *log forging* with malicious entries
  * *HTTP response splitting* to inject unauthorized headers, etc.

### Path Alteration Handlers
path-alteration characters (`..`) can be exploited for unauthorized access into files or directories
* applications that construct filepaths from user input are particularly vulnerable
* in Java
  * use Java's `File.getCanonicalPath` method
  * define whitelists for allowed paths
  * sanitize user inputs for `..`, `/` and `\`, enforce least privilege access
