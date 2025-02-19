# Producer and Consumer in java
consume messages with java processes

### setup kafka, add topic and 4 partitions
* `docker compose up`
* `kafka-topics.sh --create --bootstrap-server localhost:9092 --partitions 4 --topic myorders1`

### in 2 java terminals 
* `mvn clean install exec:java -Dexec.mainClass="com.ps.demo7.Consumer" -Dexec.args="1"`
    * creates two consumers in the same group
    * can check in docker logs: `docker logs broker-1`, etc.

### send messages
* run Producer
* check Consumer terminals and see that both are receiving messages
* in another terminal:
  * `kafka-consumer-groups.sh --all-groups --all-topics --bootstrap-server localhost:9092 --describe`
  * outputs table
    ```
    GROUP           TOPIC           PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID                                               HOST            CLIENT-ID
    1consumer       myorders1       3          16              16              0               consumer-1consumer-1-b7b998eb-3141-4d3d-8308-9a0a1841b7d8 /172.19.0.1     consumer-1consumer-1
    1consumer       myorders1       2          7               7               0               consumer-1consumer-1-b7b998eb-3141-4d3d-8308-9a0a1841b7d8 /172.19.0.1     consumer-1consumer-1
    1consumer       myorders1       1          4               4               0               consumer-1consumer-1-2469723e-6c7d-45e0-bcbb-e6a5f72cbe79 /172.19.0.1     consumer-1consumer-1
    1consumer       myorders1       0          8               8               0               consumer-1consumer-1-2469723e-6c7d-45e0-bcbb-e6a5f72cbe79 /172.19.0.1     consumer-1consumer-1
    ```

### cause re-balance event
* in another terminal, create another Consumer:
  * `mvn clean install exec:java -Dexec.mainClass="com.ps.demo7.Consumer" -Dexec.args="1"`
* observe re-balancing with the above monitoring commands
  * for example, as message containing `...Preparing to rebalance group 1consumer in state PreparingRebalance with old generation 2` appears in one of the docker logs
