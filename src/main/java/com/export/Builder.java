package com.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.web.word.bean.NodeBean;
import com.web.word.bean.NodeContentBean;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;



/**
 * 导出文档的构建器:
 * 0， 初始化模板信息
 * 1， 获取文档的信息
 * 2， 通过模板生成HTML页面
 * 3， 通过HTML转换称为Word文档
 * 
 * @author apple
 */
public final class Builder {
	
	public static String ENCODE = "UTF-8";
	public static String FILE_PATH = "";
	public static String DOWNLOAD_FILE_NAME="filename";
	
	private static Configuration  conf = new Configuration();
	{
		conf.setDefaultEncoding(ENCODE);
		conf.setClassForTemplateLoading(this.getClass(), "/com/export/template");
		conf.setObjectWrapper(new DefaultObjectWrapper());
	}
	
	private static Builder builder = new Builder();
	
	private Builder(){
		//TODO 执行一些初始化的方法
	}
	
	public static Builder getInstance(){
		if(builder == null){
			builder = new Builder();
		}
		
		return builder;
	}
	
	/**
	 * 生成HTML文件
	 * @param fileName
	 */
	public void generateHTML(String fileName, List<NodeBean> nodes, String style){
		
		FileOutputStream fos = null;
		Writer out = null;
		
		try{
			File  file = new File(fileName);
			fos = new FileOutputStream(file);
			out = new OutputStreamWriter(fos,"UTF-8");

			generateJspHead(out, fileName);
			generateStyle(out, style);
			generateHeadEnd(out);
			generateBody(out, nodes);
			generateFooter(out);
			
			out.flush();
		}catch(Exception e){
			
		}finally{
			//TODO 关闭输出流的内容
			try{
				if(fos != null){
					fos.close();
				}
				if(out != null){
					out.close();
				}
			}catch(Exception ee){
				//Nothing to do 
			}
		}
	}
	/*
	 * 返回html的字符转
	 * 
	 */
	/*
	 * 字符串写入文件
	 * filepath:文件的绝对路径，写入的字符串
	 */
	public void writeFile(String fileName,String text){
		FileOutputStream fos = null;
		Writer out = null;
		
		try{
			File  file = new File(fileName);
			fos = new FileOutputStream(file);
			out = new OutputStreamWriter(fos,"UTF-8");
			out.write(text);
			out.flush();
		}catch(Exception e){
			
		}finally{
			//TODO 关闭输出流的内容
			try{
				if(fos != null){
					fos.close();
				}
				if(out != null){
					out.close();
				}
			}catch(Exception ee){
				//Nothing to do 
			}
		}
	}
	public String getHtmlStr(String fileName,List<NodeBean> nodes, String style) throws Exception{
		
		Writer out = new StringWriter();
		Map<String, String> maps = new HashMap<String, String>();
		//文件头
		maps.put(DOWNLOAD_FILE_NAME, fileName);
		Template template = conf.getTemplate("header.ftl");
		template.process(maps, out);
		
		//文件样式style
		maps.put("style", style);
		template = conf.getTemplate("style.ftl");
		template.process(maps, out);
		
		//HTML head 结束
		template = conf.getTemplate("header_end.ftl");
		template.process(null, out);
		
		//body结束
//		Map<String, List<NodeBean>> nodemaps = new HashMap<String, List<NodeBean>>();
		Map<String, Object> nodemaps = new HashMap<String, Object>();
		nodemaps.put("nodes", nodes);
		template = conf.getTemplate("forum_topics.ftl");
		template.process(nodemaps, out);
		//foot
		template = conf.getTemplate("footer.ftl");
		template.process(null, out);
		String strhtml=out.toString();
		strhtml = addlist(strhtml);
		//String strhtml=Function.optimizeContent(out.toString());
		out.close();
		return strhtml;
	}
	public String getContentHtmlStr(String fileName,String html, String style) throws Exception{
		
		Writer out = new StringWriter();
		Map<String, String> maps = new HashMap<String, String>();
		//文件头
		maps.put(DOWNLOAD_FILE_NAME, fileName);
		Template template = conf.getTemplate("header.ftl");
		template.process(maps, out);
		
		//文件样式style
		maps.put("style", style);
		template = conf.getTemplate("style.ftl");
		template.process(maps, out);
		
		//HTML head 结束
		template = conf.getTemplate("header_end.ftl");
		template.process(null, out);
		
		//body
		maps.put("bodyHtml", html);
		template = conf.getTemplate("body.ftl");
		template.process(maps, out);
		//foot
		template = conf.getTemplate("footer.ftl");
		template.process(null, out);
		String strhtml=out.toString();
		strhtml = addlist(strhtml);
		//String strhtml=Function.optimizeContent(out.toString());
		out.close();
		return strhtml;
	}
	
