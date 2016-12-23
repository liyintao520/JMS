package com.lyt.queue.provider;
/**
 * 生产者的消息接口
 * @author liyintao
 *
 */
public interface DNProvider {
	public void init();
	public void sendMessage(String disname);
}
