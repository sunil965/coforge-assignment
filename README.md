# Coforge-Assignment
POC with Api Gateway, Service Registry, Kafka (Multiple consumers &amp; producers), Redis cache.

Sequence to run services-
1) Service Registry
2) API Gateway
3) User-app

* ### Redis
- First install Redis in local and make it run then start the User application.

* ###  Kafka
Start Kafka in Local:

* ##### `Terminal` 1

D:\Kafka\kafka_2.13-3.5.0\bin\windows>zookeeper-server-start.bat ..\..\config\zookeeper.properties

* ##### `Terminal` 2

D:\Kafka\kafka_2.13-3.5.0\\bin\windows>kafka-server-start.bat ..\..\config\server.properties

* ##### `Terminal` 3

D:\Kafka\kafka_2.13-3.5.0\bin\windows>kafka-topics.bat --create --topic test-topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3
Created topic test-topic.

##### => To check list of topic created.

D:\Kafka\kafka_2.13-3.5.0\bin\windows>kafka-topics.bat --list --bootstrap-server localhost:9092
test-topic

Here "_**D:\Kafka\kafka_2.13-3.5.0\bin\windows**_" is the directory where Kafka is downloaded.


##### => Swagger URL.
http://localhost:8082/swagger-ui/index.html#/user-controller

##### => H2 Console.
http://localhost:8082/h2-console/l

##### NOTE : To make any service call from API GATEWAY just replace the port of any Service with port of Gateway in URL from postman for testing.
