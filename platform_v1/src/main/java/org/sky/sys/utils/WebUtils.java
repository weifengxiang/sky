/**   
 * @Title: WebUtils.java 
 * @Package org.sky.sys.utils 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author A18ccms A18ccms_gmail_com   
 * @date 2016-10-27 下午4:44:47 
 * @version V1.0   
 */

package org.sky.sys.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: WebUtils
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 位峰祥
 * @date 2016-10-27 下午4:44:47
 * @version V1.0
 */
public class WebUtils {
	/**
	 * 
	* @Title: isAjaxRequest 
	* @Description: TODO(判断是否是ajax) 
	* @param @param request
	* @param @param response
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String requestType = request.getHeader("X-Requested-With");
		if(StringUtils.isNull(requestType)){
			return false;
		}else{
			return true;
		}
	}
}
