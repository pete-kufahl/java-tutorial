# ksqlDB demonstration

* `docker compose up -d`
  * adds containers `ksgldb-server` and `ksqldb-cli`
* `curl localhost:8082/brokers` to verify

### enter ksqlDB-cli and notify where the server is
* `docker exec -it ksqldb-cli ksql http://ksqldb-server:8088`
* in ksql, configure and create a kafka stream:
  * `show all topics;`
```commandline
Kafka Topic                            | Partitions | Partition Replicas
--------------------------------------------------------------------------
_confluent-ksql-default__command_topic | 1          | 1                  
_schemas                               | 1          | 3                  
default_ksql_processing_log            | 1          | 1
--------------------------------------------------------------------------
```
  * `SET 'auto.offset.reset'='earliest';`
  * `CREATE STREAM tempReadings (zipcode VARCHAR, sensortime BIGINT, temp DOUBLE)
       WITH (kafka_topic='readings', timestamp='sensortime', value_format='json', partitions=1);`
```commandline
 Message        
----------------
 Stream created 
----------------
```
  * `show topics extended;`
  * `show streams extended;`
    * the readout should contain our created stream, described with `TIMESTAMP='sensortime', VALUE_FORMAT='JSON'`
  * insert values into the stream
    * `INSERT INTO tempReadings (zipcode, sensortime, temp) VALUES ('1865', UNIX_TIMESTAMP(), 20);`
    * repeat, with changes values for a few lines
  
### (still in ksql) make a query
```commandline
SELECT zipcode, TIMESTAMPTOSTRING(WINDOWSTART, 'HH:mm:ss') as windowtime,
> COUNT(*) AS rowcount, AVG(temp) as temp
> FROM tempReadings
> WINDOW TUMBLING (SIZE 1 HOURS)
> GROUP BY zipcode EMIT CHANGES;
```
* response:
```commandline
+----------------------------------+----------------------------------+----------------------------------+----------------------------------+
|ZIPCODE                           |WINDOWTIME                        |ROWCOUNT                          |TEMP                              |
+----------------------------------+----------------------------------+----------------------------------+----------------------------------+
|1865                              |03:00:00                          |1                                 |20.0                              |
|1806                              |03:00:00                          |1                                 |25.0                              |
|1865                              |04:00:00                          |1                                 |32.0                              |
|1806                              |04:00:00                          |1                                 |27.0                              |
```
* note that as a streaming app, the query is not finished until terminated (^C)

### in another terminal, create a consumer of the filtered readings
* `kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic readings --from-beginning`
```commandline
{"ZIPCODE":"1865","SENSORTIME":1738209200125,"TEMP":20.0}
{"ZIPCODE":"1806","SENSORTIME":1738209250473,"TEMP":25.0}
{"ZIPCODE":"1865","SENSORTIME":1738209670222,"TEMP":32.0}
{"ZIPCODE":"1806","SENSORTIME":1738209715598,"TEMP":27.0}
```
* these are the four values emitted from ksqldb

### in ksqldb
* create another table
  ```
  CREATE TABLE highsandlows WITH (kafka_topic='readings') AS 
    SELECT MIN(temp) as min_temp, MAX(temp) as max_temp, zipcode
    FROM tempReadings GROUP BY zipcode;
  ```
* response:
```commandline
 Message                                   
-------------------------------------------
 Created query with ID CTAS_HIGHSANDLOWS_3 
-------------------------------------------
```
* `SELECT min_temp, max_temp, zipcode FROM highsandlows WHERE zipcode = '1865';`
```commandline
+----------------------------------------------+----------------------------------------------+----------------------------------------------+
|MIN_TEMP                                      |MAX_TEMP                                      |ZIPCODE                                       |
+----------------------------------------------+----------------------------------------------+----------------------------------------------+
|20.0                                          |32.0                                          |1865                                          |
Query terminated
```
    * this shows that queries into tables (as opposed to streams) are not "live"
* `INSERT INTO tempReadings (zipcode, sensortime, temp) VALUES ('1865', UNIX_TIMESTAMP() + 360000, 35);`
* rerun the query
* table is updated with a new `MAX_TEMP`
```commandline
+----------------------------------------------+----------------------------------------------+----------------------------------------------+
|MIN_TEMP                                      |MAX_TEMP                                      |ZIPCODE                                       |
+----------------------------------------------+----------------------------------------------+----------------------------------------------+
|20.0                                          |35.0                                          |1865                                          |
Query terminated
```