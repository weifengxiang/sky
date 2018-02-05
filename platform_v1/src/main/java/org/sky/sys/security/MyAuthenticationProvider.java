package org.sky.sys.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.sky.sys.model.SysUser;
import org.sky.sys.model.SysUserRole;
import org.sky.sys.model.SysUserRoleExample;
import org.sky.sys.service.SysOrganService;
import org.sky.sys.service.SysUserRoleService;
import org.sky.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider implements Serializable  {
	@Autowired
	private SysUserService userService;
	@Autowired
	private SysUserRoleService userRoleService;
	@Autowired
	private SysOrganService organService;
	/**
	 * 用户验证
	 */
	@Override
	protected void additionalAuthenticationChecks(UserDetails user,	UsernamePasswordAuthenticationToken token)throws AuthenticationException {
		String rawPassword = (String) token.getCredentials(); //登录密码 
		String passwd = user.getPassword(); //数据库中密码
		//验证密码
		if (!passwd.equals(rawPassword)) {  
			throw new AuthenticationServiceException("用户密码不正确");  
		}
		MyUserDetails mu =(MyUserDetails) user;
		mu.setLoginDate(new Date());//设置登录时间
	}

	/**
	 * 用户初始化
	 */
	@Override
	protected UserDetails retrieveUser(String loginCode,UsernamePasswordAuthenticationToken token)	throws AuthenticationException {
		UserDetails user = null; 
		SysUser sysuser = userService.getSysUserByCode(loginCode);
		if(null==sysuser){
			throw new AuthenticationServiceException("登录名不存在");  
		}else if("1".equals(sysuser.getStatus())){
			throw new AuthenticationServiceException("用户被锁定");  
		}
		token.setDetails(sysuser);
		//用户角色
    	ArrayList<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
    	List<SysUserRole> roleslist = userRoleService.getSysUserRoleByUserCode(loginCode);
    	for(final SysUserRole role:roleslist){
    		authorities.add(new GrantedAuthority() {
    			private static final long serialVersionUID = 1L;
    			@Override
    			public String getAuthority() {
    				return role.getRoleCode();
    			}
    		});
    	}
    	authorities.add(new GrantedAuthority() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				return "ROLE_USER";
			}
		});
		user=new MyUserDetails(loginCode,sysuser.getId(),sysuser.getName(),sysuser.getPassword(),sysuser.getOrganCode(),
				"","","",null,authorities,"",true,true,true,true);
	    return user;  
	}
 

}
