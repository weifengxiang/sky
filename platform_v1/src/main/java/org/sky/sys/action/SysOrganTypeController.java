package org.sky.sys.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.sky.sys.action.BaseController;
import org.sky.sys.exception.ServiceException;
import org.sky.sys.model.SysOrganType;
import org.sky.sys.model.SysOrganTypeExample;
import org.sky.sys.model.SysOrganTypeExample.Criteria;
import org.sky.sys.service.SysOrganTypeService;
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
public class SysOrganTypeController extends BaseController{
	@Autowired
	private SysOrganTypeService sysorgantypeService;
	
	public SysOrganTypeController() {
		// TODO Auto-generated constructor stub
	}
	/**
	*显示组织机构类型列表页面
	**/
	@RequestMapping(value = "/sys/SysOrganType/initSysOrganTypeListPage", method = { RequestMethod.GET })
	public String initSysOrganTypeListPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/organ/listsysorgantype";
	}
	/**
	 * 组织机构类型分页查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/SysOrganType/getSysOrganTypeByPage", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysOrganTypeByPage(
			HttpServletRequest request, 
			HttpServletResponse response){
		String filter = request.getParameter("filter");
		Map filterMap = JsonUtils.json2map(filter);
		String sortfield=request.getParameter("sortfield");
		Page p= super.getPage(request);
		SysOrganTypeExample pote= new SysOrganTypeExample();
		if(null!=filterMap){
			pote.createCriteria();
			pote.integratedQuery(filterMap);
		}
		
		if(!StringUtils.isNull(sortfield)){
			pote.setOrderByClause(sortfield);
		}
		pote.setPage(p);
		PageListData pageData = sysorgantypeService.getSysOrganTypeByPage(pote);
		return JsonUtils.obj2json(pageData);
	}
	/**
	*显示组织机构类型新增页面
	**/
	@RequestMapping(value = "/sys/SysOrganType/initAddSysOrganTypePage", method = { RequestMethod.GET })
	public String initAddSysOrganTypePage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/organ/editsysorgantype";
	}
	/**
	*显示组织机构类型修改页面
	**/
	@RequestMapping(value = "/sys/SysOrganType/initEditSysOrganTypePage", method = { RequestMethod.GET })
	public String initEditSysOrganTypePage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/organ/editsysorgantype";
	}
	/**
	*显示组织机构类型详细页面
	**/
	@RequestMapping(value = "/sys/SysOrganType/initDetailSysOrganTypePage", method = { RequestMethod.GET })
	public String initDetailSysOrganTypePage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/organ/detailsysorgantype";
	}
	/**
	*保存新增/修改组织机构类型
	**/
	@RequestMapping(value = "/sys/SysOrganType/saveAddEditSysOrganType", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String saveAddEditSysOrganType(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			SysOrganType edit = (SysOrganType) getEntityBean(request,SysOrganType.class);
			sysorgantypeService.saveAddEditSysOrganType(edit);
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
	*删除组织机构类型
	**/
	@RequestMapping(value = "/sys/SysOrganType/delSysOrganType", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String delSysOrganType(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			String delRows=request.getParameter("delRows");
			List de=JsonUtils.json2list(delRows, SysOrganType.class);
			sysorgantypeService.delSysOrganTypeById(de);
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
	*根据主键查询组织机构类型
	**/
	@RequestMapping(value = "/sys/SysOrganType/getSysOrganTypeById", method =RequestMethod.GET,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysOrganTypeById(
			HttpServletRequest request, 
			HttpServletResponse response){
		String id = request.getParameter("id");
		SysOrganType bean = sysorgantypeService.getSysOrganTypeById(id);
		return JsonUtils.obj2json(bean);
	}
}