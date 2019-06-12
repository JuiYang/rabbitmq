package com.tcl.simple;

import com.rabbitmq.client.*;
import com.tcl.utils.MQConnection;

import java.io.IOException;

public class RabbitMqClient {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        try {
            // 创建连接
            Connection connection = MQConnection.getConnection("localhost");
            // 获取通道
            Channel channel = connection.createChannel();
            // 新API
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DefaultConsumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    String message = new String(body,"utf-8");
                    System.out.println(" [x] Received '" + message + "'");
                }
            };
            // 监听通道
            channel.basicConsume(QUEUE_NAME, true, consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
/**

    / 创建连接
            Connection connection = MQConnection.getConnection("localhost");
            // 获取通道
            Channel channel = connection.createChannel();



            // 创建队列消费者
            QueueingConsumer consumer = new QueueingConsumer(channel);
            // 监听队列
            channel.basicConsume(QUEUE_NAME, true, consumer);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            while (true) {
                // 获取消息
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                String body = new String(delivery.getBody());
                System.out.println(" [x] Recv '" + body + "'");
            }
 */
