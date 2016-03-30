package com.web.alipay.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.web.alipay.controller.AlipaySimpleController;
import com.web.alipay.service.RechargeService;

public class InfoTransfer {
	
	private static final Logger LOGGER = Logger.getLogger(InfoTransfer.class);
	public static String transfer(	String count , String orderno,String url){
		Map<String , String> paramsMap = new HashMap<String,String>();
		//订单信息
		paramsMap.put("service", AlipayConfig.service);
		paramsMap.put("partner", AlipayConfig.partner);
		paramsMap.put("_input_charset", AlipayConfig.input_charset);
		paramsMap.put("return_url", url+AlipayConfig.return_url);
		LOGGER.info("同步通知地址======"+ url+AlipayConfig.return_url);
		paramsMap.put("notify_url", url+AlipayConfig.notify_url);
		LOGGER.info("异步通知地址======"+url+AlipayConfig.notify_url);
		paramsMap.put("out_trade_no",orderno);
		paramsMap.put("subject", AlipayConfig.commodity);
		paramsMap.put("payment_type", AlipayConfig.payment_type);
		paramsMap.put("total_fee", count);
		paramsMap.put("seller_id", AlipayConfig.partner);
		return AlipaySubmit.buildRequest(paramsMap, "POST", "SUBMIT");
	}
	
	public static String getorderno(){
		SimpleDateFormat simdata1 = new SimpleDateFormat("yyyyMMddHHmmss");
		String createOrderTime = simdata1.format(new Date());
		String orderno= createOrderTime+CreateMerchantOrderId(8);
		return orderno;
		
	}
	public static String CreateMerchantOrderId(int code_len) {   
        int count = 0;   
        char str[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };   
        StringBuffer pwd = new StringBuffer("");   
        Random r = new Random();   
        while (count < code_len) {   
            int i = Math.abs(r.nextInt(10));   
            if (i >= 0 && i < str.length) {   
                pwd.append(str[i]);   
                count++;   
            }   
        }   
        return pwd.toString();   
    }

}
