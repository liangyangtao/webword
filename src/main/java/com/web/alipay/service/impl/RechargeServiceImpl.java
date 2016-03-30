package com.web.alipay.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.database.bean.Alipayasynchronouslog;
import com.database.bean.AlipayasynchronouslogExample;
import com.database.bean.Alipaysynchronouslog;
import com.database.bean.AlipaysynchronouslogExample;
import com.database.bean.WordUserMoney;
import com.database.bean.WordUserMoneyExample;
import com.database.bean.WordUserMoneyLog;
import com.database.bean.WordUserMoneyLogExample;
import com.database.dao.AlipayasynchronouslogMapper;
import com.database.dao.AlipaysynchronouslogMapper;
import com.database.dao.WordUserMoneyLogMapper;
import com.database.dao.WordUserMoneyMapper;
import com.web.alipay.controller.AlipaySimpleController;
import com.web.alipay.service.RechargeService;
@Service
public class RechargeServiceImpl  implements RechargeService{
	private static final Logger LOGGER = Logger.getLogger(RechargeServiceImpl.class);
	@Autowired
	public AlipaysynchronouslogMapper returnMapper;
	@Autowired
	public AlipayasynchronouslogMapper notifyMapper;
	@Autowired
	public WordUserMoneyMapper usermoneyMapper;
	@Autowired
	public WordUserMoneyLogMapper usermoneylogMapper;
	
