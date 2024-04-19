## Using task queue

#### Publish and consume tasks via the `task_queue` queue inside the `cc-dev-vhost` vhost.
#### Messages are sent directly to the queue without an explicitly declared exchange in the middle.
<br>

#### List the queues lie inside the `cc-dev-vhost` vhost
```
rabbitmqctl list_queues --vhost cc-dev-vhost
```

#### Compile the source files:
```
javac -d target/classes -cp lib/amqp-client-5.21.0.jar src/main/java/nguye/taskqueue/*.java
```

#### Run two workers (in separated terminal instances):
```
java -cp "target/classes;lib/amqp-client-5.21.0.jar;lib/slf4j-api-2.0.12.jar;lib/slf4j-simple-2.0.12.jar" nguye.taksqueue.Worker
```

#### Create several tasks which take less than 1s to be done (immediately), and publish them to the queue:
```
java -cp "target/classes;lib/amqp-client-5.21.0.jar;lib/slf4j-api-2.0.12.jar;lib/slf4j-simple-2.0.12.jar" nguye.taskqueue.NewTask <task_name>
```
![normal tasks](images/normal-tasks.png)

#### The two workers handle the tasks in parallel:
![normal tasks worker1](images/normal-tasks-worker1.png)
![normal tasks worker 2](images/normal-tasks-worker2.png)

#### Now there are no messages in the queue
![](images/before-sending-time-consuming-tasks.png)

#### Create two time-consuming tasks, and publish them to the queue:
```
java -cp "target/classes;lib/amqp-client-5.21.0.jar;lib/slf4j-api-2.0.12.jar;lib/slf4j-simple-2.0.12.jar" nguye.taskqueue.NewTask ........15s task....... 
```
```
java -cp "target/classes;lib/amqp-client-5.21.0.jar;lib/slf4j-api-2.0.12.jar;lib/slf4j-simple-2.0.12.jar" nguye.taskqueue.NewTask ..........20s task.......... 
```
![](images/long-tasks.png)

#### Each worker receive a task, and one them dies while handling the task:
![](images/long-tasks-worker1.png)
![](images/long-tasks-worker2.png)

#### With `autoAck` set to *true*, all messages discarded from the queue:
![](images/after-sending-time-consuming-tasks.png)

#### With `autoAck` set to *false*, if a worker dies while handling a task, that task will be re-delivered to the other worker:
![](images/auto-ack-false-worker1.png)
![](images/auto-ack-false-worker2.png)

#### All messages remain in the queue:
![](images/auto-ack-false-queue.png)