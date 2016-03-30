package com.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.database.bean.Module;
import com.google.gson.Gson;
import com.web.view.service.RiskMyService;

public class ModuleInit  extends TimerTask {
	private ServletContext context = null;
	public static List<Module> modules = new ArrayList<Module>();
	
	public ModuleInit(ServletContext context) {
		this.context = context;
	}

	public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}
	@Override
	public void run() {

		ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		RiskMyService riskMyService =  ctx.getBean(RiskMyService.class);
		modules = riskMyService.allmodules();
		Gson gson = new Gson();
	}

}
