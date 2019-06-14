package com.tcl.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tcl.utils.MQConnection;

import java.util.ArrayList;
import java.util.List;

public class Send {

    private static final String EXCHANGE_NAME = "routing-direct-logs";

    public static void main(String[] args) throws Exception {

        Connection connection = MQConnection.getConnection("localhost");

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        String message = "routing direct message";
        List<String> messages = new ArrayList<>();
        messages.add("routing direct error message");
        messages.add("routing direct info message");
        messages.add("routing direct warning message");
        List<String> keys = new ArrayList<>();
        keys.add("error");
        keys.add("info");
        keys.add("warning");
        for (int i = 0; i < 10; i++){
            channel.basicPublish(EXCHANGE_NAME, keys.get(i%3), null, messages.get(i%3).getBytes());
            Thread.sleep(1000);
        }

        System.out.println("发送完毕");
        channel.close();
        connection.close();

    }
}
