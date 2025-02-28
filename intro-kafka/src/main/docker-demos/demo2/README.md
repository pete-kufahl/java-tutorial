# interaction with kafka via CLI
* `docker compose up -d`

* run kafka CLI (have the bin/ in $PATH)
  * `kafka-console-producer.sh --bootstrap-server 127.0.0.1:9092 --topic first_topic`
    * type in a few messages (1st one has a warning)
  * `kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic first_topic --from-beginning`
    * after a few seconds, messages will appear in order
* check on topics
  * `curl localhost:8082/topics`
    * should see `[first_topic]`
