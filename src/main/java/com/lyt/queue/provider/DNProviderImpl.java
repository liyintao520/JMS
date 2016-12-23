package com.lyt.queue.provider;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息接口的实现
 * @author liyintao
 *
 */
public class DNProviderImpl implements DNProvider {
	
	//要连接activemq 需要参数（用户，密码，默认URL）
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
	
	ConnectionFactory connectionFactory;// 连接工厂
	Connection connection = null;//JMS包中的 连接
	Session session;// 会话 接受或者发送消息的线程
	
	//我要对这个线程和生产者 一一绑定
	ThreadLocal<MessageProducer> t1 = new ThreadLocal<MessageProducer>();
	
	ThreadLocal<Integer> count1 = new ThreadLocal<Integer>();
	@Override
	public void init() {
		try {
			//实例化连接工厂
			connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);
			//通过连接工厂获取连接
			connection = connectionFactory.createConnection();
			//启动连接
			connection.start();
			// 创建Session   这里选择的是：事物模式
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送消息
	 */
	public void sendMessage(String disname) {
		try {
			Queue queue = session.createQueue(disname);
			MessageProducer messageProducer = null;
			if(t1.get() != null){
				messageProducer = t1.get();
			}else{
				messageProducer = session.createProducer(queue);
				t1.set(messageProducer);
			}
			while(true){
				Integer count = count1.get();
				if(count == null){
					count =0;
				}
				count += 1;
				//这里过程是：你运行之后，消息会发送到中间件，然后由中间件异步的发送消息
				TextMessage msg = session.createTextMessage(Thread.currentThread().getName() + " Provider:我是XX平台，我需要发送短信：短信内容---P2P模式我是生产者  " + count);
				System.out.println(Thread.currentThread().getName() + " --Provider:我是XX平台，我需要发送短信：短信内容content." + count);
				count1.set(count);
				messageProducer.send(msg);
				//发送完成之后提交。
				session.commit();
			}
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
