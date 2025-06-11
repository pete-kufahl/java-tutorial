# Output Encoding
secure output encoding (for web apps, creating benign output before it's rendered in the browser) is an important safety measure against injection attacks

## encoding mechanics
* proper encoding of certain characters (angle brackets, quotation marks) is critical for web security
  * angle brackets enable XSS attacks: `<script>malicious function</script>`
  * quotations delineate string literals
  * example vulnerability in webapp backend:
    ```java
    // response could contain JS code as part of a XSS attack
    const response = await fetch('some url', { method: 'GET' } );
    ...
    const patient = await response.json();
    // use the result of possibly malicious input to make an HTML element
    var script = document.createElement('script'); 
    script.textContent = patient.notes;
    document.body.appendChild(script);
    // browser executes JS code passed from user input to the database as notes
  
* HTML entity encoding neutralizes these threats
  * use HTML-sensitive input sanitization: see **healthcare-server - PatientResource - addNotes**
  * OWASP-defined dependency: `org.owasp.html.Sanitizers` and `org.owasp.html.PolicyFactory`
  * encodes something like `alert('XSS')` into `alert(&#39;XSS&#39;)` which cannot be executed by the browser

## SQL injection
* happens when user interactions are translated into SQL queries
* malicious inputs manipulate query execution, trick the DB into executing commands
 
* prevention involves sanitization and prepared statements
  * parameterized queries use placeholders for arguments in an SQL template
  * prevents input from terminating a SQL command
  * example: **healthcare-repository - PatientJdbcRepository**
  ```java
  private static final String ADD_NOTES = """
    UPDATE Patients SET NOTES = ?
    WHERE ID = ?
  """;
  ...
  @Override
  public void addNotes(String id, String notes) {
      try (Connection connection = dataSource.getConnection()) {
          PreparedStatement preparedStatement = connection.prepareStatement(ADD_NOTES);
          preparedStatement.setString(1, notes);
          preparedStatement.setString(2, id);
          preparedStatement.execute();
      } catch (SQLException e) {
          throw new RepositoryException("Failed to add notes to " + id, e);
      }
  }