	@Override
	public void insertalipayreturn(Map<String,String> maps) {
		try{
		Alipaysynchronouslog syn = new Alipaysynchronouslog();
		syn.setOutTradeNo(maps.get("orderno"));
		syn.setInserttime(new Date());
		syn.setUserid(Integer.parseInt(maps.get("userId").toString()));
		returnMapper.insertSelective(syn);
		
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void insertalipaynotify(Map<String, String> maps) {
		//判断是否同步通知已经将业务逻辑处理，根据订单号查用户资金记录
		//如果有订单信息，只记日志
		//如果没有订单信息，用户资金记录表，用户资金表插入数据，并记录日志
		try{
		Alipayasynchronouslog ansy = new Alipayasynchronouslog();
		ansy.setOutTradeNo(maps.get("orderno"));
		ansy.setUserid(Integer.parseInt(maps.get("userId").toString()));
		notifyMapper.insertSelective(ansy);
		}catch(Exception e){
			e.printStackTrace();
		} 
		
	}

	
	

	@Override
	public void updatealipayreturn(Map<String, String> maps) {
		try{
		Alipaysynchronouslog ansy = new Alipaysynchronouslog();
		ansy.setOutTradeNo(maps.get("out_trade_no"));
		ansy.setSignType(maps.get("sign_type"));
		ansy.setTradeNo(maps.get("trade_no"));
		ansy.setIsSuccess(maps.get("is_success"));
		ansy.setTotalFee(maps.get("total_fee"));
		ansy.setSign(maps.get("sign"));
		//ansy.setBody(maps.get("body"));
		ansy.setPaymentType(maps.get("payment_type"));
		ansy.setBuyerId(maps.get("buyer_id"));
		ansy.setBuyerEmail(maps.get("buyer_email"));
		ansy.setSubject(maps.get("subject"));
		ansy.setNotifyId(maps.get("notify_id"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date notifytime = sdf.parse(maps.get("notify_time"));
		ansy.setNotifyTime(notifytime);
		ansy.setSellerId(maps.get("seller_id"));
		ansy.setSellerEmail(maps.get("seller_email"));
		ansy.setTradeStatus(maps.get("trade_status"));
		ansy.setNotifyType(maps.get("notify_type"));
		AlipaysynchronouslogExample example = new AlipaysynchronouslogExample();
		example.or().andOutTradeNoEqualTo(maps.get("out_trade_no").toString());
		returnMapper.updateByExampleSelective(ansy, example);
		LOGGER.info("更新同步表完成");
		}catch (Exception e){
			LOGGER.error("更新同步表失败");
			e.printStackTrace();
		}
		
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updatealipaynotify(Map<String, String> maps) {
		try{
			Alipayasynchronouslog ansy = new Alipayasynchronouslog();
			ansy.setOutTradeNo(maps.get("out_trade_no"));
			ansy.setTotalFee(maps.get("total_fee"));
			ansy.setBuyerId(maps.get("buyer_id"));
			ansy.setSignType(maps.get("sign_type"));
			ansy.setTradeNo(maps.get("trade_no"));
			ansy.setSign(maps.get("sign"));
			ansy.setBody(maps.get("body"));
			ansy.setPaymentType(maps.get("payment_type"));
			ansy.setBuyerEmail(maps.get("buyer_email"));
			ansy.setNotifyId(maps.get("notify_id"));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			Date notifytime = sdf.parse(maps.get("notify_time"));
			ansy.setNotifyTime(notifytime);
			ansy.setSellerId(maps.get("seller_id"));
			ansy.setSellerEmail(maps.get("seller_email"));
			ansy.setTradeStatus(maps.get("trade_status"));
			ansy.setNotifyType(maps.get("notify_type"));
			AlipayasynchronouslogExample example = new AlipayasynchronouslogExample();
			example.or().andOutTradeNoEqualTo(maps.get("out_trade_no"));
			notifyMapper.updateByExampleSelective(ansy, example);
			LOGGER.info("更新异步表完成");
			LOGGER.info("开始更新用户资金记录");
			userrecharge(maps);
			
			}catch (Exception e){
				e.printStackTrace();
				LOGGER.info("更新异步表失败");
			}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void userbindingorder(Map<String, String> maps) {
		try{
		Alipayasynchronouslog ansy = new Alipayasynchronouslog();
		ansy.setOutTradeNo(maps.get("orderno"));
		ansy.setUserid(Integer.parseInt(maps.get("userId").toString()));
		notifyMapper.insertSelective(ansy);
		Alipaysynchronouslog syn = new Alipaysynchronouslog();
		syn.setOutTradeNo(maps.get("orderno"));
		syn.setInserttime(new Date());
		syn.setUserid(Integer.parseInt(maps.get("userId").toString()));
		returnMapper.insertSelective(syn);
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	//@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
		public void userrecharge(Map<String,String> params) {
			WordUserMoney usermoney = new WordUserMoney();
			try{
				  
			//根据订单号查userId
						AlipayasynchronouslogExample example = new AlipayasynchronouslogExample();
						example.or().andOutTradeNoEqualTo(params.get("out_trade_no"));
						List<Alipayasynchronouslog> list  = notifyMapper.selectByExample(example);
						if(!list.isEmpty()){
									String total = params.get("total_fee");
									//根据订单号查是否已存在
									WordUserMoneyLogExample mlogexample = new WordUserMoneyLogExample();
									mlogexample.or().andOutTradeNoEqualTo(params.get("out_trade_no"));
									List<WordUserMoneyLog> logs = usermoneylogMapper.selectByExample(mlogexample);
									if(logs.isEmpty()){
										  WordUserMoneyLog usermoneylog = new WordUserMoneyLog();
										  usermoneylog.setAddMoney(Double.parseDouble(total));
										  usermoneylog.setType("add");
										  usermoneylog.setBuyType("before");
										  usermoneylog.setMoney(Double.parseDouble(total));
										  usermoneylog.setAddMoney(Double.parseDouble(total));
										  usermoneylog.setMoneyType("pay");
										  usermoneylog.setCreateTime(new Date());
										  usermoneylog.setOutTradeNo(params.get("out_trade_no"));
										  usermoneylog.setUserId(list.get(0).getUserid());
										  usermoneylogMapper.insertSelective(usermoneylog);
										  //根据userid查是否存在，存在更新，不存在插入
										  WordUserMoneyExample mexample = new WordUserMoneyExample();
										  mexample.or().andUserIdEqualTo(list.get(0).getUserid());
										  List<WordUserMoney> mlist = usermoneyMapper.selectByExample(mexample);
										  if(mlist.isEmpty()){
											  usermoney.setUserId(usermoneylog.getUserId());
											  usermoney.setCreateTime(new Date());
											  usermoney.setUpdateTime(new Date());
											// LOGGER.info("1total==="+Double.parseDouble(total));
											  usermoney.setMoney(Double.parseDouble(total));
											  usermoneyMapper.insertSelective(usermoney);
										  }else{
											 double m= mlist.get(0).getMoney();
											 m = m+Double.parseDouble(total);
											 usermoney.setUpdateTime(new Date());
											 usermoney.setMoney(m);
											 usermoney.setUserId(usermoneylog.getUserId());
											 usermoneyMapper.updateByExampleSelective(usermoney, mexample);
										  }
									}else{
										  WordUserMoneyExample mexample = new WordUserMoneyExample();
										  mexample.or().andUserIdEqualTo(list.get(0).getUserid());
										  List<WordUserMoney> tlist = usermoneyMapper.selectByExample(mexample);
										  usermoney.setMoney(tlist.get(0).getMoney());
										  usermoney.setUserId(tlist.get(0).getUserId());
										  
									}
						}
						LOGGER.info("更新用户资金记录完成");		
			}catch(Exception e){
				LOGGER.info("更新用户资金记录失败");	
				throw new RuntimeException();
			}
		}
}
