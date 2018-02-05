package org.sky.sys.action;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sky.sys.model.SysDictItem;
import org.sky.sys.service.SysCommonService;
import org.sky.sys.utils.ApplicationCached;
import org.sky.sys.utils.JsonUtils;
import org.sky.sys.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
* 
* @Title: CommonController.java 
* @Package org.sky.sys.action 
* @Description: TODO(通用查询Controller) 
* @author 位峰祥   
* @date 2016-6-2 下午9:32:08 
* @version V1.0
*/
@Controller
public class SysCommonController {
	@Autowired
	private SysCommonService syscommonservice;
	
	public SysCommonController() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * combogrid数据查询默认分页
	 */

	@RequestMapping(value = "/sys/common/queryCombogridData", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String queryCombogridData(HttpServletRequest request, 
			HttpServletResponse response){
		Map param= new HashMap();
		String tablename = request.getParameter("tablename");
		String fields = request.getParameter("fields");
		String qColumn = request.getParameter("qColumn");
		String filter = request.getParameter("filter"); 
		String value = request.getParameter("q");
		String sort = request.getParameter("sort");
		String page=request.getParameter("page");//当前第几页
		String rows=request.getParameter("rows");//每页显示条数
		if(StringUtils.isNull(page)){
			page="1";
		}
		if(StringUtils.isNull(rows)){
			rows="20";
		}
		int _begin=(Integer.parseInt(page)-1)*Integer.parseInt(rows);
		int _rows=Integer.parseInt(rows);
		param.put("tablename", tablename);
		param.put("fields", fields);
		String filterValue="";
		if(!StringUtils.isNull(qColumn)&&!StringUtils.isNull(value)&&!"undefined".equals(value)){
			filterValue=" and "+qColumn+" like '"+value+"%'";
		}
		if(!StringUtils.isNull(filter)&&!StringUtils.isNull(filter)&&!"undefined".equals(filter)){
			Map f=JsonUtils.json2map(filter);
			for (Object key : f.keySet()) {  
				filterValue=filterValue+" and "+ (key+"").split("@")[0]+" "+(key+"").split("@")[1]+" '"+f.get(key)+"'";
			}  
		}
		if(!StringUtils.isNull(filterValue)&&!StringUtils.isNull(filterValue)){
			param.put("filter", filterValue);
		}
		if(!StringUtils.isNull(sort)){
			param.put("sort", sort);
		}
		param.put("begin", _begin);
		param.put("rows", _rows);
		List<Map> list = syscommonservice.queryComboData(param);
		String data = JsonUtils.obj2json(list);
		return data;
	}
	/**
	 * 通用下拉
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/common/queryComboBoxData", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String queryComboBoxData(HttpServletRequest request, 
			HttpServletResponse response){
		Map param= new HashMap();
		String tablename = request.getParameter("tablename");
		String fields = request.getParameter("fields");
		String qColumn = request.getParameter("qColumn");
		String filter = request.getParameter("filter"); 
		String value = request.getParameter("q");
		String sort = request.getParameter("sort");
		param.put("tablename", tablename);
		param.put("fields", fields);
		String filterValue="";
		if(!StringUtils.isNull(qColumn)&&!StringUtils.isNull(value)&&!"undefined".equals(value)){
			filterValue=" and "+qColumn+" like '"+value+"%'";
		}
		if(!StringUtils.isNull(filter)&&!StringUtils.isNull(filter)&&!"undefined".equals(filter)){
			Map f=JsonUtils.json2map(filter);
			for (Object key : f.keySet()) {  
				filterValue=filterValue+" and "+ (key+"").split("@")[0]+" "+(key+"").split("@")[1]+" '"+f.get(key)+"'";
			}  
		}
		if(!StringUtils.isNull(filterValue)&&!StringUtils.isNull(filterValue)){
			param.put("filter", filterValue);
		}
		if(!StringUtils.isNull(sort)){
			param.put("sort", sort);
		}
		List<Map> list = syscommonservice.queryComboData(param);
		String data = JsonUtils.obj2json(list);
		return data;
	}
	/**
	 * 简单根据字典类型通用combobox字典数据编号名称
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/common/simpQueryComboBoxDictItem", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody List<SysDictItem> simpQueryComboBoxDictItem(HttpServletRequest request, 
			HttpServletResponse response){
		String filter=request.getParameter("filter");
		Map filterMap = JsonUtils.json2map(filter);
		String dicType=(String)filterMap.get("dicType");
		List<SysDictItem> list = ApplicationCached.getDictItemByDicType(dicType);
		return list;
	}
	/**
	 * 通用帮助
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sys/common/help/{name}", method = { RequestMethod.GET })
	public String commonHelp(
			@PathVariable String name,
			HttpServletRequest request, HttpServletResponse response) {
		return "jsp/sys/help/"+name+"help";
	} 
}
