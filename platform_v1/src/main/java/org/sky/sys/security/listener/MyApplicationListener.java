/**   
 * @Title: MyApplicationListener.java 
 * @Package org.sky.sys.security.listener 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author A18ccms A18ccms_gmail_com   
 * @date 2016-10-25 下午3:40:50 
 * @version V1.0   
 */

package org.sky.sys.security.listener;

import java.util.TimeZone;

import org.sky.sys.utils.ApplicationCached;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MyApplicationListener
 * @Description: TODO(监听spring容器启动完后事件)
 * @author 位峰祥
 * @date 2016-10-25 下午3:40:50
 * @version V1.0
 */
@Component("MyApplicationListener")
public class MyApplicationListener implements
		ApplicationListener<ContextRefreshedEvent> {// ContextRefreshedEvent为初始化完毕事件，spring还有很多事件可以利用

	/**
	 * 当一个ApplicationContext被初始化或刷新触发
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// roleDao.getUserList();//spring容器初始化完毕加载用户列表到内存
		System.out.println("spring容器初始化完毕================================================");
		TimeZone tz = TimeZone.getTimeZone("GMT+8"); 
	    TimeZone.setDefault(tz);
	    ApplicationCached.init();
		System.out.println("ApplicationCached初始化完毕================================================");

	  
	}

}