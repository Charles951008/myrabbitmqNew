package com.wsl.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils {

    /**
     * 获取MQ的链接
     * @return
     * @throws java.util.concurrent.TimeoutException
     * @throws java.io.IOException
     */

    public static Connection getConnection() throws IOException, TimeoutException {
        //定义一个链接工厂
        ConnectionFactory factory=new ConnectionFactory();

        //设置服务地址
        factory.setHost("127.0.0.1");

        //AMQP 5672
        factory.setPort(5672);

        //vhost_mmr RabbitMQ中间件的库名
        factory.setVirtualHost("/vhost_mmr");

        //设置用户名
        factory.setUsername("forwsl");

        //设置密码
        factory.setPassword("forwsl");

        return factory.newConnection();
    }
}
