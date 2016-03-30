package com.export;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.web.homePage.util.Word;



public class ChartReplacer {

	/**
	 * 正则表达式，匹配文本中的图片
	 */
	private static final String REGEXP = "(<img[^>]*>)|(<input[^>]*>)";
	private static final String SRC = "( src=\"[^\"]*\")|( src=\'[^\']*\')";

	/**
	 * 
	 * @param topic
	 *            导出文章实体
	 * @param lists
	 *            图片列表
	 * @return
	 */
	public static List<File> replaceIMG(ExportTopics topic, List<File> lists,
			String basePath) {
		for (Property p : topic.getList()) {
			//
			String text = p.getPostText();
			if (text != null && !text.equals("")) {
				p.setPostText(getElement(text, lists, basePath));
			}
		}
		return lists;
	}
	/**
	 * 
	 * @param text
	 *            文档字符串内容
	 * @param lists
	 *            图片列表
	 * @return
	 * 转换完成的字符串
	 */
	public static String  replaceIMG(String text, List<File> lists,String basePath) {
		String newstr="";	
		if (text != null && !text.equals("")) {
			newstr=getElement(text, lists, basePath);
		}
		return newstr;
		//return lists;
	}

	public static String getElement(String text, List<File> lists,
			String basePath) {
//		int count = 0;
//		boolean bl = false;
		Matcher matcher = getMatcher(REGEXP, text);
		while (matcher.find()) {
			Matcher m = getMatcher(SRC, matcher.group());
			while (m.find()) {
				String img = m.group();
				//System.out.println("++"+img);
				if(img.indexOf("\"")>-1){
					img = img.substring(6, img.lastIndexOf("\""));
				}else{
					img = img.substring(6, img.lastIndexOf("\'"));
				}
				
				int index = img.indexOf("webword/");
				if (index > 0) {
					img = img.substring(index+7, img.length());
				}
				//if(img.indexOf("http")==0||img.indexOf("HTTP")==0) continue;
				String realPath = basePath + File.separator + img;
				//System.out.println("realPath="+realPath);
				boolean flag = false;
				/*if(count == 0){
//					bl = true;
					realPath = ArticlePageOne.getPicPath(realPath,basePath);
					flag = DwindlePic.s_pic(realPath, realPath+"bak", 560, 560, true,true);
					count++;
					
				}else{*/
					flag = DwindlePic.s_pic(realPath, realPath+"bak", 560, 560, true,false);
//				}
				
				
				if(flag==true){
					File file = new File(realPath+"bak");
					lists.add(file);
				}else{
					File file = new File(realPath);
					lists.add(file);
				}
				break;//找到图片后退出
				
			}
		}
		return matcher.replaceAll(Word.TEXT);
	}

	private static Matcher getMatcher(String regexp, String content) {
		Pattern pattern = Pattern.compile(regexp, Pattern.UNICODE_CASE
				+ Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(content);
		return matcher;
	}
	//替换图片字符串
	public static  String  replaceImgText(String text){
		String textt = Pattern.compile(REGEXP).matcher(text).replaceAll(Word.TEXT);
		return textt;
	}
	/**
	 * test
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// String text =
		// "<input alt=\"\" src=\"updown/industry/photo/pjpeg/upload__8a30ae7_136aa8d75b2__7ffd_00000006.jpg\"\n type=\"image\" /><input alt=\"\" src=\"updown/industry/photo/pjpeg/upload__8a30ae7_136aa8d75b2__7ffd_00000006.jpg\" type=\"image\" />";
		String text = "<p><span style=\"color: gray; font-size: 12px\"><img src=\"http://wwww/1.jpg\" /><input alt=\"\" Src=\"new/industry/photo/gif/upload_eba85b8_136b8b558ef__7ffd_00000002.gif\" type=\"image\" /><iMg alt=\"\" src=\"updown/industry/photo/gif/upload_eba85b8_136b8b558ef__7ffd_00000000.gif\" /><img alt=\"\" src=\"updown/industry/photo/gif/upload_eba85b8_136b8b558ef__7ffd_00000001.gif\" />双击此处进行编辑</span></p>";
		/*
		ExportTopics topic = new ExportTopics();
		Property pro = new Property();
		pro.setPostText(text);
		List<Property> prolist= new ArrayList<Property>();
		prolist.add(pro);
		topic.setList(prolist);
		//文件列表
		List<File> lists = new ArrayList<File>();
		
		replaceIMG(topic,lists,"E://");
		*/
		List<File> lists = new ArrayList<File>();
		String textt=replaceIMG(text,lists,"E:\\");
		for(int i=0;i<lists.size();i++){
			 System.out.println("文件路径"+lists.get(i).getPath());
		}
		System.out.println(textt);
		/*
		String textt = Pattern.compile(REGEXP).matcher(text)
				.replaceAll(Word.TEXT);
		System.out.println("--"+replaceImgText(text)+"-");
		// System.out.println(textt);
		//获取图片的地址
		Pattern pattern = Pattern.compile(REGEXP, Pattern.UNICODE_CASE
				+ Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			// System.out.println(matcher.group());
			// System.out.println(matcher.group());
			System.out.println("--"+matcher.group());
			Pattern p = Pattern.compile("src=\".*?\"", Pattern.UNICODE_CASE
					+ Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(matcher.group());
			while (m.find()) {
				String s = m.group();
				String ss = s.substring(5, s.lastIndexOf("\""));
				System.out.println("ss="+ss);
			}
			// System.out.println(matcher.group(img));
		}
		matcher.replaceAll(Word.TEXT);
		*/
	}
}
