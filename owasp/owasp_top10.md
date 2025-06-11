# OWASP Top 10 (as of 2024)

## Broken Access Control
failure is when users of an app overstep their boundaries in accessing information
* common causes: overlooking least priviledge, unnecessary access
* exploits: URL manipulations, api bypasses, insecure references
* affects 94% of apps with > 300k instances
* exposes sensitive information, mishandles data, enables CSRF (cross-ste request forgery)

## Cryptographic Failures (formerly Sensitive Data Exposure)
lack of proper cryptography, exposing data
* common causes: hard-coded passwords, risky algorithms, low entropy
* unencrypted data transmission, obselete cryptography
* weak/default keys, improper rotation, public exposure

## Injection
vulnerabilities include XSS (cross-site scripting), SQL injection
* 94% apps tested, 19% highest incidence
* external control of file names or paths
* common causes: improper user data handling, poorly-written dynamic queries
* types: SQL, NoSQL, OS commands; common vulnerabiltiies exploited in different interpreters

## Insecure Design
added by OWASP in 2021 to focus on design flaws and urge threat modeling, secure design integration
* risks: sensitive info leaks, unprotected credentials
* distinct from implementation issues
* holistic approach includes secure tooling and updated dependencies, platforms, etc.

## Security Misconfiguration
misconfiguration affects about 90% of applications
* improper configurations, XML entity issues
* insecure permissions, unnecessary features, default accounts
* unchanged passwords & revealing stacktraces
* suboptimal security in servers and frameworks

## Vulnerable and Outdated Components
not directly linked with common vulnerability exposures (CVEs), but made the 2024 list
* challenges: testing, assessing outdated third-party components
* conditions: unaware of component versions, dependencies
* risks: using vulnerable, outdated or unsupported software

## Identification and Authentication Failures
allowing automated attacks like credential-stuffing or brute-force attacks
* acceptance of weak or common passwords
* fragile credential recovery methods
* storing passwords as plain text
* lack of robust multi-factor authentication, secure sessions

## Software and Data Integrity Failures
introduced in 2021 list, concentrating on assumptions of integrity of externally-sourced software & data
* risks from untrusted plugins, libraries, modules, CDNs
* CI/CD pipeline vulnerabilities, unauthorized access risks
* auto-update functionality in applications without integrity verification

## Security Logging and Monitoring Failures
despite the scarcity of data, acknowledged for being critical for breach detection and response
* scope includes improper log handling
* lack of adequate monitoring & logging leads to insecurity
* absence of centralized analysis

## Server-Side request Forgery (SSRF)
relatively low occurence rate but with significant risk
* lack of oversight against URLs supplied by the users of a server application
* vulnerabilities allow pypassing firewalls, VPNs and ACLs
* linked to feature-rich web apps and cloud services
* threatens internal systems, sensitive data security
