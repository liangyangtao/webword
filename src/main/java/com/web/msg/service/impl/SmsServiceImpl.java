package com.web.msg.service.impl;

import com.web.msg.UnbankSms;
import com.web.msg.UnbankSms.Sms;
import com.web.msg.service.SmsService;


public class SmsServiceImpl implements SmsService {

	private UnbankSms unbankSms;

	public void setUnbankSms(UnbankSms unbankSms) {
		this.unbankSms = unbankSms;
	}

	@Override
	public Sms sendSms(String mobile, int type,int appid) {
		Sms sms = unbankSms.sendSms(mobile, type,appid);
		// TODO 记录到数据库中, 日志
		return sms;
	}

	@Override
	public boolean checkSecurityCode(String mobile, String code) {
		return unbankSms.isValidate(mobile, code);
	}

}
