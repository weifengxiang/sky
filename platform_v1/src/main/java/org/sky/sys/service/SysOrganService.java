package org.sky.sys.service;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.sky.sys.client.SysCommonMapper;
import org.sky.sys.client.SysOrganMapper;
import org.sky.sys.exception.ServiceException;
import org.sky.sys.model.SysOrgan;
import org.sky.sys.model.SysOrganExample;
import org.sky.sys.utils.PageListData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sky.sys.utils.BspUtils;
import org.sky.sys.utils.CommonUtils;
import org.sky.sys.utils.StringUtils;
import org.sky.sys.utils.TreeStru;
@Service
public class SysOrganService {
	private final Logger logger=Logger.getLogger(SysOrganService.class);
	@Autowired
	private SysOrganMapper sysorganmapper;
	@Autowired
	private SysCommonMapper syscommonmapper;
	/**
	*分页查询
	**/
	public PageListData getSysOrganByPage(SysOrganExample ep){
		long totalCount = sysorganmapper.countByExample(ep);
		List list = sysorganmapper.selectByExample(ep);
		PageListData pld = new PageListData();
		pld.setTotal(totalCount);
		pld.setRows(list);
		return pld;
	}
	/**
	 * 查询组织机构树
	 * @param m
	 * @return
	 */
	public List<TreeStru> getSysOrganTree(Map m){
		List<TreeStru> list = new ArrayList();
		String code = (String)m.get("code");
		SysOrganExample soe = new SysOrganExample();
		soe.createCriteria().andParCodeEqualTo(code);
		List<SysOrgan> solist = sysorganmapper.selectByExample(soe);
		for(SysOrgan so:solist){
			TreeStru ts = new TreeStru();
			ts.setId(so.getCode());
			ts.setText(so.getName()+"["+so.getCode()+"]");
			ts.setSeq(so.getSeq());
			ts.setIconCls(so.getIcon());
			if(so.getChildCount()>0){
				ts.setState("closed");
			}else{
				ts.setState("open");
			}
			ts.setData(so);
			list.add(ts);
		}
		return list;
	}
	/**
	*保存列表新增及修改
	**/
	@Transactional
	public void saveSysOrgan(List<SysOrgan> addlist,
			List<SysOrgan> updatelist) throws ServiceException{
		try{
			if(null!=addlist&&addlist.size()>0){
				for(SysOrgan add:addlist){
					sysorganmapper.insertSelective(add);
				}
			}
			if(null!=updatelist&&updatelist.size()>0){
				for(SysOrgan update:updatelist){
					sysorganmapper.updateByPrimaryKeySelective(update);
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
	public void saveAddSysOrgan(SysOrgan add) throws ServiceException{
		try{
			sysorganmapper.insertSelective(add);
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
	public void saveAddEditSysOrgan(SysOrgan edit) throws ServiceException{
		try{
			Timestamp ts = syscommonmapper.queryTimestamp();
			if(StringUtils.isNull(edit.getId())){
				//新增
				edit.setId(CommonUtils.getUUID(32));
				edit.setCreater(BspUtils.getLoginUser().getCode());
				edit.setCreateTime(ts);
				edit.setUpdater(BspUtils.getLoginUser().getCode());
				edit.setUpdateTime(ts);
				sysorganmapper.insertSelective(edit);
			}else{
				//修改
				edit.setUpdater(BspUtils.getLoginUser().getCode());
				edit.setUpdateTime(ts);
				sysorganmapper.updateByPrimaryKeySelective(edit);
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
	public void delSysOrganById(SysOrgan so){
		sysorganmapper.deleteByPrimaryKey(so.getId());
	}
	/**
	*根据主键查询对象
	**/
	public SysOrgan getSysOrganById(String id){
		SysOrgan bean = sysorganmapper.selectByPrimaryKey(id);
		return bean;
	}
}