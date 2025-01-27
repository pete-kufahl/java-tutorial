# CLI producer demo

* bring up kafka with a `myorders` topic
  * `docker compose up -d`
  * `kafka-topics.sh --create --bootstrap-server 127.0.0.1:9092 --replication-factor 3 --partitions 3 --topic myorders`
* in 2 terminals:
  * `kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic myorders --group 1`
* confirm presence of both consumers in docker logs
  * `docker logs broker-1 | grep "group 1"`
* in another terminal, set up producer with `key:value` messaging
  * `kafka-console-producer.sh --bootstrap-server 127.0.0.1:9092 --topic myorders --property "parse.key=true" --property "key.separator=:"`
  * enter some key:value messages with one key
    * messages appear in one of the consumer terminals
    * this is because only one key was used
  * enter more messages with different keys
    * messages will be distributed between the two consumer terminals
* display distribution of messages to consumers
  * `kafka-consumer-groups.sh --all-groups --bootstrap-server 127.0.0.1:9092 --describe`
    * returns a table of messages and consumers