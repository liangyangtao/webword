package com.web.view.service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.database.bean.CompanyGroupRelation;
import com.database.bean.CompanyGroupRelationExample;
import com.database.bean.Module;
import com.database.bean.ModuleExample;
import com.database.bean.MyModule;
import com.database.bean.MyModuleExample;
import com.database.bean.RiskCompany;
import com.database.bean.RiskCompanyExample;
import com.database.bean.UserCompany;
import com.database.bean.UserCompanyExample;
import com.database.bean.UserModules;
import com.database.bean.UserModulesExample;
import com.database.bean.WordColumnGroupExample;
import com.database.bean.WordUsers;
import com.database.bean.WordUsersExample;
import com.database.dao.CompanyGroupRelationMapper;
import com.database.dao.ModuleMapper;
import com.database.dao.MyModuleMapper;
import com.database.dao.RiskCompanyMapper;
import com.database.dao.UserCompanyMapper;
import com.database.dao.UserModulesMapper;
import com.database.dao.WordUsersMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.util.DateTool;
import com.web.bean.ParamsBean;
import com.web.bean.SearchNewsData;
import com.web.utils.Fetcher;
import com.web.utils.HttpClientBuilder;
import com.web.view.bean.RiskCut;
import com.web.view.bean.RiskSearchCondition;
import com.web.view.bean.SearchRiskData;
import com.web.view.service.RiskMyService;
@Service
public class RiskMyServiceImpl implements RiskMyService {
	private static final Logger LOGGER = Logger.getLogger(RiskMyServiceImpl.class);
	@Value("${riskByJson}")
	private String riskByJson;
	@Value("${riskByMulti}")
	private String riskByMulti;
	
	@Value("${riskByKeyWordOnly}")
	private String riskByKeyWordOnly;
	@Autowired
	private ModuleMapper moduleMapper;
	@Autowired
	private UserModulesMapper usermodulesMapper;
	@Autowired
	private MyModuleMapper mymoduleMapper;
	@Autowired
	private WordUsersMapper wordusersMapper;
	@Autowired
	private UserCompanyMapper usercompanyMapper;
	@Autowired
	private CompanyGroupRelationMapper companyGroupRelationMapper;
	
