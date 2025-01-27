# producer/consumer demos with java

## producer demo
### setup kafka, consumer
* `docker compose up`
* `kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic myorders --from-beginning --key-deserializer org.apache.kafka.common.serialization.StringDeserializer --value-deserializer org.apache.kafka.common.serialization.DoubleDeserializer --property print.key=true --property key.separator=, --group 1`

### in java project
* mvn clean compile
* run Producer.main()

### check consumer
* should show randomly generated messages ....
```
AZ,1793.0
MS,246.0
OK,1718.0
OH,7406.0
OH,254.0
NM,4594.0
```