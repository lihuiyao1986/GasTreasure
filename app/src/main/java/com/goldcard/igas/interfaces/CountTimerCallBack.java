package com.goldcard.igas.interfaces;

/**
 * 计时器回调接口
 * @author yanshengli
 * @since 2015-2-27
 */
public interface CountTimerCallBack
{

	/**
	 * 计时正在进行
	 * @param remainSecond
	 */
	public void timerCountRunning(long remainSecond);
	
	
	/**
	 * 计时结束
	 */
	public void timerFinished();
}