	@Autowired
	private RiskCompanyMapper riskCompanyMapper;
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int savemymodule(ParamsBean pb) {
		int result = 0 ;
		//根据选择的栏目id ，拼成JSON
		String mids = pb.getMids();
		String [] temp = mids.trim().split(",");
		if(StringUtils.isEmpty(mids)){
			//没选栏目
			MyModule mymodule = new MyModule();
			mymodule.setModuleinfo("");
			if(null!=pb.getInterest()&&!"".equals(pb.getInterest())){
				mymodule.setInterest(pb.getInterest());
			}
			if(null!=pb.getKeyword()&&!"".equals(pb.getKeyword())){
				mymodule.setKeyword(pb.getKeyword());
			}
			if(null!=pb.getResponsibility()&&!"".equals(pb.getResponsibility())){
				mymodule.setResponsibility(pb.getResponsibility());
			}
			if(null!=pb.getStation()&&!"".equals(pb.getStation())){
				mymodule.setStation(pb.getStation());
			}
			mymodule.setUserid(pb.getUserId());
			mymodule.setCreatedate(new Date());
			//查询mymodule表是否有当前用户记录没有插入有更新
			MyModuleExample m1example = new MyModuleExample();
			m1example.or().andUseridEqualTo(pb.getUserId());
			List<MyModule> l = mymoduleMapper.selectByExampleWithBLOBs(m1example);
			if(l.isEmpty()){
				result = mymoduleMapper.insertSelective(mymodule);
			}else{
				MyModuleExample mexample2 = new MyModuleExample();
				mexample2.or().andUseridEqualTo(pb.getUserId());
				result = mymoduleMapper.updateByExampleSelective(mymodule, mexample2);
			}
		}else{
			List<String> list = Arrays.asList(temp);
			List<Integer> ids = new ArrayList<Integer>();
			for(String str : list){
				ids.add(Integer.parseInt(str));
			}
			//查所有栏目
			List<Module> modules = new ArrayList<Module>();
			ModuleExample example = new ModuleExample();
		//	example.or().andMidIn(ids);
			try {
				modules = moduleMapper.selectByExample(example);
				
				
				Map<Integer,String> map1 = new HashMap<Integer,String>();
				Map<Integer,Integer> map2 = new HashMap<Integer,Integer>();
				for(Module module : modules){
					//所有栏目id，名称 map1
					map1.put(module.getMid(), module.getMname());
					//所有栏目id，父节点id map2
					map2.put(module.getMid(), module.getParentid());
				}
				//找出所有子节点的父节点
				Map<Integer,String> nodes = new HashMap<Integer,String>();
				//list为所有字节点集合String类型
				a:for(String str : list){
					int pid = 0;
					int mid = Integer.parseInt(str);
					if(map2.containsKey(mid)){
						nodes.put(mid, map1.get(mid));
						 pid = map2.get(mid);
						b:while(true){
							if(pid==0){
								break b;
							}else{
								nodes.put(pid, map1.get(pid));
								pid =map2.get(pid);
							}
						}
					}
				}
				List<Module> mymodules = new ArrayList<Module>();
				for(Map.Entry<Integer,String> entry :nodes.entrySet()){
					Module module = new Module();
					module.setMid(entry.getKey());
					module.setMname(entry.getValue());
					module.setParentid(map2.get(entry.getKey()));
					mymodules.add(module);
				}
				if(null!=mymodules&&mymodules.size()>0){
					List<Module> mytree = bulidmytree(mymodules);
					Gson gson = new Gson();
					String moduleinfo = gson.toJson(mytree);
					MyModule mymodule = new MyModule();
					mymodule.setModuleinfo(moduleinfo);
					if(null!=pb.getInterest()&&!"".equals(pb.getInterest())){
						mymodule.setInterest(pb.getInterest());
					}
					if(null!=pb.getKeyword()&&!"".equals(pb.getKeyword())){
						mymodule.setKeyword(pb.getKeyword());
					}
					if(null!=pb.getResponsibility()&&!"".equals(pb.getResponsibility())){
						mymodule.setResponsibility(pb.getResponsibility());
					}
					if(null!=pb.getStation()&&!"".equals(pb.getStation())){
						mymodule.setStation(pb.getStation());
					}
					mymodule.setUserid(pb.getUserId());
					mymodule.setCreatedate(new Date());
					
					//查询mymodule表是否有当前用户记录没有插入有更新
					MyModuleExample m1example = new MyModuleExample();
					m1example.or().andUseridEqualTo(pb.getUserId());
					List<MyModule> l = mymoduleMapper.selectByExampleWithBLOBs(m1example);
					if(l.isEmpty()){
						result = mymoduleMapper.insertSelective(mymodule);
					}else{
						MyModuleExample mexample2 = new MyModuleExample();
						mexample2.or().andUseridEqualTo(pb.getUserId());
						result = mymoduleMapper.updateByExampleSelective(mymodule, mexample2);
					}
				}
			} catch (Exception e) {
				LOGGER.error("SAVE MYMODULE ERROR"+e);
			}
		}
		
		
		return result>0?1:0;
	}

	@Override
	public List<Module> getmymodule(ParamsBean pb) {
		String result = "";
		List<MyModule> mymodule = new ArrayList<MyModule>();
		List<Module> list = new ArrayList<Module>();
		MyModuleExample example = new MyModuleExample();
		example.or().andUseridEqualTo(pb.getUserId());
		try {
			mymodule = mymoduleMapper.selectByExampleWithBLOBs(example);
			
			if(null!=mymodule&&mymodule.size()>0){
				if(null!=mymodule.get(0).getModuleinfo()&&StringUtils.isNotBlank(mymodule.get(0).getModuleinfo())){
					result = mymodule.get(0).getModuleinfo();
					Gson gson = new Gson();
					 list = gson.fromJson(result, new TypeToken<List<Module>>(){}.getType());
				}
				
			}
		} catch (Exception e) {
			LOGGER.error("QUERY MY MODULE ERROR"+e);
			e.printStackTrace();
		}
	
		
		return list;
	}

