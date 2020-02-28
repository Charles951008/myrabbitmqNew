package com.wsl.rabbitmq.simple;

import com.rabbitmq.client.*;
import com.wsl.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者获取消息
 * @author 10211
 */
public class Receive {
    private static final String QUEUE_NAME="test_simple_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取链接
        Connection connection = ConnectionUtils.getConnection();
        //创建频道
        Channel channel = connection.createChannel();
        
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg= new String(body,"utf-8");
                System.out.println("new api receive:"+msg);
            }
        };
        //监听队列
        channel.basicConsume(QUEUE_NAME,true,defaultConsumer);

    }
}
