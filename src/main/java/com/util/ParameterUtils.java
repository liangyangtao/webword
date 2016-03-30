package com.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 前台变量替换的操作
 * 
 * @author apple
 */
public class ParameterUtils {

//	 
	
	public static Map<String, String> maps = new HashMap<String, String>();
	static{
		maps.put("{0}", "beginYear");	//'{0}':起始年, 
		maps.put("{1}", "beginMonth");	//'{1}':起始月份,
		maps.put("{2}", "beginDay");	//'{2}':起始日期（保留）
		maps.put("{3}", "endYear");		//'{3}':结束年
		maps.put("{4}", "endMonth");	//'{4}':结束月份
		maps.put("{5}", "endDay");		//'{5}':结束日期（保留）
		maps.put("{6}", "oldIndustry");	//'{6}':旧版行业名称
		maps.put("{7}", "privinceArea");//'{7}':带省市区的区域名称
		maps.put("{8}", "newIndustry");	//'{8}':新版行业名称
		maps.put("{9}", "area");		//'{9}':区域（不带市区）
		maps.put("{10}", "num_zh");		//'{10}':自动编号（中文）
		maps.put("{11}", "num");		//'{11}':自动编号（数字）
		maps.put("{12}", "beginQuarter");	//'{12}':起始年季度
		maps.put("{13}", "endQuarter");	//'{13}':结束年季度
		maps.put("{14}", "beginYear");	//'{14}':房地产报表区域名称; 动态模板参数请使用单引号，静态模板参数不要使用单引号,
		maps.put("{15}", "beginYear");	//'{15}':起始年(下季度)
		maps.put("{16}", "beginYear");	//'{16}':起始月(下季度),
		maps.put("{17}", "beginYear");	//'{17}':起始季度(下季度)
		maps.put("{18}", "beginYear");	//'{18}':结束年(下季度),
		maps.put("{19}", "beginYear");	//'{19}':结束月(下季度),
		maps.put("{20}", "beginYear");	// '{20}':结束季度(下季度)
		maps.put("{21}", "beginYear");	//'{21}':起始月(下月)。
	}
	
	
	public static void main(String[] args){
		String string = "第一章  {3}年{13}季度{9}{8}外部环境分析";
		System.out.println(string.indexOf("{3}"));
		String t = string.replace("{3}", "2014");
		String s = t.replace("{1}", "XXX");
		
		System.out.println(t);
		System.out.println(s);
	}
}