	@Override
	public String risks(ParamsBean pb) {
		//按栏目搜资讯时，若用户选择了区域栏目，栏目资讯要按区域排序
		//1 先查用户选择的栏目
		
		MyModuleExample m1example = new MyModuleExample();
		m1example.or().andUseridEqualTo(pb.getUserId());
		List<MyModule> l = mymoduleMapper.selectByExampleWithBLOBs(m1example);
		List<Module> myselect = new ArrayList<Module>();
		List<String>  citys= new ArrayList<String>();
		if(null!=l&&l.size()>0){
			if(null!=l.get(0).getModuleinfo()&&StringUtils.isNotBlank(l.get(0).getModuleinfo())){
				String mjson = l.get(0).getModuleinfo();
				Gson gson = new Gson();
				myselect = gson.fromJson(mjson, new TypeToken<List<Module>>(){}.getType());
				//遍历用户选择的区域
				for(Module m : myselect){
					if(m.getMid()==17&&m.getHas()==1){
						 getcity(m,citys);
					}
				}
			}
		}
		String result = "";
		if(pb.getMid()>0){
			List<RiskSearchCondition> conditions = new ArrayList<RiskSearchCondition>();
			RiskSearchCondition condition1 = new RiskSearchCondition();
			List<Integer> mids = new ArrayList<Integer>();
			mids.add(pb.getMid());
			condition1.setCategoryIds(mids);
			conditions.add(condition1);
			
			RiskSearchCondition condition2 = new RiskSearchCondition();
			List<Integer> mids2 = new ArrayList<Integer>();
			mids2.add(pb.getMid());
			condition2.setCategoryIds(mids2);
			List<String> temp1 = new ArrayList<String>(); 
			if(null!=pb.getKeyword()&&!"".equals(pb.getKeyword())){
				
				for(String s : Arrays.asList(pb.getKeyword().trim().split(" "))){
					temp1.add(s);
				}
			//	List<String> temp1 = Arrays.asList(pb.getKeyword().trim().split(" "));
				
				
			}
			if(!citys.isEmpty()){
				for(String str : citys){
					temp1.add(str);
				}
			}
			if(!temp1.isEmpty()){
				condition2.setShouldTitleWords(temp1);
			}
			conditions.add(condition2);
			RiskSearchCondition condition3 = new RiskSearchCondition();
			List<Integer> mids3 = new ArrayList<Integer>();
			mids3.add(pb.getMid());
			condition3.setCategoryIds(mids3);
			List<String> temp2 = new ArrayList<String>(); 
			if(null!=pb.getKeyword()&&!"".equals(pb.getKeyword())){
				//temp2 =Arrays.asList(pb.getKeyword().trim().split(" "));
				for(String s : Arrays.asList(pb.getKeyword().trim().split(" "))){
					temp2.add(s);
				}
			}
			
			if(!citys.isEmpty()){
				for(String str : citys){
					temp2.add(str);
				}
			}
			if(!temp2.isEmpty()){
				condition3.setShouldContentWords(temp2);
			}
			conditions.add(condition3);
			Gson gson = new Gson();
			String jsonStr = gson.toJson(conditions);
			String from = "0";
			if(pb.getCurNumber()>0){
				from=(pb.getCurNumber()-1)*20+"";
			}else{
				from ="0";
			}
			String pageSize ="20";
			String fullContent ="false";
			String orderByColumn ="";
			if(pb.getSorttype()==0){
				orderByColumn ="SCORE";
			}else{
				orderByColumn ="TIME";
			}

			Map<String,String> map = new HashMap<String,String>();
			map.put("from", from);
			map.put("pageSize", pageSize);
			map.put("fullContent", fullContent);
			map.put("orderByColumn", orderByColumn);
			map.put("jsonStr", jsonStr);
			 result = getHtml(map,riskByMulti);
			SearchNewsData  data = new SearchNewsData();
			data = gson.fromJson(result, SearchNewsData.class);
			if(null!=pb.getKeyword()&&!"".equals(pb.getKeyword())){
				sethighlight(data,pb.getKeyword());
			}
			result = gson.toJson(data);
		}else if(StringUtils.isNotBlank(pb.getKeyword().trim())){
			//全库搜
			String queryString = pb.getKeyword().trim();
			String from = "0";
			if(pb.getCurNumber()>0){
				from=(pb.getCurNumber()-1)*20+"";
			}else{
				from ="0";
			}
			String pageSize ="20";
			String fullContent ="false";
			Map<String,String> map = new HashMap<String,String>();
			map.put("from", from);
			map.put("pageSize", pageSize);
			map.put("fullContent", fullContent);
			map.put("queryString", queryString);
			result = getHtml(map,riskByKeyWordOnly);
			
			SearchNewsData data = new SearchNewsData();
			
			Gson gson = new Gson();
			data = gson.fromJson(result,SearchNewsData.class);

			sethighlight(data,queryString);
			result = gson.toJson(data);
			
		}
		
		//title = title.replaceAll("("+sb.toString()+")", "<span style='color:red;'>$1</span>");
		//content = content.replaceAll("("+sb.toString()+")", "<span style='color:red;'>$1</span>");
		
		
		return result;
	}
	//设置高亮
	public void sethighlight(SearchNewsData  data ,String keyword){
		List<RiskCut> risks = new ArrayList<RiskCut>();
		List<RiskCut> newrisks = new ArrayList<RiskCut>();
		risks = data.getData();
		for(RiskCut risk :risks){
			String title = risk.getTitle();
			String content = risk.getContent();
			List<String> strs = Arrays.asList(keyword.trim().split(" "));
			for(String str : strs){
				if(null!=title){
					title = title.replaceAll(str, "<span style='color:red;'>"+str+"</span>");
				}
				if(null!=content){
					content = content.replaceAll(str, "<span style='color:red;'>"+str+"</span>");
				}
			
			}
			risk.setTitle(title);
			risk.setContent(content);
			newrisks.add(risk);
		}
		data.setData(newrisks);
	}
	
	

