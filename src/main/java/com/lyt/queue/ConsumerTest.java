package com.lyt.queue;

import com.lyt.queue.cousumer.DNConsumer;
import com.lyt.queue.cousumer.DNConsumerImpl;
import com.lyt.queue.provider.DNProvider;

/**
 * 消费者的测试类
 * @author liyintao
 */
public class ConsumerTest {
	public static void main(String[] args) {
		DNConsumer consumer = new DNConsumerImpl();
		//初始化，并接收消息
		consumer.init();
		//consumer.getMessage("lyt-DNF");
		
		ConsumerTest t = new ConsumerTest();
		new Thread(t.new MyThread(consumer)).start();
		new Thread(t.new MyThread(consumer)).start();
		new Thread(t.new MyThread(consumer)).start();
		new Thread(t.new MyThread(consumer)).start();
		new Thread(t.new MyThread(consumer)).start();
		new Thread(t.new MyThread(consumer)).start();
		new Thread(t.new MyThread(consumer)).start();
		new Thread(t.new MyThread(consumer)).start();
		new Thread(t.new MyThread(consumer)).start();
		new Thread(t.new MyThread(consumer)).start();
	}
	
	private class MyThread implements Runnable{
		private DNConsumer dnp;
		public MyThread(DNConsumer dnp){
			this.dnp = dnp;
		}
		
		public void run() {
			while(true){
				dnp.getMessage("lyt-DNF20");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
