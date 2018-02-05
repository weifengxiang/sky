package org.sky.sys.security;

import java.sql.Clob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.sky.sys.service.SysRoleOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

//1 加载资源与权限的对应关系  

/**
 * 该过滤器的主要作用就是通过spring著名的IoC生成securityMetadataSource。
 * securityMetadataSource相当于本包中自定义的MyInvocationSecurityMetadataSourceService。
 * 该MyInvocationSecurityMetadataSourceService的作用提从数据库提取权限和资源，装配到HashMap中，
 * 供Spring Security使用，用于权限校验。
 * 
 */
public class MySecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {
	@Autowired
	private  SysRoleOperationService roleOperSercie;

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	private RequestMatcher pathMatcher;

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return new ArrayList<ConfigAttribute>();
	}

	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}
	/**
	 * 清除缓存数据
	 */
	public static void clearResource(){
		resourceMap=null;
	}
	// 加载所有资源与权限的关系
	public  void loadResourceDefine() {
		if (resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			Map<String,List<String>> resources = roleOperSercie.getRoleByOpeUrl();
			Set<String> keySet = resources.keySet();
			for(String key:keySet){
				List<String> roleList = resources.get(key);
				Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
				for(String roleCode:roleList){
					ConfigAttribute configAttribute = new SecurityConfig(roleCode);
					configAttributes.add(configAttribute);
				}
				resourceMap.put("/"+key, configAttributes);
			}
		}

	}

	// 返回所请求资源所需要的权限
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		Iterator<String> it = resourceMap.keySet().iterator();
		while (it.hasNext()) {
			String resURL = it.next();
			Iterator<String> ite = resourceMap.keySet().iterator();
			pathMatcher = new AntPathRequestMatcher(resURL);
			if (pathMatcher.matches(((FilterInvocation) object).getRequest())) {
				Collection<ConfigAttribute> returnCollection = resourceMap
						.get(resURL);
				return returnCollection;
			}
		}
		return null;
	}

}