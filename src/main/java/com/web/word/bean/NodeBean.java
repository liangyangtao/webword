package com.web.word.bean;

import java.util.ArrayList;
import java.util.List;

public class NodeBean extends TableBean {

	private Integer nodeId;
	private Integer pid;
	private Integer articleId;
	private String nodeNameStatic;
	private String nodeNameDynamic;
	private Integer order;
	private Integer realId;
	
	private String content="";
	
	private Boolean allowDrag = true;
	private Boolean allowDrop = true;
	
	private Integer nodeType;//内容节点为1,0是封面,2是包含内容插件节点
	private String icon ="normal";
	private String contentId;//内容节点的id
	private Integer contentpluginId=0;//内容插件的id
	
	private List<NodeBean> children = new ArrayList<NodeBean>();
	private List<String> ids = new ArrayList<String>();
	
	private String idStr="";
	public NodeBean(){
		
	}
	
	public NodeBean(Integer id, Integer pid, Integer articleId, String title){
		this.id = id;
		this.nodeId = id;
		this.pid = pid;
		this.articleId = articleId;
		this.nodeNameStatic = title;
	}
	
	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.id = nodeId;
		this.nodeId = nodeId;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getNodeNameStatic() {
		return nodeNameStatic;
	}

	public void setNodeNameStatic(String nodeNameStatic) {
		this.text = nodeNameStatic;
		this.nodeNameStatic = nodeNameStatic;
	}

	public String getNodeNameDynamic() {
		return nodeNameDynamic;
	}

	public void setNodeNameDynamic(String nodeNameDynamic) {
		this.nodeNameDynamic = nodeNameDynamic;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public List<NodeBean> getChildren() {
		return children;
	}

	public void setChildren(List<NodeBean> children) {
		this.children = children;
	}

	public Integer getRealId() {
		return realId;
	}

	public void setRealId(Integer realId) {
		this.realId = realId;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "NodeBean [nodeId=" + nodeId + ", pid=" + pid + ", articleId=" + articleId + ", nodeNameStatic=" + nodeNameStatic + "]";
	}

	public Boolean getAllowDrag() {
		return allowDrag;
	}

	public void setAllowDrag(Boolean allowDrag) {
		this.allowDrag = allowDrag;
	}

	public Boolean getAllowDrop() {
		return allowDrop;
	}

	public void setAllowDrop(Boolean allowDrop) {
		this.allowDrop = allowDrop;
	}

	public Integer getNodeType() {
		return nodeType;
	}

	public void setNodeType(Integer nodeType) {
		this.nodeType = nodeType;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public Integer getContentpluginId() {
		return contentpluginId;
	}

	public void setContentpluginId(Integer contentpluginId) {
		this.contentpluginId = contentpluginId;
	}

	public String getIdStr() {
		return idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

}
