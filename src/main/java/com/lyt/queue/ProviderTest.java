package com.lyt.queue;

import com.lyt.queue.provider.DNProvider;
import com.lyt.queue.provider.DNProviderImpl;

/**
 * 生产者的测试类
 * @author liyintao
 */
public class ProviderTest {
	public static void main(String[] args) {
		DNProvider provider = new DNProviderImpl();
		//初始化
		provider.init();
		//确定一个生产的名称，与消费者名要求一致
		//provider.sendMessage("lyt-DNF");//不结合线程做
		
		ProviderTest t = new ProviderTest();
		new Thread(t.new MyThread(provider)).start();
		new Thread(t.new MyThread(provider)).start();
		new Thread(t.new MyThread(provider)).start();
		new Thread(t.new MyThread(provider)).start();
		new Thread(t.new MyThread(provider)).start();
		new Thread(t.new MyThread(provider)).start();
		new Thread(t.new MyThread(provider)).start();
		new Thread(t.new MyThread(provider)).start();
		new Thread(t.new MyThread(provider)).start();
		new Thread(t.new MyThread(provider)).start();
	}
	
	private class MyThread implements Runnable{
		private DNProvider dnp;
		public MyThread(DNProvider dnp){
			this.dnp = dnp;
		}
		
		public void run() {
			while(true){
				dnp.sendMessage("lyt-DNF20");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
