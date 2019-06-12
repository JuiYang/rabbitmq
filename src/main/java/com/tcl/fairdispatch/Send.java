package com.tcl.fairdispatch;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tcl.utils.MQConnection;

public class Send {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) {
        try {
            Connection connection = MQConnection.getConnection("localhost");

            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // 每个消息在确认消息之前，队列不发送消息到消费者，消费者一次只能处理一个消息
            int prefetchCount = 1;
            channel.basicQos(prefetchCount);

            for (int i = 0; i < 50; i++) {
                String message = "Hello World, tom! fair id: " + i;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                Thread.sleep(10);
            }


            System.out.println("发送完毕");
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
