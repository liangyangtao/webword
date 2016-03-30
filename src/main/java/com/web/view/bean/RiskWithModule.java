package com.web.view.bean;

import java.util.List;

import com.database.bean.Module;

public class RiskWithModule {
	
	private Module module;
	
	private List<Risk> riskList;

	
	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public List<Risk> getRiskList() {
		return riskList;
	}

	public void setRiskList(List<Risk> riskList) {
		this.riskList = riskList;
	}
	
	
	
	
}
