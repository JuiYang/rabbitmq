package com.tcl.ps;

import com.rabbitmq.client.*;
import com.tcl.utils.MQConnection;

import java.io.IOException;
import java.util.Queue;

public class Recv2 {

    private  final  static String QUEUE_NAME = "hello" ;
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) {

        try {
            Connection connection = MQConnection.getConnection("localhost");

            final Channel channel = connection.createChannel();

            System.out.println(" [*] Waiting for messages. To exit press  CTRL+C");

//            channel.queueDeclare(QUEUE_NAME, false,false,false,null);
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName,EXCHANGE_NAME, "");


            channel.basicQos(1);

            DefaultConsumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    String message = new String(body,"utf-8");
                    System.out.println(" [2] Received '" + message + "'");
                    // 手动回执信息
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            };
            // 监听通道
            channel.basicConsume(queueName, true, consumer);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
