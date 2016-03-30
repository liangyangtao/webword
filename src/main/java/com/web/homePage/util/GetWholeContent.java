/*
 * <p>Title: 知识自动化平台</p>
 * <p>Description: GetWholeContent.java</p>
 * <p>Copyright: Copyright (c) 2015 北京银联信投资顾问有限责任公司，版权所有. </p>
 * <p>Company: 北京银联信投资顾问有限责任公司</p>
 * @author knight
 * @date 2015-5-18
 * @version 1.0
 */
package com.web.homePage.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.util.Md5Util;


/**
 * <p>Title: GetWholeContent</p>
 * <p>Description: TODO</p>
 * @author knight
 * @date 2015-5-18
 */
public class GetWholeContent {
	
	private static final int FIRST_NUM=1;
	private static final int SECOND_NUM=2;

	private String docFilePath="";
	private String htmlFilePath="";
	
	private static final Logger logger = Logger.getLogger(GetWholeContent.class);
	
	public GetWholeContent(String docFilePath,String htmlFilePath){
		this.docFilePath=docFilePath;
		this.htmlFilePath=htmlFilePath;
	}
	
	public static void main(String[] args) {
		//GetWholeContent content1 = new GetWholeContent("f:\\a\\22.doc","f:\\a");
		//Document  document=content1.getDocument("F:\\apache-tomcat-7.0.61\\webapps\\webword\\upload\\1432112564448.html","gb2312");
        //String content = document.body().html();
        //String newContent = "";
        //String tempStr = content;
        String regex = "(<img anchorname=[^>]*?>)";
		//Pattern pattern = Pattern.compile(regex,Pattern.UNICODE_CASE+Pattern.CASE_INSENSITIVE);
		String content = "<img anchorname=\"_354376841\" class=\"anchorclass\"><img anchorname=\"_Toc354376841\" class=\"anchorclass\">" +
				"<img dsgsdgsgsg><h1 class='aa'>nnnnnnnn</h1><p>aaa</p><h1 ssss>uuuu</h1>" +
				"<img dgdggdhgdh><img anchorname=\"_Toc354376841\" class=\"anchorclass\">";
		//Matcher matcher = pattern.matcher(content);
		//String newContent = "";
		//String tempStr = content;
		//while(matcher.find()){
 			content = content.replaceAll(regex, "<img>");
		//}
		System.out.println(content);
	}
	public String getContent() {
		String content = "";
		DocMain app = new DocMain(false);
		 try {
	        	app.setSaveOnExit(false);
	        	app.openDocument(docFilePath);
	        	String fileName =  System.currentTimeMillis()+"" ;
	            String htmlAddress=htmlFilePath+"\\"+fileName+".html";
	 	        app.saveAs(htmlFilePath+"\\"+fileName+".html");
	 	        Document  document=getDocument(htmlAddress,"UTF-8");// UTF-8 unicode
	 	        content = document.body().html();
	 	        //大于10兆不处理标题
	 	        File htmlFile = new File(htmlAddress);
	 	        if(htmlFile.length()<=1024*1024*10){
	 	        	String newContent = "";
		 	        String tempStr = content;
		 	        String regex="(<h[1-6][^>]*?>)([^\t]*?)(</h[1-6]>)";
		 			Pattern pattern = Pattern.compile(regex,Pattern.UNICODE_CASE+Pattern.CASE_INSENSITIVE);
		 			Matcher matcher = pattern.matcher(content);
		 			while(matcher.find()){
		 				String text = matcher.group(1);
		 				newContent += tempStr.substring(0,tempStr.indexOf(text));
		 				newContent += text.substring(0,3)+" id='"+Md5Util.getRandom()+"'";
		 				tempStr = tempStr.substring(tempStr.indexOf(text)+3);
		 			}
		 			content = newContent+tempStr;
	 	        }
	 			/*//将<a name="_Toc366259617">替换成<a>
	 			regex="(<a[^>]*?>)([^\t]*?)(</a>)";
	 			pattern = Pattern.compile(regex,Pattern.UNICODE_CASE+Pattern.CASE_INSENSITIVE);
	 			matcher = pattern.matcher(content);
	 			newContent = "";
	 			tempStr = content;
	 			while(matcher.find()){
	 				String text = matcher.group(1);
	 				newContent += tempStr.substring(0,tempStr.indexOf(text));
	 				newContent += "<a>";
	 				tempStr = tempStr.substring(tempStr.indexOf(matcher.group(2)));
	 			}
	 			content = newContent+tempStr;*/
	 			
	 	       String regex = "(<a name=[^>]*?>)";
	 			content = content.replaceAll(regex, "<a>");
	 			regex = "(<img anchorname=[^>]*?>)";
	 			content = content.replaceAll(regex, "<img>");
			} catch (RuntimeException e) {
				logger.error(e);
			}finally{
				app.close();
			}
		 return content;
	}
	
	/**
	 * 依据文件路径和编码将文件封装成DOCUMENT
	 * @param filePath
	 * @param encoding
	 * @return
	 */
	public  Document getDocument(String filePath,String encoding){
		String fileString="";
		try {
			fileString = FileUtils.readFileToString(new File(
					filePath), encoding);
		} catch (IOException e) {
			logger.error(e);
		}
		String articleContent=fileString.replaceAll("&Oslash;", "").trim();
		String content=articleContent.replaceAll("&nbsp;", "");
		String handlerContent=content.replaceAll("返回目录", "");
		Document doc = Jsoup.parse(handlerContent);
		modifyImageSrc(doc);
		doc.getElementsByAttributeValueContaining("style", "display:none").remove();
		doc.getElementsByAttributeValueContaining("style", "font-family:Wingdings;").remove();
		return doc;
	}
	/**
	 * 修改图片的路径，将本地路径改为网络路径
	 * @param document
	 */
	protected void modifyImageSrc(Document document){
		
		if(document==null){
			return;
		}
		for(Element e:document.getAllElements()){
			if(e.attributes().hasKey("style")){
				e.removeAttr("style");
			}
			
		}
		Elements elements=document.getElementsByTag("img");
		if(elements==null){
			return;
		}
		for(Element element:elements){
			String srcString=element.attr("src");
			String altString = element.attr("alt");
			//System.out.println(altString);
			boolean bl= srcString.startsWith("file");
			if(bl){
				String[] ps=srcString.split("\\\\");
				String path= ps[ps.length-SECOND_NUM]+"/"+ps[ps.length-FIRST_NUM];
				element.attr("src", "upload/"+path);
			}else{
				element.attr("src", "upload/"+srcString);
			}
		}
	}
}
