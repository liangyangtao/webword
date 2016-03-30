package com.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.unbank.chart.highchart.type.ThemeType;
import com.unbank.chart.util.GsonUtil;
import com.unbank.model.UnbankModel;
import com.unbank.model.param.AreaParam;
import com.unbank.model.param.BaseParam;
import com.unbank.model.param.DateParam;
import com.unbank.model.param.IndustryParam;
import com.unbank.model.param.Params;
import com.unbank.plugin.service.PluginService;

/**
 * 测试时打开 activemq-context.xml debug 注释, web环境请关掉
 * 
 * @author zile
 * 
 */
public class TestRpc {

	public static ApplicationContext ctx;

	static {
		String[] xmlCfg = new String[] { "config/activemq-context.xml" };
		ctx = new FileSystemXmlApplicationContext(xmlCfg);
	}

	public void rpc() {
		PluginService service = ctx.getBean("clientPluginService",
				PluginService.class);

		int id = getPluginId();

		try {
			// 模型
			// UnbankModel um = service.plugin(id, params());
			UnbankModel um = service.template(id, params());

			System.out.println();
			System.out.println("Data Module:");
			System.out.println(GsonUtil.toJsonTree(um.getData()));
			System.out.println();
			System.out.println("Template:");
			System.out.println(um.getTemplate());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("远程调用异常");
			
		}

	}

	/**
	 * 测试的插件ID
	 * 
	 * @return
	 */
	private int getPluginId() {
		return 3;
	}

	/**
	 * 测试的参数
	 * 
	 * @return
	 */
	private Params params() {
		// build params
		Params params = new Params();
		// base
		BaseParam bp = new BaseParam();
		bp.setType(ThemeType.DARK_UNICA);
		// bp.setLinkPrefix("/resources/images");

		// date
		DateParam dp = new DateParam();
		dp.setFrom("2014-01-01");
		dp.setTo("2014-02-02");
		// industry
		IndustryParam ip = new IndustryParam();
		ip.setIndustryId(0);
		// area
		AreaParam ap = new AreaParam();
		ap.setAreaId(1);

		//
		params.put(DateParam.class, dp);
		params.put(IndustryParam.class, ip);
		params.put(AreaParam.class, ap);
		params.put(BaseParam.class, bp);
		return params;
	}

	public static void main(String[] args) {
		TestRpc t = new TestRpc();
		t.rpc();
	}

}
