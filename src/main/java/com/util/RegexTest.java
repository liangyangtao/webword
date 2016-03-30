package com.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/*
 * 转义
 */
public class RegexTest {

	//循环方式解决
	public static String regexReplace(String str){
		
//		String str = "\\'abc\\'def'adsf'adf";
		List<Integer> list = new ArrayList<Integer>();
		char[] ch = str.toCharArray();
		for(int i = 0; i<ch.length; i++){
			if(String.valueOf(ch[i]).equals("'")){
				if(i == 0){
					list.add(i);
				}else{
					if(!String.valueOf(ch[i-1]).equals("\\")){
						list.add(i);
					}
				}
			}
		}
		StringBuffer sb = new StringBuffer(str);
		for(int i=list.size()-1; i>=0; i--){
			sb.replace(list.get(i), list.get(i)+1, "\\'");
		}
		return sb.toString();
	}
	
	//commons-lang方式
	public static String regexReplaceAll(String str){
//		StringBuffer sb = new StringBuffer(str);
		String aaa = StringUtils.replace(str, "\\'", "!@##@!!@##@!");
//		System.out.println(aaa);
		String bbb = StringUtils.replace(aaa,"'", "\\'");
//		System.out.println(bbb);
		String ccc = StringUtils.replace(bbb,"!@##@!!@##@!", "\\'");
		
		return ccc;
	}
	
	//多个空格替换成一个空格，可以替换多次
	public static String replaceAllSpace(String str){
		str = str.replaceAll(" +", " ");//替换所有空格
		return str;
	}
	
}
