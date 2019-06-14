package com.tcl.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.tcl.utils.MQConnection;

import java.util.ArrayList;
import java.util.List;

public class Send {


    private static final String EXCHANGE_NAME = "topic-logs";

    public static void main(String[] args) throws Exception {

        Connection connection = MQConnection.getConnection("localhost");

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String message = "topic message";
        List<String> messages = new ArrayList<>();
        messages.add("topic error message");
        messages.add("topic info message");
        messages.add("topic warning message");
        List<String> keys = new ArrayList<>();
        keys.add("code.error");
        keys.add("code.info");
        keys.add("code.warning");
        for (int i = 0; i < 10; i++){
            channel.basicPublish(EXCHANGE_NAME, keys.get(i%3), null, messages.get(i%3).getBytes());
            Thread.sleep(1000);
        }

        System.out.println("发送完毕");
        channel.close();
        connection.close();

    }
}
