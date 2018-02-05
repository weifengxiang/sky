package org.sky.sys.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.sky.sys.action.BaseController;
import org.sky.sys.exception.ServiceException;
import org.sky.sys.model.SysOperation;
import org.sky.sys.model.SysRole;
import org.sky.sys.model.SysRoleExample;
import org.sky.sys.model.SysRoleWidget;
import org.sky.sys.model.SysRoleWidgetExample;
import org.sky.sys.model.SysUserRole;
import org.sky.sys.model.SysRoleExample.Criteria;
import org.sky.sys.service.SysRoleService;
import org.sky.sys.service.SysRoleWidgetService;
import org.sky.sys.utils.JsonUtils;
import org.sky.sys.utils.Page;
import org.sky.sys.utils.PageListData;
import org.sky.sys.utils.ResultData;
import org.sky.sys.utils.StringUtils;
import org.sky.sys.utils.TreeStru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
@Controller
public class SysRoleController extends BaseController{
	@Autowired
	private SysRoleService sysroleService;
	@Autowired
	private SysRoleWidgetService sysrolewidgetService;
	
	public SysRoleController() {
		// TODO Auto-generated constructor stub
	}
	/**
	*显示角色表管理页面
	**/
	@RequestMapping(value = "/sys/SysRole/initSysRoleManagePage", method = { RequestMethod.GET })
	public String initSysRoleManagePage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/role/sysrolemanage";
	}
	/**
	 * 角色表分页查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/SysRole/getSysRoleByPage", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysRoleByPage(
			HttpServletRequest request, 
			HttpServletResponse response){
		String filter = request.getParameter("filter");
		Map filterMap = JsonUtils.json2map(filter);
		String sortfield=request.getParameter("sortfield");
		Page p= super.getPage(request);
		SysRoleExample pote= new SysRoleExample();
		if(null!=filterMap){
			pote.createCriteria();
			pote.integratedQuery(filterMap);
		}
		
		if(!StringUtils.isNull(sortfield)){
			pote.setOrderByClause(sortfield);
		}
		pote.setPage(p);
		PageListData pageData = sysroleService.getSysRoleByPage(pote);
		return JsonUtils.obj2json(pageData);
	}
	/**
	*显示角色表新增页面
	**/
	@RequestMapping(value = "/sys/SysRole/initAddSysRolePage", method = { RequestMethod.GET })
	public String initAddSysRolePage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/role/editsysrole";
	}
	/**
	*显示角色表修改页面
	**/
	@RequestMapping(value = "/sys/SysRole/initEditSysRolePage", method = { RequestMethod.GET })
	public String initEditSysRolePage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/role/editsysrole";
	}
	/**
	*显示角色表详细页面
	**/
	@RequestMapping(value = "/sys/SysRole/initDetailSysRolePage", method = { RequestMethod.GET })
	public String initDetailSysRolePage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/role/detailsysrole";
	}
	/**
	*保存新增/修改角色表
	**/
	@RequestMapping(value = "/sys/SysRole/saveAddEditSysRole", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String saveAddEditSysRole(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			SysRole edit = (SysRole) getEntityBean(request,SysRole.class);
			sysroleService.saveAddEditSysRole(edit);
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
	*删除角色表
	**/
	@RequestMapping(value = "/sys/SysRole/delSysRole", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String delSysRole(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			String delRows=request.getParameter("delRows");
			List de=JsonUtils.json2list(delRows, SysRole.class);
			sysroleService.delSysRoleById(de);
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
	*根据主键查询角色表
	**/
	@RequestMapping(value = "/sys/SysRole/getSysRoleById", method =RequestMethod.GET,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysRoleById(
			HttpServletRequest request, 
			HttpServletResponse response){
		String id = request.getParameter("id");
		SysRole bean = sysroleService.getSysRoleById(id);
		return JsonUtils.obj2json(bean);
	}
	/**
	 * 角色操作树页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/SysRole/initRoleOpeTreePage", method = { RequestMethod.GET })
	public String initRoleOpeTreePage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/role/roleopetree";
	} 
	/**
	 * 根据角色编码查询功能树
	 * @param roleCode
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/SysRole/getRoleOpeTree/{roleCode}", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody List<TreeStru> getRoleOpeTree(
			@PathVariable String roleCode,
			HttpServletRequest request, 
			HttpServletResponse response){
		return sysroleService.getRoleOpeTree(roleCode);
	}
	/**
	 * 保存角色操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/SysRole/saveRoleOperation", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String saveRoleOperation(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			String role = request.getParameter("role");
			String operation = request.getParameter("operation");
			SysRole sr = JsonUtils.json2pojo(role, SysRole.class);
			List<SysOperation> solist = JsonUtils.json2list(operation, SysOperation.class);
			sysroleService.saveRoleOperation(sr,solist);
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
	*显示角色微件列表页面
	**/
	@RequestMapping(value = "/sys/SysRole/initSysRoleWidgetPage", method = { RequestMethod.GET })
	public String initSysRoleWidgetPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/role/sysrolewidget";
	}
	/**
	 * 角色微件分页查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/SysRole/getSysRoleWidgetByPage", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysRoleWidgetByPage(
			HttpServletRequest request, 
			HttpServletResponse response){
		String filter = request.getParameter("filter");
		Map filterMap = JsonUtils.json2map(filter);
		String sortfield=request.getParameter("sortfield");
		Page p= super.getPage(request);
		SysRoleWidgetExample pote= new SysRoleWidgetExample();
		if(null!=filterMap){
			pote.createCriteria();
			pote.integratedQuery(filterMap);
		}
		
		if(!StringUtils.isNull(sortfield)){
			pote.setOrderByClause(sortfield);
		}
		pote.setPage(p);
		PageListData pageData = sysrolewidgetService.getSysRoleWidgetByPage(pote);
		return JsonUtils.obj2json(pageData);
	}
	@RequestMapping(value = "/sys/SysRole/saveAddEditSysRoleWidgetList", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String saveAddEditSysRoleWidgetList(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			String insertRows = request.getParameter("insertRows");
			String updateRows = request.getParameter("updateRows");
			List<SysRoleWidget> insert=null;
			List<SysRoleWidget> update=null;
			if(!StringUtils.isNull(insertRows)){
				insert = JsonUtils.json2list(insertRows, SysRoleWidget.class);
			}
			if(!StringUtils.isNull(updateRows)){
				update = JsonUtils.json2list(updateRows, SysRoleWidget.class);
			}
			sysrolewidgetService.saveSysRoleWidget(insert, update);
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
	*删除角色微件
	**/
	@RequestMapping(value = "/sys/SysRole/delSysRoleWidget", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String delSysRoleWidget(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			String delRows=request.getParameter("delRows");
			List de=JsonUtils.json2list(delRows, SysRoleWidget.class);
			sysrolewidgetService.delSysRoleWidgetById(de);
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
}