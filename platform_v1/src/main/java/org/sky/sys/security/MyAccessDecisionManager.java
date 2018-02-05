package org.sky.sys.security;

import java.util.Collection;
import java.util.Iterator;

import org.sky.sys.utils.BspUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class MyAccessDecisionManager implements AccessDecisionManager {
	@Autowired
	private SessionRegistry sessionRegistry;
	@Override
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		//校验Session是否有效 
		FilterInvocation fi = (FilterInvocation)object;
		String sessionId = fi.getHttpRequest().getSession(true).getId();
		SessionInformation si = sessionRegistry.getSessionInformation(sessionId);
		if(null==si||si.isExpired()){
			throw new SessionAuthenticationException("当前登陆用户已失效");
		}
		//未启用权限过滤
		if(!BspUtils.isSecurityFilter()){
			return;
		}
		if (null == configAttributes) {
			return;
		}
		Iterator<ConfigAttribute> cons = configAttributes.iterator();
		while (cons.hasNext()) {
			ConfigAttribute ca = cons.next();
			String needRole = ((SecurityConfig) ca).getAttribute();
			// gra 为用户所被赋予的权限，needRole为访问相应的资源应具有的权限
			for (GrantedAuthority gra : authentication.getAuthorities()) {
				if (needRole.trim().equals(gra.getAuthority().trim())) {
					return;
				}
			}
		}
		 //没有权限    会跳转到登录页面 
		throw new AccessDeniedException("没有权限");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}
}
