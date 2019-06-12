package com.tcl.ps;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tcl.utils.MQConnection;

public class Send {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) {
        try{
            Connection connection = MQConnection.getConnection("localhost");

            Channel channel = connection.createChannel();



//            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");


            String message = "Hello World, tom!";
            channel.basicPublish(EXCHANGE_NAME, "",null, message.getBytes());


            System.out.println("发送完毕");
            channel.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
