# interaction with kafka REST api

* run kafka: `docker compose up`
* open Postman
  * import `rest_proxy.yaml`
  * create environment and set `BaseUrl` variable to http://localhost:8080
  * GET /brokers -> `[1, 2, 3]`
  * GET /topics -> `[]`
* CLI: create a topic
  * `kafka-topics.sh --create --bootstrap-server 127.0.0.1:9092 --replication-factor 1 --partitions 3 --topic myorders`
    * `Created topic myorders.`
* in Postman, confirm presence of topic
  * GET /topics -> `[ myorders ]`
  * GET /topics/:topicName with path variable `topicName` = `myorders`
    * returns JSON with config and partition data
* CLI: get kafka to reassign partitions (to adjust for runtime conditions)
  * reassignment in a .json file
  * `kafka-reassign-partitions.sh --bootstrap-server 127.0.0.1:9092 --reassignment-json-file increase_replication.json --execute`
    * success message (background task) with reassignment data
* Postman: confirm
  * GET /topics/:topicName
    * shows change partition
* terminal: `docker stop broker-2`
* CLI: send a message
  * `kafka-console-producer.sh --bootstrap-server 127.0.0.1:9092 --topic myorders`
  * enter text (messages)
* Postman: GET /topics/:topicName
  * shows broker 2 as `in_sync: false`
    * vague messaging
* CLI: create a consumer to show breakage
  * `kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic myorders --from-beginning`
    * no messages received
* bring back `broker-2` to show recovery
  * terminal: `docker compose up -d`
  * Postman: GET /topics/:topicName
    * shows brokers in sync across replicas
  * consumer shows messages

