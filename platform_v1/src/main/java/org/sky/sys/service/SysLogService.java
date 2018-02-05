package org.sky.sys.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.sky.sys.client.SysLogMapper;
import org.sky.sys.exception.ServiceException;
import org.sky.sys.model.SysLog;
import org.sky.sys.model.SysLogExample;
import org.sky.sys.utils.PageListData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysLogService {
	private final Logger logger=Logger.getLogger(SysLogService.class);
	@Autowired
	private SysLogMapper syslogmapper;
	/**
	*分页查询
	**/
	public PageListData getSysLogByPage(SysLogExample ep){
		long totalCount = syslogmapper.countByExample(ep);
		List list = syslogmapper.selectByExample(ep);
		PageListData pld = new PageListData();
		pld.setTotal(totalCount);
		pld.setRows(list);
		return pld;
	}
	/**
	*保存列表新增及修改
	**/
	@Transactional
	public void saveSysLog(List<SysLog> addlist,
			List<SysLog> updatelist) throws ServiceException{
		try{
			if(null!=addlist&&addlist.size()>0){
				for(SysLog add:addlist){
					syslogmapper.insertSelective(add);
				}
			}
			if(null!=updatelist&&updatelist.size()>0){
				for(SysLog update:updatelist){
					syslogmapper.updateByPrimaryKeySelective(update);
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
	public void saveAddSysLog(SysLog add) throws ServiceException{
		try{
			syslogmapper.insertSelective(add);
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
	*保存编辑单个对象
	**/
	@Transactional
	public void saveEditSysLog(SysLog edit) throws ServiceException{
		try{
			syslogmapper.updateByPrimaryKeySelective(edit);
		}catch(Exception e){
			logger.error(e);
			throw new ServiceException(e.getMessage());
		}
	}
	/**
	*根据主键批量删除对象
	**/
	@Transactional
	public void delSysLogById(List<SysLog> delList){
		for(SysLog del:delList){
			syslogmapper.deleteByPrimaryKey(del.getId());
		}
	}
	/**
	*根据主键查询对象
	**/
	public SysLog getSysLogById(String id){
		SysLog bean = syslogmapper.selectByPrimaryKey(id);
		return bean;
	}
}