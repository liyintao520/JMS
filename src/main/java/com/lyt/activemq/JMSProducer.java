package com.lyt.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 消息生产者
 * 运行之后会产生消息：http://127.0.0.1:8161/admin/queues.jsp可以查看到
 * @author liyintao
 */
public class JMSProducer {
	private static final String USERNAME=ActiveMQConnection.DEFAULT_USER; // 默认的连接用户名
	private static final String PASSWORD=ActiveMQConnection.DEFAULT_PASSWORD; // 默认的连接密码
	private static final String BROKEURL=ActiveMQConnection.DEFAULT_BROKER_URL; // 默认的连接地址
	private static final int SENDNUM=10; // 发送的消息数量
	
	public static void main(String[] args) {
		ConnectionFactory connectionFactory;// 连接工厂
		Connection connection = null;// JMS包中的 连接
		Session session;// 会话 接受或者发送消息的线程
		Destination destination;// 消息的目的地
		MessageProducer messageProducer;// 消息生产者
		//实例化连接工厂
		//打断点 查看URL：failover://tcp://localhost:61616
		connectionFactory = new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORD, JMSProducer.BROKEURL);
		try {
			//通过连接工厂获取连接
			connection = connectionFactory.createConnection();
			//启动连接
			connection.start();
			// 创建Session
			/**
			 * 直接 Receive 方式
			 * Session.AUTO_ACKNOWLEDGE。 当客户成功的从receive方法返回的时候， 或者从MessageListener.onMessage
				方法成功返回的时候，会话自动确认客户收到的消息。
				Session.CLIENT_ACKNOWLEDGE。 客户通过消息的 acknowledge 方法确认消息。需要注意的是，在这种模
				式中，确认是在会话层上进行：确认一个被消费的消息将自动确认所有已被会话消 费的消息。例如，如果一
				个消息消费者消费了 10 个消息，然后确认第 5 个消息，那么所有 10 个消息都被确认。
				Session.DUPS_ACKNOWLEDGE。 该选择只是会话迟钝第确认消息的提交。如果 JMS provider 失败，那么可
				能会导致一些重复的消息。如果是重复的消息，那么 JMS provider 必须把消息头的 JMSRedelivered 字段设置
				为 true。
			 */
			//session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE); // 创建Session
			session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE); // 创建Session
			// 创建消息队列
			destination = session.createQueue("点对点消息队列");
			// 创建消息生产者
			messageProducer = session.createProducer(destination);
			// 通过session 和 生产者 就可以发送消息了
			sendMessage(session, messageProducer); // 发送消息
			session.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(connection!=null){
				try {
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 发送消息
	 * @param session	会话 接受或者发送消息的线程
	 * @param messageProducer	消息生产者
	 * @throws Exception
	 */
	public static void sendMessage(Session session,MessageProducer messageProducer)throws Exception{
		for(int i=0;i<JMSProducer.SENDNUM;i++){
			//消息类型好几种，我们这里采用的是TextMessage
			TextMessage message=session.createTextMessage("ActiveMQ 发送的消息"+i);
			System.out.println("发送消息："+"ActiveMQ 发送的消息"+i);
			messageProducer.send(message);
		}
	}
}
