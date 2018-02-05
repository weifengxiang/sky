/**   
* @Title: PubOnlineUsersController.java 
* @Package org.es.sys.action 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2016-12-7 下午4:20:24 
* @version V1.0   
*/


package org.sky.sys.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sky.sys.exception.ServiceException;
import org.sky.sys.security.MyUserDetails;
import org.sky.sys.utils.JsonUtils;
import org.sky.sys.utils.PageListData;
import org.sky.sys.utils.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/** 
 * @ClassName: PubOnlineUsersController 
 * @Description: TODO(在线用户管理) 
 * @author 位峰祥 
 * @date 2016-12-7 下午4:20:24 
 * @version V1.0 
 */
@Controller
public class SysOnlineUsersController {
	@Autowired
	private SessionRegistry sessionRegistry;

	@RequestMapping(value = "/sys/onlineuser/initOnlineUserListPage", method = { RequestMethod.GET })
	public String initOnlineUserListPage(Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/onlineuser/onlineuser";
	}
	@RequestMapping(value = "/sys/onlineuser/getOnlineUser", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getOnlineUser(
			HttpServletRequest request, 
			HttpServletResponse response){
		List<Object> slist =sessionRegistry.getAllPrincipals();  
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        List rows = new ArrayList();
        for(int i=0;i<slist.size();i++){  
            List<SessionInformation> sessionList = sessionRegistry.getAllSessions(slist.get(i),true);   
            MyUserDetails user=(MyUserDetails)slist.get(i);  
            for(SessionInformation t:sessionList){
            	System.out.println(JsonUtils.obj2json(t.getPrincipal()));
            	Map res = new HashMap();
            	res.put("id", user.getUserid());
            	res.put("loginid", user.getLoginid());
            	res.put("userid", user.getUserid());
            	res.put("username", user.getUsername());
            	res.put("organname", user.getOrganname());
            	res.put("depname", user.getDepname());
            	res.put("loginip",  user.getLoginIp());
            	res.put("logindate",  sdf.format(user.getLoginDate()));
            	res.put("sessionId", t.getSessionId());
            	res.put("lastRequest", sdf.format(t.getLastRequest()));
            	rows.add(res);
            }  
        }  
        PageListData pageData = new PageListData();
        pageData.setTotal(slist.size());
        pageData.setRows(rows);
        return JsonUtils.obj2json(pageData);
	}
	@RequestMapping(value = "/sys/onlineuser/kickOut", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String kickOut(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			String kickOutRows=request.getParameter("kickOutRows");
			List<Map> deList=JsonUtils.json2list(kickOutRows, Map.class);
			for(Map de:deList){
			 String loginid = (String)de.get("loginid");
			 System.out.println("kick out : " +loginid);  
		        List<Object> objects = this.sessionRegistry.getAllPrincipals();  
		        for (Object o : objects) {  
		        	MyUserDetails user = (MyUserDetails) o;  
		            if (user.getLoginid().equals(loginid)) {
		                List<SessionInformation> sis = this.sessionRegistry  
		                        .getAllSessions(o, false);  
		                if (sis != null) {  
		                    for (SessionInformation si : sis) {  
		                        si.expireNow();  
		                        System.out.println(si.isExpired() ? "yes,  session be expired":"no yet,session still active");  
		                        System.out.println("---"+loginid+"---have be kick out!"); 
		                        sessionRegistry.removeSessionInformation((String)de.get("sessionId"));
		                    }  
		                }  
		            }  
		        }  
		        System.out.println("no one call---"+loginid+"---login");
			}
			rd.setCode(ResultData.code_success);
			rd.setName("强制下线成功");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rd.setCode(ResultData.code_error);
			rd.setName("强制下线失败<br>"+e.getMessage());
		}
		return JsonUtils.obj2json(rd);
	}
}
