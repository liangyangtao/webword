package com.web.view.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.database.bean.Article;
import com.database.bean.WordJournal;
import com.database.bean.WordResource;
import com.database.bean.WordResourceExample;
import com.database.bean.WordShoppingCart;
import com.database.bean.WordShoppingCartExample;
import com.database.bean.WordUserMoney;
import com.database.bean.WordUserMoneyExample;
import com.database.bean.WordUserMoneyLog;
import com.database.dao.ArticleMapper;
import com.database.dao.WordJournalMapper;
import com.database.dao.WordResourceMapper;
import com.database.dao.WordShoppingCartMapper;
import com.database.dao.WordUserMoneyLogMapper;
import com.database.dao.WordUserMoneyMapper;
@Service
public class DirectBuyService {

	@Autowired
	WordJournalMapper wordJournalMapper;
	@Autowired
	WordUserMoneyMapper wordUserMoneyMapper;
	@Autowired
	WordResourceMapper wordResourceMapper;
	@Autowired
	WordUserMoneyLogMapper wordUserMoneyLogMapper;
	@Autowired
	ArticleMapper articleMapper;
	@Autowired
	WordShoppingCartMapper  wordShoppingCartMapper;


	/**
	 * 直接购买
	 * @param resoureType
	 * @param articleId
	 * @param journalId
	 * @return
	 */
	@Transactional
	public Map<String, Object> prevBuy(String resoureType, Integer articleId, Integer journalId, Integer userId) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		//用户账户余额
		Double balance = 0.0;
		//此次购买所需创享币
		Double needMoney = 0.0;
		//差额
		Double needRecharge = 0.0;
		Date date = new Date();
		
		//验证是否已购买
		if("journal".equals(resoureType)){
			WordResourceExample e = new WordResourceExample();
			e.or().andUserIdEqualTo(userId).andResourceTypeEqualTo(resoureType).andJournalIdEqualTo(journalId).andEndTimeGreaterThanOrEqualTo(date);
			List<WordResource> wordResources= wordResourceMapper.selectByExample(e);
			if(wordResources != null && !wordResources.isEmpty()){
				map.put("result", -1);
				map.put("msg", "已经购买过该期刊");
				return map;
			}
		}else if("journalarticle".equals(resoureType)){
			WordResourceExample e = new WordResourceExample();
			e.or().andUserIdEqualTo(userId).andResourceTypeEqualTo("journal").andJournalIdEqualTo(journalId).andEndTimeGreaterThanOrEqualTo(date);
			e.or().andUserIdEqualTo(userId).andResourceTypeEqualTo(resoureType).andArticleIdEqualTo(articleId);
			List<WordResource> wordResources= wordResourceMapper.selectByExample(e);
			if(wordResources!=null && !wordResources.isEmpty()){
				map.put("result", -1);
				map.put("msg", "已经购买过该期刊");
				return map;
			}
		}else if("article".equals(resoureType)){
			WordResourceExample e = new WordResourceExample();
			e.or().andUserIdEqualTo(userId).andResourceTypeEqualTo(resoureType).andArticleIdEqualTo(articleId);
			List<WordResource> wordResources= wordResourceMapper.selectByExample(e);
			if(wordResources!=null && !wordResources.isEmpty()){
				map.put("result", -1);
				map.put("msg", "已经购买过该期刊");
				return map;
			}
		}
		
		//获取用户资金信息
		WordUserMoney wordUserMoney = null;
		WordUserMoneyExample example = new WordUserMoneyExample();
		example.or().andUserIdEqualTo(userId);
		List<WordUserMoney> list = wordUserMoneyMapper.selectByExample(example);
		if (list != null && list.size() == 1){
			wordUserMoney = list.get(0);
			balance = wordUserMoney.getMoney();
		}
		//根据资源类型获取购买所需的创享币，如果是选择购买整个期刊则获取期刊的价格，
		if ("journal".equals(resoureType)){
			WordJournal wordJournal = wordJournalMapper.selectByPrimaryKey(journalId);
			if (wordJournal != null){
				needMoney = wordJournal.getPrice();
			}
		}else{
			Article article = articleMapper.selectByPrimaryKey(articleId);
			if (article != null){
				needMoney = article.getArticlePrice();
			}
		}
		
