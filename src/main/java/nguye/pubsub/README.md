## Using publish/subscribe
#### Use a fanout exchange to dispatch messages from one publisher to all subscribers.
<br>

#### Compile the source files:
```
javac -d target/classes -cp lib/amqp-client-5.21.0.jar src/main/java/nguye/pubsub/*.java
```
#### Run the publisher to dispatch messages:
```
java -cp "target/classes;lib/amqp-client-5.21.0.jar;lib/slf4j-api-2.0.12.jar;lib/slf4j-simple-2.0.12.jar" nguye.pubsub.Publisher
```
![](images/dispatch.png)
#### Run the subscribers to receive messages:
```
java -cp "target/classes;lib/amqp-client-5.21.0.jar;lib/slf4j-api-2.0.12.jar;lib/slf4j-simple-2.0.12.jar" nguye.pubsub.Subscriber
```
#### Subscriber 1:
![](images/sub1.png)
#### Subscriber 2:
![](images/sub2.png)