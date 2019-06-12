package com.tcl.fairdispatch;

import com.rabbitmq.client.*;
import com.tcl.utils.MQConnection;

import java.io.IOException;

public class Recv2 {

    private  final  static String QUEUE_NAME = "hello" ;

    public static void main(String[] args) {

        try {
            Connection connection = MQConnection.getConnection("localhost");
            final Channel channel = connection.createChannel();

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            // 保证一次只分发一个消息
            channel.basicQos(1);

            DefaultConsumer consumer = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body) throws IOException {
                    String message = new String(body,"utf-8");
                    System.out.println(" [2] fair Received '" + message + "'");
                    try{
                        Thread.sleep(10);
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        // 手动回执信息
                        channel.basicAck(envelope.getDeliveryTag(),false);
                    }
                }
            };
            // 监听通道
            boolean autoAck = false;
            channel.basicConsume(QUEUE_NAME, autoAck, consumer);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
