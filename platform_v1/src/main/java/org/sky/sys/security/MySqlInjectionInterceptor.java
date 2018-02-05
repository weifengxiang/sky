package org.sky.sys.security;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MySqlInjectionInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,	HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,	Object arg2, ModelAndView arg3) throws Exception {

	}
	/**
	 * 该方法将在请求处理之前进行调用
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object arg2) throws Exception {
//		if(request.getMethod().toLowerCase().equals("get"))return true;
//		//
//		Enumeration<String> names=request.getParameterNames();
//		while (names.hasMoreElements()) {
//			String name = (String) names.nextElement();
//			String[] values=request.getParameterValues(name);
//			for (int i = 0; i < values.length; i++) {
//				 String value=values[i].toLowerCase();
//				 if(validat(value)){
//					 response.setStatus(403);
//					 return false;
//				 }
//			}
//		}
		return true;
	}
	/**
	 * 
	 */
	private boolean validat(String value){
		String regstr= "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
		return value.matches(regstr);
	} 
}