	@Override
	public SearchNewsData allrisks(ParamsBean pb) {
//		//查用户授权的栏目
//		UserModulesExample myexample = new UserModulesExample();
//		myexample.or().andUseridEqualTo(pb.getUserId());
//		List<UserModules> templist = usermodulesMapper.selectByExample(myexample);
//		if(templist.isEmpty()){
//			//所有栏目都未授权
//			return new SearchNewsData();
//		}else{
//			StringBuffer ids = new StringBuffer();
//			for(UserModules um : templist){
//				ids.append(um.getMid());
//				ids.append(",");
//			}
//			
//		}
	
		
		
		Gson gson = new Gson();
		RiskSearchCondition condition = new RiskSearchCondition();
		
		String queryString = pb.getKeyword().trim();
		String from = "0";
		if(pb.getCurNumber()>0){
			from=(pb.getCurNumber()-1)*20+"";
		}else{
			from ="0";
		}
		String pageSize ="20";
		String fullContent ="false";
		Map<String,String> map = new HashMap<String,String>();
		map.put("from", from);
		map.put("filterJsonStr", gson.toJson(condition));
		map.put("pageSize", pageSize);
		map.put("fullContent", fullContent);
		map.put("queryString", queryString);
		String result = getHtml(map,riskByKeyWordOnly);
		
		SearchNewsData data = new SearchNewsData();
		
		//Gson gson = new Gson();
		data = gson.fromJson(result,SearchNewsData.class);

		data.setCurNumber(pb.getCurNumber());
		sethighlight(data,queryString);
		return data;
	}
	private static String getHtml(Map<String, String> params ,String url) {

		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
		BasicCookieStore cookieStore = new BasicCookieStore();
		HttpClientBuilder httpClientBuilder = new HttpClientBuilder(false,
				poolingHttpClientConnectionManager, cookieStore);
		CloseableHttpClient httpClient = httpClientBuilder.getHttpClient();
		Fetcher fetcher1 = new Fetcher(cookieStore, httpClient);
		String html ="";
		try {
					html = fetcher1.post(url, params, null, "utf-8");
				} catch (Exception e) {
			
				}
		return html;
	}

