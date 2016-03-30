package com.web.view.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.database.bean.NewsColumn;
import com.database.bean.NewsColumnExample;
import com.database.bean.NewsColumnGroup;
import com.database.bean.NewsColumnGroupExample;
import com.database.bean.NewsGroupContact;
import com.database.bean.NewsGroupContactExample;
import com.database.bean.NewsLabel;
import com.database.bean.NewsLabelExample;
import com.database.bean.NewsMyPlate;
import com.database.bean.NewsMyPlateExample;
import com.database.bean.NewsPic;
import com.database.bean.NewsPicExample;
import com.database.bean.NewsSource;
import com.database.bean.NewsSourceExample;
import com.database.dao.NewsColumnGroupMapper;
import com.database.dao.NewsColumnMapper;
import com.database.dao.NewsGroupContactMapper;
import com.database.dao.NewsLabelMapper;
import com.database.dao.NewsMyPlateMapper;
import com.database.dao.NewsPicMapper;
import com.google.gson.Gson;
import com.util.CommonUtils;
import com.util.DateTool;
import com.util.DateUtil;
import com.web.bean.Condition;
import com.web.bean.NewsSearchCondition;
import com.web.bean.SearchNewsData;
import com.web.view.bean.RiskCut;


@Service
public class NewsColumnManageService {
	
	@Autowired
	NewsColumnMapper newsColumnMapper;
	@Autowired
	NewsColumnGroupMapper newsColumnGroupMapper;
	@Autowired
	NewsMyPlateMapper newsMyPlateMapper;
	@Autowired
	NewsGroupContactMapper newsGroupContactMapper;
	@Autowired
	NewsPicMapper newsPicMapper;
	@Autowired
	NewsLabelMapper newsLabelMapper;

	
	@Value("${searchByWebName}")
	private String searchByWebName;
	@Value("${newsSearchByMultiJsonStr}")
	private String newsSearchByMultiJsonStr;
	
	private static Logger logger = Logger.getLogger(NewsColumnManageService.class);
	
