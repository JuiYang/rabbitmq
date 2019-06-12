package com.tcl.work;

import com.rabbitmq.client.*;
import com.tcl.utils.MQConnection;

import java.io.IOException;

public class Recv2 {

    private  final  static String QUEUE_NAME = "hello" ;

    public static void main(String[] args) {

        try {
            Connection connection = MQConnection.getConnection("localhost");

            Channel channel = connection.createChannel();

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DefaultConsumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    String message = new String(body,"utf-8");
                    System.out.println(" [2] Received '" + message + "'");
                }
            };
            // 监听通道
            channel.basicConsume(QUEUE_NAME, true, consumer);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
