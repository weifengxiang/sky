package org.sky.sys.utils;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sky.sys.model.SysOrgan;
import org.sky.sys.model.SysUser;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class BspUtils {
	/**
	 * 获取当前登录用户
	 * 
	 * @return
	 */
	public static SysUser getLoginUser() {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getDetails();
		if (obj instanceof SysUser) {
			SysUser user = (SysUser) obj;
			return user;
		} else {
			return null;
		}
	}
	/**
	 * 根据id获取spring注册bean
	 * 
	 * @param beanId
	 * @return
	 */
	public static Object getBean(String beanId) {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		return wac.getBean(beanId);
	}

	public static <T> T getBean(Class<T> clazz) {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		return wac.getBean(clazz);
	}

	/**
	 * 获取session
	 * 
	 * @return
	 */
	public static HttpSession getHttpSession() {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getSession(true);
		return session;
	}

	/**
	 * 获取request
	 * 
	 * @return
	 */
	public static HttpServletRequest getHttpRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request;
	}

	/**
	 * 是否开启权限过滤 开发模式或者登陆用户是管理员或者超级管理员时显示所有菜单
	 * 
	 * @return
	 */
	public static boolean isSecurityFilter() {
		String userid = null;
		if (null != BspUtils.getLoginUser()) {
			userid = BspUtils.getLoginUser().getCode();
		}
		if ("false".equals(ConfUtils.getValue("productModel")) || "ADMIN".equals(userid)
				|| "SUPERADMIN".equals(userid)) {
			return false;
		}
		return true;
	}
}
