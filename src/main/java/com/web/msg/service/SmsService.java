package com.web.msg.service;

import com.web.msg.UnbankSms.Sms;


public interface SmsService {

	/**
	 * 手机号
	 * 
	 * @param mobile
	 * @param type
	 * @param appid
	 * @return
	 */
	Sms sendSms(String mobile, int type,int appid);

	/**
	 * 手机号, 验证码
	 * 
	 * @param mobile
	 * @param code
	 * @return
	 */
	boolean checkSecurityCode(String mobile, String code);

}
