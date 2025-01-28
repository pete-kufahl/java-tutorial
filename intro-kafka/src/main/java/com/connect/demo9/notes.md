# Connect using a schema registry and Avro precompiler, write to a mongoDB instance

## downloads
### get hub client for Kafka
* `brew tap confluentinc/homebrew-confluent-hub-client`
* `brew install --cask confluent-hub-client`
  * check version number (7.3.4), it may be needed
### avro converter
* fix MacOS problem, if required...
  * `sudo ln -s /opt/homebrew/Caskroom/confluent-hub-client/7.3.4/bin/confluent-hub /usr/local/bin/confluent-hub`
* `confluent-hub install confluentinc/kafka-connect-avro-converter:7.5.0 --component-dir ~/Applications/kafka_2.13-3.9.0/libs --worker-configs worker.properties`
### mongoDB sync connector
* `confluent-hub install mongodb/kafka-connect-mongodb:1.11.0 --component-dir ~/Applications/kafka_2.13-3.9.0/libs --worker-configs worker.properties`
### missing .jar files (for these versions)
* `cp guava-32.1.2-jre.jar ~/Applications/kafka_2.13-3.9.0/libs`
* `cp failureaccess-1.0.1.jar ~/Applications/kafka_2.13-3.9.0/libs`

## set up kafka

### docker-compose includes mongo dependency
* `docker compose up -d`

### kafka setup topic
* `curl localhost:8082/brokers` to verify kafka server (3 brokers)
* `kafka-topics.sh --create --bootstrap-server localhost:9092 --partitions 4 --topic connect-distributed`

### avro setup
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
              <outputDirectory>${project.basedir}/src/main/java/</outputDirectory>
  ...
  ```
* there may be compilation errors with these avro and plugin versions.
  * may need to replace the two superclass constructor calls in Album.java:
    * replace `super(SCHEMA$, MODEL$);` with `super(SCHEMA$);`
    * `mvn clean verify`
    
* `mvn generate-sources`
  * creates model java class in destination folder (according to the `namespace` item in the .avsc file)

### start additional kafka topics to handle distributed connection
* note that these topics are described at the bottom of `worker.properties`
* `kafka-topics.sh --create --bootstrap-server localhost:9092 --topic kafka_connect_statuses --config cleanup.policy=compact`
  * specifies deletion of old messages with the same key
* `kafka-topics.sh --create --bootstrap-server localhost:9092 --topic kafka_connect_offsets --config cleanup.policy=compact`
* `kafka-topics.sh --create --bootstrap-server localhost:9092 --topic kafka_connect_configs --config cleanup.policy=compact`

### run kafka connect in distributed mode
* `connect-distributed.sh worker.properties`
  * creates the consumers

### deploy connectors via connect's REST api
* in Postman:
  * POST to http://localhost:8083/connectors
  * body:
  ```
  { "name": "mongo-sink",
    "config": {
        "connector.class":"com.mongodb.kafka.connect.MongoSinkConnector",
        "connection.uri": "mongodb://localhost:27017",
        "database": "quickstart",
        "collection": "topicData",
        "topics": "connect-distributed"
    }
  }
  ```
  * response should be `201 Created`
  * verify with a GET to http://localhost:8083/connectors
  * response:
  ```
  [
    "mongo-sink"
  ]
  ```
  * also GET  http://localhost:8083/connectors/mongo-sink to see more details, including tasks


* run Sender 
* with Compass, connect to `http://localhost:27017` and find message contents in `quickstart.topicData`


  