package nguye.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class Subscriber {

    private final static String EXCHANGE_NAME = "logs";

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
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        // Declare a temporary queue. Once all the consumers have been disconnected from the queue, it will be
        // automatically deleted
        String queueName = channel.queueDeclare().getQueue();
        // Create a binding between the fanout exchange and the temporary queue
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*]" + argv[0] + ": Waiting for messages...");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x]" + argv[0] + ": Received '" + message + "'");
        };
        // Tell the consumer to consume messages from the connected queue
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
