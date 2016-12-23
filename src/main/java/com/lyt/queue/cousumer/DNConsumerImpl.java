package com.lyt.queue.cousumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class DNConsumerImpl implements DNConsumer {
	//要连接activemq 需要参数（用户，密码，默认URL）
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
	
	ConnectionFactory connectionFactory;// 连接工厂
	Connection connection = null;//JMS包中的 连接
	Session session;// 会话 接受或者发送消息的线程
	//我要对这个线程和生产者 一一绑定
	ThreadLocal<MessageConsumer> t1 = new ThreadLocal<MessageConsumer>();
	
//	ThreadLocal<Integer> count1 = new ThreadLocal<Integer>();
	public void init() {
		try {
			//实例化连接工厂
			connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);
			//通过连接工厂获取连接
			connection = connectionFactory.createConnection();
			//启动连接
			connection.start();
			// 创建Session   这里选择的是：事物模式
			//TODO 消费消息的话没有事物的 所以是false 不让他自动提交
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void getMessage(String disname) {
		try {
			Queue queue = session.createQueue(disname);
//			MessageConsumer messageConsumer = session.createConsumer(queue);
			
			MessageConsumer messageConsumer = null;
			
			if(t1.get() != null){
				messageConsumer = t1.get();
			}else{
				messageConsumer = session.createConsumer(queue);
			}
			while(true){
				TextMessage message = (TextMessage) messageConsumer.receive();
				//拿到消息之后，给一个应答
				message.acknowledge();
				
//				Thread.sleep(1000);
				if(message != null){
					System.out.println(Thread.currentThread().getName() + "--Consumer：我是消费者：我接收的内容是：" + message.getText());
				}else{
					break;
				}
			}
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
	}

}
