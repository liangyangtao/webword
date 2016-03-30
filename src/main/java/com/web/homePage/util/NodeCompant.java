/*
 * <p>Title: 知识自动化平台</p>
 * <p>Description: NodeCompant.java</p>
 * <p>Copyright: Copyright (c) 2015 北京银联信投资顾问有限责任公司，版权所有. </p>
 * <p>Company: 北京银联信投资顾问有限责任公司</p>
 * @author knight
 * @date 2015-5-8
 * @version 1.0
 */
package com.web.homePage.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.web.word.bean.NodeBean;


/**
 * <p>Title: NodeCompant</p>
 * <p>Description: TODO</p>
 * @author knight
 * @date 2015-5-8
 */
@Component
public class NodeCompant {
	/**
	 * 递归，生成Ext tree需要树形结构目录
	 * 
	 * @param targetList
	 * @param originalList
	 * @param pid
	 */
	public void recursion(List<NodeBean> targetList, List<NodeBean> originalList, Integer pid) {
		for(NodeBean bean : originalList) {
			if(bean.getPid().equals(pid)) {
				if(bean.getOrder()!=null&&bean.getOrder()==0&&bean.getPid()==0&&bean.getNodeNameStatic().equals("封面")){//是封面
					bean.setAllowDrag(false);
					bean.setAllowDrop(false);
					bean.setLeaf(true);
				}
				targetList.add(bean);
				recursion(bean.getChildren(), originalList, bean.getNodeId());
			}
		}
	}
	/**
	 * 递归，生成Ext tree需要树形结构目录
	 * 大纲区专用
	 * @param targetList
	 * @param originalList
	 * @param pid
	 */
	public void recursionNode(List<NodeBean> targetList, List<NodeBean> originalList, Integer pid) {
		for(NodeBean bean : originalList) {
			if(bean.getPid().equals(pid)) {
				//System.out.println("getNodeNameStatic="+bean.getNodeNameStatic());
				if(bean.getOrder()!=null&&bean.getOrder()==0&&bean.getPid()==0&&bean.getNodeNameStatic().equals("封面")){//是封面
					bean.setAllowDrag(false);
					bean.setAllowDrop(false);
					bean.setLeaf(true);
				}
				if(bean.getContentpluginId()!=null&&bean.getContentpluginId()!=0){//是内容插件
					bean.setIcon("images/contentplugin.png");
				}
				if(bean.getContent()!=null){
						//addChildNode(bean);
					bean.setContent("");
				}
				//System.out.println("name="+bean.getNodeNameStatic());
				targetList.add(bean);
				recursionNode(bean.getChildren(), originalList, bean.getNodeId());
			}
		}
	}
	//添加子元素
	public void addChildNode(NodeBean node){
		String str= node.getContent();//str null 空会报错
	//String strreg= "<p (class=contentname|class=\"contentname\")><strong([^>]+)>([^>]*)</strong>[^>]*</p>";//过滤的正则
		String strreg= "<p (class=contentname|class=\"contentname\")>[^<]*<strong name=([^>]*)>([^>]*)</strong>[^\t]*?</p>";//过滤的正则
		Pattern pattern = Pattern.compile(strreg, Pattern.UNICODE_CASE+ Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		while(matcher.find()){
			//System.out.println("matcher.group(2)="+matcher.group(2));
			//System.out.println("matcher.group(3)="+matcher.group(3));
			NodeBean newnode = new NodeBean();
			newnode.setNodeNameStatic(matcher.group(3));
			newnode.setLeaf(true);
			newnode.setIcon("images/wenz.png");
			newnode.setNodeType(1);//
			if(matcher.group(2)!=null){
				int startIndex = matcher.group(2).indexOf("\"");
				int endIndex = matcher.group(2).lastIndexOf("\"");
				if(startIndex>-1&&endIndex>-1){
					String contentname = matcher.group(2).substring(+1,matcher.group(2).lastIndexOf("\""));
					//System.out.println("contentname="+contentname);
					newnode.setContentId(contentname);
					//newnode.setId(contentname);
					node.getChildren().add(newnode);
				}
			}
		}
	}
	/**
	 * 递归，生成Ext tree需要树形结构目录
	 * 文档区专用 删除封面
	 * @param targetList 返回的列表
	 * @param originalList 原始的列表
	 * @param pid开始的pid
	 * @param flag 0:没有封面
	 */
	public void articleNode(List<NodeBean> targetList, List<NodeBean> originalList, Integer pid,Integer flag) {
		for(NodeBean bean : originalList) {
			if(bean.getPid().equals(pid)) {
				if(bean.getOrder()!=null&&bean.getOrder()==0&&bean.getPid()==0&&bean.getNodeNameStatic().equals("封面")){//是封面
					bean.setAllowDrag(false);
					bean.setAllowDrop(false);
					bean.setLeaf(true);
					if(flag==0){
						continue;//封面不处理
					}
				}
				targetList.add(bean);
				recursion(bean.getChildren(), originalList, bean.getNodeId());
			}
		}
	}
	/**
	 * 找到节点的nodeId
	 * @param targetList
	 * @param originalList
	 * @param pid
	 */
	public void beanByNodeId(List<NodeBean> targetList,List<NodeBean> returnList,Integer nodeId) {
		for(NodeBean bean : targetList) {
			if(bean.getNodeId().equals(nodeId)) {
				returnList.add(bean);
				//System.out.println("nodeBean1="+bean.getContent());
				return ;
				//return nodeBean;
			}else if(bean.getChildren().size()>0){//有子节点clone()
				beanByNodeId(bean.getChildren(),returnList,nodeId);
			}
		}
		//return nodeBean;
	}
	/*
	 * 递归生成list 返回不包含节点的子节点
	 */
	public  void getNodeList(List<NodeBean> nodes,List<NodeBean> newnodes,int realId){
		realId+=1;
		for(NodeBean bean : nodes){
			bean.setRealId(realId);
			if(bean.getContent()!=null){
				//bean.setContent(setTitle(realId,bean.getContent()));//规则化标题的内容
			}
			newnodes.add(bean);
			if(bean.getChildren().size()>0){
				getNodeList(bean.getChildren(),newnodes,realId);
			}
		}
	}
}
