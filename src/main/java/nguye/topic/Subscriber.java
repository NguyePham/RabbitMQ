package nguye.topic;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

public class Subscriber {

    private final static String EXCHANGE_NAME = "topic_type";
    private static final String[] TOPIC = {"", "one.#", "*.two.*", "*.two.three"};

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
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        // Declare a temporary queue
        String queueName = channel.queueDeclare().getQueue();

        // Each subscriber is interested in a specific topic, so we create one binding for each subscriber
        // Convert the subscriber ID to an integer
        int subscriberId = Integer.parseInt(argv[0].substring(3));
        channel.queueBind(queueName, EXCHANGE_NAME, TOPIC[subscriberId]);

        System.out.println(" [*] Subscriber " + subscriberId + ": Waiting for messages...");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x]" + argv[0] + ": Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        // Tell the consumer to consume messages from the connected queue
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
