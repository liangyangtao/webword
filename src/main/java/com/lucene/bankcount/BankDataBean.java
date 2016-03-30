package com.lucene.bankcount;

import java.util.List;
import java.util.Map;

public class BankDataBean {
	//昨一天统计数
	private int onedaycount;
	//昨一天变化
	private double onedaychange;
	//昨七天统计数
	private int sevendaycount;
	//昨七天变化
	private double sevendaychange;
	//昨十五天统计数
	private int fifteendaycount;
	//昨十五天变化
	private double fifteendaychange;
	//日期数据集合
	private List<Map<String,Object>> dataList;
	//logo图片名称
	private String logoimgname;
	//银行名称
	private String bankname;
	
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getLogoimgname() {
		return logoimgname;
	}
	public void setLogoimgname(String logoimgname) {
		this.logoimgname = logoimgname;
	}
	public int getOnedaycount() {
		return onedaycount;
	}
	public void setOnedaycount(int onedaycount) {
		this.onedaycount = onedaycount;
	}
	public double getOnedaychange() {
		return onedaychange;
	}
	public void setOnedaychange(double onedaychange) {
		this.onedaychange = onedaychange;
	}
	public int getSevendaycount() {
		return sevendaycount;
	}
	public void setSevendaycount(int sevendaycount) {
		this.sevendaycount = sevendaycount;
	}
	public double getSevendaychange() {
		return sevendaychange;
	}
	public void setSevendaychange(double sevendaychange) {
		this.sevendaychange = sevendaychange;
	}
	public int getFifteendaycount() {
		return fifteendaycount;
	}
	public void setFifteendaycount(int fifteendaycount) {
		this.fifteendaycount = fifteendaycount;
	}
	public double getFifteendaychange() {
		return fifteendaychange;
	}
	public void setFifteendaychange(double fifteendaychange) {
		this.fifteendaychange = fifteendaychange;
	}
	public List<Map<String, Object>> getDataList() {
		return dataList;
	}
	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}
	
	
}
