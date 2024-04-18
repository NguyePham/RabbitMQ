package nguye.taskqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class NewTask {

    private static final String TASK_QUEUE_NAME = "task_queue";

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

            // Declare a task queue to send tasks to
            channel.queueDeclare(TASK_QUEUE_NAME, false, false, false, null);

            // Allow arbitrary messages to be sent from the command line
            String message = String.join(" ", argv);

            channel.basicPublish("", TASK_QUEUE_NAME,
                    null,
                    message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }

}