	/**
	 * 获取推荐栏目列表
	 * @param typeId
	 * @return
	 */
	public List<NewsColumnGroup> getRecNewsColumn(Integer typeId,List<NewsMyPlate> plateList){
		
		List<NewsColumnGroup> list = getNewsColumnGroup(typeId,null);

		for (NewsColumnGroup group : list){
			List<NewsColumn> columnList1 = group.getColumns();
			if (columnList1 != null && columnList1.size() > 0){
				for (NewsColumn column1 : columnList1){
					if (column1 != null){
						List<NewsColumn> columnList2 = column1.getColumns();
						if (columnList2 != null && columnList2.size() > 0){
							for (NewsColumn column2 : columnList2){
								if (plateList != null && plateList.size() > 0){
									for (NewsMyPlate plate : plateList){
										if (plate.getColumnId() == column2.getId()){
											column2.setAddFlag(1);
											continue;
										}
									}
								}
								if (column2.getAddFlag() == null){
									column2.setAddFlag(0);
								}
							}
						}
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 检索栏目组
	 * @param typeId
	 * @param userId
	 * @return
	 */
	public List<NewsColumnGroup> getNewsColumnGroup(Integer typeId,Integer userId){
		
		NewsColumnGroupExample example = new NewsColumnGroupExample();
		if (userId!=null){
			example.or().andUserIdEqualTo(userId).andTypeIdEqualTo(typeId);
		}else{
			example.or().andTypeIdEqualTo(typeId);
		}
		//取出栏目组
		List<NewsColumnGroup> list = newsColumnGroupMapper.selectByExample(example);
		
		for (NewsColumnGroup ncg : list){
			NewsGroupContactExample ngce = new NewsGroupContactExample();
			ngce.setOrderByClause("order_id");
			ngce.or().andGroupIdEqualTo(ncg.getId());
			//查询中间表，取出typeid
			List<NewsGroupContact> ngcList = newsGroupContactMapper.selectByExample(ngce);
			if (!ngcList.isEmpty()){
				List<Integer> values = new ArrayList<Integer>();
				for (NewsGroupContact ngc : ngcList){
					values.add(ngc.getColumnId());
				}
				NewsColumnExample e = new NewsColumnExample();
				e.or().andIdIn(values).andPidEqualTo(0);
				List<NewsColumn> ncs = newsColumnMapper.selectByExample(e);
				List<NewsColumn> newNcs = new ArrayList<NewsColumn>();
				Map<Integer, NewsColumn> colMap = new HashMap<Integer, NewsColumn>();
				for (NewsColumn nc : ncs){
					colMap.put(nc.getId(), nc);
				}
				for (NewsGroupContact ngc : ngcList){
					NewsColumn co = colMap.get(ngc.getColumnId());
					newNcs.add(co);
				}
				diguiColumn(newNcs);
				ncg.setColumns(newNcs);
			}
		}
		return list;
	}
	
	/**
	 * 递归后台栏目
	 * @param 
	 */
	private void diguiColumn(List<NewsColumn> list) {

		if (!list.isEmpty()){
			for (NewsColumn nc : list){
				NewsColumnExample e = new NewsColumnExample();
				if (nc != null){
					e.or().andPidEqualTo(nc.getId());
					List<NewsColumn> subs = newsColumnMapper.selectByExample(e);
					nc.setColumns(subs);
					diguiColumn(subs);
				}
			}
		}
	}
	
	/**
	 * 生成目录的树形结构
	 * @param userId
	 * @return
	 */
	public List<NewsMyPlate> getUserNewsMyPlate(int userId){
		
		List<NewsMyPlate> oldPlateList = newsMyPlateMapper.getNewsMyPlate(userId);
		List<NewsMyPlate> targetList = new ArrayList<NewsMyPlate>();
		treeList(targetList,oldPlateList,0);
		//生成目录树的结构
		return targetList;
	}
	
	/**
	 * 递归，生成tree需要树形结构目录
	 * 
	 * @param targetList
	 * @param originalList
	 * @param pid
	 */
	public void treeList(List<NewsMyPlate> targetList, List<NewsMyPlate> oldPlateList, Integer pid) {
		
		for (NewsMyPlate plate : oldPlateList) {
			if (plate.getPid().equals(pid)) {
				targetList.add(plate);
				treeList(plate.getSubs(), oldPlateList, plate.getPlateId());
			}
		}
	}
	
	/**
	 * 根据用户id获取栏目
	 * @param userId
	 * @return
	 */
	public List<NewsMyPlate> getNewsMyPlate(int userId){
		return newsMyPlateMapper.getNewsMyPlate(userId);
		
	}
	
	/**
	 * 获取栏目
	 * @param plate
	 * @return
	 */
	public NewsMyPlate plateGet(int plateId){
		return newsMyPlateMapper.selectByPrimaryKey(plateId);
	}	
	
	/**
	 * 修改栏目
	 * @param plate
	 * @return
	 */
	public boolean plateEdit(NewsMyPlate plate){
		newsMyPlateMapper.updateByPrimaryKeySelective(plate);
		return true;
	} 
	
	/**
	 * 通过pid获取栏目数量
	 * @param userId
	 * @param pid
	 * @return
	 */
	public int getCountByPid(int userId,int pid){
		return newsMyPlateMapper.getCountByPid(userId, pid);
	}
	
	/**
	 * 添加新建栏目
	 * @param plate
	 * @return
	 */
	public NewsMyPlate plateAdd(NewsMyPlate plate){
		
		int maxOrderId = newsMyPlateMapper.getNewsPlateMaxOrder(plate.getUserId(),plate.getPid());
		plate.setOrderId(maxOrderId+1);
		plate.setInserTime(DateUtil.currentDateTime());
		
		//获取图片
		NewsMyPlateExample example = new NewsMyPlateExample();
		example.or().andUserIdEqualTo(plate.getUserId());
		List<NewsMyPlate> list = newsMyPlateMapper.selectByExample(example);
		List<String> pics = new ArrayList<String>();
		if (list != null && list.size() > 0){
			for (NewsMyPlate p : list){
				if (!StringUtil.isBlank(p.getPicPath())){
					pics.add(p.getPicPath());					
				}
			}
		}
		List<NewsPic> picList = searchNewsPic(pics);
		if (picList != null && picList.size() > 0){
			plate.setPicPath(picList.get(0).getPath());
		}
		//保存
		int plateId = newsMyPlateMapper.insertSelective(plate);
		return plate;
	}
	
	/**
	 * 添加推荐的栏目
	 * @param plate
	 * @return
	 */
	public NewsMyPlate plateAddHot(NewsMyPlate plate){

		int maxOrderId = newsMyPlateMapper.getNewsPlateMaxOrder(plate.getUserId(),plate.getPid());
		plate.setOrderId(maxOrderId+1);
		plate.setInserTime(DateUtil.currentDateTime());
		NewsColumn column = newsColumnMapper.selectByPrimaryKey(plate.getColumnId());
		if (column != null){
			//订阅数加1
			column.setCount(column.getCount()+1);
			newsColumnMapper.updateByPrimaryKey(column);
			
			plate.setConditions(column.getConditions());
			plate.setPlateName(column.getColumnName());
			plate.setPicPath(column.getPicPath());
		}
		int plateId = newsMyPlateMapper.insertSelective(plate);
		return plate;
	}
	
	/**
	 * 删除栏目(包含子栏目)
	 * @param plateid
	 * @return
	 */
	public boolean plateDel(int plateId){
		
		NewsMyPlateExample example = new NewsMyPlateExample();
		example.or().andPlateIdEqualTo(plateId);
//		example.or().andPidEqualTo(plateId);
		newsMyPlateMapper.deleteByExample(example);
		return true;
	}	
	
	/**
	 * 获取推荐的栏目
	 * @param id
	 * @return
	 */
	public NewsColumn getNewsColumn(Integer id){
		
		NewsColumn column = newsColumnMapper.selectByPrimaryKey(id);
		return column;
	}
	
	/**
	 * 
	 * @param firstId 拖动元素的id修改顺序
	 * @param loaction 拖动的方向before after
	 * @param loactionId
	 * @return
	 */
	public boolean updateOrder(int firstId,String loaction,int loactionId){
		
		NewsMyPlate firstWordPlate = newsMyPlateMapper.selectByPrimaryKey(firstId);
		NewsMyPlate locationWordPlate = newsMyPlateMapper.selectByPrimaryKey(loactionId);
		
		if (firstWordPlate==null||locationWordPlate==null){
			return false;
		}
		if ("before".equals(loaction)){//插入到之前
			/*
			 * 1.firstId栏目的orderid 等于 loactionId栏目的orderId
			 * 2.id不等于firstId,并orderId大于等于 firstId的所有栏目,orderId+1
			 */
			firstWordPlate.setOrderId(locationWordPlate.getOrderId());
			newsMyPlateMapper.updateOrder(firstWordPlate);
			newsMyPlateMapper.updateOrderAdd(firstWordPlate);
		}else {//插入到之后
			/**
			 * 1.firstId栏目的orderid 等于 loactionId元素的orderId+1
			 * id不等于firstId,并orderId大于等于 firstId的所有栏目,orderId+1
			 */
			firstWordPlate.setOrderId(locationWordPlate.getOrderId()+1);
			newsMyPlateMapper.updateOrder(firstWordPlate);
			newsMyPlateMapper.updateOrderAdd(firstWordPlate);
		}
		return true;
	}
	
	/**
	 * 根据columnID查询栏目
	 * @param userId
	 * @param columnID
	 * @return
	 */
	public List<NewsMyPlate> getPlatList(int userId, int columnId){
		
		NewsMyPlateExample example = new NewsMyPlateExample();
		example.or().andColumnIdEqualTo(columnId).andUserIdEqualTo(userId);
		List<NewsMyPlate> list = newsMyPlateMapper.selectByExample(example);
		return list;
	}
	
	/**
	 * 获取新闻源
	 * @param newsBean
	 * @return
	 */
	public String searchNewsSource(NewsSource newsBean) {
		
		String pageSize = "100";//默认加载100条
		NewsSourceExample example = new NewsSourceExample();
		NewsSourceExample.Criteria cr = example.createCriteria();
		if (newsBean.getSourcename() != null && !"".equals(newsBean.getSourcename())){
			cr.andSourcenameLike("%" + newsBean.getSourcename() +"%");
		}
		
		long start = System.currentTimeMillis();
		logger.warn("获取新闻源开始" + System.currentTimeMillis());
		String sourcename = "";
		if (newsBean.getSourcename() != null){
			sourcename = newsBean.getSourcename();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("webName",sourcename);
		map.put("from","0");
		map.put("size",pageSize);
		String resultJosn = CommonUtils.getHtml(map, searchByWebName);
		long end = System.currentTimeMillis();
		logger.warn("获取新闻源结束" + end + " 共耗时:" + (end - start) + "ms！");

		return resultJosn;
	}
	
	/**
	 * 我的栏目下的新闻预览
	 * @param plateId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> plateForNews(NewsMyPlate plate, Integer pageNo,
			Integer pageSize) {

		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}

		Map<String, String> map = new HashMap<String, String>();
		Gson gson = new Gson();
		List<NewsSearchCondition> searchCondition = new ArrayList<NewsSearchCondition>();
		if (pageNo == 1) {
			map.put("from", 0 + "");
		} else {
			map.put("from", ((pageNo - 1) * pageSize) + "");
		}

		Condition condition = gson.fromJson(plate.getConditions(),Condition.class);
		NewsSearchCondition sc = new NewsSearchCondition();
		NewsSearchCondition scc = new NewsSearchCondition();
		if (StringUtils.isNotBlank(condition.getMustTagNames())) {
			List<String> l = new ArrayList<String>();
			for (String s : condition.getMustTagNames().split("_")) {
				l.add(s);
			}
			sc.setMustTagNames(l);
			scc.setMustTagNames(l);
		}
		if (StringUtils.isNotBlank(condition.getShouldTagNames())) {
			List<String> l = new ArrayList<String>();
			for (String s : condition.getShouldTagNames().split("_")) {
				l.add(s);
			}
			sc.setShouldTagNames(l);
			scc.setShouldTagNames(l);
		}
		if (StringUtils.isNotBlank(condition.getMustNotTagNames())) {
			List<String> l = new ArrayList<String>();
			for (String s : condition.getMustNotTagNames().split("_")) {
				l.add(s);
			}
			sc.setMustNotTagNames(l);
			scc.setMustNotTagNames(l);
		}
		// 关键词
		boolean flag = condition.getFlag() == 1 ? true : false;
		// 标题
		// and关键词
		if (StringUtils.isNotBlank(condition.getMustWordNames())) {
			String[] temp = condition.getMustWordNames().split("_");
			List<String> l = new ArrayList<String>();
			for (String str : temp) {
				l.add(str);
			}
			if (flag) {
				sc.setMustTitleWords(l);
			} else {
				scc.setMustContentWords(l);
			}
		}
		// OR关键词
		if (StringUtils.isNotBlank(condition.getShouldWordNames())) {
			String[] temp = condition.getShouldWordNames().split("_");
			List<String> l = new ArrayList<String>();
			for (String str : temp) {
				l.add(str);
			}
			if (flag) {
				sc.setShouldTitleWords(l);
			} else {
				scc.setShouldContentWords(l);
			}
		}
		// not关键词
		if (StringUtils.isNotBlank(condition.getMustNotWordNames())) {
			String[] temp = condition.getMustNotWordNames().split("_");
			List<String> l = new ArrayList<String>();
			for (String str : temp) {
				l.add(str);
			}
			if (flag) {
				sc.setMustNotTitleWords(l);
			} else {
				scc.setMustNotContentWords(l);
			}
		}
		//新闻来源
		if (StringUtils.isNotBlank(condition.getSource())){
			String[] temp = condition.getSource().split("_");
			List<String> l = new ArrayList<String>();
			for (String str : temp) {
				l.add(str);
			}
			sc.setShouldWebNames(l);
			scc.setShouldWebNames(l);
		}
		if (!sc.isEmpty()){
			searchCondition.add(sc);
		}
		if (!flag){//新闻正文
			if (!scc.isEmpty()){
				searchCondition.add(scc);
			}			
		}
		map.put("jsonStr", gson.toJson(searchCondition));
		map.put("pageSize", pageSize + "");
		map.put("fullContent", "false");
		map.put("searchFlag", "true");
		long start = System.currentTimeMillis();
		logger.warn("栏目获取新闻开始" + System.currentTimeMillis());
		String json = CommonUtils.getHtml(map, newsSearchByMultiJsonStr);
		long end = System.currentTimeMillis();
		logger.warn("栏目取新闻结束" + end + " 共耗时:" + (end - start) + "ms！");
		SearchNewsData sd = new SearchNewsData();
		long count = 0;
		if(json!=null && !json.equals("")){
			sd = gson.fromJson(json, SearchNewsData.class);
			count=sd.getCount();
			if(count == 0){
				if (StringUtils.isNotBlank(condition.getMustTagNames())) {
					List<String> l = new ArrayList<String>();
					for (String s : condition.getMustTagNames().split("_")) {
						l.add(s);
					}
					
					sc.setShouldKeywords(l);
					sc.setShouldTitleWords(l);
					sc.setMustTagNames(null);
					scc.setShouldKeywords(l);
					scc.setShouldTitleWords(l);
					scc.setMustTagNames(null);
					searchCondition = new ArrayList<NewsSearchCondition>();
					if (!sc.isEmpty()){
						searchCondition.add(sc);
					}
					if (!flag){//新闻正文
						if (!scc.isEmpty()){
							searchCondition.add(scc);
						}
					}
					map.put("jsonStr", gson.toJson(searchCondition));
					json = CommonUtils.getHtml(map, newsSearchByMultiJsonStr);
					if(json!=null && !json.equals("")){
						sd = gson.fromJson(json, SearchNewsData.class);
						count=sd.getCount();
					}		
				}				
			}
			if (count > 100){
				count = 100;//默认100条不需要全部展现
			}
		}
		if(sd.getData()!=null && !sd.getData().isEmpty()){
			for(RiskCut rc : sd.getData()){
				//将新闻时间由long转换成Date类型
				if (rc.getNewsDate() != null){
					rc.setNewsDateTime(DateTool.longToString(rc.getNewsDate(),"yyyy-MM-dd HH:mm:ss"));	
				}
			}
		}
		Map<String, Object> page = new HashMap<String, Object>();
		page.put("list", sd.getData());
		page.put("count", count);
		page.put("pageSize", pageSize);
		page.put("pageNo", pageNo);
		page.put("pageCount", ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1)));
		return page;
	}
	
	/**
	 *  推荐栏目下的新闻预览
	 * @param columnId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> columnForNews(Integer columnId, Integer pageNo,
			Integer pageSize) {

		if (pageNo == null || pageNo < 1) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 5;
		}
		Map<String, String> map = new HashMap<String, String>();
		List<NewsColumn> templist = new ArrayList<NewsColumn>();
		Gson gson = new Gson();
		List<NewsSearchCondition> searchCondition = new ArrayList<NewsSearchCondition>();
		NewsColumn column = newsColumnMapper.selectByPrimaryKey(columnId);
		if (column == null) {
			return null;
		}

		if (templist == null || templist.size() == 0){
			templist.add(column);
		}
		if (pageNo == 1) {
			map.put("from", 0 + "");
		} else {
			map.put("from", ((pageNo - 1) * pageSize) + "");
		}
		for (NewsColumn nc : templist) {
			Condition condition = gson.fromJson(nc.getConditions(),Condition.class);
			if (condition == null) {
				continue;
			}

			NewsSearchCondition sc = new NewsSearchCondition();
			NewsSearchCondition scc = new NewsSearchCondition();
			if (StringUtils.isNotBlank(condition.getMustTagNames())) {
				List<String> l = new ArrayList<String>();
				for (String s : condition.getMustTagNames().split("_")) {
					l.add(s);
				}
				sc.setMustTagNames(l);
				scc.setMustTagNames(l);
			}
			if (StringUtils.isNotBlank(condition.getShouldTagNames())) {
				List<String> l = new ArrayList<String>();
				for (String s : condition.getShouldTagNames().split("_")) {
					l.add(s);
				}
				sc.setShouldTagNames(l);
				scc.setShouldTagNames(l);
			}
			if (StringUtils.isNotBlank(condition.getMustNotTagNames())) {
				List<String> l = new ArrayList<String>();
				for (String s : condition.getMustNotTagNames().split("_")) {
					l.add(s);
				}
				sc.setMustNotTagNames(l);
				scc.setMustNotTagNames(l);
			}
			// 关键词
			boolean flag = condition.getFlag() == 1 ? true : false;
			// 标题
			// and关键词
			if (StringUtils.isNotBlank(condition.getMustWordNames())) {
				String[] temp = condition.getMustWordNames().split("_");
				List<String> l = new ArrayList<String>();
				for (String str : temp) {
					l.add(str);
				}
				if (flag) {
					sc.setMustTitleWords(l);
				} else {
					scc.setMustContentWords(l);
				}
			}
			// OR关键词
			if (StringUtils.isNotBlank(condition.getShouldWordNames())) {
				String[] temp = condition.getShouldWordNames().split("_");
				List<String> l = new ArrayList<String>();
				for (String str : temp) {
					l.add(str);
				}
				if (flag) {
					sc.setShouldTitleWords(l);
				} else {
					scc.setShouldContentWords(l);
				}
			}
			// not关键词
			if (StringUtils.isNotBlank(condition.getMustNotWordNames())) {
				String[] temp = condition.getMustNotWordNames().split("_");
				List<String> l = new ArrayList<String>();
				for (String str : temp) {
					l.add(str);
				}
				if (flag) {
					sc.setMustNotTitleWords(l);
				} else {
					scc.setMustNotContentWords(l);
				}
			}
			//新闻来源
			if (StringUtils.isNotBlank(condition.getSource())){
				String[] temp = condition.getSource().split("_");
				List<String> l = new ArrayList<String>();
				for (String str : temp) {
					l.add(str);
				}
				sc.setShouldWebNames(l);
				scc.setShouldWebNames(l);
			}
			if (!sc.isEmpty()){
				searchCondition.add(sc);
			}
			if (!flag){//新闻正文
				if (!scc.isEmpty()){
					searchCondition.add(scc);			
				}
			}
		}
		map.put("jsonStr", gson.toJson(searchCondition));
		map.put("pageSize", pageSize + "");
		map.put("fullContent", "false");
		long start = System.currentTimeMillis();
		logger.warn("栏目获取新闻开始" + System.currentTimeMillis());
		String json = CommonUtils.getHtml(map, newsSearchByMultiJsonStr);
		long end = System.currentTimeMillis();
		logger.warn("栏目取新闻结束" + end + " 共耗时:" + (end - start) + "ms！");
		SearchNewsData sd = new SearchNewsData();
		long count = 0;
		if(json!=null && !json.equals("")){
			sd = gson.fromJson(json, SearchNewsData.class);
			count=sd.getCount();
			if(count == 0){
				for (NewsSearchCondition nsc : searchCondition) {
					nsc.setShouldKeywords(nsc.getMustTagNames());
					nsc.setShouldTitleWords(nsc.getMustTagNames());
					nsc.setMustTagNames(null);
				}
				map.put("jsonStr", gson.toJson(searchCondition));
				json = CommonUtils.getHtml(map, newsSearchByMultiJsonStr);
				if(json!=null && !json.equals("")){
					sd = gson.fromJson(json, SearchNewsData.class);
					count=sd.getCount();
				}
			}
			if (count > 100){
				count = 100;//默认100条不需要全部展现
			}
		}
		if(sd.getData()!=null && !sd.getData().isEmpty()){
			for(RiskCut rc : sd.getData()){
				//将新闻时间由long转换成Date类型
				if (rc.getNewsDate() != null){
					rc.setNewsDateTime(DateTool.longToString(rc.getNewsDate(),"yyyy-MM-dd HH:mm:ss"));	
				}
			}
		}
		Map<String, Object> page = new HashMap<String, Object>();
		page.put("list", sd.getData());
		page.put("count", count);
		page.put("pageSize", pageSize);
		page.put("pageNo", pageNo);
		page.put("pageCount", ((count % pageSize == 0) ? (count / pageSize)
				: ((count / pageSize) + 1)));
		return page;
	}
	
	
	/**
	 * 获取图片
	 * @param newsBean
	 * @return
	 */
	public List<NewsPic> searchNewsPic(List<String> pics) {
		
		NewsPicExample example = new NewsPicExample();
		if (pics != null && pics.size() > 0){
			NewsPicExample.Criteria cr = example.createCriteria();
			cr.andPathNotIn(pics);
		}
		return newsPicMapper.selectByExample(example);
	}
	
	/**
	 * 根据名称搜索新闻标签
	 * @param name
	 * @return
	 */
	public List<NewsLabel> searchNewsLabels(String name,int pageId,int pageSize){
		int start = (pageId-1)*pageSize;
		NewsLabelExample example = new NewsLabelExample();
		example.setLimitStart(start);
		example.setLimitEnd(pageSize);
		example.or().andNameLike("%"+name+"%");
		example.setOrderByClause("id asc");
		List<NewsLabel> list = newsLabelMapper.selectByExample(example);
		return list;
	}
}
