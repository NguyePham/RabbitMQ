package nguye.routing;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

public class Subscriber {

    private final static String EXCHANGE_NAME = "direct_type";
    private static final String BINDING_KEY_ONE = "binding1";
    private static final String BINDING_KEY_TWO = "binding2";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        // Setting up is the same as the publisher
        factory.setHost("localhost");
        factory.setUsername("cc-admin");
        factory.setPassword("taxi123");
        factory.setVirtualHost("cc-dev-vhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // Connect the consumer to the same exchange as the publisher
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        // Declare a temporary queue
        String queueName = channel.queueDeclare().getQueue();

        // Create bindings
        // We have two types of message
        // Subscriber 1 can receive only the first type of message and subscriber 2 can receive both
        channel.queueBind(queueName, EXCHANGE_NAME, BINDING_KEY_ONE);
        if (argv[0].equals("sub2")) {
            channel.queueBind(queueName, EXCHANGE_NAME, BINDING_KEY_TWO);
        }

        System.out.println(" [*]" + argv[0] + ": Waiting for messages...");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x]" + argv[0] + ": Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        // Tell the consumer to consume messages from the connected queue
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
