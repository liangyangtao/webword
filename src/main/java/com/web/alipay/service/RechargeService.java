package com.web.alipay.service;

import java.util.Map;

import com.database.bean.Alipayasynchronouslog;
import com.database.bean.WordUserMoney;
import com.database.bean.WordUserMoneyLog;

public interface RechargeService {
	/**
	 * 充值开始，用户与订单绑定
	 */
	void userbindingorder(Map<String,String> maps);
	
	/**
	 * 同步通知表 insert
	 */

		void insertalipayreturn(Map<String,String> maps);
		/**
		 * 同步通知表 update
		 */
		void updatealipayreturn(Map<String,String> maps);
		
		
		/**
		 * 修改用户资金表及资金记录表
		 */
		void userrecharge(Map<String,String> params);
		
		/**
		 * 异步通知表 insert
		 */
		void insertalipaynotify(Map<String,String> maps);
		
		/**
		 * 异步通知表update notify
		 */
		void updatealipaynotify(Map<String,String> maps);
		/**
		 * 异步通知修改用户资金表及资金记录表
		 */
		
		
}
