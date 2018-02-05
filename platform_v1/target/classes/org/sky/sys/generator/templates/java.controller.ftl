package ${basePackage}.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.sky.sys.action.BaseController;
import org.sky.sys.exception.ServiceException;
import ${basePackage}.model.${className};
import ${basePackage}.model.${className}Example;
import ${basePackage}.model.${className}Example.Criteria;
import ${basePackage}.service.${className}Service;
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
public class ${className}Controller extends BaseController{
	@Autowired
	private ${className}Service ${className?lower_case}Service;
	
	public ${className}Controller() {
		// TODO Auto-generated constructor stub
	}
	/**
	*显示${comment}列表页面
	**/
	@RequestMapping(value = "/${urlPrefix}/${className}/init${className}ListPage", method = { RequestMethod.GET })
	public String init${className}ListPage(
			HttpServletRequest request, HttpServletResponse response) {
		return "${targetDir}list${className?lower_case}";
	}
	/**
	 * ${comment}分页查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/${urlPrefix}/${className}/get${className}ByPage", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String get${className}ByPage(
			HttpServletRequest request, 
			HttpServletResponse response){
		String filter = request.getParameter("filter");
		Map filterMap = JsonUtils.json2map(filter);
		String sortfield=request.getParameter("sortfield");
		Page p= super.getPage(request);
		${className}Example pote= new ${className}Example();
		if(null!=filterMap){
			pote.createCriteria();
			pote.integratedQuery(filterMap);
		}
		if(!StringUtils.isNull(sortfield)){
			pote.setOrderByClause(sortfield);
		}
		pote.setPage(p);
		PageListData pageData = ${className?lower_case}Service.get${className}ByPage(pote);
		return JsonUtils.obj2json(pageData);
	}
	/**
	*显示${comment}新增页面
	**/
	@RequestMapping(value = "/${urlPrefix}/${className}/initAdd${className}Page", method = { RequestMethod.GET })
	public String initAdd${className}Page(
			HttpServletRequest request, HttpServletResponse response) {
		return "${targetDir}edit${className?lower_case}";
	}
	/**
	*显示${comment}修改页面
	**/
	@RequestMapping(value = "/${urlPrefix}/${className}/initEdit${className}Page", method = { RequestMethod.GET })
	public String initEdit${className}Page(
			HttpServletRequest request, HttpServletResponse response) {
		return "${targetDir}edit${className?lower_case}";
	}
	/**
	*显示${comment}详细页面
	**/
	@RequestMapping(value = "/${urlPrefix}/${className}/initDetail${className}Page", method = { RequestMethod.GET })
	public String initDetail${className}Page(
			HttpServletRequest request, HttpServletResponse response) {
		return "${targetDir}detail${className?lower_case}";
	}
	/**
	*保存新增/修改${comment}
	**/
	@RequestMapping(value = "/${urlPrefix}/${className}/saveAddEdit${className}", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String saveAddEdit${className}(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			${className} edit = (${className}) getEntityBean(request,${className}.class);
			${className?lower_case}Service.saveAddEdit${className}(edit);
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
	*删除${comment}
	**/
	@RequestMapping(value = "/${urlPrefix}/${className}/del${className}", method =RequestMethod.POST,produces = "application/json;charset=UTF-8")
	public @ResponseBody String del${className}(
			HttpServletRequest request, 
			HttpServletResponse response){
		ResultData rd= new ResultData();
		try {
			String delRows=request.getParameter("delRows");
			List de=JsonUtils.json2list(delRows, ${className}.class);
			${className?lower_case}Service.del${className}ById(de);
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
	*根据主键查询${comment}
	**/
	@RequestMapping(value = "/${urlPrefix}/${className}/get${className}ById", method =RequestMethod.GET,produces = "application/json;charset=UTF-8")
	public @ResponseBody String get${className}ById(
			HttpServletRequest request, 
			HttpServletResponse response){
		String id = request.getParameter("id");
		${className} bean = ${className?lower_case}Service.get${className}ById(id);
		return JsonUtils.obj2json(bean);
	}
}