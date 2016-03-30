package com.unbank.test;

import java.util.ArrayList;
import java.util.List;

public class OutlineBean {

	private String id;
	private String text;
	private Boolean expanded = true;
	private Boolean leaf = null;
	private Object obj;
	private List<OutlineBean> children = new ArrayList<OutlineBean>();
	private Boolean checked = null;
	private String qtip;
	private Integer flag;

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

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public List<OutlineBean> getChildren() {
		return children;
	}

	public void setChildren(List<OutlineBean> children) {
		this.children = children;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getQtip() {
		return qtip;
	}

	public void setQtip(String qtip) {
		this.qtip = qtip;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}
