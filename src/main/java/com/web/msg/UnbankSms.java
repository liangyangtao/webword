package com.web.msg;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class UnbankSms {

	// public static final int SECURITY_CODE = 1;
	// public static final int ACTIVATION_CODE = 2;

	// private static final String SECURITY = "验证码";
	// private static final String ACTIVATION = "激活码";

	protected static Logger logger = Logger.getLogger(UnbankSms.class);

	private Map<String, PhoneSecurity> p = new ConcurrentHashMap<String, PhoneSecurity>();

	private String url;

	private String account;

	private String password;

	private String content0;
	private String content1;
	private String content2;
	
	public void setContent2(String content2) {
		this.content2 = content2;
	}

	/**
	 * 验证码的取值范围
	 */
	private int codeSize;

	/**
	 * 长度, 补零
	 */
	private int codeLen;

	/**
	 * 过期时间
	 */
	private int expiryTime = 120;

	private UnbankSms() {
	}

	/**
	 * 验证码是否过期
	 * 
	 * @param mobile
	 * @return
	 */
	public boolean isValidate(String mobile, String code) {
		try {
			PhoneSecurity ps = p.get(mobile);
			long t = System.currentTimeMillis() - ps.getEffectiveDate();

			boolean validate = ps.getSecurityCode().equals(code)
					&& t < (expiryTime * 1000);
			if (validate) {
				logger.info("code validate pass! phone:" + mobile + " code:"
						+ code);
			} else {
				logger.info("code validate not pass! phone:" + mobile
						+ " code:" + code);
			}
			return validate;
		} catch (Exception e) {
			logger.error("code validate error!", e);
			return false;
		}
	}

	public Sms sendSms(String mobile, int type,int appid) {
		Sms sms = new Sms();

		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);

		// client.getParams().setContentCharset("GBK");
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType",
				"application/x-www-form-urlencoded;charset=UTF-8");

		// String content = new String("您的验证码是：1234。请不要把验证码泄露给其他人。");
		String security = code();
		//appid:知识创享1，风险预警0
		String ct = "";
		if (type == 0) {
			if(appid==0){
				ct = content0;
			}else if(appid==1){
				ct = content2;
			}
		
		} else if (type == 1) {
			if(appid==0){
				ct = content1;
			}
			
		} else if (type == 2) {
			if(appid==0){
				ct = content1;
			}
			
		}

		// 提交短信
		NameValuePair[] data = {
				new NameValuePair("account", account),
				new NameValuePair("password", password),
				new NameValuePair("mobile", mobile),
				new NameValuePair("content", MessageFormat.format(ct, security)) };

		method.setRequestBody(data);

		try {
			client.executeMethod(method);

			String SubmitResult = method.getResponseBodyAsString();

			// System.out.println(SubmitResult);

			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();

			String code = root.elementText("code");
			String msg = root.elementText("msg");
			String smsid = root.elementText("smsid");

			// System.out.println(code);
			// System.out.println(msg);
			// System.out.println(smsid);

			sms.setCode(code);
			sms.setMsg(msg);
			sms.setSmsid(smsid);
			sms.setSecurityCode(security);

			if (code.equals("2")) {
				p.put(mobile,
						new PhoneSecurity(security, System.currentTimeMillis()));
				sms.setSuccess(true);
			}

			logger.info("send sms info, phone:" + mobile + " code:" + code
					+ " msg:" + msg + " smsid:" + smsid);

		} catch (HttpException e) {
			logger.error("send sms error!", e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("send sms error!", e);
			e.printStackTrace();
		} catch (DocumentException e) {
			logger.error("send sms error!", e);
			e.printStackTrace();
		}

		return sms;
	}

	/**
	 * 获取验证码
	 * 
	 * @return
	 */
	private String code() {
		int num = (int) (codeSize * Math.random());
		String code = String.valueOf(num);
		StringBuilder sb = new StringBuilder();
		int loop = codeLen - code.length();
		for (int i = 0; i < loop; i++) {
			sb.append("0");
		}
		sb.append(code);
		// System.out.println(sb.toString());
		return sb.toString();
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setContent0(String content0) {
		this.content0 = content0;
	}

	public void setContent1(String content1) {
		this.content1 = content1;
	}
	public void setCodeSize(int codeSize) {
		this.codeSize = codeSize;
	}
	
	public void setCodeLen(int codeLen) {
		this.codeLen = codeLen;
	}

	public void setExpiryTime(int expiryTime) {
		this.expiryTime = expiryTime;
	}

	public class Sms {

		private String code;
		private String msg;
		private String smsid;

		private String securityCode;

		private boolean success;

		public Sms() {
		}

		public Sms(String code, String msg, String smsid) {
			this.code = code;
			this.msg = msg;
			this.smsid = smsid;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public String getSmsid() {
			return smsid;
		}

		public void setSmsid(String smsid) {
			this.smsid = smsid;
		}

		public String getSecurityCode() {
			return securityCode;
		}

		public void setSecurityCode(String securityCode) {
			this.securityCode = securityCode;
		}

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

	}

	private static class PhoneSecurity {
		private String securityCode;
		private long effectiveDate;

		private PhoneSecurity(String securityCode, long effectiveDate) {
			this.securityCode = securityCode;
			this.effectiveDate = effectiveDate;
		}

		public String getSecurityCode() {
			return securityCode;
		}

		public long getEffectiveDate() {
			return effectiveDate;
		}

	}

	/**
	 * test
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Date d = new Date();
		long effectiveDate = d.getTime();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long expiryDate = new Date().getTime();
		System.out.println((expiryDate - effectiveDate));

		//

		UnbankSms us = new UnbankSms();
		us.setExpiryTime(3);
		us.p.put("123", new PhoneSecurity("1234", System.currentTimeMillis()));
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(us.isValidate("123", "1234"));
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(us.isValidate("123", "1234"));

	}
}
