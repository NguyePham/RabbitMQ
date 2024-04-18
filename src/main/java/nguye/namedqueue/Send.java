package nguye.namedqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Send {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        // Configure the connection
        factory.setHost("localhost");
        factory.setUsername("cc-admin");
        factory.setPassword("taxi123");
        factory.setVirtualHost("cc-dev-vhost");
            // Create a connection to the RabbitMQ node
        try (Connection connection = factory.newConnection();
             // Create a channel within that connection
             Channel channel = connection.createChannel()) {

            // Declare a named queue to send messages to
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            // Publish three messages to the queue
            String message = "This is the first message";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
            message = "This is the second message";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
            message = "This is the third message";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}