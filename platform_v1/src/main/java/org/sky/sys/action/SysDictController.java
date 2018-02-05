package org.sky.sys.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.sky.sys.action.BaseController;
import org.sky.sys.exception.ServiceException;
import org.sky.sys.model.SysDict;
import org.sky.sys.model.SysDictExample;
import org.sky.sys.model.SysDictItem;
import org.sky.sys.model.SysDictItemExample;
import org.sky.sys.model.SysDictExample.Criteria;
import org.sky.sys.service.SysDictItemService;
import org.sky.sys.service.SysDictService;
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
public class SysDictController extends BaseController{
	@Autowired
	private SysDictService sysdictService;
	@Autowired
	private SysDictItemService sysdictitemService;
	
	public SysDictController() {
		// TODO Auto-generated constructor stub
	}
	/**
	*显示字典表列表页面
	**/
	@RequestMapping(value = "/sys/SysDict/initSysDictListPage", method = { RequestMethod.GET })
	public String initSysDictListPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/dict/listsysdict";
	}
	/**
	 * 字典表分页查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/SysDict/getSysDictByPage", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysDictByPage(
			HttpServletRequest request, 
			HttpServletResponse response){
		String filter = request.getParameter("filter");
		Map filterMap = JsonUtils.json2map(filter);
		String sortfield=request.getParameter("sortfield");
		Page p= super.getPage(request);
		SysDictExample pote= new SysDictExample();
		if(null!=filterMap){
			pote.createCriteria();
			pote.integratedQuery(filterMap);
		}
		
		if(!StringUtils.isNull(sortfield)){
			pote.setOrderByClause(sortfield);
		}
		pote.setPage(p);
		PageListData pageData = sysdictService.getSysDictByPage(pote);
		return JsonUtils.obj2json(pageData);
	}
	/**
	*显示字典表新增页面
	**/
	@RequestMapping(value = "/sys/SysDict/initAddSysDictPage", method = { RequestMethod.GET })
	public String initAddSysDictPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/dict/editsysdict";
	}
	/**
	*显示字典表修改页面
	**/
	@RequestMapping(value = "/sys/SysDict/initEditSysDictPage", method = { RequestMethod.GET })
	public String initEditSysDictPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/dict/editsysdict";
	}
	/**
	*显示字典表详细页面
	**/
	@RequestMapping(value = "/sys/SysDict/initDetailSysDictPage", method = { RequestMethod.GET })
	public String initDetailSysDictPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/dict/detailsysdict";
	}
	/**
	*保存新增/修改字典表
	**/
	@RequestMapping(value = "/sys/SysDict/saveAddEditSysDict", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String saveAddEditSysDict(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			SysDict edit = (SysDict) getEntityBean(request,SysDict.class);
			sysdictService.saveAddEditSysDict(edit);
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
	*删除字典表
	**/
	@RequestMapping(value = "/sys/SysDict/delSysDict", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String delSysDict(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			String delRows=request.getParameter("delRows");
			List de=JsonUtils.json2list(delRows, SysDict.class);
			sysdictService.delSysDictById(de);
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
	*根据主键查询字典表
	**/
	@RequestMapping(value = "/sys/SysDict/getSysDictById", method =RequestMethod.GET,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysDictById(
			HttpServletRequest request, 
			HttpServletResponse response){
		String id = request.getParameter("id");
		SysDict bean = sysdictService.getSysDictById(id);
		return JsonUtils.obj2json(bean);
	}
	/**
	 * 字典项表分页查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/SysDict/getSysDictItemByPage", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysDictItemByPage(
			HttpServletRequest request, 
			HttpServletResponse response){
		
		String filter = request.getParameter("filter");
		Map filterMap = JsonUtils.json2map(filter);
		String sortfield=request.getParameter("sortfield");
		Page p= super.getPage(request);
		SysDictItemExample pote= new SysDictItemExample();
		if(null!=filterMap){
			pote.createCriteria();
			pote.integratedQuery(filterMap);
		}
		
		if(!StringUtils.isNull(sortfield)){
			pote.setOrderByClause(sortfield);
		}
		pote.setPage(p);
		PageListData pageData = sysdictitemService.getSysDictItemByPage(pote);
		return JsonUtils.obj2json(pageData);
	}
	/**
	*显示字典项表新增页面
	**/
	@RequestMapping(value = "/sys/SysDict/initAddSysDictItemPage", method = { RequestMethod.GET })
	public String initAddSysDictItemPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/dict/editsysdictitem";
	}
	/**
	*显示字典项表修改页面
	**/
	@RequestMapping(value = "/sys/SysDict/initEditSysDictItemPage", method = { RequestMethod.GET })
	public String initEditSysDictItemPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/dict/editsysdictitem";
	}
	/**
	*显示字典项表详细页面
	**/
	@RequestMapping(value = "/sys/SysDict/initDetailSysDictItemPage", method = { RequestMethod.GET })
	public String initDetailSysDictItemPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/dict/detailsysdictitem";
	}
	/**
	*保存新增/修改字典项表
	**/
	@RequestMapping(value = "/sys/SysDict/saveAddEditSysDictItem", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String saveAddEditSysDictItem(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			SysDictItem edit = (SysDictItem) getEntityBean(request,SysDictItem.class);
			sysdictitemService.saveAddEditSysDictItem(edit);
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
	*删除字典项表
	**/
	@RequestMapping(value = "/sys/SysDict/delSysDictItem", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String delSysDictItem(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			String delRows=request.getParameter("delRows");
			List de=JsonUtils.json2list(delRows, SysDictItem.class);
			sysdictitemService.delSysDictItemById(de);
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
	*根据主键查询字典项表
	**/
	@RequestMapping(value = "/sys/SysDict/getSysDictItemById", method =RequestMethod.GET,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysDictItemById(
			HttpServletRequest request, 
			HttpServletResponse response){
		String id = request.getParameter("id");
		SysDictItem bean = sysdictitemService.getSysDictItemById(id);
		return JsonUtils.obj2json(bean);
	}
}