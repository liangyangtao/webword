package com.web.view.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.database.bean.Article;
import com.database.bean.ArticleExample;
import com.database.bean.WordJournal;
import com.database.bean.WordJournalExample;
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
public class ShoppingCartService {
	@Autowired
	WordShoppingCartMapper  wordShoppingCartMapper;
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
	public List<WordShoppingCart> getMyShoppingCart(Integer userId) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("user_id", userId);
		return wordShoppingCartMapper.selectMyShoppingCart(map);
	}
	/**
	 * 加入购物车
	 * @param wordShoppingCart
	 */
	public Map<String,Object> add(WordShoppingCart wordShoppingCart) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		WordShoppingCartExample example = new WordShoppingCartExample();
		Date date = new Date();
		//期刊加入购物车
		if(wordShoppingCart.getResoureType().equals("journal")){
			wordShoppingCart.setArticleId(0);
			example.or().andPayFlagEqualTo(0).andUserIdEqualTo(wordShoppingCart.getUserId()).andResoureTypeEqualTo(wordShoppingCart.getResoureType()).andJournalIdEqualTo(wordShoppingCart.getJournalId());
			List<WordShoppingCart> list = wordShoppingCartMapper.selectByExample(example);
			//判断购物车是否存在
			if(!(list!=null && !list.isEmpty())){
				WordResourceExample e = new WordResourceExample();
				e.or().andUserIdEqualTo(wordShoppingCart.getUserId()).andResourceTypeEqualTo(wordShoppingCart.getResoureType()).andJournalIdEqualTo(wordShoppingCart.getJournalId()).andEndTimeGreaterThanOrEqualTo(date);
				List<WordResource> wordResources= wordResourceMapper.selectByExample(e);
				//判断资源是否存在
				if(wordResources!=null && !wordResources.isEmpty()){
					map.put("result", 0);
					map.put("msg", "已经购买此资源");
				}else{
					e.clear();
					e.or().andUserIdEqualTo(wordShoppingCart.getUserId()).andResourceTypeEqualTo(wordShoppingCart.getResoureType()).andJournalIdEqualTo(wordShoppingCart.getJournalId()).andEndTimeLessThanOrEqualTo(date);
					wordResources= wordResourceMapper.selectByExample(e);
					if(wordResources!=null && !wordResources.isEmpty()){
						WordShoppingCart cart = list.get(0);
						cart.setPayFlag(0);
						cart.setCreateTime(new Date());
						wordShoppingCartMapper.updateByPrimaryKeySelective(cart);
					}else{
						wordShoppingCartMapper.insertSelective(wordShoppingCart);
					}
					map.put("result", 1);
				}
			}else{
				map.put("result", 0);
				map.put("msg", "购物车已存在");
			}
		}else if(wordShoppingCart.getResoureType().equals("journalarticle")){
			example.or().andPayFlagEqualTo(0).andUserIdEqualTo(wordShoppingCart.getUserId()).andResoureTypeEqualTo(wordShoppingCart.getResoureType()).andArticleIdEqualTo(wordShoppingCart.getArticleId());
			List<WordShoppingCart> list = wordShoppingCartMapper.selectByExample(example);
			//判断购物车是否存在
			if(!(list!=null && !list.isEmpty())){
				WordResourceExample e = new WordResourceExample();
				e.or().andUserIdEqualTo(wordShoppingCart.getUserId()).andResourceTypeEqualTo(wordShoppingCart.getResoureType()).andArticleIdEqualTo(wordShoppingCart.getArticleId());
				List<WordResource> wordResources= wordResourceMapper.selectByExample(e);
				//判断资源是否存在
				if(wordResources!=null && !wordResources.isEmpty()){
					map.put("result", 0);
					map.put("msg", "已经购买此资源");
				}else{
					e.clear();
					e.or().andUserIdEqualTo(wordShoppingCart.getUserId()).andResourceTypeEqualTo(wordShoppingCart.getResoureType()).andJournalIdEqualTo(wordShoppingCart.getJournalId()).andEndTimeGreaterThanOrEqualTo(date);
					wordResources= wordResourceMapper.selectByExample(e);
					if(wordResources!=null && !wordResources.isEmpty()){
						map.put("result", 0);
						map.put("msg", "已经购买包含此资源的期刊");
					}else{
						wordShoppingCartMapper.insertSelective(wordShoppingCart);
						map.put("result", 1);
					}
				}
			}else{
				map.put("result", 0);
				map.put("msg", "购物车已存在");
			}
		}else if(wordShoppingCart.getResoureType().equals("article")){
			example.or().andPayFlagEqualTo(0).andUserIdEqualTo(wordShoppingCart.getUserId()).andResoureTypeEqualTo(wordShoppingCart.getResoureType()).andArticleIdEqualTo(wordShoppingCart.getArticleId());
			List<WordShoppingCart> list = wordShoppingCartMapper.selectByExample(example);
			//判断购物车是否存在
			if(!(list!=null && !list.isEmpty())){
				WordResourceExample e = new WordResourceExample();
				e.or().andUserIdEqualTo(wordShoppingCart.getUserId()).andResourceTypeEqualTo(wordShoppingCart.getResoureType()).andArticleIdEqualTo(wordShoppingCart.getArticleId());
				List<WordResource> wordResources= wordResourceMapper.selectByExample(e);
				//判断资源是否存在
				if(wordResources!=null && !wordResources.isEmpty()){
					map.put("result", 0);
					map.put("msg", "已经购买此资源");
				}else{
					wordShoppingCartMapper.insertSelective(wordShoppingCart);
					map.put("result", 1);
				}
			}else{
				map.put("result", 0);
				map.put("msg", "购物车已存在");
			}
		}
		return map;
	}
	/**
	 * 删除购物车
	 * @param cartIds
	 * @param userId
	 */
	public void del(String cartIds,Integer userId) {
		// TODO Auto-generated method stub
		if(cartIds!=null && !cartIds.equals("")){
			String [] ids = cartIds.split("_");
			List<Integer> values = new ArrayList<Integer>();
			for(String cartId:ids){
				values.add(Integer.valueOf(cartId));
			}
			WordShoppingCartExample example = new WordShoppingCartExample();
			example.or().andUserIdEqualTo(userId).andCartIdIn(values).andPayFlagEqualTo(0);
			wordShoppingCartMapper.deleteByExample(example);
		}
	}
	
	/**
	 * 预支付
	 * @param cartIds
	 * @param userId
	 */
	public Map<String,Object> prevPay(String cartIds, Integer userId) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		WordShoppingCartExample example = new WordShoppingCartExample();
		Date date = new Date();
		if(cartIds!=null && !cartIds.equals("")){
			Double total = 0.00;
			Double money = 0.00;
			String [] ids = cartIds.split("_");
			List<Integer> values = new ArrayList<Integer>();
			for(String cartId:ids){
				values.add(Integer.valueOf(cartId));
			}
			example.or().andUserIdEqualTo(userId).andCartIdIn(values).andPayFlagEqualTo(1);
			//检查购物车是否正确
			List<WordShoppingCart> carts = wordShoppingCartMapper.selectByExample(example);
			if(carts!=null && !carts.isEmpty()){
				map.put("result", 0);
				map.put("msg", "购物车中存在已付款商品！");
				return map;
			}
			example.clear();
			example.or().andUserIdEqualTo(userId).andCartIdIn(values).andResoureTypeEqualTo("journal");
			List<WordShoppingCart> cartJouranls = wordShoppingCartMapper.selectByExample(example);
			example.clear();
			example.or().andUserIdEqualTo(userId).andCartIdIn(values).andResoureTypeEqualTo("journalarticle");
			List<WordShoppingCart> cartJouranlArticles = wordShoppingCartMapper.selectByExample(example);
			//检查购物车是否有期刊包含期刊文档
			if(cartJouranls!=null && !cartJouranls.isEmpty() && cartJouranlArticles!=null && !cartJouranlArticles.isEmpty()){
				for(WordShoppingCart cart:cartJouranlArticles){
					for(WordShoppingCart c:cartJouranls){
						if(cart.getJournalId().intValue()==c.getJournalId().intValue()){
							map.put("result", 0);
							map.put("msg", "所购买期刊\""+wordJournalMapper.selectByPrimaryKey(c.getJournalId()).getName()+"\"包含期刊文档\""+articleMapper.selectByPrimaryKey(cart.getArticleId()).getArticleName()+"\"");
							break;
						}
					}
				}
				if(map.get("result")!=null && ((Integer)map.get("result")).intValue()==0){
					return map;
				}
			}
			example.clear();
			example.or().andUserIdEqualTo(userId).andCartIdIn(values);
			carts = wordShoppingCartMapper.selectByExample(example);
			//检查是否重复购买
			if(carts!=null && !carts.isEmpty()){
				for(WordShoppingCart cart:carts){
					if(cart.getResoureType().equals("journal")){
						WordResourceExample e = new WordResourceExample();
						e.or().andUserIdEqualTo(cart.getUserId()).andResourceTypeEqualTo(cart.getResoureType()).andJournalIdEqualTo(cart.getJournalId()).andEndTimeGreaterThanOrEqualTo(date);
						List<WordResource> wordResources= wordResourceMapper.selectByExample(e);
						if(wordResources!=null && !wordResources.isEmpty()){
							map.put("result", 0);
							map.put("msg", "已经购买过期刊:"+wordJournalMapper.selectByPrimaryKey(cart.getJournalId()).getName());
							break;
						}
					}else if(cart.getResoureType().equals("journalarticle")){
						WordResourceExample e = new WordResourceExample();
						e.or().andUserIdEqualTo(cart.getUserId()).andResourceTypeEqualTo("journal").andJournalIdEqualTo(cart.getJournalId()).andEndTimeGreaterThanOrEqualTo(date);
						e.or().andUserIdEqualTo(cart.getUserId()).andResourceTypeEqualTo(cart.getResoureType()).andArticleIdEqualTo(cart.getArticleId());
						List<WordResource> wordResources= wordResourceMapper.selectByExample(e);
						if(wordResources!=null && !wordResources.isEmpty()){
							map.put("result", 0);
							map.put("msg", "已经购买过期刊文档:"+articleMapper.selectByPrimaryKey(cart.getArticleId()).getArticleName());
							break;
						}
					}else if(cart.getResoureType().equals("article")){
						WordResourceExample e = new WordResourceExample();
						e.or().andUserIdEqualTo(cart.getUserId()).andResourceTypeEqualTo(cart.getResoureType()).andArticleIdEqualTo(cart.getArticleId());
						List<WordResource> wordResources= wordResourceMapper.selectByExample(e);
						if(wordResources!=null && !wordResources.isEmpty()){
							map.put("result", 0);
							map.put("msg", "已经购买过文档:"+articleMapper.selectByPrimaryKey(cart.getArticleId()).getArticleName());
							break;
						}
					}
				}
				if(map.get("result")!=null && ((Integer)map.get("result")).intValue()==0){
					return map;
				}
			}
			example.clear();
			example.or().andUserIdEqualTo(userId).andCartIdIn(values).andPayFlagEqualTo(0).andResoureTypeEqualTo("journal");
			//查询期刊购物车
			List<WordShoppingCart> journals = wordShoppingCartMapper.selectByExample(example);
			List<Integer> journalIds = new ArrayList<Integer>();
			if(journals!=null && !journals.isEmpty()){
				for(WordShoppingCart cart:journals){
					journalIds.add(cart.getJournalId());
				}
				WordJournalExample wordJournalExample = new WordJournalExample();
				wordJournalExample.or().andPassTypeEqualTo("PASSED").andIdIn(journalIds);
				//查出对应期刊累计价格
				List<WordJournal> wordJournals =wordJournalMapper.selectByExample(wordJournalExample);
				if(wordJournals!=null && !wordJournals.isEmpty()){
					for(WordJournal journal:wordJournals){
						total+=journal.getPrice();
					}
				}
			}
			example.clear();
			example.or().andUserIdEqualTo(userId).andCartIdIn(values).andPayFlagEqualTo(0).andResoureTypeNotEqualTo("journal");
			//查询文档购物车
			List<WordShoppingCart> articles = wordShoppingCartMapper.selectByExample(example);
			List<Integer> articleIds = new ArrayList<Integer>();
			if(articles!=null && !articles.isEmpty()){
				for(WordShoppingCart cart:articles){
					articleIds.add(cart.getArticleId());
				}
				ArticleExample articleExample = new ArticleExample();
				articleExample.or().andPassTypeEqualTo("PASSED").andArticleIdIn(articleIds);
				//查出对应文档累计价格
				List<Article> wordArticles =articleMapper.selectByExample(articleExample);
				if(wordArticles!=null && !wordArticles.isEmpty()){
					for(Article article:wordArticles){
						total+=article.getArticlePrice();
					}
				}
			}
			//我的账户的创享币
			WordUserMoneyExample wordUserMoneyExample = new WordUserMoneyExample();
			wordUserMoneyExample.or().andUserIdEqualTo(userId);
			List<WordUserMoney> wordUserMoneys = wordUserMoneyMapper.selectByExample(wordUserMoneyExample);
			if(wordUserMoneys!=null && !wordUserMoneys.isEmpty()){
				money = wordUserMoneys.get(0).getMoney();
			}else{
				money = 0.00;
			}
			map.put("money", money);
			map.put("total", total);
			map.put("result", 1);
		}else{
			map.put("result", 0);
			map.put("msg", "购物车错误！");
		}
		return map;
	}
	
	/**
	 * 付款
	 * @param cartIds
	 * @param userId
	 * @return
	 */
	@Transactional
	public Map<String, Object> pay(String cartIds, Integer userId) {
		// TODO Auto-generated method stub
		Map<String,Object> m = new HashMap<String,Object>();
		Map<String,Object> map = prevPay(cartIds, userId);
		String journalIds = "";
		String articleIds = "";
		Integer result =  (Integer)map.get("result");
		Double total = (Double) map.get("total");
		Double money = (Double) map.get("money");
		m.put("total", total);
		m.put("cartIds", cartIds.split("_"));
		if(result.intValue()==1){
			//创享币是否充足
			if(money>=total){
				//扣款
				WordUserMoneyExample wordUserMoneyExample = new WordUserMoneyExample();
				wordUserMoneyExample.or().andUserIdEqualTo(userId);
				List<WordUserMoney> wordUserMoneys = wordUserMoneyMapper.selectByExample(wordUserMoneyExample);
				if(wordUserMoneys!=null && !wordUserMoneys.isEmpty()){
					WordUserMoney wordUserMney = wordUserMoneys.get(0);
					if(wordUserMney.getMoney()>=total){
						wordUserMney.setMoney(wordUserMney.getMoney()-total);
						wordUserMoneyMapper.updateByPrimaryKeySelective(wordUserMney);
						//查询购物车
						String [] ids = cartIds.split("_");
						List<Integer> values = new ArrayList<Integer>();
						for(String cartId:ids){
							values.add(Integer.valueOf(cartId));
						}
						WordShoppingCartExample example = new WordShoppingCartExample();
						example.or().andUserIdEqualTo(userId).andCartIdIn(values).andPayFlagEqualTo(0);
						List<WordShoppingCart> carts = wordShoppingCartMapper.selectByExample(example);
						if(carts!=null && !carts.isEmpty()){
							for(WordShoppingCart cart:carts){
								Date now = new Date();
								//加入资源库
								WordResource wordResource = new WordResource();
								wordResource.setArticleId(cart.getArticleId());
								wordResource.setBuyType("before");
								wordResource.setCartId(cart.getCartId());
								wordResource.setCreateTime(now);
								wordResource.setDoId(userId);
								wordResource.setJournalId(cart.getJournalId());
								wordResource.setResourceType(cart.getResoureType());
								wordResource.setUserId(userId);
								//期刊
								if(cart.getResoureType()!=null && cart.getResoureType().equals("journal")){
									WordJournal journal = wordJournalMapper.selectByPrimaryKey(cart.getJournalId());
									if(journal!=null){
										wordResource.setPrice(journal.getPrice());
									}
									Calendar calendar = Calendar.getInstance();  
									calendar.add(calendar.YEAR, 1);
									Date end = calendar.getTime();
									end.setHours(0);
									end.setMinutes(0);
									end.setSeconds(0);
									long t = end.getTime();
									wordResource.setStartTime(now);
									wordResource.setEndTime(new Date(t-1000));
								}else{
									Article article = articleMapper.selectByPrimaryKey(cart.getArticleId());
									if(article!=null){
										wordResource.setPrice(article.getArticlePrice());
									}
								}
								if(wordResource.getResourceType().equals("journal")){
									//如果之前买过期刊并且过期 修改结束时间
									WordResourceExample e = new WordResourceExample();
									e.or().andUserIdEqualTo(userId).andResourceTypeEqualTo("journal").andJournalIdEqualTo(cart.getJournalId());
									List<WordResource> wordResources= wordResourceMapper.selectByExample(e);
									if(wordResources!=null && !wordResources.isEmpty()){
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
								//更改购物车状态
								cart.setPayFlag(1);
								wordShoppingCartMapper.updateByPrimaryKeySelective(cart);
								//资金变动明细
								WordUserMoneyLog wordUserMoneyLog = new WordUserMoneyLog();
								wordUserMoneyLog.setUserId(userId);
								wordUserMoneyLog.setType("del");
								wordUserMoneyLog.setBuyType("before");
								wordUserMoneyLog.setMoney(wordResource.getPrice());
								wordUserMoneyLog.setAddMoney(wordResource.getPrice());
								wordUserMoneyLog.setResourceType(wordResource.getResourceType());
								wordUserMoneyLog.setResourceId(wordResource.getResourceId());
								wordUserMoneyLog.setDoId(userId);
								wordUserMoneyLog.setCreateTime(now);
								wordUserMoneyLogMapper.insertSelective(wordUserMoneyLog);
								//删除购物车中未购买该期刊下文档
								WordShoppingCartExample wsce = new WordShoppingCartExample();
								wsce.or().andUserIdEqualTo(userId).andPayFlagEqualTo(0).andResoureTypeEqualTo("journalarticle").andJournalIdEqualTo(cart.getJournalId());
								wordShoppingCartMapper.deleteByExample(wsce);
								m.put("result", 1);
								if(cart.getJournalId().intValue()!=0){
									journalIds+=cart.getJournalId()+",";
								}
								if(cart.getArticleId().intValue()!=0){
									articleIds+=cart.getArticleId()+",";
								}
							}
							//推荐（购买的人还购买）
							Map<String,String> parMap = new HashMap<String,String>();
							if(!journalIds.equals("")){
								parMap.put("journalIds", journalIds.substring(0, journalIds.length()-1));
							}
							if(!articleIds.equals("")){
								parMap.put("articleIds", articleIds.substring(0, articleIds.length()-1));
							}
							parMap.put("userId", userId+"");
							m.put("journals", wordJournalMapper.getRecommendJournal(parMap));
							m.put("articles", articleMapper.getRecommendJournal(parMap));
						}else{
							m.put("result", 0);
							m.put("msg", "购物车错误！");
						}
					}else{
						m.put("msg", "余额不足，请充值后付款！");
					}
				}
			}else{
				m.put("result", 0);
				m.put("msg", "余额不足，请充值后付款！");
			}
		}else{
			m.put("result", 0);
			m.put("msg", map.get("msg"));
		}
		return m;
	}
	
	/**
	 * 购物车未付款数量
	 * @param userId
	 * @return
	 */
	public Integer getMyShoppingCartCount(Integer userId) {
		// TODO Auto-generated method stub
		WordShoppingCartExample example = new WordShoppingCartExample();
		example.or().andUserIdEqualTo(userId).andPayFlagEqualTo(0);
		return wordShoppingCartMapper.countByExample(example);
	}
}
