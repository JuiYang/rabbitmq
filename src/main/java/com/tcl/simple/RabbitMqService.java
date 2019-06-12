package com.tcl.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tcl.utils.MQConnection;


public class RabbitMqService {

    private  final  static String QUEUE_NAME = "hello" ;

    public static void main(String[] args) {
        try {
            Connection connection = MQConnection.getConnection("localhost");
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

            System.out.println("发送完毕");
            channel.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
