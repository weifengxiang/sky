package org.sky.sys.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.sky.sys.action.BaseController;
import org.sky.sys.exception.ServiceException;
import org.sky.sys.model.SysUser;
import org.sky.sys.model.SysUserDataAccess;
import org.sky.sys.model.SysUserDataAccessExample;
import org.sky.sys.model.SysUserExample;
import org.sky.sys.model.SysUserRoleExample;
import org.sky.sys.model.SysUserExample.Criteria;
import org.sky.sys.model.SysUserRole;
import org.sky.sys.service.SysUserDataAccessService;
import org.sky.sys.service.SysUserRoleService;
import org.sky.sys.service.SysUserService;
import org.sky.sys.utils.JsonUtils;
import org.sky.sys.utils.Page;
import org.sky.sys.utils.PageListData;
import org.sky.sys.utils.ResultData;
import org.sky.sys.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
@Controller
public class SysUserController extends BaseController{
	@Autowired
	private SysUserService sysuserService;
	@Autowired
	private SysUserRoleService sysuserroleservice;
	@Autowired
	private SysUserDataAccessService sysuserdataaccessservice;
	
	public SysUserController() {
		// TODO Auto-generated constructor stub
	}
	/**
	*显示用户表管理页面
	**/
	@RequestMapping(value = "/sys/SysUser/initSysUserManagePage", method = { RequestMethod.GET })
	public String initSysUserManagePage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/user/sysusermanage";
	}
	/**
	 * 用户表分页查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/SysUser/getSysUserByPage", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysUserByPage(
			HttpServletRequest request, 
			HttpServletResponse response){
		String filter = request.getParameter("filter");
		Map filterMap = JsonUtils.json2map(filter);
		String sortfield=request.getParameter("sortfield");
		Page p= super.getPage(request);
		SysUserExample pote= new SysUserExample();
		if(null!=filterMap){
			pote.createCriteria();
			pote.integratedQuery(filterMap);
		}
		
		if(!StringUtils.isNull(sortfield)){
			pote.setOrderByClause(sortfield);
		}
		pote.setPage(p);
		PageListData pageData = sysuserService.getSysUserByPage(pote);
		return JsonUtils.obj2json(pageData);
	}
	/**
	*显示用户表新增页面
	**/
	@RequestMapping(value = "/sys/SysUser/initAddSysUserPage", method = { RequestMethod.GET })
	public String initAddSysUserPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/user/editsysuser";
	}
	/**
	*显示用户表修改页面
	**/
	@RequestMapping(value = "/sys/SysUser/initEditSysUserPage", method = { RequestMethod.GET })
	public String initEditSysUserPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/user/editsysuser";
	}
	/**
	*显示用户表详细页面
	**/
	@RequestMapping(value = "/sys/SysUser/initDetailSysUserPage", method = { RequestMethod.GET })
	public String initDetailSysUserPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/user/detailsysuser";
	}
	/**
	*保存新增/修改用户表
	**/
	@RequestMapping(value = "/sys/SysUser/saveAddEditSysUser", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String saveAddEditSysUser(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			SysUser edit = (SysUser) getEntityBean(request,SysUser.class);
			sysuserService.saveAddEditSysUser(edit);
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
	*删除用户表
	**/
	@RequestMapping(value = "/sys/SysUser/delSysUser", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String delSysUser(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			String delRows=request.getParameter("delRows");
			List de=JsonUtils.json2list(delRows, SysUser.class);
			sysuserService.delSysUserById(de);
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
	*根据主键查询用户表
	**/
	@RequestMapping(value = "/sys/SysUser/getSysUserById", method =RequestMethod.GET,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysUserById(
			HttpServletRequest request, 
			HttpServletResponse response){
		String id = request.getParameter("id");
		SysUser bean = sysuserService.getSysUserById(id);
		return JsonUtils.obj2json(bean);
	}
	/**
	 * 用户角色管理页面
	 */
	@RequestMapping(value = "/sys/SysUser/initSysUserRoleManagePage", method = { RequestMethod.GET })
	public String initSysUserRoleManagePage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/user/listsysuserrole";
	}
	/**
	 * 用户数据权限管理页面
	 */
	@RequestMapping(value = "/sys/SysUser/initSysUserDataAccessManagePage", method = { RequestMethod.GET })
	public String initSysUserDataAccessManagePage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/user/listsysuserdataaccess";
	}
	/**
	 * 用户角色列表新增修改
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/SysUser/saveAddEditSysUserRoleList", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String saveAddEditSysUserRoleList(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			String insertRows = request.getParameter("insertRows");
			String updateRows = request.getParameter("updateRows");
			List<SysUserRole> insert=null;
			List<SysUserRole> update=null;
			if(!StringUtils.isNull(insertRows)){
				insert = JsonUtils.json2list(insertRows, SysUserRole.class);
			}
			if(!StringUtils.isNull(updateRows)){
				update = JsonUtils.json2list(updateRows, SysUserRole.class);
			}
			sysuserroleservice.saveSysUserRole(insert, update);
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
	 * 用户角色表分页查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/SysUser/getSysUserRoleByPage", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysUserRoleByPage(
			HttpServletRequest request, 
			HttpServletResponse response){
		String filter = request.getParameter("filter");
		Map filterMap = JsonUtils.json2map(filter);
		String sortfield=request.getParameter("sortfield");
		Page p= super.getPage(request);
		SysUserRoleExample pote= new SysUserRoleExample();
		if(null!=filterMap){
			pote.createCriteria();
			pote.integratedQuery(filterMap);
		}
		
		if(!StringUtils.isNull(sortfield)){
			pote.setOrderByClause(sortfield);
		}
		pote.setPage(p);
		PageListData pageData = sysuserroleservice.getSysUserRoleByPage(pote);
		return JsonUtils.obj2json(pageData);
	}
	/**
	*删除用户角色表
	**/
	@RequestMapping(value = "/sys/SysUser/delSysUserRole", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String delSysUserRole(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			String delRows=request.getParameter("delRows");
			List de=JsonUtils.json2list(delRows, SysUserRole.class);
			sysuserroleservice.delSysUserRoleById(de);
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
	 * 保存用户数据权限
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/SysUser/saveAddEditSysUserDataAccessList", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String saveAddEditSysUserDataAccessList(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			String insertRows = request.getParameter("insertRows");
			String updateRows = request.getParameter("updateRows");
			List<SysUserDataAccess> insert=null;
			List<SysUserDataAccess> update=null;
			if(!StringUtils.isNull(insertRows)){
				insert = JsonUtils.json2list(insertRows, SysUserDataAccess.class);
			}
			if(!StringUtils.isNull(updateRows)){
				update = JsonUtils.json2list(updateRows, SysUserDataAccess.class);
			}
			sysuserdataaccessservice.saveSysUserDataAccess(insert, update);
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
	 * 用户数据权限表分页查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/SysUser/getSysUserDataAccessByPage", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysUserDataAccessByPage(
			HttpServletRequest request, 
			HttpServletResponse response){
		String filter = request.getParameter("filter");
		Map filterMap = JsonUtils.json2map(filter);
		String sortfield=request.getParameter("sortfield");
		Page p= super.getPage(request);
		SysUserDataAccessExample pote= new SysUserDataAccessExample();
		if(null!=filterMap){
			pote.createCriteria();
			pote.integratedQuery(filterMap);
		}
		
		if(!StringUtils.isNull(sortfield)){
			pote.setOrderByClause(sortfield);
		}
		pote.setPage(p);
		PageListData pageData = sysuserdataaccessservice.getSysUserDataAccessByPage(pote);
		return JsonUtils.obj2json(pageData);
	}
	/**
	*删除用户数据权限表
	**/
	@RequestMapping(value = "/sys/SysUser/delSysUserDataAccess", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String delSysUserDataAccess(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			String delRows=request.getParameter("delRows");
			List de=JsonUtils.json2list(delRows, SysUserDataAccess.class);
			sysuserdataaccessservice.delSysUserDataAccessById(de);
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
	@RequestMapping(value = "/sys/SysUser/updatePwd", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String updatePwd(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			String oldpwd=request.getParameter("oldpwd");
			String newpwd=request.getParameter("newpwd");
			String configpwd=request.getParameter("configpwd");
			sysuserService.updatePwd(oldpwd,newpwd,configpwd);
			rd.setCode(ResultData.code_success);
			rd.setName("修改成功");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rd.setCode(ResultData.code_error);
			rd.setName("修改失败<br>"+e.getMessage());
		}
		return JsonUtils.obj2json(rd);
	}
	/**
	 * 密码重置
	 * @param userCode
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/SysUser/resetPwd", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String resetPwd(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			String resetRows=request.getParameter("resetRows");
			List reset=JsonUtils.json2list(resetRows, SysUser.class);
			sysuserService.resetPwd(reset);
			rd.setCode(ResultData.code_success);
			rd.setName("重置成功");
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rd.setCode(ResultData.code_error);
			rd.setName("重置失败<br>"+e.getMessage());
		}
		return JsonUtils.obj2json(rd);
	}
}