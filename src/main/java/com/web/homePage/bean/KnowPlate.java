/*
 * <p>Title: 知识自动化平台</p>
 * <p>Description: KnowPlate.java</p>
 * <p>Copyright: Copyright (c) 2015 北京银联信投资顾问有限责任公司，版权所有. </p>
 * <p>Company: 北京银联信投资顾问有限责任公司</p>
 * @author knight
 * @date 2015-5-13
 * @version 1.0
 */
package com.web.homePage.bean;

/**
 * <p>Title: KnowPlate</p>
 * <p>Description: TODO</p>
 * @author knight
 * @date 2015-5-13
 */
public class KnowPlate {
	private int plate_id;
	private int pid;
	private String plate_name;
	public int getPlate_id() {
		return plate_id;
	}
	public void setPlate_id(int plate_id) {
		this.plate_id = plate_id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getPlate_name() {
		return plate_name;
	}
	public void setPlate_name(String plate_name) {
		this.plate_name = plate_name;
	}
}
