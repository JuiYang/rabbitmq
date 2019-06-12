package com.tcl.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tcl.utils.MQConnection;

public class Send {

    private  final  static String QUEUE_NAME = "hello" ;

    public static void main(String[] args) {
        try{
            Connection connection = MQConnection.getConnection("localhost");

            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);


            for (int i = 0; i < 50; i++){
                String message = "Hello World, tom! id: " + i;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                Thread.sleep(200);
            }


            System.out.println("发送完毕");
            channel.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
