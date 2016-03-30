package com.unbank.basic.bean;
/**
 * 
 * 类描述:登录用户详细信息实体类
 *
 * @author anzhao
 * @version 1.0
 * 创建时间：2014-8-8 下午4:25:41  
 * JDK版本：sun jdk 1.6
 */
public class LoginUser {
	private String userId;//用户ID
	private String userName;//用户姓名
	private String userRole;//用户角色
	private String password;//用户登录密码
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	
	

}
