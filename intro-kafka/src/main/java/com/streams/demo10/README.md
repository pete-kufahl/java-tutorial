## kafka streaming operations demo
setting up applications to use serialization and deserialization simultaneously (*serde*)

* `docker compose up -d`
* `curl localhost:8082/brokers` to verify

### create topics : raw and filtered number readings
* `kafka-topics.sh --create --bootstrap-server localhost:9092 --partitions 4 --topic RawTempReadings`
* `kafka-topics.sh --create --bootstrap-server localhost:9092 --partitions 4 --topic ValidatedTempReadings`

### create consumer
  ```
    kafka-console-consumer.sh --bootstrap-server localhost:9092 \
    --topic ValidatedTempReadings --from-beginning \
    --key-deserializer org.apache.kafka.common.serialization.StringDeserializer\
     --value-deserializer org.apache.kafka.common.serialization.IntegerDeserializer\
     --property print.key=true\
     --property key.separator=,\
     --group 1
  ```

### in java, create producer
* send string->integer pairs: key = randomly picked sensor ids (1, 2, 3), value = random integers

### create kafka streams "ETL" application
* key serde type: String
* value serde type: integer
* set up a StreamsBuild object to describe streaming operations (filtering)
  * create a topology with the builder
  * send it to KafkaStreams class

### run and test
* start ETL Streams app
* start Producer app
* watch terminal: readout of filtered temperature messages