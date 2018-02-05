package org.sky.sys.service;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.sky.sys.client.SysCommonMapper;
import org.sky.sys.client.SysDictMapper;
import org.sky.sys.exception.ServiceException;
import org.sky.sys.model.SysDict;
import org.sky.sys.model.SysDictExample;
import org.sky.sys.utils.PageListData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sky.sys.utils.BspUtils;
import org.sky.sys.utils.CommonUtils;
import org.sky.sys.utils.StringUtils;
@Service
public class SysDictService {
	private final Logger logger=Logger.getLogger(SysDictService.class);
	@Autowired
	private SysDictMapper sysdictmapper;
	@Autowired
	private SysCommonMapper syscommonmapper;
	/**
	*分页查询
	**/
	public PageListData getSysDictByPage(SysDictExample ep){
		long totalCount = sysdictmapper.countByExample(ep);
		List list = sysdictmapper.selectByExample(ep);
		PageListData pld = new PageListData();
		pld.setTotal(totalCount);
		pld.setRows(list);
		return pld;
	}
	/**
	*保存列表新增及修改
	**/
	@Transactional
	public void saveSysDict(List<SysDict> addlist,
			List<SysDict> updatelist) throws ServiceException{
		try{
			if(null!=addlist&&addlist.size()>0){
				for(SysDict add:addlist){
					sysdictmapper.insertSelective(add);
				}
			}
			if(null!=updatelist&&updatelist.size()>0){
				for(SysDict update:updatelist){
					sysdictmapper.updateByPrimaryKeySelective(update);
				}
			}
		}catch(Exception e){
			logger.error("字典批量保存异常",e);
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
	public void saveAddSysDict(SysDict add) throws ServiceException{
		try{
			sysdictmapper.insertSelective(add);
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
	public void saveAddEditSysDict(SysDict edit) throws ServiceException{
		try{
			Timestamp ts = syscommonmapper.queryTimestamp();
			if(StringUtils.isNull(edit.getId())){
				//新增
				edit.setId(CommonUtils.getUUID(32));
				edit.setCreater(BspUtils.getLoginUser().getCode());
				edit.setCreateTime(ts);
				edit.setUpdater(BspUtils.getLoginUser().getCode());
				edit.setUpdateTime(ts);
				sysdictmapper.insertSelective(edit);
			}else{
				//修改
				edit.setUpdater(BspUtils.getLoginUser().getCode());
				edit.setUpdateTime(ts);
				sysdictmapper.updateByPrimaryKeySelective(edit);
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
	public void delSysDictById(List<SysDict> delList){
		for(SysDict del:delList){
			sysdictmapper.deleteByPrimaryKey(del.getId());
		}
	}
	/**
	*根据主键查询对象
	**/
	public SysDict getSysDictById(String id){
		SysDict bean = sysdictmapper.selectByPrimaryKey(id);
		return bean;
	}
}