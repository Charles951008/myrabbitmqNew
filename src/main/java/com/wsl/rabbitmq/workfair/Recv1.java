package com.wsl.rabbitmq.workfair;

import com.rabbitmq.client.*;
import com.wsl.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv1 {
    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取链接
        Connection connection = ConnectionUtils.getConnection();

        //获取channel
        final Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);


        int prefetchCount=1;
        channel.basicQos(prefetchCount);//保证一次只分发一个


        //定义一个消费者
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            //一旦有消息就会触发这个方法  消息到达
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("[1] Recv msg:" + msg);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("[1] done");

                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };

        boolean autoAck = false;//自动应答改成false
        channel.basicConsume(QUEUE_NAME, autoAck, defaultConsumer);
    }
}
