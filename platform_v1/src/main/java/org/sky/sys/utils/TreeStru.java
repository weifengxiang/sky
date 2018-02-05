package org.sky.sys.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeStru {

	private String id;
	private String text;
	private String state;
	private String iconCls;
	private String defUrl;
	private String domId;
	private boolean checked;
	private int seq;
	private Object data= new Object();
	private List<TreeStru> children=new ArrayList();
	
	
	public List<TreeStru> getChildren() {
		return children;
	}
	public void setChildren(List<TreeStru> children) {
		this.children = children;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getDomId() {
		return domId;
	}
	public void setDomId(String domId) {
		this.domId = domId;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getDefUrl() {
		return defUrl;
	}
	public void setDefUrl(String defUrl) {
		this.defUrl = defUrl;
	}
	
}
