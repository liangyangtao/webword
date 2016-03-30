package com.web.view.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.service.CommonController;
import com.database.bean.Module;
import com.database.bean.ModuleExample;
import com.database.bean.UserSaveContentWithBLOBs;
import com.database.bean.WordUsers;
import com.google.gson.Gson;
import com.web.bean.ParamsBean;
import com.web.bean.SearchNewsData;
import com.web.view.bean.Risk;
import com.web.view.bean.SearchRiskData;
import com.web.view.bean.SearchTitleData;
import com.web.view.bean.UserBuyThemeRiskStatus;
import com.web.view.service.RiskMyService;
import com.web.view.service.RiskViewService;

@Controller
@RequestMapping(value = "/risk/view/")
public class RiskMyController extends CommonController {
	@Autowired
	private  RiskMyService riskMyService;
	private int movepage = 5;
	@Autowired
	private RiskViewService riskViewService;
	private static final Logger LOGGER = Logger.getLogger(RiskMyController.class);
	/**
	 * 存储我的栏目
	 */
	@RequestMapping(value="saveMymodule" ,method=RequestMethod.POST)
	public  void saveMymodule(HttpSession session,HttpServletResponse response,ParamsBean pb ) throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		int userId = (Integer) session.getAttribute("userId");
		pb.setUserId(userId);
		int result = riskMyService.savemymodule(pb);
		map.put("result", result);
		map.put("info", "成功");
		
