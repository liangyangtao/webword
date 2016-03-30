package com.web.view.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.database.bean.LoginLog;
import com.database.bean.Module;
import com.database.bean.ModuleExample;
import com.database.bean.User;
import com.database.bean.UserCompany;
import com.database.bean.UserCompanyExample;
import com.database.bean.UserModules;
import com.database.bean.UserModulesExample;
import com.database.bean.UserSaveContent;
import com.database.bean.UserSaveContentExample;
import com.database.bean.UserSaveContentWithBLOBs;
import com.database.bean.WordUsers;
import com.database.bean.WordUsersKey;
import com.database.dao.LoginLogMapper;
import com.database.dao.ModuleMapper;
import com.database.dao.UserCompanyMapper;
import com.database.dao.UserModulesMapper;
import com.database.dao.UserSaveContentMapper;
import com.database.dao.WordUsersMapper;
import com.google.gson.Gson;
import com.util.DateTool;
import com.web.view.bean.Risk;
import com.web.view.bean.RiskSearchCondition;
import com.web.view.bean.SearchRiskData;
import com.web.view.bean.UserBuyThemeRiskStatus;
import com.web.view.fetch.Fetcher;

@Service
public class RiskViewService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RiskViewService.class); 

	@Autowired
	private ModuleMapper moduleMapper;
	
	@Autowired
	private UserModulesMapper userModulesMapper;
	@Value("${riskByJson}")
	private String riskByJson;
	
	@Value("${riskByNewsId}")
	private String riskByNewsId;
	
	@Value("${riskByMulti}")
	private String riskByMulti;
	
	@Value("${riskByKeyWordOnly}")
	private String riskByKeyWordOnly;
	
	@Value("${riskMoreLikeSearch}")
	private String riskMoreLikeSearch;
	
	@Value("${riskSearchByEsId}")
	private String riskSearchByEsId;
	
	@Value("${riskMoreLikeSearchByEsId}")
	private String riskMoreLikeSearchByEsId;
	
	@Value("${checkdays}")
	private long checkdays;
	
	@Value("${picUrl}")
	private String picUrl;
	@Autowired
	private WordUsersMapper wordUsersMapper;
	
	@Autowired
	private LoginLogMapper loginLogMapper;
	@Autowired
	private UserSaveContentMapper userSaveContentMapper;
	@Autowired
	private UserCompanyMapper userCompanyMapper;
	
	/**
	 * 依据父级分类的ID查找子级分类
	 * @param parentId 父级分类的ID
	 * @return
	 */
	public List<Module> getModulesByParentId(int parentId){
		List<Module> moduleList=new ArrayList<Module>();
		ModuleExample example=new ModuleExample();
		example.setOrderByClause("sortnumber asc ");
		example.createCriteria().andParentidEqualTo(parentId);
		if(parentId==0){
			example.setLimitStart(0);
			example.setLimitEnd(4);
		}
		moduleList=moduleMapper.selectByExample(example);
		return moduleList;
	}
	
	public Module getModuleById(int id){
		return moduleMapper.selectByPrimaryKey(id);
	}
	
	
	/**
	 *  依据指定的搜索条件从索引中搜索相应的Risk
	 * @param condition 搜索条件
	 * @param pageSize 分页大小
	 * @param from 分页起始
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param orderByColumn 排序字段
	 * @param fullContent 是否返回全部正文
	 * @return
	 */
	public SearchRiskData fetchRisk(RiskSearchCondition condition,int pageSize,int from ,Date startTime,Date endTime,String orderByColumn,boolean fullContent){
		Fetcher fetcher=new Fetcher();
		Map<String,String> params=new HashMap<String,String>();
		
		params.put("jsonStr", condition.toJsonString());
		params.put("from", String.valueOf(from));
		params.put("pageSize", String.valueOf(pageSize));
		
		if(startTime!=null){
			long s_time=startTime.getTime();
			params.put("startTime",String.valueOf(s_time));
		}
		
		if(endTime!=null){
			long e_time=endTime.getTime();
			params.put("endTime",String.valueOf(e_time));
		}
		if(orderByColumn!=null){
			params.put("orderByColumn", orderByColumn);
		}
		
		params.put("fullContent", String.valueOf(fullContent));
		
		//String url="http://10.0.2.152:8080/SearchPlatform/rest/risk/multiConditionSearch/searchByJsonStr";
		String url =riskByJson;
		
		String result= fetcher.fetch(url, params);
		
		if(result!=null){
			Gson gson=new Gson();
			SearchRiskData  searchRiskData = gson.fromJson(result, SearchRiskData.class);
			for(Risk risk:searchRiskData.getData()){
				risk.setNewDate(DateTool.stampToDate(risk.getNewsDate()));
			}
			return searchRiskData;
		}
		return null;
	}
	
	/**
	 * 依据抓取ID和分类ID或者风险信息
	 * @param id 新闻抓取ID
	 * @param categoryId 分类ID
	 * @return
	 */
	public Risk fetchRiskById(int id,int categoryId){
		Fetcher fetcher=new Fetcher();
		
		//String url="http://10.0.2.152:8080/SearchPlatform/rest/risk/multiConditionSearch/searchByIds";
		String url = riskByNewsId;
		Map<String,String> params=new HashMap<String,String>();
		
		params.put("crawl_id", String.valueOf(id));
		params.put("categoryId", String.valueOf(categoryId));
		
		String result= fetcher.fetch(url, params);
		if(result!=null){
			Gson gson=new Gson();
			Risk risk= gson.fromJson(result, Risk.class);
			risk.setNewDate(DateTool.stampToDate(risk.getNewsDate()));
			return risk;
		}
		return null;
	}
	
	/**
	 * 更具esId获取的详情
	 * @param esId
	 * @return
	 */
	public Risk fetchRiskByEsId(String esId){
		Fetcher fetcher=new Fetcher();
		
		String url = riskSearchByEsId;
		Map<String,String> params=new HashMap<String,String>();
		
		params.put("esId", esId);
		
		String result= fetcher.fetch(url, params);
		if(result!=null){
			Gson gson=new Gson();
			Risk risk= gson.fromJson(result, Risk.class);
			risk.setNewDate(DateTool.stampToDate(risk.getNewsDate()));
			return risk;
		}
		return null;
	}
	/**
	 * 基于内容的推荐
	 * @param id
	 * @param categoryId
	 * @param from
	 * @param pageSize
	 * @return
	 */
	public SearchRiskData getMoreLikeRisks(int id,int categoryId,int from,int pageSize){
		
		Fetcher fetcher=new Fetcher();
		//String url="http://10.0.2.152:8080/SearchPlatform/rest/risk/multiConditionSearch/moreLikeSearch";
		String url =riskMoreLikeSearch;
		Map<String,String> params=new HashMap<String,String>();
		
		params.put("crawl_id", String.valueOf(id));
		params.put("categoryId", String.valueOf(categoryId));
		params.put("from", String.valueOf(from));
		params.put("pageSize", String.valueOf(pageSize));
		
		String result= fetcher.fetch(url, params);
		
		if(result!=null){
			Gson gson=new Gson();
			return gson.fromJson(result, SearchRiskData.class);
		}
		
		return null;
	}
	/**
	 * 获取栏目路径-栏目页
	 * @param id
	 * @return
	 */
	public List<Module> getModulePathById(int id){
		List<Module> moduleList = new ArrayList<Module>();
		while(id>0){
			Module module = getModuleById(id);
			id = module.getParentid();
			moduleList.add(module);
		}
		Collections.reverse(moduleList);
		return moduleList;
	}
	/**
	 * 获取栏目树
	 * @param categoryId:栏目ID 
	 * @param risk
	 * 1.根据risk的栏目 id 获取栏目id，然后剔除父栏目，获得最后一级栏目
	 * 2.通过最后一级栏目，递归获取父栏目。
	 */
	public List<Module> getModulePath(int categoryId,Risk risk){
		List<Module> modulPathList =new ArrayList<Module>(); 
		String categoryStr=risk.getCategoryId().trim().replaceAll(" ", ",");
		//System.out.println(categoryStr);
		//获取最后一级目录
		List<Module> moduleList=moduleMapper.getLastModule(categoryStr);
		//System.out.println(moduleList.size());
		List<Module> treeList = new ArrayList<Module>();
		boolean flag =false;//没有匹配的目录
		for(Module module:moduleList){
			treeList.clear();
			getParentPath(module,treeList);
			for(Module treeModule:treeList){
				if(treeModule.getMid()==categoryId){
					modulPathList=treeList;
					flag=true;//有匹配的目录
					break;
				}
			}
			if(flag){
				break;
			}
		}
		Collections.reverse(modulPathList);
		return modulPathList;
		//
	}
	/**
	 * 递归产生节点
	 * @param oldlist
	 * @param newList
	 * @param pid
	 */
	public void modulePath(List<Module> moduleList,List<Module> treeList,int pid){
		for(Module module :moduleList){
			System.out.println(module.getParentid()+",pid="+pid);
			if(module.getParentid()==pid){
				System.out.println("--");
				treeList.add(module);
				modulePath(moduleList,module.getChild(),module.getMid());
			}
		}
	}
	/**
	 * 递归找到节点
	 * @param moduleList
	 * @param mid
	 * @return
	 */
	public boolean modulePathNode(List<Module> moduleList,int mid){
		for(Module module :moduleList){
			if(module.getMid()==mid){
				return true;
			}else{
				return modulePathNode(module.getChild(),mid);
			}
		}
		return false;
	}
	/**
	 * 递归生成 数组
	 * @param moduleList
	 * @param mid
	 * @return
	 */
	public void getModulePathList(List<Module> modulePathList,Module module){
		if(module!=null){//目录不能为空
			modulePathList.add(module);
			if(module.getChild().size()>0){
				for(Module moduleNode:module.getChild()){
					getModulePathList(modulePathList,moduleNode);
				}
			}
		}
	}
	/**
	 * 递归获取父节点
	 * @param module
	 * @param moduleList
	 */
	public void getParentPath(Module module,List<Module> moduleList){
		moduleList.add(module);
		if(module.getParentid()!=0){
			Module parentModule = moduleMapper.selectByPrimaryKey(module.getParentid());
			if(parentModule!=null){//
				getParentPath(parentModule,moduleList);
			}
		}
	}
	/**
	 * 获取用户最后栏目表
	 */
	public List<UserModules> getUserModules(Risk risk ,WordUsers user){
		String categoryStr=risk.getCategoryId().trim().replaceAll(" ", ",");
		//获得最后一级
		List<Module> moduleList=moduleMapper.getLastModule(categoryStr);
		List<Integer> midList = new ArrayList<Integer>();
		for(Module module:moduleList){
			midList.add(module.getMid());
		}
		UserModulesExample userModulesExample = new UserModulesExample();
		userModulesExample.or().andUseridEqualTo(user.getUserId()).andMidIn(midList);
		List<UserModules> userModulesList = userModulesMapper.selectByExample(userModulesExample);
		return userModulesList;
	}
	/**
	 * 检验试用期
	 * true:有权限，false:没有权限
	 */
	public boolean checkPermissions(WordUsers user){
		//8-12 8-19 返回8 天
		System.out.println(DateTool.betweenDate(user.getDateAffect(),new Date()));
		if(DateTool.betweenDate(user.getDateAffect(),new Date())<=31){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否有正式订阅栏目
	 */
	public boolean checkUserModules(Risk risk ,WordUsers user){
		List<UserModules> userModulesList = getUserModules(risk ,user);
		if(userModulesList.size()>0){
			return true;
		}
		return false;
	}
	/**
	 * 检验栏目是否有看的权限
	 * @return true:代表有权限。
	 * 获取栏目信息的栏目ID,获取最后一级栏目ID。
	 * 更具最后一级栏目id，增加userId,在权限表里面查询
	 * true:有权限，false:没有权限
	 */
	@SuppressWarnings("deprecation")
	public boolean checkColumns(Risk risk , WordUsers user){
		List<UserModules> userModulesList = getUserModules(risk ,user);
		try {
			if(userModulesList.size()>0){
				//判断是否在时间内
				Date time = new Date();
				Date endtime = null;
				for (UserModules us : userModulesList) {
				    endtime = us.getEndtime();
				    if(endtime==null){
						return true;
					}
				    endtime.setDate(endtime.getDate()+1);
				    if(time.getTime()<endtime.getTime()){
						return true;
					}else{
						return false;
					}
				}
			}
			return false;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return false;
	}
	
	/**
	 * 检验企业是否有看的权限
	 * @return true:代表有权限。
	 * 获取栏目信息的栏目ID,获取最后一级栏目ID。
	 * 更具最后一级栏目id，增加userId,在权限表里面查询
	 * true:有权限，false:没有权限
	 */
	@SuppressWarnings("deprecation")
	public boolean checkCompany(Risk risk, WordUsers user) {
		UserCompanyExample userCompanyExample = new UserCompanyExample();
		userCompanyExample.or().andUseridEqualTo(user.getUserId());
		List<UserCompany> UserCompanyList = userCompanyMapper.selectByExample(userCompanyExample);
		try {
			if(UserCompanyList.size()>0){
				//判断是否在时间内
				Date endtime = UserCompanyList.get(0).getEndtime();
				Date time = new Date();
				if(endtime==null){
					return false;
				}
				endtime.setDate(endtime.getDate()+1);
				if(time.getTime()<endtime.getTime()){
					return true;
				}else{
					return false;
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return false;
	}
	/**
	 * 替换图片 
	 * static/unbankImage/images/20150722/ca3fb8991a7403d846553fe8251d912e.gif
	 * http://10.0.2.35:8080/unbankImage/images/20150722/ca3fb8991a7403d846553fe8251d912e.gif
	 * @param content
	 * @return
	 */
	public String replaceImg(String content){
		  String searchImgReg = "(?x)(src|SRC|background|BACKGROUND)=('|\")(static.*?/)(.*?.(jpg|JPG|png|PNG|gif|GIF|jpeg|JPEG))('|\")";
		  // 修改图片链接地址
		  Pattern pattern = Pattern.compile(searchImgReg);
		  Matcher matcher = pattern.matcher(content);
		  StringBuffer replaceStr = new StringBuffer();
		  while (matcher.find()) {
			  matcher.appendReplacement(replaceStr, matcher.group(1) + "="+picUrl
					  + matcher.group(4) + "");//逐个动态替换图片链接地址
		  }
		  matcher.appendTail(replaceStr);//添加尾部
		  return replaceStr.toString();
	}
	
	public int getUserRiskThemeStatus(WordUsers user) {
		//1 判断用户是不是购买了栏目
		List<UserModules> userModulesList=userModulesMapper.getUserModulesList(user.getUserId());
		//查询用户状态 1 表示已购买栏目，2 栏目  11 购买  12 购买即将到期  13 购买已经到期  21 试用阶段 22 试用即将到期 23 试用已经到期
		if(userModulesList!=null&&userModulesList.size()==0){//未购买栏目
			WordUsers wordUsers=wordUsersMapper.selectByPrimaryKey(new WordUsersKey(user.getUserAccount(),user.getUserId()));
			Date affectDate=wordUsers.getDateAffect();//试用开始时间
			//判断试用时间是不是已经大于27天
			Date nowDate=new Date();
	
			SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
			String nowDate2=format.format(nowDate);
			
			Calendar c = Calendar.getInstance();
			SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("yyyyMMdd");
			c = Calendar.getInstance(Locale.CHINESE);
			c.setTime(affectDate);
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+27-1);
			String affectDate27 =simpleDateTimeFormat.format(c.getTime());
			
			c.setTime(affectDate);
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+30-1);
			String affectDate30 =simpleDateTimeFormat.format(c.getTime());
			
			if(Integer.parseInt(nowDate2)>Integer.parseInt(affectDate30)){//过期
				return UserBuyThemeRiskStatus.GQ_SY;
			}else if(Integer.parseInt(nowDate2)>Integer.parseInt(affectDate27)&&Integer.parseInt(nowDate2)<=Integer.parseInt(affectDate30)){//即将过期
				return UserBuyThemeRiskStatus.JJDQ_SY;
			}else{
				return UserBuyThemeRiskStatus.ZC_SY;
			}
		}
		return UserBuyThemeRiskStatus.ZC_GM;
	}
	public int getUserThemeStatus(WordUsers user,Map maps) {
		//1 判断用户是不是购买了栏目
		List<UserModules> userModulesList=userModulesMapper.getUserModulesList(user.getUserId());
		//查询用户状态 1 表示已购买栏目，2 栏目  11 购买  12 购买即将到期  13 购买已经到期  21 试用阶段 22 试用即将到期 23 试用已经到期
		if(userModulesList!=null&&userModulesList.size()==0){//未购买栏目
			WordUsers wordUsers=wordUsersMapper.selectByPrimaryKey(new WordUsersKey(user.getUserAccount(),user.getUserId()));
			Date affectDate=wordUsers.getDateAffect();//试用开始时间
			//判断试用时间是不是已经大于27天
			Date nowDate=new Date();
	
			SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
			String nowDate2=format.format(nowDate);
			
			Calendar c = Calendar.getInstance();
			SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("yyyyMMdd");
			c = Calendar.getInstance(Locale.CHINESE);
			c.setTime(affectDate);
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+27-1);
			String affectDate27 =simpleDateTimeFormat.format(c.getTime());
			
			c.setTime(affectDate);
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+30-1);
			String affectDate30 =simpleDateTimeFormat.format(c.getTime());
			
			if(Integer.parseInt(nowDate2)>Integer.parseInt(affectDate30)){//过期
				return UserBuyThemeRiskStatus.GQ_SY;
			}else if(Integer.parseInt(nowDate2)>Integer.parseInt(affectDate27)&&Integer.parseInt(nowDate2)<=Integer.parseInt(affectDate30)){//即将过期
				return UserBuyThemeRiskStatus.JJDQ_SY;
			}else{
				return UserBuyThemeRiskStatus.ZC_SY;
			}
		}else{//已购买
			Date endtime =null;
			String str = null;
			String key = null;
			for (UserModules userModules : userModulesList) {
				endtime = userModules.getEndtime();
				//System.out.println(DateTool.betweenDate(new Date(),endtime));
				//选择3天内到期的
				if(endtime!=null){
					if(DateTool.betweenDate(new Date(),endtime)<4&&DateTool.betweenDate(new Date(),endtime)>0){//栏目3天内到期
						str =  userModules.getMid()+"";
						key = DateTool.betweenDate(new Date(),endtime)+"";
							if(maps.containsKey(key)){
								String newvalue = (String) maps.get(key);
								newvalue = newvalue+","+str;
								maps.put(key, newvalue);
							}else{
								maps.put(key, str);
							}
					}
				}else{
					continue;
				}
				}
			}
		System.out.println(!maps.isEmpty());
		if(!maps.isEmpty()){
			return UserBuyThemeRiskStatus.JJDQ_GM;
		}else if(getcompanyDate(user)>0){
			return UserBuyThemeRiskStatus.JJDQ_GM;
		}
		return UserBuyThemeRiskStatus.ZC_GM;
	};
	public int getcompanyDate(WordUsers user) {
		//1 判断用户是不是购买了企业
		UserCompanyExample  example = new UserCompanyExample();
		example.or().andUseridEqualTo(user.getUserId());
		List<UserCompany> companyList = userCompanyMapper.selectByExample(example);
		if(companyList!=null&&companyList.size()>0){//企业3天内到期
			for (UserCompany userCompany : companyList) {
				Date endtime2 = userCompany.getEndtime();
				if(endtime2!=null){
					if(DateTool.betweenDate(new Date(),endtime2)<4&&DateTool.betweenDate(new Date(),endtime2)>0){
						long betweenDate = DateTool.betweenDate(new Date(),endtime2);
						return (int) betweenDate;
					}
				}
			}
		}
		return 0;
	}
	/**
	 * 获得购买即将到期栏目名称
	 * @param user
	 * @return
	 */
	public List<Module> getshopDate(WordUsers user,Map<String , String> maps) {
		try {
			if(maps.containsKey("1")){
				List<Integer> list = new ArrayList<Integer>();
				String [] arr = maps.get("1").toString().split(",");
				for (int i = 0; i < arr.length; i++) {
					list.add(Integer.parseInt(arr[i]));
				}
				ModuleExample example = new ModuleExample();
				 example.or().andMidIn(list);
				 example.setLimitStart(0);
				 example.setLimitEnd(2);
				 List<Module> timeList = moduleMapper.selectByExample(example);
				 String mname1 = "";
				 for (Module u : timeList) {
					u.setRight(1);
					String mname = u.getMname();
					mname1 += mname+",";
				 }
				 mname1 = mname1.substring(0,mname1.length()-1);
				 for (int i = 0; i <timeList.size(); i++) {
					 timeList.get(i).setMname(mname1);
				 }
				return timeList;
			}else if(maps.containsKey("2")){
				List<Integer> list = new ArrayList<Integer>();
				String [] arr = maps.get("2").toString().split(",");
				for (int i = 0; i < arr.length; i++) {
					list.add(Integer.parseInt(arr[i]));
				}
				ModuleExample example = new ModuleExample();
				 example.or().andMidIn(list);
				 example.setLimitStart(0);
				 example.setLimitEnd(2);
				 List<Module> timeList = moduleMapper.selectByExample(example);
				 String mname1 = "";
				 for (Module u : timeList) {
					 u.setRight(2);
					String mname = u.getMname();
					mname1 += mname+",";
				 }
				 mname1 = mname1.substring(0,mname1.length()-1);
				 for (int i = 0; i <timeList.size(); i++) {
					 timeList.get(i).setMname(mname1);
				 }
				return timeList;
			}else if(maps.containsKey("3")){
				List<Integer> list = new ArrayList<Integer>();
				String [] arr = maps.get("3").toString().split(",");
				for (int i = 0; i < arr.length; i++) {
					list.add(Integer.parseInt(arr[i]));
				}
				ModuleExample example = new ModuleExample();
				 example.or().andMidIn(list);
				 example.setLimitStart(0);
				 example.setLimitEnd(2);
				 List<Module> timeList = moduleMapper.selectByExample(example);
				 String mname1 = "";
				 for (Module u : timeList) {
					 u.setRight(3);
					String mname = u.getMname();
					mname1 += mname+",";
				 }
				 mname1 = mname1.substring(0,mname1.length()-1);
				 for (int i = 0; i <timeList.size(); i++) {
					 timeList.get(i).setMname(mname1);
				 }
				return timeList;
			}
		} catch (NumberFormatException e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}
	/**
	 * 获得试用期到期时间
	 * @param user
	 * @return
	 */
	public String getExpirationDate(WordUsers user) {
		WordUsers wordUsers=wordUsersMapper.selectByPrimaryKey(new WordUsersKey(user.getUserAccount(),user.getUserId()));
		Date affectDate=wordUsers.getDateAffect();//试用开始时间
		
		Calendar c = Calendar.getInstance();
		c = Calendar.getInstance(Locale.CHINESE);
		c.setTime(affectDate);
		c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+30-1);
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String affectDate30 =format.format(c.getTime());  
		return affectDate30;
	}
	/**
	 * 获得今天是否是是首次登录
	 * @param user
	 * @return
	 */
	public String getIsFirstLogin(WordUsers user) {
		List<String> list=wordUsersMapper.getTodayLoginList(user.getUserId());
		if(list!=null&&list.size()>0){
			return "N";
		}
		return "Y";
	}

	public int insertLoginLog(WordUsers wordUsers) {
		LoginLog record=new LoginLog();
		record.setUserid(wordUsers.getUserId());
		record.setUsername(wordUsers.getUserName());
		record.setLogintime(new Date());
		return loginLogMapper.insert(record);
		
	}

	public UserSaveContent getUserSaveContent(int userId, String esId) {
		return userSaveContentMapper.getUserSaveContentByUserIdAndContentId(userId,  esId);
	}

	public int insertUserCollection(UserSaveContentWithBLOBs usc) {
		Date date=new Date();
		usc.setInsertTime(date);
		usc.setUpdateTime(date);
		usc.setFileIndex(0);
		usc.setPassType("SAVED");
		return userSaveContentMapper.insert(usc);
	}

}
