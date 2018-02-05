package org.sky.sys.service;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.sky.sys.client.SysCommonMapper;
import org.sky.sys.client.SysUserDataAccessMapper;
import org.sky.sys.client.SysUserMapper;
import org.sky.sys.client.SysUserRoleMapper;
import org.sky.sys.exception.ServiceException;
import org.sky.sys.model.SysUser;
import org.sky.sys.model.SysUserDataAccessExample;
import org.sky.sys.model.SysUserExample;
import org.sky.sys.model.SysUserRoleExample;
import org.sky.sys.utils.PageListData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sky.sys.utils.BspUtils;
import org.sky.sys.utils.CommonUtils;
import org.sky.sys.utils.MD5Utils;
import org.sky.sys.utils.StringUtils;
@Service
public class SysUserService {
	private final Logger logger=Logger.getLogger(SysUserService.class);
	@Autowired
	private SysUserMapper sysusermapper;
	@Autowired
	private SysCommonMapper syscommonmapper;
	@Autowired
	private SysUserRoleMapper sysuserrolemapper;
	@Autowired
	private SysUserDataAccessMapper sysuserdataaccessmapper;
	/**
	*分页查询
	**/
	public PageListData getSysUserByPage(SysUserExample ep){
		long totalCount = sysusermapper.countByExample(ep);
		List list = sysusermapper.selectByExample(ep);
		PageListData pld = new PageListData();
		pld.setTotal(totalCount);
		pld.setRows(list);
		return pld;
	}
	/**
	*保存列表新增及修改
	**/
	@Transactional
	public void saveSysUser(List<SysUser> addlist,
			List<SysUser> updatelist) throws ServiceException{
		try{
			if(null!=addlist&&addlist.size()>0){
				for(SysUser add:addlist){
					sysusermapper.insertSelective(add);
				}
			}
			if(null!=updatelist&&updatelist.size()>0){
				for(SysUser update:updatelist){
					sysusermapper.updateByPrimaryKeySelective(update);
				}
			}
		}catch(Exception e){
			logger.error(e);
			if(e.getMessage().contains("的值太大")){
				throw new ServiceException("输入的字段值过长！");
			}
			throw new ServiceException(e.getMessage());
		}
	}
	/**
	*保存添加单个对象
	**/
	@Transactional
	public void saveAddSysUser(SysUser add) throws ServiceException{
		try{
			sysusermapper.insertSelective(add);
		}catch(Exception e){
			logger.error(e);
			if(e.getMessage().contains("违反唯一约束条件")){
				throw new ServiceException("违反唯一约束条件");
			}else{
				throw new ServiceException(e.getMessage());
			}
		}
	}
	/**
	*保存新增/编辑单个对象
	**/
	@Transactional
	public void saveAddEditSysUser(SysUser edit) throws ServiceException{
		try{
			Timestamp ts = syscommonmapper.queryTimestamp();
			if(StringUtils.isNull(edit.getId())){
				//新增
				edit.setId(CommonUtils.getUUID(32));
				edit.setPassword(MD5Utils.MD5LOWER("111111"));
				edit.setCreater(BspUtils.getLoginUser().getCode());
				edit.setCreateTime(ts);
				edit.setUpdater(BspUtils.getLoginUser().getCode());
				edit.setUpdateTime(ts);
				sysusermapper.insertSelective(edit);
			}else{
				//修改
				edit.setUpdater(BspUtils.getLoginUser().getCode());
				edit.setUpdateTime(ts);
				sysusermapper.updateByPrimaryKeySelective(edit);
			}
		}catch(Exception e){
			logger.error(e);
			throw new ServiceException(e.getMessage());
		}
	}
	/**
	*根据主键批量删除对象
	**/
	@Transactional
	public void delSysUserById(List<SysUser> delList){
		for(SysUser del:delList){
			sysusermapper.deleteByPrimaryKey(del.getId());
			//删除用户角色及数据权限
			SysUserRoleExample sure = new SysUserRoleExample();
			sure.createCriteria().andUserCodeEqualTo(del.getCode());
			sysuserrolemapper.deleteByExample(sure);
			SysUserDataAccessExample sudae = new SysUserDataAccessExample();
			sudae.createCriteria().andUserCodeEqualTo(del.getCode());
			sysuserdataaccessmapper.deleteByExample(sudae);
		}
	}
	/**
	*根据主键查询对象
	**/
	public SysUser getSysUserById(String id){
		SysUser bean = sysusermapper.selectByPrimaryKey(id);
		return bean;
	}
	/**
	 * 根据账户查询
	 * @param code
	 * @return
	 */
	public SysUser getSysUserByCode(String code){
		SysUserExample e = new SysUserExample();
		e.createCriteria().andCodeEqualTo(code);
		List<SysUser> list = sysusermapper.selectByExample(e);
		if(null!=list&&1==list.size()){
			return list.get(0);
		}else{
			return null;
		}
	}
	public SysUser getSysUserByMobilePhone(String mobilePhone){
		SysUserExample e = new SysUserExample();
		e.createCriteria().andMobeltelEqualTo(mobilePhone);
		List<SysUser> list = sysusermapper.selectByExample(e);
		if(null!=list&&1==list.size()){
			return list.get(0);
		}else{
			return null;
		}
	}
	public void updatePwd(String oldpwd,String newpwd,String configpwd) throws ServiceException{
		String oldpwdMD5=MD5Utils.MD5LOWER(oldpwd);
		String loginpwd = BspUtils.getLoginUser().getPassword();
		if(!oldpwdMD5.equals(loginpwd)){
			throw new ServiceException("原密码不正确");
		}else if(!newpwd.equals(configpwd)){
			throw new ServiceException("两次输入的密码不一致");
		}
		try{
		SysUser user= new SysUser();
		user.setPassword(MD5Utils.MD5LOWER(newpwd));
		user.setUpdateTime(syscommonmapper.queryTimestamp());
		SysUserExample sue = new SysUserExample();
		sue.createCriteria().andCodeEqualTo(BspUtils.getLoginUser().getCode());
		sysusermapper.updateByExampleSelective(user,sue);
		}catch(Exception e){
			logger.error(e);
			if(e.getMessage().contains("的值太大")){
				throw new ServiceException("输入的字段值过长！");
			}
			throw new ServiceException(e.getMessage());
		}
	}
	/**
	 * 密码重置
	 * @param userCode
	 * @throws ServiceException
	 */
	@Transactional
	public void resetPwd(List<SysUser> list) throws ServiceException{
		try{
			for(SysUser su:list){
				SysUser user= new SysUser();
				user.setPassword(MD5Utils.MD5LOWER("111111"));
				user.setUpdateTime(syscommonmapper.queryTimestamp());
				SysUserExample sue = new SysUserExample();
				sue.createCriteria().andCodeEqualTo(su.getCode());
				sysusermapper.updateByExampleSelective(user,sue);
			}
		}catch(Exception e){
			logger.error(e);
			throw new ServiceException(e.getMessage());
		}
	}
}