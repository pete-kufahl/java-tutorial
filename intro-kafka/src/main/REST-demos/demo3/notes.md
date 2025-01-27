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
