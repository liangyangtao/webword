package com.listener;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class TimerListener implements ServletContextListener {

	
	private Timer timer;

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		if(timer!=null)
			timer.cancel();
			sce.getServletContext().log("定时器销毁~~~");
			System.out.println("定时任务结束~~");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		timer = new Timer(true);
		timer.schedule(new ModuleInit(sce.getServletContext()), 10000, 1000*60*60);
	}

}
