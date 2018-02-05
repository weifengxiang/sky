package org.sky.sys.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import org.sky.sys.model.SysUser;
import org.sky.sys.model.SysWidget;
import org.sky.sys.service.SecurityCodeService;
import org.sky.sys.service.SysService;
import org.sky.sys.service.SysWidgetService;
import org.sky.sys.utils.BspUtils;
import org.sky.sys.utils.JsonUtils;
import org.sky.sys.utils.MenuStru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
/**
 * 
* @Title: PubSysController.java 
* @Package org.sky.sys.action 
* @Description: TODO(系统Controller) 
* @author 位峰祥   
* @date 2016-6-2 下午10:34:46 
* @version V1.0
 */
@Controller
public class SysController {
	@Autowired
	private SecurityCodeService securityCodeService;
	@Autowired
	private SessionRegistry sessionRegistry;
	@Autowired
	private SysService sysService;
	@Autowired
	private SysWidgetService syswidgetservice;
	
	/**
	 * 获取验证码
	 * 
	 * @return
	 */
	@RequestMapping(value = "/securitycode", method = RequestMethod.GET, produces = "image/jpg")
	public @ResponseBody
	byte[] getSecurityCode(HttpServletRequest request) {
		return securityCodeService.createSecurityCode(request);
	}

	/**
	 * 验证码校验
	 * @param locale
	 * @param model
	 * @param secode
	 * @return
	 */
	@RequestMapping(value = "/equalseccode", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String equalseccode(HttpServletRequest request, Model model,
			@RequestParam String secode) {
		boolean isequal=false;
		boolean istimeout=false;
		boolean issesinv = false;
		JSONObject json = new JSONObject();
		try{
			HttpSession session = request.getSession(true);
			String sessionseccode = (String) session.getAttribute("securitycode");
			if(null==sessionseccode){
				issesinv=true;
			}
			long sectime = (Long) request.getSession().getAttribute("sectime");
			//2分钟不登录超时限制
			istimeout = (System.currentTimeMillis() - sectime) > 2 * 60 * 1000;
			isequal = secode.equals(sessionseccode);
		}catch(Exception e){
			issesinv=true;
			e.printStackTrace();
		}
		//3:session失效;2:计算超时;1:计算错误;0:计算成功
		if(issesinv){
			json.put("success",3);
		}else if(istimeout){
			json.put("success",2);
		}else if(isequal){
			json.put("success",0);
		}else{
			json.put("success",1);
		}
		return json.toString();
	}
	/**
	 * 登录
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.GET })
	public String login(Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		return "login";
	}
	@RequestMapping(value = "/loginfailed", method = { RequestMethod.GET })
	public String loginfailed(Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		return "login";
	}
	@RequestMapping(value = "/loginsuccess", method = { RequestMethod.GET })
	public String loginsuccess(Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		SysUser loginuser = BspUtils.getLoginUser();
		int onlineNo = sessionRegistry.getAllPrincipals().size();
		String msg = "欢迎【"+loginuser.getName()+"】登录	当前"+onlineNo+"用户在线";
		request.setAttribute("LoginUserMsg", msg);
		/**
		 * 可以根据这里跳转到不同的页面
		 */
		return "jsp/main/main";
	}
	
	@RequestMapping(value = "/logout", method = { RequestMethod.GET })
	public String logout(Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		request.getSession(true).invalidate();
		String basePath = request.getScheme()+"://"+request.getServerName()+
				":"+request.getServerPort()+request.getContextPath()+"/";
		return "redirect:"+basePath;
	}
	@RequestMapping(value = "/sys/getNavMenu", method =RequestMethod.POST,produces = "text/html;charset=UTF-8")
	public @ResponseBody String getNavMenu(
			HttpServletRequest request, HttpServletResponse response){
		List<MenuStru> menulist = sysService.getLeftNavMenuData();
		return JsonUtils.obj2json(menulist);
	}
	@RequestMapping(value = "/sys/onLineUserNum", method = { RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public @ResponseBody int getOnLineUserNum(){
		return sessionRegistry.getAllPrincipals().size();
	}
	@RequestMapping(value = "/sys/home", method = { RequestMethod.GET })
	public String home(Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/main/home";
	}
	@RequestMapping(value = "/sys/workbench", method = { RequestMethod.GET })
	public ModelAndView workbench(Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		String userCode=BspUtils.getLoginUser().getCode();
		List<SysWidget> pwlist = syswidgetservice.getSysWidgetListByUserCode(userCode);
		ModelAndView mv =  new ModelAndView();
		mv.addObject("wdlist", JsonUtils.obj2json(pwlist));
		mv.setViewName("jsp/main/workbench");
		return mv;
	}
	@RequestMapping(value = "/sys/widget/{name}", method = { RequestMethod.GET })
	public ModelAndView widget(@PathVariable String name,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv= new ModelAndView();
		mv.setViewName("jsp/main/widget/"+name);
		return mv;
	}
	@RequestMapping(value = "/error/{name}", method = { RequestMethod.GET })
	public ModelAndView error(@PathVariable String name,
			HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv= new ModelAndView();
		mv.setViewName("jsp/error/"+name);
		return mv;
	}
}
