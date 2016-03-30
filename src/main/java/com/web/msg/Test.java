package com.web.msg;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.web.msg.service.SmsService;

public class Test {

	public static ApplicationContext ctx;

	static {
		String[] xmlCfg = new String[] {
				"config/spring-context.xml" };
		ctx = new FileSystemXmlApplicationContext(xmlCfg);
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void doTest() throws Exception {
		SmsService service = ctx.getBean(SmsService.class);
		service.sendSms("15011568102", 1,0);
	}

	public static void main(String[] args) {
		Test t = new Test();
		try {
			t.doTest();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