		try{
			LOGGER.info("urlName=risk/view/saveMymodule,urlMsg=保存栏目,result="+result+"userId="+userId);
		}catch(Exception e){
			
		}
		responseJson(response,map);
	}
	
	/**
	 * 查询我的栏目
	 */
	@RequestMapping(value="queryMymodule" ,method=RequestMethod.POST)
	public  void queryMymodule(HttpServletResponse response,ParamsBean pb ) throws IOException{
		Map<String,Object> map = new HashMap<String,Object>();
		String result = "";
	//	String result = riskMyService.getmymodule(pb);
		map.put("result", result);
		responseJson(response,map);
	}
	/**
	 * 按栏目查资讯
	 */
	@RequestMapping(value="queryArticleList" ,method=RequestMethod.GET)
	public  void queryArticleList(HttpServletResponse response,ParamsBean pb )  throws IOException{
		List<Risk> list = new ArrayList<Risk>();
		String result="";
		result = riskMyService.risks(pb);
		
		 
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
		//responseJson(response,result);
	}
	/***
	 * 按栏目查资讯
	 */
	@RequestMapping(value="queryArticleList" ,method=RequestMethod.POST)
	public  void newqueryArticleList(HttpServletResponse response,ParamsBean pb ,HttpSession session)  throws IOException{
		List<Risk> list = new ArrayList<Risk>();
		int userId = (Integer) session.getAttribute("userId");
		pb.setUserId(userId);
		String result = riskMyService.risks(pb);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
		//responseJson(response,result);
	}
	
	/**
	 * 按企业查资讯
	 */
	@RequestMapping(value="queryCompanyList" ,method=RequestMethod.GET)
	public  void queryCompanyList(HttpServletResponse response,ParamsBean pb,HttpServletRequest request)  throws IOException{
		List<SearchTitleData> list = new ArrayList<SearchTitleData>();
		List<SearchRiskData> tlist = new ArrayList<SearchRiskData>();
		
		String result = "";
		List<Module> module = new ArrayList<Module>();
		//点击左侧企业
		if(pb.getMid()>0){
			module = riskMyService.companyParentRisk(pb);
			//如果有3级
			if(module.size()>0){
			for (int i = 0; i < module.size(); i++) {
				Integer mid = module.get(i).getMid();
				pb.setMid(mid);
				tlist =riskMyService.companyTitle(pb);
				SearchTitleData sd = new SearchTitleData();
				sd.setMid(pb.getMid());
				sd.setMname(module.get(i).getMname());
				sd.setList(tlist);
				list.add(sd);
			}  
			Gson gson = new Gson();
		 	result = gson.toJson(list);
			}else{
				//根据2级更多查询
				result = riskMyService.companyrisks(pb);
			}
		}else{
			//全部
			List<Integer> moduleMid = new ArrayList<Integer>();
			List<Module> allCompany = riskMyService.getCompany(pb);
			for (int i = 0; i <allCompany.size(); i++) {
				Integer mid = allCompany.get(i).getMid();
				moduleMid.add(mid);
			}
			List<Module> companyList = riskMyService.companyParentRisks(moduleMid);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i <companyList.size(); i++) {
				Integer companyid = companyList.get(i).getMid();
				sb.append(companyid+",");
			}
			pb.setMids(sb.toString());
			result = riskMyService.allCompanyList(pb);
		}
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
	/***
	 * 按企业查资讯
	 */
	@RequestMapping(value="queryCompanyList" ,method=RequestMethod.POST)
	public  void queryCompanyList(HttpServletResponse response,ParamsBean pb ,HttpSession session)  throws IOException{
		int userId = (Integer) session.getAttribute("userId");
		pb.setUserId(userId);
		List<SearchTitleData> list = new ArrayList<SearchTitleData>();
		List<SearchRiskData> tlist = new ArrayList<SearchRiskData>();
		
		String result = "";
		List<Module> module = new ArrayList<Module>();
		//点击企业查询
		if(pb.getMid()>0){
			module = riskMyService.companyParentRisk(pb);
			//如果有3级
			if(module.size()>0){
			for (int i = 0; i < module.size(); i++) {
				Integer mid = module.get(i).getMid();
				pb.setMid(mid);
				tlist =riskMyService.companyTitle(pb);
				SearchTitleData sd = new SearchTitleData();
				sd.setMid(pb.getMid());
				sd.setMname(module.get(i).getMname());
				sd.setList(tlist);
				list.add(sd);
			}  
			Gson gson = new Gson();
		 	result = gson.toJson(list);
			}else{
				//根据2级更多查询
				result = riskMyService.companyrisks(pb);
			}
		}else{
			//全部
			List<Integer> moduleMid = new ArrayList<Integer>();
			List<Module> allCompany = riskMyService.getCompany(pb);
			for (int i = 0; i <allCompany.size(); i++) {
				Integer mid = allCompany.get(i).getMid();
				moduleMid.add(mid);
			}
			List<Module> companyList = riskMyService.companyParentRisks(moduleMid);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i <companyList.size(); i++) {
				Integer companyid = companyList.get(i).getMid();
				sb.append(companyid+",");
			}
			if(StringUtils.isNotEmpty(sb.toString())){
			    pb.setMids(sb.toString());
			}
			result = riskMyService.allCompanyList(pb);
		}
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(result);
		out.close();
	}
	/*//更多查找
	@RequestMapping(value="companyMoreRisk")
	public  void companyMoreRisk(HttpServletResponse response,ParamsBean pb ,HttpSession session)  throws IOException{
		String companyrisks = riskMyService.companyrisks(pb);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.write(companyrisks);
		out.close();
	}*/
    /**
     * 栏目json
     */
	@RequestMapping(value="allmodules" ,method=RequestMethod.POST)
	public  void allmodules(HttpServletResponse response,ParamsBean pb ) throws IOException{
		
			List<Module> modules = new ArrayList<Module>();
			modules.addAll(riskMyService.allmodules()) ;
		//	modules = riskMyService.allmodules();
			Gson gson = new Gson();
			System.out.println("=========="+gson.toJson(modules));
			response.setHeader("Access-Control-Allow-Origin", "*");
			responseJson(response,modules);
	}
	/**
	 * 编辑栏目弹出授权目录树
	 */
	@RequestMapping(value="rightmodules" ,method=RequestMethod.POST)
	public  void rightmodules(HttpSession session,HttpServletResponse response,ParamsBean pb ) throws IOException{
		int userId = (Integer) session.getAttribute("userId");
			List<Module> modules = new ArrayList<Module>();
			modules = riskMyService.usermodules(userId);
			
			
		//	modules = riskMyService.allmodules();
			Gson gson = new Gson();
			System.out.println("=========="+gson.toJson(modules));
			response.setHeader("Access-Control-Allow-Origin", "*");
			responseJson(response,modules);
	}
	

	
	/**
	 * 全库搜索
	 */
	
	@RequestMapping(value = "/searchallbyword")
	public String searchall(HttpServletResponse response, HttpSession session,
			Model model,ParamsBean pb) {
		Integer startPage = pb.getStartPage();
		int pageSize = 20 ;
		SearchNewsData data =  riskMyService.allrisks(pb);
		model.addAttribute("keyword",pb.getKeyword());
		model.addAttribute("list", data);
		Long count = data.getCount();
		Integer icount = Integer.parseInt(count+"");
		int p= ((icount % pageSize == 0) ? (icount / pageSize): ((icount / pageSize) + 1));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startPage", (startPage == null ? 1 : startPage));
		map.put("endPage", Math.min((startPage == null ? 1 : startPage)
				+ movepage, p));
		map.put("count", count);
		map.put("pageCount",p);
		map.put("movepage", movepage);
		map.put("pageSize",20);
		map.put("pageNo", pb.getCurNumber());
		model.addAttribute("page", map);
		
		//获取用户信息
		WordUsers user = (WordUsers) session.getAttribute("user");
		//获取区域栏目
		//List<Module> listModule = riskViewService.getModuleByPid(0);//获取一级栏目
		List<Module> listModule = riskViewService.getModulesByParentId(0);//获取一级栏目
		model.addAttribute("id", 0);
		model.addAttribute("user", user);
		model.addAttribute("listModule", listModule);

		//model.addAttribute("info","");
		try{
			LOGGER.info("urlName=risk/view/saveMymodule,urlMsg=保存栏目,keyword="+pb.getKeyword());
		}catch(Exception e){
			
		}
		return "/riskview/MyRiskSearch";
	}
	/**
	 * 我的风险预警
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/myrisk")
	public String myrisk(HttpServletResponse response, HttpSession session,
			Model model) {
	
		int userId = 0;
		if(session.getAttribute("userId")==null){//没有登录，直接跳转到登录
			return "redirect:/risk/view/tologin.do";
		}else{
			userId = (Integer) session.getAttribute("userId");
		}
		
		WordUsers user = (WordUsers) session.getAttribute("user");
		Map<String,String> maps = new HashMap<String,String>();
		//查询用户状态 1 表示已购买栏目，2 栏目  11 购买  12 购买即将到期  13 购买已经到期  21 试用阶段 22 试用即将到期 23 试用已经到期  
		int status=riskViewService.getUserThemeStatus(user,maps);
		
		model.addAttribute("status", status);
		if(status==UserBuyThemeRiskStatus.JJDQ_SY){
			model.addAttribute("endTime", riskViewService.getExpirationDate(user));
		}
		if(status==UserBuyThemeRiskStatus.JJDQ_GM){
			if(session.getAttribute("company")!=null){
				session.setAttribute("company", 0);
				model.addAttribute("status",UserBuyThemeRiskStatus.JJDQ_SY);
			}else{
			List<Module> getshopDate = riskViewService.getshopDate(user,maps);
			int date = riskViewService.getcompanyDate(user);
			model.addAttribute("getshopDate", getshopDate);
			model.addAttribute("companyDate",date);
			
			//session.setAttribute("getshopDate", getshopDate);
			session.setAttribute("company", date);
			}
		}
		ParamsBean pb = new ParamsBean();
		pb.setUserId(userId);
		List<Module> list = riskMyService.getmymodule(pb);
		List<Module> companyList = riskMyService.getCompany(pb);
		Map<String,String> mymap = riskMyService.mykeyword(pb);
	//	System.out.println(mymodule);
		//取第一个栏目的第一个子栏目
		String firstmid = "";
		String name = "";
		if(null!=list&&list.size()>0){
			for(Module m : list){
				if("".equals(firstmid)){
					if(m.getHas()==0){
						continue;
					}else{
						firstmid = fistmodule(m).get("id").toString();
						name =  fistmodule(m).get("name").toString();
					}
				}

			}
			model.addAttribute("listSize",list.isEmpty()?0:list.size());
		}else{//没有栏目
			model.addAttribute("listSize",0);
		}
		
		if(StringUtils.isNotBlank(firstmid)){
			model.addAttribute("firstmid",firstmid);
			model.addAttribute("fistname",name);
		}else{
			model.addAttribute("firstmid","0");
			model.addAttribute("fistname",mymap.get("keyword"));
		}
	
		model.addAttribute("list", list);
		model.addAttribute("companyList", companyList);
		model.addAttribute("keyword",mymap.get("keyword"));
		model.addAttribute("station",mymap.get("station"));
		model.addAttribute("interest",mymap.get("interest"));
		model.addAttribute("responsibility",mymap.get("responsibility"));
		
		//获取区域栏目
		//List<Module> listModule = riskViewService.getModuleByPid(0);//获取一级栏目
		List<Module> listModule = riskViewService.getModulesByParentId(0);//获取一级栏目
		model.addAttribute("id", 0);
		model.addAttribute("user", user);
		model.addAttribute("listModule", listModule);
		try{
			LOGGER.info("urlName=risk/view/myrisk,urlMsg=我的风险预警, userId="+userId);
		}catch(Exception e){
			
		}
		return "/riskview/MyRisk";
	}
	//递归第一个栏目
	public Map<String,String> fistmodule(Module module){
		Map<String,String> map = new HashMap<String,String>();
	
		int mid =0 ;
		if(module.getHas()==1){
			if(module.getChild().isEmpty()){
				mid =module.getMid();
				map.put("id", mid+"");
				map.put("name", module.getMname());
				return map;
			}else{
				for(Module m : module.getChild()){
					if(null==map.get("id")){
						if(m.getHas()==0){
							continue;
						}else{
							map = fistmodule(m);
						}
					}
				}
			}
		}
		return map;
	}
	@RequestMapping(value = "/add2MyCollection")
	public void add2MyCollection(HttpServletResponse response, HttpSession session,UserSaveContentWithBLOBs usc,
			Model model) {
		Map<String,Object> map = new HashMap<String,Object>();
		int userId = (Integer) session.getAttribute("userId");
		usc.setUserId(userId);
		int result = riskViewService.insertUserCollection(usc);
		map.put("result", result);
		map.put("info", "成功");
		try {
			responseJson(response,map);
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
	}
	/**
	 * 按企业名称搜索
	 */
	@RequestMapping(value="/searchCompanyList")
	@ResponseBody
	public void searchCompanyList(ParamsBean pb,HttpServletResponse response,HttpSession session,String name,int pageId,int pageSize){
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("status", "success");
		int userId = (Integer) session.getAttribute("userId");
		pb.setUserId(userId);
		maps.put("list", riskMyService.searchCompany(pb, name,pageId,pageSize));
		maps.put("count", riskMyService.searchCountCompany(pb,name));
		maps.put("pageId", pageId);
		try {
			responseJson(response, maps);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