	@Override
	public List<Module> allmodules() {
		List<Module> modules = new ArrayList<Module>();
		List<Module> resultmodules = new ArrayList<Module>();
		
		//查顶级栏目
		modules = getfinalnodemodule();
		//遍历出每个顶级栏目及子栏目
		for(Module module : modules){
			Module m = new Module();
			m = gettree(module.getMid());
			resultmodules.add(m);
		}
		return resultmodules;
	}
	//获取全部
	public List<Module> getfinalnodemodule(){
	
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(17);
		ids.add(20);
		ids.add(26);
		ids.add(149);
		List<Module> modules = new ArrayList<Module>();
		ModuleExample example = new ModuleExample();
		example.or().andMidIn(ids);
		example.setOrderByClause("sortnumber");
		modules = moduleMapper.selectByExample(example);
		return modules;
	}
	//根据pid获取节点对象
		public List<Module> getnodemodule(int nodeid){
			List<Module> module = new ArrayList<Module>();
			ModuleExample example = new ModuleExample();
			example.or().andParentidEqualTo(nodeid);
			module = moduleMapper.selectByExample(example);
			return module;
		}
	//根据节点id取节点对象
	public Module getmodule(int nodeid){
		Module module = new Module();
		module = moduleMapper.selectByPrimaryKey(nodeid);
		return module;
	}
	//递归获取树
	public Module gettree(int finalnodeid){
		//根据finalnoedid获取节点对象
		Module module = getmodule(finalnodeid);
		//查所有子节点
		List<Module> modules = getnodemodule(finalnodeid);
		//遍历子节点
		for(Module m : modules){
			Module n = gettree(m.getMid());
			module.getChild().add(n);
		}
		return module;
	}
	
	//构建自己的Tree
	public List<Module> bulidmytree(List<Module> list){
		List<Module> mytree = new ArrayList<Module>();
		mytree.addAll(allmodules())	;
		
		for(Module module : list){
			 build(mytree, module);
			
		}
		return mytree;
	}
	// 设置选择标志位
	public void build(List<Module> list ,Module selectedmodule){
		for(Module module :list){
			if(module.getMid().equals(selectedmodule.getMid())){
				module.setHas(1);
				break;
			}else if(module.getChild()!=null&&module.getChild().size()>0){
				build(module.getChild(),selectedmodule);
			}
		}
		
	}
//构建已给权限的TREE
	public List<Module> bulidrighttree(List<Module> righttree,List<Module> list){
		//List<Module> righttree = ModuleInit.modules;
		for(Module module : list){
			buildright(righttree, module);
		}
		return righttree;
	}
	//设置权限标志
	public void buildright(List<Module> list ,Module rightedmodule){
		for(Module module :list){
			if(module.getMid().equals(rightedmodule.getMid())){
				module.setRight(1);
				break;
			}else if(module.getChild()!=null&&module.getChild().size()>0){
				buildright(module.getChild(),rightedmodule);
			}
		}
	}
	
	@Override
	public Map<String,String> mykeyword(ParamsBean pb) {
		Map<String,String> map = new HashMap<String,String>();
		String mykeyword ="";
		List<MyModule> mymodule = new ArrayList<MyModule>();
		MyModuleExample example = new MyModuleExample();
		example.or().andUseridEqualTo(pb.getUserId());
		mymodule = mymoduleMapper.selectByExampleWithBLOBs(example);
		if(null!=mymodule&&mymodule.size()>0){
			mykeyword = mymodule.get(0).getKeyword();
			map.put("keyword", StringUtils.isNotBlank(mykeyword)?mykeyword:"");
			map.put("station", StringUtils.isNotBlank(mymodule.get(0).getStation())?mymodule.get(0).getStation():"");
			map.put("interest", StringUtils.isNotBlank(mymodule.get(0).getInterest())?mymodule.get(0).getInterest():"");
			map.put("responsibility", StringUtils.isNotBlank(mymodule.get(0).getResponsibility())?mymodule.get(0).getResponsibility():"");
			
		}
		return map;
	}

