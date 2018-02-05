package org.sky.sys.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sky.sys.model.SysMenu;
import org.sky.sys.model.SysOperation;
import org.sky.sys.service.SysFunctionService;
import org.sky.sys.utils.JsonUtils;
import org.sky.sys.utils.ResultData;
import org.sky.sys.utils.StringUtils;
import org.sky.sys.utils.TreeStru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SysFunctionController extends BaseController {
	@Autowired
	private SysFunctionService functionService;
	
	/**
	*显示功能管理
	**/
	@RequestMapping(value = "/sys/function/initSysFunctionManangePage", method = { RequestMethod.GET })
	public String initSysFunctionManangePage(HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/function/sysfunmanage";
	}
	/**
	 * 功能管理树数据查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/function/getSysFunctionTree", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody List<TreeStru> getSysFunctionTree(HttpServletRequest request, 
			HttpServletResponse response){
		String data= request.getParameter("data");
		Map dataMap=null;
		if(!StringUtils.isNull(data)){
			dataMap = JsonUtils.json2map(data);
		}
		return functionService.getFunctionTree(dataMap);
	}
	/**
	 * 打开菜单添加页面
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/function/initAddSysMenuPage", method = { RequestMethod.GET })
	public String initAddSysMenu(HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/function/editsysmenu";
	}
	/**
	 * 打开菜单修改页面
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/function/initEditSysMenuPage", method = { RequestMethod.GET })
	public String initEditSysMenu(HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/function/editsysmenu";
	}
	/**
	*根据主键查询菜单表
	**/
	@RequestMapping(value = "/sys/function/getSysMenuById", method =RequestMethod.GET,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysMenuById(
			HttpServletRequest request, 
			HttpServletResponse response){
		String id = request.getParameter("id");
		SysMenu bean = functionService.getSysMenuById(id);
		return JsonUtils.obj2json(bean);
	}
	/**
	*保存添加/修改菜单表
	**/
	@RequestMapping(value = "/sys/function/saveAddEditSysMenu", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String saveAddEditSysMenu(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			SysMenu edit = (SysMenu) getEntityBean(request,SysMenu.class);
			functionService.saveAddEditSysMenu(edit);
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
	 * 打开操作添加页面
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/function/initAddSysOperationPage", method = { RequestMethod.GET })
	public String initAddSysOperation(HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/function/editsysoperation";
	}
	/**
	 * 打开操作修改页面
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/function/initEditSysOperationPage", method = { RequestMethod.GET })
	public String initEditSysOperation(HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/function/editsysoperation";
	}
	/**
	*根据主键查询操作表
	**/
	@RequestMapping(value = "/sys/function/getSysOperationById", method =RequestMethod.GET,produces = "application/json;charset=UTF-8")
	public @ResponseBody String getSysOperationById(
			HttpServletRequest request, 
			HttpServletResponse response){
		String id = request.getParameter("id");
		SysOperation bean = functionService.getSysOperationById(id);
		return JsonUtils.obj2json(bean);
	}
	/**
	*保存添加/修改操作表
	**/
	@RequestMapping(value = "/sys/function/saveAddEditSysOperation", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String saveEditSysOperation(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			SysOperation edit = (SysOperation) getEntityBean(request,SysOperation.class);
			functionService.saveAddEditSysOperation(edit);
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
	 * 删除菜单
	 */
	@RequestMapping(value = "/sys/function/delSysMenu", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody ResultData delSysMenu(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			String data=request.getParameter("data");
			Map pm = JsonUtils.json2map(data);
			String code = (String)pm.get("code");
			functionService.delSysMenuByCode(code);
			rd.setCode(ResultData.code_success);
			rd.setName("菜单删除成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rd.setCode(ResultData.code_error);
			rd.setName("菜单删除失败<br>"+e.getMessage());
		}
		return rd;
	}
	/**
	 * 删除操作
	 */
	@RequestMapping(value = "/sys/function/delSysOperation", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody ResultData delSysOperation(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			String data=request.getParameter("data");
			Map pm = JsonUtils.json2map(data);
			String code = (String)pm.get("code");
			functionService.delSysOperationByCode(code);
			rd.setCode(ResultData.code_success);
			rd.setName("操作删除成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rd.setCode(ResultData.code_error);
			rd.setName("操作删除失败<br>"+e.getMessage());
		}
		return rd;
	}
}
