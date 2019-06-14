package com.tcl.routing;


import com.rabbitmq.client.*;
import com.tcl.utils.MQConnection;

import java.io.IOException;

public class Recv1 {

    private static final String EXCHANGE_NAME = "routing-direct-logs";
    private static final String QUEUE_NAME = "logs";

    public static void main(String[] args) throws Exception {

        Connection connection = MQConnection.getConnection("localhost");
        final Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String queueName = channel.queueDeclare().getQueue();

//        channel.queueDeclare(queueName, false, false, false, null);

        channel.queueBind(queueName, EXCHANGE_NAME, "error");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "utf-8");
                System.out.println(" [1] routing Received '" + message + "'");
                // 手动回执信息
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // 监听通道: autoAck: true为自动确认消息，false为手动确认消息
        channel.basicConsume(queueName, false, consumer);

    }
}
