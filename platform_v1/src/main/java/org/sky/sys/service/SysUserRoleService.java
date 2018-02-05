package org.sky.sys.service;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.sky.sys.client.SysCommonMapper;
import org.sky.sys.client.SysUserRoleMapper;
import org.sky.sys.exception.ServiceException;
import org.sky.sys.model.SysUserRole;
import org.sky.sys.model.SysUserRoleExample;
import org.sky.sys.utils.PageListData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sky.sys.utils.BspUtils;
import org.sky.sys.utils.CommonUtils;
import org.sky.sys.utils.StringUtils;
@Service
public class SysUserRoleService {
	private final Logger logger=Logger.getLogger(SysUserRoleService.class);
	@Autowired
	private SysUserRoleMapper sysuserrolemapper;
	@Autowired
	private SysCommonMapper syscommonmapper;
	/**
	*分页查询
	**/
	public PageListData getSysUserRoleByPage(SysUserRoleExample ep){
		long totalCount = sysuserrolemapper.countByExample(ep);
		List list = sysuserrolemapper.selectByExample(ep);
		PageListData pld = new PageListData();
		pld.setTotal(totalCount);
		pld.setRows(list);
		return pld;
	}
	/**
	*保存列表新增及修改
	**/
	@Transactional
	public void saveSysUserRole(List<SysUserRole> addlist,
			List<SysUserRole> updatelist) throws ServiceException{
		try{
			Timestamp ts = syscommonmapper.queryTimestamp();
			if(null!=addlist&&addlist.size()>0){
				for(SysUserRole add:addlist){
					add.setId(CommonUtils.getUUID(32));
					add.setCreater(BspUtils.getLoginUser().getCode());
					add.setCreateTime(ts);
					add.setUpdater(BspUtils.getLoginUser().getCode());
					add.setUpdateTime(ts);
					sysuserrolemapper.insertSelective(add);
				}
			}
			if(null!=updatelist&&updatelist.size()>0){
				for(SysUserRole update:updatelist){
					update.setUpdater(BspUtils.getLoginUser().getCode());
					update.setUpdateTime(ts);
					sysuserrolemapper.updateByPrimaryKeySelective(update);
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
	public void saveAddSysUserRole(SysUserRole add) throws ServiceException{
		try{
			sysuserrolemapper.insertSelective(add);
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
	public void saveAddEditSysUserRole(SysUserRole edit) throws ServiceException{
		try{
			Timestamp ts = syscommonmapper.queryTimestamp();
			if(StringUtils.isNull(edit.getId())){
				//新增
				edit.setId(CommonUtils.getUUID(32));
				edit.setCreater(BspUtils.getLoginUser().getCode());
				edit.setCreateTime(ts);
				edit.setUpdater(BspUtils.getLoginUser().getCode());
				edit.setUpdateTime(ts);
				sysuserrolemapper.insertSelective(edit);
			}else{
				//修改
				edit.setUpdater(BspUtils.getLoginUser().getCode());
				edit.setUpdateTime(ts);
				sysuserrolemapper.updateByPrimaryKeySelective(edit);
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
	public void delSysUserRoleById(List<SysUserRole> delList){
		for(SysUserRole del:delList){
			sysuserrolemapper.deleteByPrimaryKey(del.getId());
		}
	}
	/**
	*根据主键查询对象
	**/
	public SysUserRole getSysUserRoleById(String id){
		SysUserRole bean = sysuserrolemapper.selectByPrimaryKey(id);
		return bean;
	}
	public List<SysUserRole> getSysUserRoleByUserCode(String userCode){
		SysUserRoleExample e = new SysUserRoleExample();
		e.createCriteria().andUserCodeEqualTo(userCode);
		return sysuserrolemapper.selectByExample(e);
	}
}