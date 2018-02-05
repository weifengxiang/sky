package org.sky.sys.service;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.sky.sys.client.SysCommonMapper;
import org.sky.sys.client.SysRoleWidgetMapper;
import org.sky.sys.exception.ServiceException;
import org.sky.sys.model.SysRoleWidget;
import org.sky.sys.model.SysRoleWidgetExample;
import org.sky.sys.utils.PageListData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sky.sys.utils.BspUtils;
import org.sky.sys.utils.CommonUtils;
import org.sky.sys.utils.StringUtils;
@Service
public class SysRoleWidgetService {
	private final Logger logger=Logger.getLogger(SysRoleWidgetService.class);
	@Autowired
	private SysRoleWidgetMapper sysrolewidgetmapper;
	@Autowired
	private SysCommonMapper syscommonmapper;
	/**
	*分页查询
	**/
	public PageListData getSysRoleWidgetByPage(SysRoleWidgetExample ep){
		long totalCount = sysrolewidgetmapper.countByExample(ep);
		List list = sysrolewidgetmapper.selectByExample(ep);
		PageListData pld = new PageListData();
		pld.setTotal(totalCount);
		pld.setRows(list);
		return pld;
	}
	/**
	*保存列表新增及修改
	**/
	@Transactional
	public void saveSysRoleWidget(List<SysRoleWidget> addlist,
			List<SysRoleWidget> updatelist) throws ServiceException{
		try{
			Timestamp ts = syscommonmapper.queryTimestamp();
			if(null!=addlist&&addlist.size()>0){
				for(SysRoleWidget add:addlist){
					add.setId(CommonUtils.getUUID(32));
					add.setCreater(BspUtils.getLoginUser().getCode());
					add.setCreateTime(ts);
					add.setUpdater(BspUtils.getLoginUser().getCode());
					add.setUpdateTime(ts);
					sysrolewidgetmapper.insertSelective(add);
				}
			}
			if(null!=updatelist&&updatelist.size()>0){
				for(SysRoleWidget update:updatelist){
					update.setUpdater(BspUtils.getLoginUser().getCode());
					update.setUpdateTime(ts);
					sysrolewidgetmapper.updateByPrimaryKeySelective(update);
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
	public void saveAddSysRoleWidget(SysRoleWidget add) throws ServiceException{
		try{
			sysrolewidgetmapper.insertSelective(add);
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
	public void saveAddEditSysRoleWidget(SysRoleWidget edit) throws ServiceException{
		try{
			Timestamp ts = syscommonmapper.queryTimestamp();
			if(StringUtils.isNull(edit.getId())){
				//新增
				edit.setId(CommonUtils.getUUID(32));
				edit.setCreater(BspUtils.getLoginUser().getCode());
				edit.setCreateTime(ts);
				edit.setUpdater(BspUtils.getLoginUser().getCode());
				edit.setUpdateTime(ts);
				sysrolewidgetmapper.insertSelective(edit);
			}else{
				//修改
				edit.setUpdater(BspUtils.getLoginUser().getCode());
				edit.setUpdateTime(ts);
				sysrolewidgetmapper.updateByPrimaryKeySelective(edit);
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
	public void delSysRoleWidgetById(List<SysRoleWidget> delList){
		for(SysRoleWidget del:delList){
			sysrolewidgetmapper.deleteByPrimaryKey(del.getId());
		}
	}
	/**
	*根据主键查询对象
	**/
	public SysRoleWidget getSysRoleWidgetById(String id){
		SysRoleWidget bean = sysrolewidgetmapper.selectByPrimaryKey(id);
		return bean;
	}
}