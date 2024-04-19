## Using named queue

#### Publish and consume three messages via the `hello` queue inside the `cc-dev-vhost` vhost. 
#### Messages are sent directly to the queue without an explicitly declared exchange in the middle.
<br>

#### Compile the source files:
```
javac -d target/classes -cp lib/amqp-client-5.21.0.jar src/main/java/nguye/namedqueue/*.java
```
#### Run the consumer:
```
java -cp "target/classes;lib/amqp-client-5.21.0.jar;lib/slf4j-api-2.0.12.jar;lib/slf4j-simple-2.0.12.jar" nguye.namedqueue.Receive
```
#### Run the publisher (in another terminal instance):
```
java -cp "target/classes;lib/amqp-client-5.21.0.jar;lib/slf4j-api-2.0.12.jar;lib/slf4j-simple-2.0.12.jar" nguye.namedqueue.Send
```