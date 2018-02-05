package org.sky.sys.service;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.sky.sys.client.SysCommonMapper;
import org.sky.sys.client.SysMenuMapper;
import org.sky.sys.client.SysOperationMapper;
import org.sky.sys.client.SysRoleMapper;
import org.sky.sys.client.SysRoleOperationMapper;
import org.sky.sys.exception.ServiceException;
import org.sky.sys.model.SysMenu;
import org.sky.sys.model.SysMenuExample;
import org.sky.sys.model.SysOperation;
import org.sky.sys.model.SysOperationExample;
import org.sky.sys.model.SysRole;
import org.sky.sys.model.SysRoleExample;
import org.sky.sys.model.SysRoleOperation;
import org.sky.sys.model.SysRoleOperationExample;
import org.sky.sys.utils.PageListData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sky.sys.utils.ApplicationCached;
import org.sky.sys.utils.BspUtils;
import org.sky.sys.utils.CommonUtils;
import org.sky.sys.utils.StringUtils;
import org.sky.sys.utils.TreeStru;
@Service
public class SysRoleService {
	private final Logger logger=Logger.getLogger(SysRoleService.class);
	@Autowired
	private SysRoleMapper sysrolemapper;
	@Autowired
	private SysMenuMapper sysmenumapper;
	@Autowired
	private SysOperationMapper sysoperationmapper;
	@Autowired
	private SysRoleOperationMapper sysroleoperationmapper;
	@Autowired
	private SysCommonMapper syscommonmapper;
	/**
	*分页查询
	**/
	public PageListData getSysRoleByPage(SysRoleExample ep){
		long totalCount = sysrolemapper.countByExample(ep);
		List list = sysrolemapper.selectByExample(ep);
		PageListData pld = new PageListData();
		pld.setTotal(totalCount);
		pld.setRows(list);
		return pld;
	}
	/**
	*保存列表新增及修改
	**/
	@Transactional
	public void saveSysRole(List<SysRole> addlist,
			List<SysRole> updatelist) throws ServiceException{
		try{
			if(null!=addlist&&addlist.size()>0){
				for(SysRole add:addlist){
					sysrolemapper.insertSelective(add);
				}
			}
			if(null!=updatelist&&updatelist.size()>0){
				for(SysRole update:updatelist){
					sysrolemapper.updateByPrimaryKeySelective(update);
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
	public void saveAddSysRole(SysRole add) throws ServiceException{
		try{
			sysrolemapper.insertSelective(add);
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
	public void saveAddEditSysRole(SysRole edit) throws ServiceException{
		try{
			Timestamp ts = syscommonmapper.queryTimestamp();
			if(StringUtils.isNull(edit.getId())){
				//新增
				edit.setId(CommonUtils.getUUID(32));
				edit.setCreater(BspUtils.getLoginUser().getCode());
				edit.setCreateTime(ts);
				edit.setUpdater(BspUtils.getLoginUser().getCode());
				edit.setUpdateTime(ts);
				sysrolemapper.insertSelective(edit);
			}else{
				//修改
				edit.setUpdater(BspUtils.getLoginUser().getCode());
				edit.setUpdateTime(ts);
				sysrolemapper.updateByPrimaryKeySelective(edit);
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
	public void delSysRoleById(List<SysRole> delList){
		for(SysRole del:delList){
			sysrolemapper.deleteByPrimaryKey(del.getId());
		}
	}
	/**
	*根据主键查询对象
	**/
	public SysRole getSysRoleById(String id){
		SysRole bean = sysrolemapper.selectByPrimaryKey(id);
		return bean;
	}
	/**
	 * 查询角色操作树
	 * @param param
	 * @return
	 */
	public List<TreeStru> getRoleOpeTree(String roleCode){
		SysRoleOperationExample sroe = new SysRoleOperationExample();
		sroe.createCriteria().andRoleCodeEqualTo(roleCode);
		List<SysRoleOperation> list = sysroleoperationmapper.selectByExample(sroe);
		List<TreeStru> tslist = new ArrayList();
		List<SysMenu> smlist = getSysMenuByParent("root");
		for(SysMenu sm:smlist){
			TreeStru ts = new TreeStru();
			ts.setId(sm.getCode());
			ts.setText(sm.getName());
			ts.setIconCls(sm.getIcon());
			ts.setData(sm);
			if(sm.getChildCount()>0){
				ts.setState("closed");
			}else{
				ts.setState("open");
			}
			tslist.add(ts);
			getRoleOpeTree(ts,list);
		}
		return tslist;
	}
	/**
	 * 递归查询
	 * @param ts
	 * @param srolist
	 */
	private void getRoleOpeTree(TreeStru ts,List<SysRoleOperation> srolist){
		SysMenu sm = (SysMenu)ts.getData();
		//如果不是叶子节点并且有下级则递归组装菜单
		if("0".equals(sm.getIsLeaf())&&
				sm.getChildCount()>0){
			List<SysMenu> smchild = getSysMenuByParent(sm.getCode());
			List<TreeStru> tslist = new ArrayList();
			for(SysMenu smc:smchild){
				TreeStru tsc = new TreeStru();
				tsc.setId(smc.getCode());
				tsc.setText(smc.getName());
				tsc.setIconCls(smc.getIcon());
				tsc.setData(smc);
				if(smc.getChildCount()>0){
					tsc.setState("closed");
				}else{
					tsc.setState("open");
				}
				tslist.add(tsc);
				getRoleOpeTree(tsc,srolist);
				ts.setChildren(tslist);
			}
		}else if("0".equals(sm.getIsLeaf())&&
				sm.getChildCount()==0){
			//如果不是叶子节点并且无下级菜单
			return;
		}else if("1".equals(sm.getIsLeaf())&&
				sm.getChildCount()>0){
			//如果是叶子节点并且有下级操作
			List<SysOperation> solist = getSysOperationByMenuCode(sm.getCode());
			List<TreeStru> tslist = new ArrayList();
			for(SysOperation so:solist){
				TreeStru tsc = new TreeStru();
				tsc.setId(so.getCode());
				tsc.setText(so.getName());
				tsc.setIconCls("icon-operation");
				tsc.setChecked(operationHasChecked(so,srolist));
				tsc.setData(so);
				tslist.add(tsc);
			}
			ts.setChildren(tslist);
			return;
		}else if("1".equals(sm.getIsLeaf())&&
				sm.getChildCount()==0){
			//是叶子节点并且无下级操作
			return;
		}
	}
	/**
	 * 根据父级编号获取菜单
	 * @param parentCode
	 * @return
	 */
	private List<SysMenu> getSysMenuByParent(String parentCode){
		List<SysMenu> reslist = new ArrayList();
		List<SysMenu> smlist = ApplicationCached.getMenulist();
		for(SysMenu sm:smlist){
			if(parentCode.equals(sm.getParentCode())){
				reslist.add(sm);
			}
		}
		return reslist;
	}
	/**
	 * 根据菜单编号获取操作
	 * @param menuCode
	 * @return
	 */
	private List<SysOperation> getSysOperationByMenuCode(String menuCode){
		List<SysOperation> reslist = new ArrayList();
		List<SysOperation> solist = ApplicationCached.getOperadionlist();
		for(SysOperation so:solist){
			if(menuCode.equals(so.getMenuCode())){
				reslist.add(so);
			}
		}
		return reslist;
	}
	/**
	 * 操作是否被选中
	 * @param so
	 * @param srolist
	 * @return
	 */
	private boolean operationHasChecked(SysOperation so,List<SysRoleOperation> srolist){
		for(SysRoleOperation sro:srolist){
			if(so.getCode().equals(sro.getOpeCode())){
				return true;
			}
		}
		return false;
	}
	/**
	 * 保存角色操作对应关系
	 * @param role
	 * @param solist
	 * @throws ServiceException
	 */
	@Transactional
	public void saveRoleOperation(SysRole role,List<SysOperation> solist) throws ServiceException{
		try{
		SysRoleOperationExample soe = new SysRoleOperationExample();
		soe.createCriteria().andRoleCodeEqualTo(role.getCode());
		sysroleoperationmapper.deleteByExample(soe);
		Timestamp ts = syscommonmapper.queryTimestamp();
		for(SysOperation so:solist){
			SysRoleOperation sro = new SysRoleOperation();
			sro.setId(CommonUtils.getUUID(32));
			sro.setCreater(BspUtils.getLoginUser().getCode());
			sro.setCreateTime(ts);
			sro.setOpeCode(so.getCode());
			sro.setOpeUrl(so.getUrl());
			sro.setRoleCode(role.getCode());
			sro.setUpdater(BspUtils.getLoginUser().getCode());
			sro.setUpdateTime(ts);
			sysroleoperationmapper.insertSelective(sro);
		}
		}catch(Exception e){
			logger.error(e);
			throw new ServiceException("操作失败："+e.getMessage());
		}
	}
}