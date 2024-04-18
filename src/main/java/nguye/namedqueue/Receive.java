package nguye.namedqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.nio.charset.StandardCharsets;

public class Receive {

    private final static String QUEUE_NAME = "hello";

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

        // Connect the consumer to the same queue as the publisher. The queue is identified by its name
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages...");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        };
        // Tell the consumer to consume messages from the connected queue
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}