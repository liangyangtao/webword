package com.web.alipay.util;

import java.util.Map;

public class InfoVerify {
	
	
		/**
		 * 验证通知合法性
		 * @param Map<String, String>  params
		 * @return boolean
		 */
	
	   public static boolean verify(Map<String, String> params) {
		   return AlipayNotify.verify(params);
	   }

}
