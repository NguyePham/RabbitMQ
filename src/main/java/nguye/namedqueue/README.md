## This is an example demonstrating the use of a named queue

#### First, check how many queue are created within the `cc-dev-vhost` vhost, and how many messages are in them:
```
rabbitmqctl list_queues --vhost cc-dev-vhost
```
---

#### Then, run the publisher `Send.java` to send three messages to the _hello_ queue, then check again.
#### Then, run the consumer `Receive.java` to consume those three messages, then check again.
#### Or, you can run the consumer before sending any messages to the queue. The consumer will stay alive and listen for messages to arrive