package com.lyt.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 消息监听
 * @author liyintao
 */
public class Listener implements MessageListener{

	public void onMessage(Message msg) {
		try {
			System.out.println("监听--收到的消息： " + ((TextMessage)msg).getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
