# Authentication and Password Management
determine who gets access to what, inside applications

## Communicating Authentication Data
standard: use encrypted channels for secure communication
  * when a user logs in, the credentials must reach the server securely
    * Transport Layer Security (TLS) ensures that these data are encrypted during network traversal
  * prevent leakage by avoiding logging of auth. data
    * implement strict logging policies

### Authentication Validation
validate beyond credentials match
* use secure hashing algorithms, e.g. bcrypt
  * computationally intensive encryption works against brute-force attacks
* error handling: balance informativeness and security
* monitor and log login attempts to track suspicious behavior

### Mitigate Timing Attacks
attackers use the time taken to authenticate credentials to get information: valid attempts take more time to process
* use constant time to mitigate theis risk

## Validating and Storing Authentication Data
insecure storage risks breaches, often termed *collateral damage* in a data breach

### Hashing and Salting
one-way function to create unique strings from secure inputs
* prefer bcrypt, PBKDF2 for storage
* a *salt* is a string added to a password prior to hashing to add randomness
  * helps prevent identical hash creation
  * in Java, use `SecureRandom` for unique salts
* **healthcare-cli - PasswordHasher** shows how to use a hasher and salt
  * `BCryptPasswordEncoder` from the Spring Security Core module takes care of both the hashing and salting

### Tokens
random tokens are used to secure sessions and passwords
* short-lived and easily invalidated, reducing the window of opportunity for exploitation
* also use `SecureRandom` for token generation

### Enforce Password Complexity and Change Policies
require regular password updates
* use a secure token-based email process to notify users
* * **healthcare-cli - PasswordHasher - isPasswordComplex** uses a regular expression to enforce password complexity
* complex passwords made by intensive hash functions help throw off timing attacks
  * **healthcare-cli - Pbkdf2Hasher** demos the PBKDF2 algorithm from javax.crypto api
  * **healthcare-cli - SpringSCrypt** demos the heavier SCrypt encoder; the Spring library just wraps the BouncyCastle class with an easier interface