package org.sky.sys.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sky.sys.action.BaseController;
import org.sky.sys.exception.ServiceException;
import org.sky.sys.model.SysLog;
import org.sky.sys.model.SysLogExample;
import org.sky.sys.model.SysLogExample.Criteria;
import org.sky.sys.service.SysLogService;
import org.sky.sys.utils.JsonUtils;
import org.sky.sys.utils.Page;
import org.sky.sys.utils.PageListData;
import org.sky.sys.utils.ResultData;
import org.sky.sys.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
@Controller
public class SysLogController extends BaseController{
	@Autowired
	private SysLogService syslogService;
	
	public SysLogController() {
		// TODO Auto-generated constructor stub
	}
	/**
	*显示日志表页面
	**/
	@RequestMapping(value = "/sys/SysLog/initSysLog", method = { RequestMethod.GET })
	public String initSysLog(Map<String, Object> map,
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/xxcl/listsyslog";
	}
	/**
	 * 日志表分页查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/SysLog/getSysLogByPage", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysLogByPage(
			HttpServletRequest request, 
			HttpServletResponse response){
		String filter = request.getParameter("filter");
		Map filterMap = JsonUtils.json2map(filter);
		String sortfield=request.getParameter("sortfield");
		Page p= super.getPage(request);
		SysLogExample pote= new SysLogExample();
		if(null!=filterMap){
			pote.createCriteria();
			pote.integratedQuery(filterMap);
		}
		
		if(!StringUtils.isNull(sortfield)){
			pote.setOrderByClause(sortfield);
		}
		pote.setPage(p);
		PageListData pageData = syslogService.getSysLogByPage(pote);
		return JsonUtils.obj2json(pageData);
	}
	
	/**
	*保存新增日志表
	**/
	@RequestMapping(value = "/sys/SysLog/saveAddSysLog", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String saveAddSysLog(@ModelAttribute SysLog add,
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			syslogService.saveAddSysLog(add);
			rd.setCode(ResultData.code_success);
			rd.setName("保存成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rd.setCode(ResultData.code_error);
			rd.setName("保存失败<br>"+e.getMessage());
		}
		return JsonUtils.obj2json(rd);
	}
	
	/**
	*保存修改日志表
	**/
	@RequestMapping(value = "/sys/SysLog/saveEditSysLog", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String saveEditSysLog(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			SysLog edit = (SysLog) getEntityBean(request,SysLog.class);
			syslogService.saveEditSysLog(edit);
			rd.setCode(ResultData.code_success);
			rd.setName("保存成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rd.setCode(ResultData.code_error);
			rd.setName("保存失败<br>"+e.getMessage());
		}
		return JsonUtils.obj2json(rd);
	}
	/**
	*删除日志表
	**/
	@RequestMapping(value = "/sys/SysLog/delSysLog", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String delSysLog(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			String delRows=request.getParameter("delRows");
			List de=JsonUtils.json2list(delRows, SysLog.class);
			syslogService.delSysLogById(de);
			rd.setCode(ResultData.code_success);
			rd.setName("删除成功");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rd.setCode(ResultData.code_error);
			rd.setName("删除失败<br>"+e.getMessage());
		}
		return JsonUtils.obj2json(rd);
	}
	/**
	*根据主键查询日志表
	**/
	@RequestMapping(value = "/sys/SysLog/getSysLogById", method =RequestMethod.GET,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysLogById(
			HttpServletRequest request, 
			HttpServletResponse response){
		String id = request.getParameter("id");
		SysLog bean = syslogService.getSysLogById(id);
		return JsonUtils.obj2json(bean);
	}
}