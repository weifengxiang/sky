package org.sky.sys.service;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.sky.sys.client.SysCommonMapper;
import org.sky.sys.client.SysWidgetMapper;
import org.sky.sys.exception.ServiceException;
import org.sky.sys.model.SysWidget;
import org.sky.sys.model.SysWidgetExample;
import org.sky.sys.utils.PageListData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sky.sys.utils.BspUtils;
import org.sky.sys.utils.CommonUtils;
import org.sky.sys.utils.StringUtils;
@Service
public class SysWidgetService {
	private final Logger logger=Logger.getLogger(SysWidgetService.class);
	@Autowired
	private SysWidgetMapper syswidgetmapper;
	@Autowired
	private SysCommonMapper syscommonmapper;
	/**
	*分页查询
	**/
	public PageListData getSysWidgetByPage(SysWidgetExample ep){
		long totalCount = syswidgetmapper.countByExample(ep);
		List list = syswidgetmapper.selectByExample(ep);
		PageListData pld = new PageListData();
		pld.setTotal(totalCount);
		pld.setRows(list);
		return pld;
	}
	public List<SysWidget> getSysWidgetList(SysWidgetExample ep){
		List list = syswidgetmapper.selectByExample(ep);
		return list;
	}
	/**
	*保存列表新增及修改
	**/
	@Transactional
	public void saveSysWidget(List<SysWidget> addlist,
			List<SysWidget> updatelist) throws ServiceException{
		try{
			if(null!=addlist&&addlist.size()>0){
				for(SysWidget add:addlist){
					syswidgetmapper.insertSelective(add);
				}
			}
			if(null!=updatelist&&updatelist.size()>0){
				for(SysWidget update:updatelist){
					syswidgetmapper.updateByPrimaryKeySelective(update);
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
	public void saveAddSysWidget(SysWidget add) throws ServiceException{
		try{
			syswidgetmapper.insertSelective(add);
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
	public void saveAddEditSysWidget(SysWidget edit) throws ServiceException{
		try{
			Timestamp ts = syscommonmapper.queryTimestamp();
			if(StringUtils.isNull(edit.getId())){
				//新增
				edit.setId(CommonUtils.getUUID(32));
				edit.setCreater(BspUtils.getLoginUser().getCode());
				edit.setCreateTime(ts);
				edit.setUpdater(BspUtils.getLoginUser().getCode());
				edit.setUpdateTime(ts);
				syswidgetmapper.insertSelective(edit);
			}else{
				//修改
				edit.setUpdater(BspUtils.getLoginUser().getCode());
				edit.setUpdateTime(ts);
				syswidgetmapper.updateByPrimaryKeySelective(edit);
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
	public void delSysWidgetById(List<SysWidget> delList){
		for(SysWidget del:delList){
			syswidgetmapper.deleteByPrimaryKey(del.getId());
		}
	}
	/**
	*根据主键查询对象
	**/
	public SysWidget getSysWidgetById(String id){
		SysWidget bean = syswidgetmapper.selectByPrimaryKey(id);
		return bean;
	}
	/**
	 * 查询用户微件
	 */
	public List<SysWidget> getSysWidgetListByUserCode(String userCode){
		List list = syswidgetmapper.getSysWidgetListByUserCode(userCode);
		return list;
	}
}