	@Override
	public List<Module> usermodules(int userid) {
		List<Module> modules = new ArrayList<Module>();
		
		//查用户已选择栏目
		List<MyModule> mymodule = new ArrayList<MyModule>();
		MyModuleExample myexample = new MyModuleExample();
		myexample.or().andUseridEqualTo(userid);
		mymodule = mymoduleMapper.selectByExampleWithBLOBs(myexample);
		List<Module> mine = new ArrayList<Module>();
		if(null!=mymodule&&mymodule.size()>0){
			if(null!=mymodule.get(0).getModuleinfo()&&StringUtils.isNotBlank(mymodule.get(0).getModuleinfo())){
				Gson gson = new Gson();
				mine = gson.fromJson(mymodule.get(0).getModuleinfo(), new TypeToken<List<Module>>(){}.getType());
			}else{
				mine.addAll(allmodules()) ;
			}
		}else{
			mine.addAll(allmodules()) ;
			//return new ArrayList<Module>();
		}
		UserModulesExample example = new UserModulesExample();
		example.or().andUseridEqualTo(userid);
		List<UserModules> list = usermodulesMapper.selectByExample(example);
		if(list.isEmpty()){
			//判断用户注册期限
			WordUsersExample userexample = new WordUsersExample();
			userexample.or().andUserIdEqualTo(userid);
			List<WordUsers> users = wordusersMapper.selectByExample(userexample);
			if(DateTool.betweenDate(users.get(0).getDateAffect(),new Date())<30){
				//所有栏目都给权限
				List<Module> allmodule = new ArrayList<Module>();
				//查所有栏目
				ModuleExample mexample = new ModuleExample();
				allmodule = moduleMapper.selectByExample(mexample);
				
				modules = bulidrighttree(mine,allmodule);
			}else{
				return new ArrayList<Module>();
			}
			
		}else{
			//根据栏目ID查栏目
			List<Module> m = new ArrayList<Module>();
			List<Integer> ids = new ArrayList<Integer>();
			for(UserModules um : list){
				ids.add(um.getMid());
			}
			ModuleExample me = new ModuleExample();
			me.or().andMidIn(ids);
			m = moduleMapper.selectByExample(me);
			modules = bulidrighttree(mine,m);
		}
		
		return modules;
	}
		
	//遍历地区
	public void getcity(Module module,List<String> list ){
		if(module.getHas()==1){
			if(module.getChild().isEmpty()){
				list.add(module.getMname());
				//map.put(module.getMid()+"",module.getMname());
			}else{
				for(Module m : module.getChild()){
					if(m.getHas()==0){
						continue;
					}else{
						 getcity(m,list);
					}
				}
			}
		}
	}

