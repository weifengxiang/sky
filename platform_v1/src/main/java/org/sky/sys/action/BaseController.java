package org.sky.sys.action;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.sky.sys.utils.BspUtils;
import org.sky.sys.utils.JsonUtils;
import org.sky.sys.utils.Page;
import org.sky.sys.utils.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
/**
 * 
* @Title: BaseController.java 
* @Package org.sky.sys.action 
* @Description: TODO(基础类Controller) 
* @author 位峰祥   
* @date 2016-6-2 下午9:34:30 
* @version V1.0
 */
public class BaseController {

	public BaseController() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 获取类的所有属性
	 * @param list
	 * @param clazz
	 * @return
	 */
	public static List<Field> getAllField(List<Field> list, Class clazz){
		if(!clazz.equals(Object.class)){
			Field[] f=clazz.getDeclaredFields();
			for(int i=0;i<f.length;i++){
				list.add(f[i]);
			}
			getAllField(list,clazz.getSuperclass());
		}
		return list;
	}
	/**
	 * 从请求中获取对象
	 * @param request
	 * @param clazz
	 * @return
	 */
	public Object getEntityBean(HttpServletRequest request,Class clazz){
		List<Field> idField = new ArrayList();
		idField=getAllField(idField, clazz);
		JSONObject js= new JSONObject();
		for (Field field : idField) {
			String name = field.getName();
			String str = request.getParameter(name);
			if(str != null){
				js.put(name, str);
			}
		}
		return JsonUtils.json2pojo(js.toString(), clazz);
	}
	/**
	 * 从请求中获取分页参数
	 * @param request
	 * @return
	 */
	public Page getPage(HttpServletRequest request){
		String page=request.getParameter("page");//当前第几页
		String rows=request.getParameter("rows");//每页显示条数
		Page p= new Page();
		p.setBegin((Integer.parseInt(page)-1)*Integer.parseInt(rows));
		p.setRows(Integer.parseInt(rows));
		return p;
	}
}