	//新首页内容
	public String getHomePageHtml(List<NodeBean> nodes, String style) throws Exception{
		Writer out = new StringWriter();
		Map<String, String> maps = new HashMap<String, String>();
		Template template = conf.getTemplate("forum_topics.ftl")/*conf.getTemplate("style.ftl")*/;
//		maps.put("style", style);
//		template.process(maps, out);
		Map<String, Object> nodemaps = new HashMap<String, Object>();
		nodemaps.put("nodes", nodes);
//		template = conf.getTemplate("forum_topics.ftl");
		template.process(nodemaps, out);
		
		String strHtml = out.toString();
		
		out.close();
		
		return strHtml;
	}
	
	
	/**
	 * 生成HTML Head
	 * @param out
	 */
	private void generateJspHead(Writer out, String fileName) throws Exception{
		Map<String, String> maps = new HashMap<String, String>();
		maps.put(DOWNLOAD_FILE_NAME, fileName);
		
		Template template = conf.getTemplate("header.ftl");
		template.process(maps, out);
	}
	
	/**
	 * 生成HTML style
	 * @param out
	 */
	private void generateStyle(Writer out, String style) throws Exception{
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("style", style);
		Template template = conf.getTemplate("style.ftl");
		template.process(maps, out);
	}
	
	/**
	 * 生成HTML head 结束
	 * @param out
	 */
	private void generateHeadEnd(Writer out) throws Exception{
		Template template = conf.getTemplate("header_end.ftl");
		template.process(null, out);
	}
	
	/**
	 * 生成HTML body
	 * @param out
	 */
	private void generateBody(Writer out, List<NodeBean> nodes) throws Exception{
		Map<String, List<NodeBean>> maps = new HashMap<String, List<NodeBean>>();
		maps.put("nodes", nodes);
		
		Template template = conf.getTemplate("forum_topics.ftl");
		template.process(maps, out);
	}
	
	/**
	 * 生成HTML Footer
	 * @param out
	 */
	private void generateFooter(Writer out) throws Exception{
		Template template = conf.getTemplate("footer.ftl");
		template.process(null, out);
	}
	/*
	 * 递归生成list
	 */
	public  List<NodeBean> getNodeList(List<NodeBean> nodes,List<NodeBean> newnodes,int realId){
		realId+=1;
		for(NodeBean bean : nodes){
			bean.setRealId(realId);
			if(bean.getContent()!=null){
				bean.setContent(setTitle(realId,bean.getContent()));//规则化标题的内容
			}
			newnodes.add(bean);
			if(bean.getChildren().size()>0){
				getNodeList(bean.getChildren(),newnodes,realId);
			}
		}
		return newnodes;
	}
	
