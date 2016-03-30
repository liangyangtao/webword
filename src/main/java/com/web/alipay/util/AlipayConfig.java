package com.web.alipay.util;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088511976347335";
	
	// 收款支付宝账号，一般情况下收款账号就是签约账号
	public static String seller_email = "ylxbanker@163.com";
	// 商户的私钥
	public static String key = "xaak5yw562wwrnzjy1n7mzoia1igb4hy";

	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\alipaylog\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "MD5";
	
	//接口名称
		public static String service = "create_direct_pay_by_user";
		//支付类型 默认值为：1（商品购买）。
		public static String payment_type = "1";
		//卖家别名支付宝账号 当签约账号就是收款账号时，请务必使用参数seller_id，即seller_id的值与partner的值相同。
		public static String seller_account_name = "";
		//超时时间 设置未付款交易的超时时间，一旦超时，该笔交易就会自动被关闭。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。该参数数值不接受小数点，如1.5h，可转换为90m。
		public static String it_b_pay = "1m";
		//同步通知地址return_url
		public static String return_url = "webword/alipay/alipayreturn.do";
		//异步通知地址
		public static String notify_url = "webword/jsp/alipay/notify_url.jsp";
		//商品名称
		public static String commodity="创享币";
		

}
