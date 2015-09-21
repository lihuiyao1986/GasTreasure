package com.goldcard.igas.getui;

import android.content.Context;


import com.goldcard.igas.getui.model.BaseGexinPayLoadModel;


/**
 * 解析个信透传消息的解析器
 * @author yanshengli
 * @since liys
 */
public interface GexinPayLoadParser
{
	/**
	 * 解析推送消息
	 * @param message
	 * @return
	 */
	public BaseGexinPayLoadModel parse(String message);
	
	/**
	 * 做消息转发
	 * @param result
	 */
	public void dispatchPayLoad(Context context, BaseGexinPayLoadModel result);
}
