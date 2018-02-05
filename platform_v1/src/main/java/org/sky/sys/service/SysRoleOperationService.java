package org.sky.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.sky.sys.client.SysRoleOperationMapper;
import org.sky.sys.exception.ServiceException;
import org.sky.sys.model.SysRoleOperation;
import org.sky.sys.model.SysRoleOperationExample;
import org.sky.sys.utils.PageListData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysRoleOperationService {
	private final Logger logger=Logger.getLogger(SysRoleOperationService.class);
	@Autowired
	private SysRoleOperationMapper sysroleoperationmapper;
	/**
	*分页查询
	**/
	public PageListData getSysRoleOperationByPage(SysRoleOperationExample ep){
		long totalCount = sysroleoperationmapper.countByExample(ep);
		List list = sysroleoperationmapper.selectByExample(ep);
		PageListData pld = new PageListData();
		pld.setTotal(totalCount);
		pld.setRows(list);
		return pld;
	}
	/**
	*保存列表新增及修改
	**/
	@Transactional
	public void saveSysRoleOperation(List<SysRoleOperation> addlist,
			List<SysRoleOperation> updatelist) throws ServiceException{
		try{
			if(null!=addlist&&addlist.size()>0){
				for(SysRoleOperation add:addlist){
					sysroleoperationmapper.insertSelective(add);
				}
			}
			if(null!=updatelist&&updatelist.size()>0){
				for(SysRoleOperation update:updatelist){
					sysroleoperationmapper.updateByPrimaryKeySelective(update);
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
	public void saveAddSysRoleOperation(SysRoleOperation add) throws ServiceException{
		try{
			sysroleoperationmapper.insertSelective(add);
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
	public void saveEditSysRoleOperation(SysRoleOperation edit) throws ServiceException{
		try{
			sysroleoperationmapper.updateByPrimaryKeySelective(edit);
		}catch(Exception e){
			logger.error(e);
			throw new ServiceException(e.getMessage());
		}
	}
	/**
	*根据主键批量删除对象
	**/
	@Transactional
	public void delSysRoleOperationById(List<SysRoleOperation> delList){
		for(SysRoleOperation del:delList){
			sysroleoperationmapper.deleteByPrimaryKey(del.getId());
		}
	}
	/**
	*根据主键查询对象
	**/
	public SysRoleOperation getSysRoleOperationById(String id){
		SysRoleOperation bean = sysroleoperationmapper.selectByPrimaryKey(id);
		return bean;
	}
	/**
	 * 获取资源对应角色集合
	 * key:resourceUrl
	 * List:roleCode
	 * @return
	 */
	public Map<String,List<String>> getRoleByOpeUrl(){
		Map<String,List<String>> urlMap = new HashMap();
		List<SysRoleOperation> roleOpeList = sysroleoperationmapper.selectByExample(null);
		for(SysRoleOperation ro:roleOpeList){
			if(null!=urlMap.get(ro.getOpeUrl())){
				urlMap.get(ro.getOpeUrl()).add(ro.getRoleCode());
			}else{
				List<String> roleList = new ArrayList();
				roleList.add(ro.getRoleCode());
				urlMap.put(ro.getOpeUrl(), roleList);
			}
		}
		return urlMap;
	}
}