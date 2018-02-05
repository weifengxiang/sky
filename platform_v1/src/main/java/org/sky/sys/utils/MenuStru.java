package org.sky.sys.utils;

import java.util.List;

import org.sky.sys.model.SysMenu;

/**
 * 系统菜单数据结构
 * @author wei
 *
 */
public class MenuStru {
	private SysMenu accordion;
	private List<TreeStru> tree;
	public SysMenu getAccordion() {
		return accordion;
	}
	public void setAccordion(SysMenu accordion) {
		this.accordion = accordion;
	}
	public List<TreeStru> getTree() {
		return tree;
	}
	public void setTree(List<TreeStru> tree) {
		this.tree = tree;
	}
}
