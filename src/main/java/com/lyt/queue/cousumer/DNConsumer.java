package com.lyt.queue.cousumer;
/**
 * 消费者接口：获得消息消费
 * @author liyintao
 */
public interface DNConsumer {
	public void init();
	public void getMessage(String disname);
}
