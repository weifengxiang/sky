package org.sky.sys.service;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.sky.sys.client.SysCommonMapper;
import org.sky.sys.client.SysFileMapper;
import org.sky.sys.exception.ServiceException;
import org.sky.sys.model.SysFile;
import org.sky.sys.model.SysFileExample;
import org.sky.sys.utils.PageListData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sky.sys.utils.BspUtils;
import org.sky.sys.utils.CommonUtils;
import org.sky.sys.utils.StringUtils;
@Service
public class SysFileService {
	private final Logger logger=Logger.getLogger(SysFileService.class);
	@Autowired
	private SysFileMapper sysfilemapper;
	@Autowired
	private SysCommonMapper syscommonmapper;
	/**
	*分页查询
	**/
	public PageListData getSysFileByPage(SysFileExample ep){
		long totalCount = sysfilemapper.countByExample(ep);
		List list = sysfilemapper.selectByExample(ep);
		PageListData pld = new PageListData();
		pld.setTotal(totalCount);
		pld.setRows(list);
		return pld;
	}
	public List<SysFile> getSysFile(SysFileExample ep){
		List list = sysfilemapper.selectByExample(ep);
		return list;
	}
	/**
	*保存列表新增及修改
	**/
	@Transactional
	public void saveSysFile(List<SysFile> addlist,
			List<SysFile> updatelist) throws ServiceException{
		try{
			Timestamp ts = syscommonmapper.queryTimestamp();
			if(null!=addlist&&addlist.size()>0){
				for(SysFile add:addlist){
					add.setId(CommonUtils.getUUID(32));
					add.setCreater(BspUtils.getLoginUser().getCode());
					add.setCreateTime(ts);
					add.setUpdater(BspUtils.getLoginUser().getCode());
					add.setUpdateTime(ts);
					sysfilemapper.insertSelective(add);
				}
			}
			if(null!=updatelist&&updatelist.size()>0){
				for(SysFile update:updatelist){
					update.setUpdater(BspUtils.getLoginUser().getCode());
					update.setUpdateTime(ts);
					sysfilemapper.updateByPrimaryKeySelective(update);
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
	public void saveAddSysFile(SysFile add) throws ServiceException{
		try{
			sysfilemapper.insertSelective(add);
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
	public void saveAddEditSysFile(SysFile edit) throws ServiceException{
		try{
			Timestamp ts = syscommonmapper.queryTimestamp();
			if(StringUtils.isNull(edit.getId())){
				//新增
				edit.setId(CommonUtils.getUUID(32));
				edit.setCreater(BspUtils.getLoginUser().getCode());
				edit.setCreateTime(ts);
				edit.setUpdater(BspUtils.getLoginUser().getCode());
				edit.setUpdateTime(ts);
				sysfilemapper.insertSelective(edit);
			}else{
				//修改
				edit.setUpdater(BspUtils.getLoginUser().getCode());
				edit.setUpdateTime(ts);
				sysfilemapper.updateByPrimaryKeySelective(edit);
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
	public void delSysFileById(List<SysFile> delList){
		for(SysFile del:delList){
			sysfilemapper.deleteByPrimaryKey(del.getId());
		}
	}
	/**
	*根据主键查询对象
	**/
	public SysFile getSysFileById(String id){
		SysFile bean = sysfilemapper.selectByPrimaryKey(id);
		return bean;
	}
}