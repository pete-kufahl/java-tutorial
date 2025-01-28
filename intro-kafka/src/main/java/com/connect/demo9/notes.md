# Connect using a schema registry and Avro precompiler, write to a mongoDB instance

## avro setup
* .avsc definition file in main/avro
* in `pom.xml`:
  ```
  <groupId>org.apache.avro</groupId>
    <artifactId>avro-maven-plugin</artifactId>
      <version>1.12.0</version>
        <executions>
          <execution>
            <phase>generate-sources</phase>
            <goals>
              <goal>schema</goal>
            </goals>
            <configuration>
              <sourceDirectory>${project.basedir}/src/main/avro/</sourceDirectory>
              `<outputDirectory>${project.basedir}/src/main/java/</outputDirectory>
  ...
  ```
    
  * 