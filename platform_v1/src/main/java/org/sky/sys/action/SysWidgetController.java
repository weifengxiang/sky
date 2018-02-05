package org.sky.sys.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.sky.sys.action.BaseController;
import org.sky.sys.exception.ServiceException;
import org.sky.sys.model.SysWidget;
import org.sky.sys.model.SysWidgetExample;
import org.sky.sys.model.SysWidgetExample.Criteria;
import org.sky.sys.service.SysWidgetService;
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
public class SysWidgetController extends BaseController{
	@Autowired
	private SysWidgetService syswidgetService;
	
	public SysWidgetController() {
		// TODO Auto-generated constructor stub
	}
	/**
	*显示微件列表页面
	**/
	@RequestMapping(value = "/sys/SysWidget/initSysWidgetListPage", method = { RequestMethod.GET })
	public String initSysWidgetListPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/widget/listsyswidget";
	}
	/**
	 * 微件分页查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/SysWidget/getSysWidgetByPage", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysWidgetByPage(
			HttpServletRequest request, 
			HttpServletResponse response){
		String filter = request.getParameter("filter");
		Map filterMap = JsonUtils.json2map(filter);
		String sortfield=request.getParameter("sortfield");
		Page p= super.getPage(request);
		SysWidgetExample pote= new SysWidgetExample();
		if(null!=filterMap){
			pote.createCriteria();
			pote.integratedQuery(filterMap);
		}
		if(!StringUtils.isNull(sortfield)){
			pote.setOrderByClause(sortfield);
		}
		pote.setPage(p);
		PageListData pageData = syswidgetService.getSysWidgetByPage(pote);
		return JsonUtils.obj2json(pageData);
	}
	/**
	*显示微件新增页面
	**/
	@RequestMapping(value = "/sys/SysWidget/initAddSysWidgetPage", method = { RequestMethod.GET })
	public String initAddSysWidgetPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/widget/editsyswidget";
	}
	/**
	*显示微件修改页面
	**/
	@RequestMapping(value = "/sys/SysWidget/initEditSysWidgetPage", method = { RequestMethod.GET })
	public String initEditSysWidgetPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/widget/editsyswidget";
	}
	/**
	*显示微件详细页面
	**/
	@RequestMapping(value = "/sys/SysWidget/initDetailSysWidgetPage", method = { RequestMethod.GET })
	public String initDetailSysWidgetPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/widget/detailsyswidget";
	}
	/**
	*保存新增/修改微件
	**/
	@RequestMapping(value = "/sys/SysWidget/saveAddEditSysWidget", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String saveAddEditSysWidget(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			SysWidget edit = (SysWidget) getEntityBean(request,SysWidget.class);
			syswidgetService.saveAddEditSysWidget(edit);
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
	*删除微件
	**/
	@RequestMapping(value = "/sys/SysWidget/delSysWidget", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String delSysWidget(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			String delRows=request.getParameter("delRows");
			List de=JsonUtils.json2list(delRows, SysWidget.class);
			syswidgetService.delSysWidgetById(de);
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
	*根据主键查询微件
	**/
	@RequestMapping(value = "/sys/SysWidget/getSysWidgetById", method =RequestMethod.GET,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysWidgetById(
			HttpServletRequest request, 
			HttpServletResponse response){
		String id = request.getParameter("id");
		SysWidget bean = syswidgetService.getSysWidgetById(id);
		return JsonUtils.obj2json(bean);
	}
}