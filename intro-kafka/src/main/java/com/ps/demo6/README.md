# producer demo with java + consumer behavior (CLI)

## producer demo
### setup kafka, add topics
* `docker compose up`
* `kafka-topics.sh --create --bootstrap-server localhost:9092 --partitions 2 --topic myorders`

### in IDE, open 4 terminals in demo6/
* in three terminals, start a consumer in `group 1`
  ```
    kafka-console-consumer.sh --bootstrap-server localhost:9092 \
    --topic myorders --from-beginning \
    --key-deserializer org.apache.kafka.common.serialization.StringDeserializer\
     --value-deserializer org.apache.kafka.common.serialization.DoubleDeserializer\
     --property print.key=true\
     --property key.separator=,\
     --group 1
  ```
* in the fourth terminal, start a consumer in `group 2`
  ```
    kafka-console-consumer.sh --bootstrap-server localhost:9092 \
    --topic myorders --from-beginning \
    --key-deserializer org.apache.kafka.common.serialization.StringDeserializer\
     --value-deserializer org.apache.kafka.common.serialization.DoubleDeserializer\
     --property print.key=true\
     --property key.separator=,\
     --group 2
  ```

* in another terminal, check consumer groups (example result)
  * `docker logs broker-1` --> messages for group 1
  * `docker logs broker-2`
  * `docker logs broker-3` --> messages for group 3

### in java project
* mvn clean compile
* run Producer.main()

### watch terminals
* in the group 1 terminals, two consumers will have messages but one remains idle
  * this is because we set up 2 partitions but had a group of 3 consumers

* in the group 2 terminal, all messages appear
  * this is because there is only one consumer in the group

