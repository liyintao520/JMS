package com.lyt.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息消费者--利用监听方式去做
 * @author liyintao failover://tcp://localhost:61616
 */
public class JMSConsumerListener {
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER; // 默认的连接用户名
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD; // 默认的连接密码
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL; // 默认的连接地址

	public static void main(String[] args) {
		ConnectionFactory connectionFactory; // 连接工厂
		Connection connection = null; // 连接
		Session session; // 会话 接受或者发送消息的线程
		Destination destination; // 消息的目的地
		MessageConsumer messageConsumer; // 消息的消费者
		// 实例化连接工厂
		connectionFactory = new ActiveMQConnectionFactory(JMSConsumerListener.USERNAME, JMSConsumerListener.PASSWORD, JMSConsumerListener.BROKEURL);
		try {
			connection = connectionFactory.createConnection(); // 通过连接工厂获取连接
			connection.start(); // 启动连接
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); // 创建Session
			// session = connection.createSession(Boolean.FALSE,
			// Session.AUTO_ACKNOWLEDGE); // 创建Session
			// TODO 测试第一个消息队列 这个值 要与生产消费者发布的名字一致
			destination = session.createQueue("点对点消息队列"); // 创建连接的消息队列
			messageConsumer = session.createConsumer(destination); // 创建消息消费者
			//利用监听去消费消息
			//注册消息监听
			messageConsumer.setMessageListener(new Listener());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