	/**
	 * 查询我的企业
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getCompany(ParamsBean pb) {

		List<UserCompany> UserCompanyList = new ArrayList<UserCompany>();
		List<CompanyGroupRelation> CompanyGrouplist = new ArrayList<CompanyGroupRelation>();
		UserCompanyExample ucExample = new UserCompanyExample();
		CompanyGroupRelationExample cgExample = new CompanyGroupRelationExample();
		List<Module>  list = new ArrayList<Module>();
		ModuleExample mExample = new ModuleExample();
		//根据userid查询
		ucExample.or().andUseridEqualTo(pb.getUserId());
		try{
			UserCompanyList = usercompanyMapper.selectByExample(ucExample);
			Integer companygroupid = null ;

			for (UserCompany userCompany : UserCompanyList) {
				companygroupid = userCompany.getCompanygroupid();
			}
			
			if(UserCompanyList.isEmpty()){
				//此用户没有企业组，默认为第一个
				RiskCompanyExample riskexample = new RiskCompanyExample();
				riskexample.setOrderByClause("id");
				List<RiskCompany> riskcompanys = riskCompanyMapper.selectByExample(riskexample);
				if(!riskcompanys.isEmpty()){
					companygroupid = riskcompanys.get(0).getId();
				}
				
			}
			if(companygroupid>0){
				//根据companygroupid查询
				cgExample.or().andCompanygroupidEqualTo(companygroupid);
				CompanyGrouplist = companyGroupRelationMapper.selectByExample(cgExample);
				List nlist = new ArrayList();
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i <CompanyGrouplist.size(); i++) {
					Integer companyid = CompanyGrouplist.get(i).getCompanyid();
					nlist.add(companyid);
					sb.append(companyid+",");
				}
				
				if(nlist!=null&&nlist.size()>0){
					mExample.or().andMidIn(nlist);
					list = moduleMapper.selectByExample(mExample);
				}
				if(StringUtils.isNotEmpty(sb.toString())){
					pb.setMids(sb.toString());
				}
				
				
			}
		}catch (Exception e) {
			LOGGER.error("QUERY MY MODULE ERROR"+e);
		}
		return list;
	}

	/**
	 * 按所有企业查
	 */
	public String allCompanyList(ParamsBean pb){
		String result = "";
		if(StringUtils.isNotEmpty(pb.getMids())){
			String [] ids = pb.getMids().split(",");
			List<RiskSearchCondition> conditions = new ArrayList<RiskSearchCondition>();
			for(String str : ids){
				RiskSearchCondition condition = new RiskSearchCondition();
				List<Integer> midsList = new ArrayList<Integer>();
				midsList.add(Integer.parseInt(str));
				condition.setCategoryIds(midsList);
				conditions.add(condition);
			}
			Gson gson = new Gson();
			String jsonStr = gson.toJson(conditions);
			String from = "0";
			if(pb.getCurNumber()>0){
				from=(pb.getCurNumber()-1)*20+"";
			}else{
				from ="0";
			}
			String pageSize ="20";
			String fullContent ="false";
			String orderByColumn ="TIME";

			Map<String,String> map = new HashMap<String,String>();
			map.put("from", from);
			map.put("pageSize", pageSize);
			map.put("fullContent", fullContent);
			map.put("orderByColumn", orderByColumn);
			map.put("jsonStr", jsonStr);
			 result = getHtml(map,riskByMulti);
			SearchNewsData  data = new SearchNewsData();
			data = gson.fromJson(result, SearchNewsData.class);
			if(null!=pb.getKeyword()&&!"".equals(pb.getKeyword())){
				sethighlight(data,pb.getKeyword());
			}
			result = gson.toJson(data);
		}
		
		
		return result;
	}
	/**
	 * 按企业更多查文章
	 */
	public String companyrisks(ParamsBean pb) {
				
				String result = "";
				if(pb.getMid()>0 ){
					List<RiskSearchCondition> conditions = new ArrayList<RiskSearchCondition>();
					RiskSearchCondition condition = new RiskSearchCondition();
					List<Integer> midsList = new ArrayList<Integer>();
					midsList.add(pb.getMid());
					condition.setCategoryIds(midsList);
					conditions.add(condition);
					Gson gson = new Gson();
					String jsonStr = gson.toJson(conditions);
					String from = "0";
					if(pb.getCurNumber()>0){
						from=(pb.getCurNumber()-1)*20+"";
					}else{
						from ="0";
					}
					String pageSize ="20";
					String fullContent ="false";
					String orderByColumn ="TIME";

					Map<String,String> map = new HashMap<String,String>();
					map.put("from", from);
					map.put("pageSize", pageSize);
					map.put("fullContent", fullContent);
					map.put("orderByColumn", orderByColumn);
					map.put("jsonStr", jsonStr);
					 result = getHtml(map,riskByMulti);
					SearchNewsData  data = new SearchNewsData();
					data = gson.fromJson(result, SearchNewsData.class);
					if(null!=pb.getKeyword()&&!"".equals(pb.getKeyword())){
						sethighlight(data,pb.getKeyword());
					}
					result = gson.toJson(data);
				}else if(StringUtils.isNotBlank(pb.getKeyword().trim())){
					//全库搜
					String queryString = pb.getKeyword().trim();
					String from = "0";
					if(pb.getCurNumber()>0){
						from=(pb.getCurNumber()-1)*20+"";
					}else{
						from ="0";
					}
					String pageSize ="20";
					String fullContent ="false";
					Map<String,String> map = new HashMap<String,String>();
					map.put("from", from);
					map.put("pageSize", pageSize);
					map.put("fullContent", fullContent);
					map.put("queryString", queryString);
					result = getHtml(map,riskByKeyWordOnly);
					
					SearchNewsData data = new SearchNewsData();
					
					Gson gson = new Gson();
					data = gson.fromJson(result,SearchNewsData.class);
					
					sethighlight(data,queryString);
					result = gson.toJson(data);
				}
				return result;
	}
	/**
	 * 根据mid查询三级企业信息
	 */
	public List<Module> companyParentRisk(ParamsBean pb) {
		List<Module> module = new ArrayList<Module>();
		ModuleExample example = new ModuleExample();
		example.or().andParentidEqualTo(pb.getMid());
		example.setOrderByClause("sortnumber");
		module = moduleMapper.selectByExample(example);
		return module;
	}
	/**
	 * 根据mids查询三级企业信息
	 */
	public List<Module> companyParentRisks(List<Integer> moduleMid){
		ModuleExample example = new ModuleExample();
		example.or().andParentidIn(moduleMid);
		List<Module> list = moduleMapper.selectByExample(example);
		return list;
	}
	/**
	 * 按企业查文章
	 */
	@Override
	public List<SearchRiskData> companyTitle(ParamsBean pb) {
		String result = "";
		
		List<SearchRiskData> rlist= new ArrayList<SearchRiskData>();
		
			List<RiskSearchCondition> conditions = new ArrayList<RiskSearchCondition>();
			RiskSearchCondition condition = new RiskSearchCondition();
			List<Integer> mid= new ArrayList<Integer>();
			mid.add(pb.getMid());
			condition.setCategoryIds(mid);
			conditions.add(condition);
			Gson gson = new Gson();
			String jsonStr = gson.toJson(conditions);
			String from = "0";
			if(pb.getCurNumber()>0){
				from=(pb.getCurNumber()-1)*5+"";
			}else{
				from ="0";
			}
			String pageSize ="5";
			String fullContent ="false";
			String 	orderByColumn ="TIME";

			Map<String,String> map = new HashMap<String,String>();
			map.put("from", from);
			map.put("pageSize", pageSize);
			map.put("fullContent", fullContent);
			map.put("orderByColumn", orderByColumn);
			map.put("jsonStr", jsonStr);
			result = getHtml(map,riskByMulti);
		    SearchRiskData  data = new SearchRiskData();
		 	data = gson.fromJson(result, SearchRiskData.class);
		 	
		 	rlist.add(data);		
		 			
			//result = gson.toJson(data);
		
		return rlist;
	}
    /**
     * 联想
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<Module> searchCompany(ParamsBean pb,String name, int pageId, int pageSize) {
		int start = (pageId-1)*pageSize;
		ModuleExample example = new ModuleExample();
		example.setLimitStart(start);
		example.setLimitEnd(pageSize);
		List<Module> company = getCompany(pb);
		@SuppressWarnings("rawtypes")
		List list = new ArrayList();
		for (int i = 0; i <company.size(); i++) {
			String mname = company.get(i).getMname();
			list.add(mname);
		}
		example.or().andMnameIn(list).andMnameLike("%"+name+"%");
		List<Module> list2 = moduleMapper.selectByExample(example);
		return list2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int searchCountCompany(ParamsBean pb,String name) {
		ModuleExample example = new ModuleExample();
		List<Module> company = getCompany(pb);
		@SuppressWarnings("rawtypes")
		List list = new ArrayList();
		for (int i = 0; i <company.size(); i++) {
			String mname = company.get(i).getMname();
			list.add(mname);
		}
		example.or().andMnameIn(list).andMnameLike("%"+name+"%");
		int count = moduleMapper.countByExample(example);
		return count;
	}

	
	
	
}
