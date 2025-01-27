# producer demo with java

### setup kafka, consumer
* `docker compose up`
* `kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic myorders --from-beginning --key-deserializer org.apache.kafka.common.serialization.StringDeserializer --value-deserializer org.apache.kafka.common.serialization.DoubleDeserializer --property print.key=true --property key.separator=, --group 1`

## in java project
* mvn clean compile