		if (balance < needMoney){		
			needRecharge = needMoney - balance;
			map.put("result", 0);
			map.put("needRecharge", needRecharge);
		}else{
			map.put("result", 1);
		}
		return map;
	}
	
	/**
	 * 直接购买
	 * @param resoureType
	 * @param articleId
	 * @param journalId
	 * @param userId
	 * @return
	 */
	@Transactional
	public Map<String, Object> buy(String resoureType, Integer articleId, Integer journalId, Integer userId) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		//用户账户余额
		Double balance = 0.0;
		//此次购买所需创享币
		Double needMoney = 0.0;
		
		Map<String,Object> prevMap = prevBuy(resoureType,articleId,journalId,userId);
		Integer result = (Integer)prevMap.get("result");
		if(result.intValue() == 1){
			//获取用户资金信息
			WordUserMoney wordUserMoney = null;
			WordUserMoneyExample example = new WordUserMoneyExample();
			example.or().andUserIdEqualTo(userId);
			List<WordUserMoney> list = wordUserMoneyMapper.selectByExample(example);
			if (list != null && list.size() == 1){
				wordUserMoney = list.get(0);
				balance = wordUserMoney.getMoney();
			}else{
				map.put("result", 0);
				map.put("msg", "余额不足，请充值后付款！");
				return map;
			}
			//根据资源类型获取购买所需的创享币，如果是选择购买整个期刊则获取期刊的价格，
			if ("journal".equals(resoureType)){
				WordJournal wordJournal = wordJournalMapper.selectByPrimaryKey(journalId);
				if (wordJournal != null){
					needMoney = wordJournal.getPrice();
				}
			}else{
				Article article = articleMapper.selectByPrimaryKey(articleId);
				if (article != null){
					needMoney = article.getArticlePrice();
				}
			}
			
			if (balance >= needMoney){		
				//修改购物车状态
				WordShoppingCartExample epl = new WordShoppingCartExample();
				if ("journal".equals(resoureType)){
					epl.or().andUserIdEqualTo(userId).andResoureTypeEqualTo(resoureType).andJournalIdEqualTo(journalId);
				}else{
					epl.or().andUserIdEqualTo(userId).andResoureTypeEqualTo(resoureType).andArticleIdEqualTo(articleId);
				}
				List<WordShoppingCart> carts = wordShoppingCartMapper.selectByExample(epl);
				if (carts != null && carts.size() > 0){
					WordShoppingCart cart = carts.get(0);
					cart.setPayFlag(1);
					wordShoppingCartMapper.updateByPrimaryKeySelective(cart);
				}
				//用户资金减少
				wordUserMoney.setMoney(balance - needMoney);
				wordUserMoneyMapper.updateByPrimaryKeySelective(wordUserMoney);
				Date now = new Date();
				
				//加入资源库
				WordResource wordResource = new WordResource();		
				wordResource.setUserId(userId);
				wordResource.setResourceType(resoureType);
				wordResource.setBuyType("before");
				wordResource.setJournalId(journalId);
				wordResource.setArticleId(articleId);
				wordResource.setPrice(needMoney);
				wordResource.setDoId(userId);
				wordResource.setCreateTime(now);
				//如果是期刊需要设置到期时间
				if ("journal".equals(resoureType)){
					Calendar calendar = Calendar.getInstance();  
					calendar.add(calendar.YEAR, 1);
					Date end = calendar.getTime();
					end.setHours(0);
					end.setMinutes(0);
					end.setSeconds(0);
					long t = end.getTime();
					wordResource.setStartTime(now);
					wordResource.setEndTime(new Date(t-1000));
					//如果之前买过期刊并且过期 修改结束时间
					WordResourceExample e = new WordResourceExample();
					e.or().andUserIdEqualTo(userId).andResourceTypeEqualTo("journal").andJournalIdEqualTo(journalId);
					List<WordResource> wordResources = wordResourceMapper.selectByExample(e);
					if (wordResources != null && wordResources.size() > 0){
						WordResource record = wordResources.get(0);
						record.setEndTime(wordResource.getEndTime());
						record.setPrice(wordResource.getPrice());
						wordResource.setResourceId(record.getResourceId());
						wordResourceMapper.updateByPrimaryKey(record);
					}else{
						wordResourceMapper.insertSelective(wordResource);
					}
				}else{
					wordResourceMapper.insertSelective(wordResource);
				}
				//增加资金变动明细
				WordUserMoneyLog wordUserMoneyLog = new WordUserMoneyLog();
				wordUserMoneyLog.setUserId(userId);
				wordUserMoneyLog.setType("del");
				wordUserMoneyLog.setBuyType("before");
				wordUserMoneyLog.setMoney(needMoney);
				wordUserMoneyLog.setAddMoney(needMoney);
				wordUserMoneyLog.setResourceType(resoureType);
				wordUserMoneyLog.setResourceId(wordResource.getResourceId());
				wordUserMoneyLog.setDoId(userId);
				wordUserMoneyLog.setCreateTime(now);
				wordUserMoneyLogMapper.insertSelective(wordUserMoneyLog);
				map.put("result", 1);
				map.put("msg", "购买成功！");
			}else{
				map.put("result", 0);
				map.put("msg", "余额不足，请充值后付款！");
			}
		}else{
			map.put("result", 0);
			map.put("msg", map.get("msg"));
		}
		return map;
	}
}
