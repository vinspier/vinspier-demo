package cn.itcast.rabbitmq.fanout;

import cn.itcast.rabbitmq.util.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 发布订阅中的
 * 广播模式fanout
 * */
public class Send {

    private final static String EXCHANGE_NAME = "fanout_exchange_test";

    public static void main(String[] argv) throws Exception {
        // 获取到连接
        Connection connection = ConnectionUtil.getConnection();
        // 获取通道
        Channel channel = connection.createChannel();
        
        // 1、声明exchange，2、指定类型为fanout  3、durable参数设置 管道和队列都为持久化 这样子消息不易丢失
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout",true);
        
        // 消息内容
        String message = "Hello everyone";
        // 发布消息到Exchange
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        System.out.println(" [生产者] Sent： '" + message + "'");

        channel.close();
        connection.close();
    }
}