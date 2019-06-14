package com.tcl.topic;

import com.rabbitmq.client.*;
import com.tcl.utils.MQConnection;

import java.io.IOException;

public class Recv2 {

    private static final String EXCHANGE_NAME = "topic-logs";
    private static final String QUEUE_NAME = "logs";

    public static void main(String[] args) throws Exception {

        Connection connection = MQConnection.getConnection("localhost");
        final Channel channel = connection.createChannel();



        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueName = channel.queueDeclare().getQueue();

//        channel.queueDeclare(queueName, false, false, false, null);

        // 路由表: #匹配一个或多个， .匹配一个
        channel.queueBind(queueName, EXCHANGE_NAME, "code.*");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "utf-8");
                System.out.println(" [2] topic Received '" + message + "'");
                // 手动回执信息
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // 监听通道: autoAck: true为自动确认消息，false为手动确认消息
        channel.basicConsume(queueName, false, consumer);
    }
}
