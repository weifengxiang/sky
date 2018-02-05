package org.sky.sys.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.sky.sys.action.BaseController;
import org.sky.sys.exception.ServiceException;
import org.sky.sys.model.SysOrgan;
import org.sky.sys.model.SysOrganExample;
import org.sky.sys.model.SysOrganExample.Criteria;
import org.sky.sys.service.SysOrganService;
import org.sky.sys.utils.JsonUtils;
import org.sky.sys.utils.Page;
import org.sky.sys.utils.PageListData;
import org.sky.sys.utils.ResultData;
import org.sky.sys.utils.StringUtils;
import org.sky.sys.utils.TreeStru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
@Controller
public class SysOrganController extends BaseController{
	@Autowired
	private SysOrganService sysorganService;
	
	public SysOrganController() {
		// TODO Auto-generated constructor stub
	}
	/**
	*显示组织机构管理页面
	**/
	@RequestMapping(value = "/sys/SysOrgan/initSysOrganManagePage", method = { RequestMethod.GET })
	public String initSysOrganListPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/organ/sysorganmanage";
	}
	@RequestMapping(value = "/sys/SysOrgan/getSysOrganTree", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody List<TreeStru> getSysOrganTree(HttpServletRequest request, 
			HttpServletResponse response){
		String data= request.getParameter("data");
		Map dataMap=null;
		if(!StringUtils.isNull(data)){
			dataMap = JsonUtils.json2map(data);
		}
		return sysorganService.getSysOrganTree(dataMap);
	}
	/**
	 * 组织机构表分页查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/SysOrgan/getSysOrganByPage", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysOrganByPage(
			HttpServletRequest request, 
			HttpServletResponse response){
		String filter = request.getParameter("filter");
		Map filterMap = JsonUtils.json2map(filter);
		String sortfield=request.getParameter("sortfield");
		Page p= super.getPage(request);
		SysOrganExample pote= new SysOrganExample();
		if(null!=filterMap){
			pote.createCriteria();
			pote.integratedQuery(filterMap);
		}
		
		if(!StringUtils.isNull(sortfield)){
			pote.setOrderByClause(sortfield);
		}
		pote.setPage(p);
		PageListData pageData = sysorganService.getSysOrganByPage(pote);
		return JsonUtils.obj2json(pageData);
	}
	/**
	*显示组织机构表新增页面
	**/
	@RequestMapping(value = "/sys/SysOrgan/initAddSysOrganPage", method = { RequestMethod.GET })
	public String initAddSysOrganPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/organ/editsysorgan";
	}
	/**
	*显示组织机构表修改页面
	**/
	@RequestMapping(value = "/sys/SysOrgan/initEditSysOrganPage", method = { RequestMethod.GET })
	public String initEditSysOrganPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/organ/editsysorgan";
	}
	/**
	*显示组织机构表详细页面
	**/
	@RequestMapping(value = "/sys/SysOrgan/initDetailSysOrganPage", method = { RequestMethod.GET })
	public String initDetailSysOrganPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/organ/detailsysorgan";
	}
	/**
	*保存新增/修改组织机构表
	**/
	@RequestMapping(value = "/sys/SysOrgan/saveAddEditSysOrgan", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String saveAddEditSysOrgan(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			SysOrgan edit = (SysOrgan) getEntityBean(request,SysOrgan.class);
			sysorganService.saveAddEditSysOrgan(edit);
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
	*删除组织机构表
	**/
	@RequestMapping(value = "/sys/SysOrgan/delSysOrgan", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String delSysOrgan(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			String data=request.getParameter("data");
			SysOrgan so=JsonUtils.json2pojo(data, SysOrgan.class);
			sysorganService.delSysOrganById(so);
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
	*根据主键查询组织机构表
	**/
	@RequestMapping(value = "/sys/SysOrgan/getSysOrganById", method =RequestMethod.GET,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysOrganById(
			HttpServletRequest request, 
			HttpServletResponse response){
		String id = request.getParameter("id");
		SysOrgan bean = sysorganService.getSysOrganById(id);
		return JsonUtils.obj2json(bean);
	}
}