package org.sky.sys.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.sky.sys.client.SysMenuMapper;
import org.sky.sys.model.SysMenu;
import org.sky.sys.utils.ApplicationCached;
import org.sky.sys.utils.BspUtils;
import org.sky.sys.utils.MenuStru;
import org.sky.sys.utils.TreeStru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 系统服务层
 * @author wei
 *
 */
@Service
public class SysService {
	private final Logger logger=Logger.getLogger(SysService.class);
	@Autowired
	private SysMenuMapper sysmenumapper;
	/**
	 * 获取系统左侧导航菜单数据
	 * @return
	 */
	public List<MenuStru> getLeftNavMenuData(){
		List<MenuStru> menuList=null;
		if(BspUtils.isSecurityFilter()){
			menuList = getLeftNavMenuRBAC();
		}else{
			menuList = getLeftNavMenuNoRBAC();
		}
		return menuList;
	}
	
	/**
	 * 组织有权限菜单
	 * @return
	 */
	private List<MenuStru> getLeftNavMenuRBAC(){
		List<MenuStru> list = new ArrayList();
		//用户具有的权限菜单
		List<SysMenu> leafMenuList = sysmenumapper.selectLeafMenuByUserCode(BspUtils.getLoginUser().getCode());
		//权限菜单的父菜单
		List<SysMenu> parMenuList = new ArrayList();
		for(SysMenu sm:leafMenuList){
			getParentSysMenu(sm,parMenuList);
		}
		//合并权限菜单及其父菜单
		List<SysMenu> userMenuList = new ArrayList();
		userMenuList.addAll(parMenuList);
		userMenuList.addAll(leafMenuList);
		Collections.sort(userMenuList,new Comparator(){
			 public int compare(Object  obj1, Object obj2 ){
				 SysMenu u1 = (SysMenu)obj1;
				 SysMenu u2 = (SysMenu)obj2;
				 if(u1.getParentCode().compareTo(u2.getParentCode())!=0){
					 return u1.getParentCode().compareTo(u2.getParentCode());  
				 }else{
					 return u1.getSeq()-u2.getSeq();
				 }
			}
		});
		//组织菜单数据
		for(SysMenu menu:userMenuList){
			if("root".equals(menu.getParentCode())){
				MenuStru ms = new MenuStru();
				ms.setAccordion(menu);
				if(hasChildMenu(menu.getCode())){
					//一级树菜单
					List<SysMenu> childlist = getChildMenu(menu.getCode(),userMenuList);
					List<TreeStru> tslist = new ArrayList();
					for(SysMenu cm:childlist){
						TreeStru ts = new TreeStru();
						ts.setId(cm.getCode());
						ts.setText(cm.getName());
						ts.setIconCls(cm.getIcon());
						ts.setSeq(cm.getSeq());
						ts.setDefUrl(cm.getDefUrl());
						ts.setData(cm);
						recursiveRBACMenuTree(ts,userMenuList);
						tslist.add(ts);
					}
					ms.setTree(tslist);
				}
				list.add(ms);
			}
		}
		return list;
	}
	/**
	 * 获取菜单的父节点,相同的父节点只添加一次
	 */
	private void getParentSysMenu(SysMenu menu,List<SysMenu> parList){
		if(null==parList){
			parList=new ArrayList();
		}
		for(SysMenu sm:ApplicationCached.getMenulist()){
			if(menu.getParentCode().equals(sm.getCode())){
				if(!isExists(sm,parList)){
					parList.add(sm);
					if(!"root".equals(sm.getParentCode())){
						getParentSysMenu(sm,parList);
					}
				}
			}
		}
	}
	/**
	 * 判断父节点是不是存在
	 */
	private boolean isExists(SysMenu sm,List<SysMenu> parList){
		for(SysMenu m:parList){
			if(m.getCode().equals(sm.getCode())){
				return true;
			}
		}
		return false;
	}
	/**
	 * 组织无权限菜单
	 * @return
	 */
	private List<MenuStru> getLeftNavMenuNoRBAC(){
		List<MenuStru> list = new ArrayList();
		for(SysMenu menu:ApplicationCached.getMenulist()){
			if("root".equals(menu.getParentCode())){
				MenuStru ms = new MenuStru();
				ms.setAccordion(menu);
				if(hasChildMenu(menu.getCode())){
					//一级树菜单
					List<SysMenu> childlist = getChildMenuNoRBAC(menu.getCode());
					List<TreeStru> tslist = new ArrayList();
					for(SysMenu cm:childlist){
						TreeStru ts = new TreeStru();
						ts.setId(cm.getCode());
						ts.setText(cm.getName());
						ts.setIconCls(cm.getIcon());
						ts.setSeq(cm.getSeq());
						ts.setDefUrl(cm.getDefUrl());
						ts.setData(cm);
						recursiveNoRBACMenuTree(ts);
						tslist.add(ts);
					}
					ms.setTree(tslist);
				}
				list.add(ms);
			}
		}
		return list;
	}
	/**
	 * 根据menucode获取子菜单
	 * @param code
	 * @return
	 */
	private List<SysMenu> getChildMenuNoRBAC(String code){
		List<SysMenu> childMenulist=new ArrayList();
		for(SysMenu menu:ApplicationCached.getMenulist()){
			if(code.equals(menu.getParentCode())){
				childMenulist.add(menu);
			}
		}
		return childMenulist;
	}
	/**
	 * 根据menucode获取子菜单
	 * @param code
	 * @return
	 */
	private List<SysMenu> getChildMenu(String code,List<SysMenu> list){
		List<SysMenu> childMenulist=new ArrayList();
		for(SysMenu menu:list){
			if(code.equals(menu.getParentCode())){
				childMenulist.add(menu);
			}
		}
		return childMenulist;
	}
	/**
	 * 判断菜单是否有下级
	 * @param code
	 * @return
	 */
	private boolean hasChildMenu(String code){
		for(SysMenu menu:ApplicationCached.getMenulist()){
			if(code.equals(menu.getParentCode())){
				return true;
			}
		}
		return false;
	}
	/**
	 * 递归组装树菜单
	 * @param ts
	 */
	private void recursiveNoRBACMenuTree(TreeStru ts){
		if(hasChildMenu(ts.getId())){
			List<SysMenu> childlist = getChildMenuNoRBAC(ts.getId());
			List<TreeStru> childmenu=new ArrayList();
			for(SysMenu sm:childlist){
				TreeStru cts= new TreeStru();
				cts.setId(sm.getCode());
				cts.setText(sm.getName());
				cts.setIconCls(sm.getIcon());
				cts.setSeq(sm.getSeq());
				cts.setDefUrl(sm.getDefUrl());
				cts.setData(sm);
				if(hasChildMenu(cts.getId())){
					cts.setState("closed");
					recursiveNoRBACMenuTree(cts);
				}else{
					cts.setState("open");
				}
				childmenu.add(cts);
			}
			ts.setState("closed");
			ts.setChildren(childmenu);
		}else{
			return;
		}
	}
	/**
	 * 组织有权限菜单
	 */
	private void recursiveRBACMenuTree(TreeStru ts,List<SysMenu> list){
		if(hasChildMenu(ts.getId())){
			List<SysMenu> childlist = getChildMenu(ts.getId(),list);
			List<TreeStru> childmenu=new ArrayList();
			for(SysMenu sm:childlist){
				TreeStru cts= new TreeStru();
				cts.setId(sm.getCode());
				cts.setText(sm.getName());
				cts.setIconCls(sm.getIcon());
				cts.setSeq(sm.getSeq());
				cts.setDefUrl(sm.getDefUrl());
				cts.setData(sm);
				if(hasChildMenu(cts.getId())){
					cts.setState("closed");
					recursiveRBACMenuTree(cts,list);
				}else{
					cts.setState("open");
				}
				childmenu.add(cts);
			}
			ts.setState("closed");
			ts.setChildren(childmenu);
		}else{
			return;
		}
	}
}
