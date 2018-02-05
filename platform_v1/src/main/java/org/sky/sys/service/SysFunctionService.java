package org.sky.sys.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.sky.sys.client.SysCommonMapper;
import org.sky.sys.client.SysMenuMapper;
import org.sky.sys.client.SysOperationMapper;
import org.sky.sys.client.SysRoleOperationMapper;
import org.sky.sys.exception.ServiceException;
import org.sky.sys.model.SysMenu;
import org.sky.sys.model.SysMenuExample;
import org.sky.sys.model.SysOperation;
import org.sky.sys.model.SysOperationExample;
import org.sky.sys.model.SysRoleOperationExample;
import org.sky.sys.utils.BspUtils;
import org.sky.sys.utils.CommonUtils;
import org.sky.sys.utils.StringUtils;
import org.sky.sys.utils.TreeStru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * 功能管理
 * @author wei
 *
 */
@Service
public class SysFunctionService {
	private final Logger logger=Logger.getLogger(SysFunctionService.class);
	@Autowired
	private SysMenuMapper sysmenumapper;
	@Autowired
	private SysOperationMapper sysoperationmapper;
	@Autowired
	private SysCommonMapper syscommonmapper;
	@Autowired
	private SysRoleOperationMapper sysroleoperationmapper;
	/**
	 * 功能管理：组装功能树
	 */
	public List<TreeStru> getFunctionTree(Map map){
		List<TreeStru> list = new ArrayList();
		if(null==map){
			throw new ServiceException("参数不能为空");
		}
		String code=(String)map.get("code");
		String isLeaf=(String)map.get("isLeaf");
		if("1".equals(isLeaf)){
			SysOperationExample soe = new SysOperationExample();
			soe.createCriteria().andMenuCodeEqualTo(code);
			soe.setOrderByClause(" seq asc ");
			List<SysOperation> solist = sysoperationmapper.selectByExample(soe);
			for(SysOperation so:solist){
				TreeStru ts = new TreeStru();
				ts.setId(so.getCode());
				ts.setText(so.getName()+"["+so.getCode()+"]");
				ts.setSeq(so.getSeq());
				ts.setIconCls("icon-operation");
				ts.setState("open");
				Map data= new HashMap();
				data.put("id", so.getId());
				data.put("code", so.getCode());
				data.put("isLeaf", "1");
				data.put("type", "operation");
				ts.setData(data);
				list.add(ts);
			}
		}else{
			SysMenuExample sme = new SysMenuExample();
			sme.createCriteria().andParentCodeEqualTo(code);
			sme.setOrderByClause(" seq asc ");
			List<SysMenu> smlist = sysmenumapper.selectByExample(sme);
			for(SysMenu sm:smlist){
				TreeStru ts = new TreeStru();
				ts.setId(sm.getCode());
				ts.setText(sm.getName()+"["+sm.getCode()+"]");
				ts.setSeq(sm.getSeq());
				ts.setIconCls(sm.getIcon());
				ts.setState(sm.getChildCount()>0?"closed":"open");
				Map data= new HashMap();
				data.put("id", sm.getId());
				data.put("code", sm.getCode());
				data.put("isLeaf",sm.getIsLeaf());
				data.put("type", "menu");
				ts.setData(data);
				list.add(ts);
			}
		}
		return list;
	}
	/**
	*根据主键查询对象
	**/
	public SysMenu getSysMenuById(String id){
		SysMenu bean = sysmenumapper.selectByPrimaryKey(id);
		return bean;
	}
	@Transactional
	public void saveAddEditSysMenu(SysMenu edit) throws ServiceException{
		try{
			Timestamp ts = syscommonmapper.queryTimestamp();
			if(StringUtils.isNull(edit.getId())){
				//新增
				edit.setId(CommonUtils.getUUID(32));
				edit.setCreater(BspUtils.getLoginUser().getCode());
				edit.setCreateTime(ts);
				edit.setUpdater(BspUtils.getLoginUser().getCode());
				edit.setUpdateTime(ts);
				if(!"1".equals(edit.getIsLeaf())){
					edit.setIsLeaf("0");
				}
				sysmenumapper.insertSelective(edit);
			}else{
				//修改
				edit.setUpdater(BspUtils.getLoginUser().getCode());
				edit.setUpdateTime(ts);
				if(!"1".equals(edit.getIsLeaf())){
					edit.setIsLeaf("0");
				}
				sysmenumapper.updateByPrimaryKeySelective(edit);
			}	
		}catch(Exception e){
			logger.error(e);
			throw new ServiceException(e.getMessage());
		}
	}
	/**
	*根据主键查询对象
	**/
	public SysOperation getSysOperationById(String id){
		SysOperation bean = sysoperationmapper.selectByPrimaryKey(id);
		return bean;
	}
	@Transactional
	public void saveAddEditSysOperation(SysOperation edit) throws ServiceException{
		try{
			Timestamp ts = syscommonmapper.queryTimestamp();
			if(StringUtils.isNull(edit.getId())){
				//新增
				edit.setId(CommonUtils.getUUID(32));
				edit.setCreater(BspUtils.getLoginUser().getCode());
				edit.setCreateTime(ts);
				edit.setUpdater(BspUtils.getLoginUser().getCode());
				edit.setUpdateTime(ts);
				if(!"1".equals(edit.getIsDefault())){
					edit.setIsDefault("0");
				}
				sysoperationmapper.insertSelective(edit);
			}else{
				//修改
				edit.setUpdater(BspUtils.getLoginUser().getCode());
				edit.setUpdateTime(ts);
				if(!"1".equals(edit.getIsDefault())){
					edit.setIsDefault("0");
				}
				sysoperationmapper.updateByPrimaryKeySelective(edit);
			}
			
		}catch(Exception e){
			logger.error(e);
			throw new ServiceException(e.getMessage());
		}
	}
	/**
	* @Title: delSysMenuByCode 
	* @Description: TODO(根据code删除菜单) 
	* @param @param code  参数
	* @return void    返回类型 
	* @throws
	 */
	@Transactional
	public void delSysMenuByCode(String code) throws ServiceException{
		try{
			recursiveDelSysMenuByCode(code);
		}catch(Exception e){
			logger.error(e);
			throw new ServiceException(e.getMessage());
		}
	}
	/**
	* @Title: recursiveDelSysMenuByCode 
	* @Description: TODO(递归删除菜单) 
	* @param @param code  参数
	* @return void    返回类型 
	* @throws
	 */
	private void recursiveDelSysMenuByCode(String code) throws ServiceException{
		try{
		SysMenuExample dsme = new SysMenuExample();
		dsme.createCriteria().andCodeEqualTo(code);
		sysmenumapper.deleteByExample(dsme);
		
		SysMenuExample sme = new SysMenuExample();
		sme.createCriteria().andParentCodeEqualTo(code);
		List<SysMenu> smlist = sysmenumapper.selectByExample(sme);
		if(null==smlist||smlist.size()==0){
			//当前菜单无下级菜单则直接删除菜单操作
			SysOperationExample soe = new SysOperationExample();
			soe.createCriteria().andMenuCodeEqualTo(code);
			List<SysOperation> solist = sysoperationmapper.selectByExample(soe);
			for(SysOperation so:solist){
				//删除操作
				sysoperationmapper.deleteByPrimaryKey(so.getId());
				//删除操作对于的角色操作表记录
				SysRoleOperationExample sroe = new SysRoleOperationExample();
				sroe.createCriteria().andOpeCodeEqualTo(so.getCode());
				sysroleoperationmapper.deleteByExample(sroe);
			}
		}else{
			//当前菜单有下级菜单则递归调用删除
			for(SysMenu sm:smlist){
				recursiveDelSysMenuByCode(sm.getCode());
			}
		}
		}catch(Exception e){
			logger.error(e);
			throw new ServiceException("递归删除菜单失败:"+e.getMessage());
		}
	}
	/**
	 * 
	* @Title: delSysOperationByCode 
	* @Description: TODO(删除操作) 
	* @param @param code
	* @param @throws ServiceException  参数
	* @return void    返回类型 
	* @throws
	 */
	@Transactional
	public void delSysOperationByCode(String code) throws ServiceException{
		try{
			//删除操作
			SysOperationExample soe = new SysOperationExample();
			soe.createCriteria().andCodeEqualTo(code);
			sysoperationmapper.deleteByExample(soe);
			SysRoleOperationExample sroe = new SysRoleOperationExample();
			sroe.createCriteria().andOpeCodeEqualTo(code);
			sysroleoperationmapper.deleteByExample(sroe);
		}catch(Exception e){
			throw new ServiceException(e.getMessage());
		}
	}
	public List<SysOperation> getSysOperationByUserCode(String userCode){
		return sysoperationmapper.selectByUserCode(userCode);
	}
}
