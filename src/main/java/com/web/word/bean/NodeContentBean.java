/*
 * <p>Title: 知识自动化平台</p>
 * <p>Description: NodeContentBean.java</p>
 * <p>Copyright: Copyright (c) 2015 北京银联信投资顾问有限责任公司，版权所有. </p>
 * <p>Company: 北京银联信投资顾问有限责任公司</p>
 * @author knight
 * @date 2015-5-8
 * @version 1.0
 */
package com.web.word.bean;

/**
 * <p>Title: NodeContentBean</p>
 * <p>Description: TODO</p>
 * @author knight
 * @date 2015-5-8
 */
public class NodeContentBean {
	private Integer contentId;
	private String content;
	private Integer articleId;
	private Integer nodeId;

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	@Override
	public String toString() {
		return "ContentBean [contentId=" + contentId + ", content=" + content + ", articleId=" + articleId + ", nodeId=" + nodeId + "]";
	}

}
