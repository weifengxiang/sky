package org.sky.sys.utils;

import java.util.ArrayList;
import java.util.List;

import org.sky.sys.client.SysDictItemMapper;
import org.sky.sys.client.SysMenuMapper;
import org.sky.sys.client.SysOperationMapper;
import org.sky.sys.model.SysDictItem;
import org.sky.sys.model.SysDictItemExample;
import org.sky.sys.model.SysMenu;
import org.sky.sys.model.SysMenuExample;
import org.sky.sys.model.SysOperation;
import org.sky.sys.model.SysOperationExample;
import org.sky.sys.utils.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;

/**
 * 
* @Title: ApplicationCached.java 
* @Package org.es.sys.utils 
* @Description: TODO(全局缓存工具类) 
* @author 位峰祥   
* @date 2016-6-3 下午4:50:44 
* @version V1.0
 */
public class ApplicationCached {
	
	/**
	 * 系统所有菜单
	 */
	private static List<SysMenu> menulist;
	/**
	 * 系统所有操作
	 */
	private static List<SysOperation> operadionlist;
	
	/**
	 * 字典的所有字典项
	 */
	private static List<SysDictItem> dictItemlist;
	
	
	static{
		try {
			SysMenuMapper menuMapper=(SysMenuMapper)BspUtils.getBean(SysMenuMapper.class);
			SysOperationMapper opeMapper=(SysOperationMapper)BspUtils.getBean(SysOperationMapper.class);
			SysDictItemMapper dictItemMapper=(SysDictItemMapper)BspUtils.getBean(SysDictItemMapper.class);
			SysMenuExample me = new SysMenuExample();
			me.setOrderByClause(" PARENT_CODE asc,SEQ asc ");
			menulist=menuMapper.selectByExample(me);
			
			SysOperationExample ope= new SysOperationExample();
			ope.setOrderByClause(" MENU_CODE asc, SEQ asc ");
			operadionlist=opeMapper.selectByExample(ope);
			
			SysDictItemExample die = new SysDictItemExample();
			die.setOrderByClause(" CODE asc,SEQ asc ");
			dictItemlist=dictItemMapper.selectByExample(die);
			
			if(RedisUtil.afterPropertiesSet()){
				RedisUtil.jedisPool.getResource().set("dict", JsonUtils.obj2json(dictItemlist));
				System.out.println("=======================================");
				System.out.println(RedisUtil.jedis.get("dict"));
				System.out.println("=======================================");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	* @Title: getDictItemByDicType 
	* @Description: TODO(根据字典类型获得字典项) 
	* @param @param dicType
	* @param @return    设定文件 
	* @return List<PubDictItem>    返回类型 
	* @throws
	 */
	public static List<SysDictItem> getDictItemByDicType(String dicType){
		List<SysDictItem> list = ApplicationCached.dictItemlist;
		List<SysDictItem> typelist = new ArrayList();
		for(SysDictItem it : list){
			if(dicType.equals(it.getDictCode())){
				typelist.add(it);
			}
		}
		return typelist;
	}
	public static void init(){
		
	}
	public static List<SysMenu> getMenulist() {
		return menulist;
	}
	public static List<SysOperation> getOperadionlist() {
		return operadionlist;
	}
	public static List<SysDictItem> getDictItemlist() {
		return dictItemlist;
	}
	
}
