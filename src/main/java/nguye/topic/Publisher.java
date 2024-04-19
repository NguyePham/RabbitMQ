package nguye.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class Publisher {

    private static final String EXCHANGE_NAME = "topic_type";

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

            // Declare a direct exchange
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

            // Allow arbitrary messages to be sent from the command line
            String routingKey = getRoutingKey(argv);
            String message = getMessage(argv);

            // Send a message with a routing key to the exchange
            // Here we have two types of message, identified by their routing key
            channel.basicPublish(EXCHANGE_NAME, routingKey,
                    null,
                    message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + "Topic " + routingKey + ": " + message + "'");
        }
    }

    private static String getRoutingKey(String[] argv) {
        if (argv.length < 1) {
            return "info";
        }
        return argv[0];
    }

    private static String getMessage(String[] argv) {
        if (argv.length < 2) {
            return "This is the default message";
        }
        return joinArgv(argv, " ", 1);
    }

    private static String joinArgv(String[] argv, String delimiter, int fromIndex) {
        int length = argv.length;
        if (length <= fromIndex) return "";

        StringBuilder words = new StringBuilder(argv[fromIndex]);
        for (int i = fromIndex + 1; i < length; i++) {
            words.append(delimiter).append(argv[i]);
        }
        return words.toString();
    }
}
