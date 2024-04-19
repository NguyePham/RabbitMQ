package nguye.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Publisher {

    private static final String EXCHANGE_NAME = "fanout_type";

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

            // Declare a fanout exchange to send log messages to
            // An exchange receives messages from producers and pushes them to the queues
            // A fanout exchange simply broadcasts all the messages it receives to all the queues it knows
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            // Allow arbitrary messages to be sent from the command line
            String message = argv.length < 1 ? "info: This is the default message" : String.join(" ", argv);

            channel.basicPublish(EXCHANGE_NAME, "",
                    null,
                    message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }

}
