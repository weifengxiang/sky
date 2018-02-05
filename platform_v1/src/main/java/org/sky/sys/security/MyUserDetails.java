/**   
* @Title: MyUserDetails.java 
* @Package org.sky.sys.security 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2016-12-8 上午9:15:57 
* @version V1.0   
*/


package org.sky.sys.security;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/** 
 * @ClassName: MyUserDetails 
 * @Description: TODO(自定义UserDetails并扩展) 
 * @author 位峰祥 
 * @date 2016-12-8 上午9:15:57 
 * @version V1.0 
 */
public class MyUserDetails implements UserDetails {
	
	private String loginid;
	
	private String userid;
	
	private String username;
	
	private String password;
	
	private String organcode;
	
	private String organname;
	
	private String depcode;
	
	private String depname;
	
	private Date loginDate;
	//用户角色集合
	private Collection authorities;
	
	private String loginIp;
	//-----------------------
	private Boolean accountNonExpired;
	
	private Boolean enabled;
	
	private Boolean credentialsNonExpired;
	
	private Boolean accountNonLocked;

	public MyUserDetails(
			String loginid,String userid,String username,String password,String organcode,
			String organname,String depcode,String depname,Date loginDate,Collection authorities,
			String loginIp,
			Boolean accountNonExpired,Boolean accountNonLocked,
			Boolean credentialsNonExpired,Boolean enabled){
		this.loginid=loginid;
		this.userid=userid;
		this.username=username;
		this.password=password;
		this.organcode=organcode;
		this.organname=organname;
		this.depcode=depcode;
		this.depname=depname;
		this.loginDate=loginDate;
		this.authorities=authorities;
		this.loginIp=loginIp;
		this.accountNonExpired=accountNonExpired;
		this.accountNonLocked=accountNonLocked;
		this.credentialsNonExpired=credentialsNonExpired;
		this.enabled=enabled;
		
	}
	public MyUserDetails(){
		
	}
	
	/**
	 * @return the loginid
	 */
	public String getLoginid() {
		return loginid;
	}
	/**
	 * @param loginid the loginid to set
	 */
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	/**
	 * @return the authorities
	 */
	public Collection getAuthorities() {
		return authorities;
	}

	/**
	 * @param authorities the authorities to set
	 */
	public void setAuthorities(Collection authorities) {
		this.authorities = authorities;
	}

	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the organcode
	 */
	public String getOrgancode() {
		return organcode;
	}

	/**
	 * @param organcode the organcode to set
	 */
	public void setOrgancode(String organcode) {
		this.organcode = organcode;
	}

	/**
	 * @return the organname
	 */
	public String getOrganname() {
		return organname;
	}

	/**
	 * @param organname the organname to set
	 */
	public void setOrganname(String organname) {
		this.organname = organname;
	}

	/**
	 * @return the depcode
	 */
	public String getDepcode() {
		return depcode;
	}

	/**
	 * @param depcode the depcode to set
	 */
	public void setDepcode(String depcode) {
		this.depcode = depcode;
	}

	/**
	 * @return the depname
	 */
	public String getDepname() {
		return depname;
	}

	/**
	 * @param depname the depname to set
	 */
	public void setDepname(String depname) {
		this.depname = depname;
	}

	/**
	 * @return the loginDate
	 */
	public Date getLoginDate() {
		return loginDate;
	}

	/**
	 * @param loginDate the loginDate to set
	 */
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	/**
	 * @return the loginIp
	 */
	public String getLoginIp() {
		return loginIp;
	}
	/**
	 * @param loginIp the loginIp to set
	 */
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 */
	/**
	 * @return the accountNonExpired
	 */
	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}
	/**
	 * @param accountNonExpired the accountNonExpired to set
	 */
	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}
	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	/**
	 * @return the credentialsNonExpired
	 */
	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}
	/**
	 * @param credentialsNonExpired the credentialsNonExpired to set
	 */
	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}
	/**
	 * @return the accountNonLocked
	 */
	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}
	/**
	 * @param accountNonLocked the accountNonLocked to set
	 */
	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 */
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return this.accountNonExpired;
	}
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 */
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return this.accountNonLocked;
	}
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return this.credentialsNonExpired;
	}
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.enabled;
	}
	@Override
	public boolean equals(Object o){
		if(!(o instanceof MyUserDetails)){
			return false;
		}else{
			MyUserDetails mu = (MyUserDetails)o;
			if(mu.getUserid().equals(this.userid)){
				return true;
			}else{
				return false;
			}
		}
	}

	public int hashCode() {
		int code = 9792;
		if (this.getPassword() != null) {
			code = code * (this.getPassword().hashCode() % 7);
		}
		if (this.getUsername() != null) {
			code = code * (this.getUsername().hashCode() % 7);
		}
		if (this.isAccountNonExpired()) {
			code = code * -2;
		}
		if (this.isAccountNonLocked()) {
			code = code * -3;
		}
		if (this.isCredentialsNonExpired()) {
			code = code * -5;
		}
		if (this.isEnabled()) {
			code = code * -7;
		}
		return code;
	}

}
