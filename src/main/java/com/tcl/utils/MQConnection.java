package com.tcl.utils;

import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MQConnection {

    public static Connection getConnection(String host) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(5672);
        factory.setVirtualHost("/host_lee");
        factory.setUsername("lee");
        factory.setPassword("lee");
        Connection connection = factory.newConnection();
        return connection;
    }
}