	/*
	 * 操作节点标题
	 */
	public String setTitle(int realId,String str){
		//str= str.toLowerCase();
		//String strnew=str.toLowerCase();
		//System.out.println(str);
		String newstr=setTable(str);
		str=setImgstr(newstr);
		realId+=1;
		//System.out.println("str="+str);
		String strreg= "<p (class=contentname|class=\"contentname\")>[^<]*<strong name=[^>]*>([^>]*)</strong>[^\t]*?</p>";//过滤的正则
		Pattern pattern = Pattern.compile(strreg, Pattern.UNICODE_CASE+ Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		StringBuffer replaceStr = new StringBuffer();
		int index=0;
		while(matcher.find()){
			//System.out.println(matcher.groupCount());
			//System.out.println(matcher.group(2));
			if(matcher.group(2)!=null){
				index++;
				matcher.appendReplacement(replaceStr, "<h"+realId+">"+index+"."+matcher.group(2)+"</h"+realId+">");//逐个替换地址
			}
		}
		matcher.appendTail(replaceStr);//添加尾部
		return replaceStr.toString();
		/*
		while(matcher.find()){
			index++;
			String divstr=matcher.group();
			//System.out.println(divstr);
			String strongname=divstr.substring(divstr.lastIndexOf("<strong>")+8,divstr.lastIndexOf("</strong>"));
			//Pattern pattern1 = Pattern.compile(strreg, Pattern.UNICODE_CASE+ Pattern.CASE_INSENSITIVE);
			Matcher matcher1 = pattern.matcher(strnew);
			strnew=matcher1.replaceFirst("<h"+realId+">"+index+"."+strongname+"</h"+realId+">");
		}
		*/
		//return strnew;
	}
	/*
	 * 替换<p><img></p>
	 */
	public String setImgstr(String str){
		String strreg= "(<p [^>]*>)+(<img[^>]*>)(.?</p>)+";
		Pattern pattern = Pattern.compile(strreg,Pattern.UNICODE_CASE+ Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		StringBuffer replaceStr = new StringBuffer();
		while(matcher.find()){
			String divstr = matcher.group(2);//img
			//String divstr = matcher.group(1);//img
			//System.out.println(divstr);
			matcher.appendReplacement(replaceStr, "<div>"+matcher.group(2)+"</div>");//逐个替换地址
		}
		matcher.appendTail(replaceStr);//添加尾部
		return replaceStr.toString();
		
	}
	/*
	 * 替换空的表格 
	 * <table border="0" cellspacing="0" cellpadding="0" align="left" style="padding-

right:10px;"></table>
	 *       <table border="0" cellspacing="0" cellpadding="0" align="left">
                    <tbody>
                        <tr class="firstRow">1</tr>
                    </tbody>
                </table>
	 */
	public String setTable(String str){
		//System.out.println(str);
		String strreg= "(<table[^>]*>[^\t]*?</table>)+";
		Pattern pattern = Pattern.compile(strreg,Pattern.UNICODE_CASE+Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		StringBuffer replaceStr = new StringBuffer();
		while(matcher.find()){
			String divstr = matcher.group(1);//img
			//String divstr = matcher.group(1);//img
			//System.out.println("divstr="+divstr);
			//System.out.println("index="+divstr.indexOf("<td"));
			if(divstr.toLowerCase().indexOf("<td")==-1&&divstr.toLowerCase().indexOf("<th")==-1){//没有包含td和 th的表格
				matcher.appendReplacement(replaceStr, "");//逐个替换地址
			}
		}
		matcher.appendTail(replaceStr);//添加尾部
		return replaceStr.toString();
	}
	  /**  
     *  复制单个文件  
     *  @param  oldPath  String  原文件路径  如：c:/fqf.txt  
     *  @param  newPath  String  复制后路径  如：f:/fqf.txt  
     *  @return  boolean 1.原文件不存在 不操作，1新文件存在 覆盖。   
     */  
   public void  copyFile(String  oldPath,  String  newPath)  {  
       try  {  
           //int  bytesum  =  0;  
           int  byteread  =  0;  
           File  oldfile  =  new  File(oldPath);
           if  (oldfile.exists())  {  //文件存在时 
               InputStream  inStream  =  new  FileInputStream(oldPath);  //读入原文件  
               FileOutputStream  fs  =  new  FileOutputStream(newPath);  
               byte[]  buffer  =  new  byte[1024];  
               //int  length;  
               while  (  (byteread  =  inStream.read(buffer))  !=  -1)  {  
                   //bytesum  +=  byteread;  //字节数  文件大小  
                  // System.out.println(bytesum);  
                   fs.write(buffer,  0,  byteread);  
               }
               inStream.close();
               fs.close();
               oldfile.delete();//删除老文件
           }  
       }  
       catch  (Exception  e)  {  
           //System.out.println("复制单个文件操作出错");  
             
 
       }  
 
   } 
 /*
  * 给图片和表格增加 编号
  */
 	public String addlist(String str){
 		//str=str.toLowerCase();
 		String strreg= "(<div class=chart>[^<]+</div>[^<]*<table[^>]*>)|(<div class=\"chart\">[^<]+</div>[^<]*<table[^>]*>)|"+
 				"(<div class=chart>[^<]+</div>[^<]*<div class=[^>]*>[^<]*<img[^>]*>)|(<div class=\"chart\">[^<]+</div>[^<]*<div class=[^>]*>[^<]*<img[^>]*>)|" +
 				"(<table[^>]*>)|(<div[^>]*>[^<]*<img[^>]*>.?</div>)|(<img[^>]*>)";
 		Pattern pattern = Pattern.compile(strreg,Pattern.UNICODE_CASE+ Pattern.CASE_INSENSITIVE);
 		Matcher matcher = pattern.matcher(str);
 		StringBuffer replaceStr = new StringBuffer();
 		int index=1;
 		while(matcher.find()){
 			//String divstr = matcher.group(0);//img
 			//System.out.println("matcher.group(0)="+matcher.group(0));
 			String divstr = null;//img
 			int strlength = matcher.groupCount();
 			//System.out.println("strlength="+strlength);
 			for(int i=0;i<strlength;i++){
 				//System.out.println("i="+i);
 				//System.out.println("matcher.group(i)="+matcher.group(i));
 				if(matcher.group(i)!=null){
 					divstr=matcher.group(i);
 					//System.out.println("divstr="+divstr+",i="+i);
 					//if(i==3||i==4){//是图片
 						if(divstr.toLowerCase().indexOf("text=")>-1){//存在 是插件图片，不处理
 							divstr=null;
 							continue;
 						}
 					//}
 					if(i==1||i==2|i==3||i==4){//自己带标题
 						int startindex = divstr.toLowerCase().indexOf("：");
 						int endindex = divstr.toLowerCase().indexOf("</div>");
 						if(startindex==-1||endindex==-1) continue;//没有解析出字符串
 						String strname = divstr.substring(startindex,endindex);
 						startindex = divstr.toLowerCase().indexOf("</div>");
 						 if(startindex!=-1){
 							 divstr ="<div class=\"chart\">图表"+index+""+strname+"</div>"+divstr.substring(startindex+6);
 						 }
 					}else{
 						//divstr="<div class=\"chart\">图表"+index+"</div>"+divstr;
 						divstr=null;
 					}
 					continue;
 				}
 			}//for
 			//System.out.println("divstr="+divstr);
 			if(divstr!=null){
 				//System.out.println("2divstr="+divstr);
 				matcher.appendReplacement(replaceStr, divstr);//逐个替换地址
 				index++;
 			}
 		}
 		matcher.appendTail(replaceStr);//添加尾部
 		return replaceStr.toString();
 		
 	}
	public static void main(String[] args) throws Exception{
		String style = "h1{text-align:center;line-height:20pt;font-size:15pt;font-family:宋体;margin:46.8pt auto 39pt auto;color:black;}h2{line-height:20pt;font-size:12pt;font-family:宋体;margin-top:31.2pt;margin-bottom:23.4pt;color:black;}h3{margin-top:23.4pt;margin-bottom:15.6pt;text-indent:22.1pt;line-height:20.0pt;font-family:宋体;font-size:11.0pt;}h4{margin-top:15.6pt;text-indent:22.4pt;line-height:20.0pt;font-size:11.0pt;font-family:宋体;}.chart{margin-top:15.6pt;mso-para-margin-top:1.0gd;mso-bidi-font-size:11.0pt;mso-bidi-font-weight:normal;mso-hansi-font-family:Arial;mso-bidi-font-family:Arial;mso-font-kerning:0pt;text-align:center;text-autospace:none;font-family:华文细黑;font-size:10.0pt;color:black;margin-right:0cm;margin-bottom:0cm;margin-left:0cm;margin-bottom:.0001pt;}.chartUnit{margin-top:15.6pt;mso-para-margin-top:1.0gd;mso-bidi-font-size:11.0pt;mso-bidi-font-weight:normal;mso-hansi-font-family:Arial;mso-bidi-font-family:Arial;mso-font-kerning:0pt;text-align:center;text-autospace:none;font-family:华文细黑;font-size:10.0pt;color:black;margin-right:0cm;margin-bottom:0cm;margin-left:0cm;margin-bottom:.0001pt;}.from{margin-bottom:15.6pt;mso-para-margin-bottom:1.0gd;text-align:center;tab-stops:135.0pt;font-size:10.0pt;mso-bidi-font-size:11.0pt;font-family:华文细黑;margin-top:0cm;margin-right:0cm;margin-left:0cm}.content p{margin-top:17.8pt;text-indent:22.4pt;line-height:20.0pt;mso-line-height-rule:exactly;font-size:11.0pt;font-family:华文细黑;mso-hansi-font-family:Calibri;mso-hansi-theme-font:minor-latin;}table{border-collapse:collapse;margin:0px auto;font-size:10pt;width:100%;border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;mso-border-bottom-alt:solid windowtext .5pt;mso-border-right-alt:solid windowtext .5pt;padding:0cm 5.4pt 0cm 5.4pt;height:16.65pt;text-align:right;mso-pagination:widow-orphan;font-size:10.0pt;font-family:华文细黑;mso-hansi-font-family:宋体;mso-bidi-font-family:Arial;color:black;mso-font-kerning:0pt;}td{border:1px solid black;}.first_row{background-color:#E6E6E6;text-align:center;}.reportChart{text-align:center;}";
		
		List<NodeBean> list = new ArrayList<NodeBean>();
		NodeBean bean = new NodeBean();
		bean.setNodeNameStatic("标题一");
		bean.setContent("<p>这是什么内容</p>");
		bean.setRealId(1);
		list.add(bean);
		bean = new NodeBean();
		bean.setNodeNameStatic("标题二");
		bean.setContent("<p>这是什么内容2</p>");
		bean.setRealId(2);
		list.add(bean);
		bean = new NodeBean();
		bean.setNodeNameStatic("标题三");
		bean.setContent("<p>这是什么内容</p>");
		bean.setRealId(3);
		list.add(bean);
		
		bean = new NodeBean();
		bean.setNodeNameStatic("标题四");
		bean.setContent("<p>这是什么内容</p>");
		bean.setRealId(1);
		list.add(bean);
		
		Builder builder = Builder.getInstance();
		//builder.generateHTML("E:\\bankwork\\8795.jsp", list, style);
		String strhtml=builder.getHtmlStr("名字", list, style);
		//System.out.println("--"+strhtml);
		//写入文件中
		//builder.writeFile("E:\\bankwork\\8795.jsp", strhtml);
		//String str="1<div class=contentname><strong>京食药监局:博强糖醋蒜二氧化硫超标下架停售</strong></div>2<div class=\"contentname\"><strong>京食药监局2</strong></div>3<div class=\"contentname\"><strong>京食药监局3</strong></div>";
		/*
		String str="<DIV class=contentcss id=p_163_1410244897512 onmousedown=addevent(event,this) name=\"plugtext\"><DIV class=contentname><STRONG>京食药监局:博强糖醋蒜二氧化硫超标下架停售</STRONG></DIV>";
		String newstr=builder.setTitle(1,str);
		System.out.println(newstr);
		*/
		//String str="<P class=contentname><STRONG>四大航企获补贴38.73亿牵手地方政府各有所图</STRONG> 1111111111111111</P><p class=contentcss id=p_163_141024><img src=1></p><p class=1><p class=contentcss id=p_163_141024><img src=2></p></p><div class=contentcss id=p_163_141024><img src=1></div>";
		/*
		String str="<P class=p_center onmousedown=\"\"><IMG alt=\"\" src=\"bbafe8c2589d4a5ec471b.jpeg\" unselectable=\"on\" oldsrc=\"W020140911145215191930.jpg\"> </P><DIV class=contentcss id=p_418734_1410942124828 onmousedown=\"\" name=\"plugtext\"><P class=contentname><STRONG>李荣融</STRONG></P><P class=\"contentname\"><STRONG>2222</STRONG></P>";
		//String newstr=builder.setImgstr(str);
		String newstr = builder.setTitle(1,str);
		System.out.println("="+newstr);
		*/
		//String str="<div class=\"chart\"> 图表 ：黑色金属矿采选业行业成长能力比率</div><table ><tr><td>1</td></tr></table><span>123</span><table ><tr><td>1</td></tr></table><div><img src='1.jpg' ></div><span>1234</span><img src='2.jpg' >";
		String str="<div class=chart>图表：黑</div><div class=\"reportChart\"><img src=\"1.png\" unselectable=\"on\"/> </div><DIV class=chart>图表1：黑 </DIV><TABLE><tr>>td>1</td></tr></TABLE><span>123</span><div class=\"chart\">图表：黑色金</div><Table ><tr><td>1</td></tr></table><span>123</span><table ><tr><td>1</td></tr></table><div><img src='3.jpg' ></div><span>1234</span><IMG src='4.jpg' >";
		String newstr = builder.addlist(str);
		System.out.println("="+newstr);
	}
}