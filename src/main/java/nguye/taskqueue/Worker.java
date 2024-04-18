package nguye.taskqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class Worker {

    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        // Setting up is the same as the publisher
        factory.setHost("localhost");
        factory.setUsername("cc-admin");
        factory.setPassword("taxi123");
        factory.setVirtualHost("cc-dev-vhost");
        // Use IntelliJ to terminate the process of Receive.java
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Connect the worker to the same queue as the publisher. The queue is identified by its name
        channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages...");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {

            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");

            try {
                doWork(message);
            } finally {
                System.out.println(" [x] Done");
            }
        };
        // Tell the worker to do the task from the connected queue
        // If `autoAck` is set to true, RabbitMQ immediately marks a message as handled and can be discarded once it
        // has delivered that message to a worker. But if a worker dies while handling a time-consuming task, the
        // message which has been dispatched to it (the one it is handling) it lost.
        // If `autoAck` is set to false, RabbitMQ will wait for an acknowledgement from the worker to decide to
        // discard the message. Here we do not sent an acknowledgement, so messages remain in the queue even after
        // they have been delivered and handled successfully.
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, consumerTag -> { });
    }

    // The fake task to simulate execution time
    // Each dot character in the fake task message takes a worker 1 second to handle
    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}