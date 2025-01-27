# Kafka Connect using a standalone instance and file sink

* `docker compose up`
  * includes the schema registry, due to new section in `docker-compose.yml`
* `curl localhost:8082/brokers` to verify the 3 brokers
* `kafka-topics.sh --create --bootstrap-server localhost:9092\
 --partitions 4 --topic connect-log`
* run the Producer
  * LogProducer publishes to connect-log, even though its static constant `TOPIC="connectlog"`

### create the standalone filesink instance (in demo8/)
* connect-standalone.sh worker.properties filesink.properties
  * requires files: worker.properties and filesink.properties
    * in worker.properties, set plugin path
  * requires maven plugins (?)
* check that the file is getting the logs
  * `tail -f file-log.txt`
  