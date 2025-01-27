# running kafka from docker container

`docker compose up`

### to check on processes:

`docker compose ps`

expected output:

```
NAME          IMAGE                              COMMAND                  SERVICE       CREATED          STATUS          PORTS
broker-1      confluentinc/cp-kafka:7.4.1        "/etc/confluent/dock…"   broker-1      15 minutes ago   Up 15 minutes   0.0.0.0:9092->9092/tcp, 0.0.0.0:29092->29092/tcp
broker-2      confluentinc/cp-kafka:7.4.1        "/etc/confluent/dock…"   broker-2      15 minutes ago   Up 15 minutes   0.0.0.0:9093->9093/tcp, 9092/tcp, 0.0.0.0:29093->29093/tcp
broker-3      confluentinc/cp-kafka:7.4.1        "/etc/confluent/dock…"   broker-3      15 minutes ago   Up 15 minutes   0.0.0.0:9094->9094/tcp, 9092/tcp, 0.0.0.0:29094->29094/tcp
rest-proxy    confluentinc/cp-kafka-rest:7.4.1   "/etc/confluent/dock…"   rest-proxy    15 minutes ago   Up 15 minutes   0.0.0.0:8082->8082/tcp
zookeeper-1   confluentinc/cp-zookeeper:7.4.1    "/etc/confluent/dock…"   zookeeper-1   15 minutes ago   Up 15 minutes   2181/tcp, 2888/tcp, 3888/tcp
zookeeper-2   confluentinc/cp-zookeeper:7.4.1    "/etc/confluent/dock…"   zookeeper-2   15 minutes ago   Up 15 minutes   2181/tcp, 2888/tcp, 3888/tcp
zookeeper-3   confluentinc/cp-zookeeper:7.4.1    "/etc/confluent/dock…"   zookeeper-3   15 minutes ago   Up 15 minutes   2181/tcp, 2888/tcp, 3888/tcp
```
### to check on broker count (should be 3):

`curl localhost:8082/brokers`

expected output: `{"brokers":[1,2,3]}`
