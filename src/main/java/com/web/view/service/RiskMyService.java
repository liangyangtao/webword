package com.web.view.service;

import java.util.List;
import java.util.Map;

import com.database.bean.Module;
import com.web.bean.ParamsBean;
import com.web.bean.SearchNewsData;
import com.web.view.bean.Risk;
import com.web.view.bean.SearchRiskData;

public interface RiskMyService {
	
	/**
	 * 存储我的栏目
	 */
	
	public int savemymodule(ParamsBean pb);
	
	/**
	 *  查询我的栏目
	 */
	public List<Module>  getmymodule(ParamsBean pb);
	
	/**
	 * 按栏目查文章
	 */
	
	public String risks(ParamsBean pb);
	
	/**
	 * 全风险库搜
	 */
	public SearchNewsData allrisks(ParamsBean pb);
	
	/**
	 * 查所有栏目
	 */
	public List<Module> allmodules();
	
	/**
	 * 查我的关键词
	 */
	public Map<String,String> mykeyword(ParamsBean pb);
	
	/**
	 * 查授权栏目树
	 */
	public List<Module> usermodules(int userid);

	/**
	 * 查我的企业
	 */
	public List<Module> getCompany(ParamsBean pb);

	/**
	 * 按企业查文章
     */
	public String companyrisks(ParamsBean pb);
	
	public String allCompanyList(ParamsBean pb);

	public List<Module> companyParentRisk(ParamsBean pb);
    /**
     * 按左侧找3级企业
     * @param pb
     * @return
     */
	public List<SearchRiskData> companyTitle(ParamsBean pb);

	/**
	 * 按企业搜索
	 */
	public List<Module> searchCompany(ParamsBean pb, String name, int pageId,int pageSize);

	public int searchCountCompany(ParamsBean pb, String name);
    /**
     * 查询全部企业下的3级企业
     * @param allCompany
     */

	public List<Module> companyParentRisks(List<Integer> moduleMid);

	

	
}
