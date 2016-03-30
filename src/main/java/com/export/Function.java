package com.export;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * @author Administrator
 * 
 */
public class Function {

	/**
	 * 将所有br分段
	 * 
	 * @param html
	 * @return
	 */
	public static String replaceBR(String html) {
		Pattern p = Pattern.compile("<br />", Pattern.UNICODE_CASE
				+ Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(html);
		return m.replaceAll("</p><p>");
	}

	/**
	 * 去除所有HTML标签
	 * 
	 * @param html
	 * @return
	 */
	public static String removeHTMLTag(String html) {
		Pattern p = Pattern.compile("<([^>]*)>");
		Matcher m = p.matcher(html);
		return m.replaceAll("");
	}

	/**
	 * 去除所有换行和回车
	 * 
	 * @return
	 */
	public static String removeRN(String html) {
		Pattern p = Pattern.compile("(\r+|\n+)");
		Matcher m = p.matcher(html);
		return m.replaceAll("");
	}

	/**
	 * 去除所有&nbsp;
	 * 
	 * @param html
	 * @return
	 */
	public static String removeNBSP(String html) {
		Pattern p = Pattern.compile("&nbsp;|　");
		Matcher m = p.matcher(html);
		return m.replaceAll("");
	}

	/**
	 * 
	 * @param html
	 * @return
	 */
	public static String removeNoneP(String html) {
		return html.replaceAll("<p>\\s*</p>", "");
	}

	/**
	 * 去除 JS
	 * 
	 * @param html
	 * @return
	 */
	public static String removeJS(String html) {
		Pattern p = Pattern.compile("(?is)<script[^>]*?>.*?<\\/script>");
		Matcher m = p.matcher(html);
		return m.replaceAll("");
	}

	public static String optimizeContent(String content) {
		// 替换换行
		Pattern p = Pattern.compile("<p([^>]*)>|</p>|<br\\s*>|<br\\s*/>",
				Pattern.UNICODE_CASE + Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(content);
		String nc = m.replaceAll("\n");

		// 替换之前版本？
		// p = Pattern.compile("\\?", Pattern.UNICODE_CASE +
		// Pattern.CASE_INSENSITIVE);
		// m = p.matcher(nc);
		// nc = m.replaceAll("&mdash;");

		// 替换所有标签，优化内容html
		p = Pattern.compile("<([^>]*)>|&nbsp;|　", Pattern.UNICODE_CASE
				+ Pattern.CASE_INSENSITIVE);
		m = p.matcher(nc);
		StringBuffer sb = new StringBuffer();
		int intable = 0;
		while (m.find()) {
			String f = m.group().toLowerCase();
			// 对于表格、图片、粗体不做替换
			if (f.contains("<table")) {
				m.appendReplacement(sb, "<table border=\"1\">\n");
				intable++;
			} else if (f.contains("/table")) {
				m.appendReplacement(sb, "</table>\n");
				intable--;
			} else if (f.contains("<tr")) {
				m.appendReplacement(sb, "<tr>");
			} else if (f.contains("<td")) {
				// 取得colspan
				StringBuffer td = new StringBuffer();
				td.append("<td");

				Pattern pc = Pattern.compile("colspan(\\D+)(\\d+)",
						Pattern.UNICODE_CASE + Pattern.CASE_INSENSITIVE);
				Matcher mc = pc.matcher(f);
				if (mc.find()) {
					td.append(" colspan=\"").append(mc.group(2)).append('"');
				}

				// 取得rowspan
				Pattern pr = Pattern.compile("rowspan(\\D+)(\\d+)",
						Pattern.UNICODE_CASE + Pattern.CASE_INSENSITIVE);
				Matcher mr = pr.matcher(f);
				if (mr.find()) {
					td.append(" rowspan=\"").append(mr.group(2)).append('"');
				}

				// 取得align
				Pattern pa = Pattern.compile("align(\\W+)(\\w+)",
						Pattern.UNICODE_CASE + Pattern.CASE_INSENSITIVE);
				Matcher ma = pa.matcher(f);
				if (ma.find()) {
					td.append(" align=\"").append(ma.group(2)).append('"');
				}
				td.append('>');

				m.appendReplacement(sb, td.toString());
			} else if (f.contains("<img") || f.contains("<strong")
					|| f.contains("/td") || f.contains("/tr")
					|| f.contains("/th") || f.contains("/strong")) {
				m.appendReplacement(sb, f);
			} else {
				m.appendReplacement(sb, "");
			}
		}
		m.appendTail(sb);
		nc = sb.toString();

		if (nc.isEmpty())
			return "";

		// 重新处理段落
		StringBuffer sb1 = new StringBuffer(nc.length());
		sb1.append("<p>");
		boolean begin = true;
		boolean blank = true;
		boolean intag = false;
		intable = 0;
		for (int i = 0; i < nc.length(); i++) {
			char c = nc.charAt(i);
			if (c == '<') {
				String temp = nc.substring(i);
				if (temp.indexOf("table") == 1)
					intable++;
				else if (temp.indexOf("/table") == 1)
					intable--;
				intag = true;
				sb1.append(c);
			} else if (c == '>') {
				intag = false;
				sb1.append(c);
			} else if (intag || (intable > 0)) {
				if (c != '\n')
					sb1.append(c);
			} else {
				if (c == '\n') {
					if (!blank) {
						sb1.append("</p>");
						blank = true;
						begin = false;
					}
				} else {
					if (!begin) {
						sb1.append("<p>");
						begin = true;
					}
					blank = false;
					sb1.append(c);
				}
			}
		}

		if (!blank) {
			sb1.append("</p>");
		}

		return Function.removeNoneP(sb1.toString());
	}
	 /**
	  * 下载网页上图片
	  * @param httpUrl http://y2.ifengimg.com/
	  * @param urlPath cmpp/2014/08/15/10/ba0e0b67-f10f-4876-82e9-d37dfd18c43b.jpg
	  * @param rootPath 根目录 
	  */
	 public static void getHtmlPicture(String httpUrl, String urlPath,String rootPath) {
	  URL url;
	  BufferedInputStream in;
	  FileOutputStream file;
	  try {
	   // System.out.println("取网络图片");
	   String fileName = urlPath.substring(urlPath.lastIndexOf("/"));
	   String filePath = rootPath+"/" //设置图片下载的根目录d://pic/
	     + urlPath.substring(0, urlPath.lastIndexOf("/"));
	   //System.out.println(filePath);
	   File newFile = new File(filePath + fileName);
	   if(!newFile.exists()){//文件不存在,就不下载
		   url = new URL(httpUrl + urlPath);
	
		   // 如果该目录不存在,则创建之
		   File uploadFilePath = new File(filePath);
		   if (uploadFilePath.exists() == false) {
		    uploadFilePath.mkdirs();
		   }
	
		   in = new BufferedInputStream(url.openStream());
		   file = new FileOutputStream(new File(filePath + fileName));
	
		   int t;
		   while ((t = in.read()) != -1) {
		    file.write(t);
		   }
		   file.close();
		   in.close();
		   //文件压缩
		   boolean flag = DwindlePic.s_pic(filePath + fileName, filePath + fileName, 560, 560, true,false);
	   }
	   //System.out.println("图片获取成功");
	  } catch (Exception e) {
	   
	  }
	 }
	 /*
	 *替换图片
	 */
	 public static String getHtml(String content,String path) throws IOException {
		  String searchImgReg = "(?x)(src|SRC|background|BACKGROUND)=('|\")(http://.*?/)(.*?.(jpg|JPG|png|PNG|gif|GIF|jpeg|JPEG))('|\")";
		  //System.out.println(content);
		  // 下载图片
		  Pattern pattern = Pattern.compile(searchImgReg);
		  Matcher matcher = pattern.matcher(content);
		  while (matcher.find()) {
			  //System.out.println(matcher.group(3));http://y2.ifengimg.com/
			  //System.out.println(matcher.group(4));cmpp/2014/08/15/10/ba0e0b67-f10f-4876-82e9-d37dfd18c43b.jpg
			  getHtmlPicture(matcher.group(3), matcher.group(4),path+"\\ueditor\\jsp\\upload\\images");
		  }
		  // 修改图片链接地址
		  pattern = Pattern.compile(searchImgReg);
		  matcher = pattern.matcher(content);
		  StringBuffer replaceStr = new StringBuffer();
		  while (matcher.find()) {
		   matcher.appendReplacement(replaceStr, matcher.group(1) + "='ueditor/jsp/upload/images/"
		     + matcher.group(4) + "'");//逐个动态替换图片链接地址
		  }
		  matcher.appendTail(replaceStr);//添加尾部
		  //System.out.println(replaceStr.toString());
		  return replaceStr.toString();
		 }
	 /**
	  * 替换图片 
	  * static/unbankImage/images/20150610/24d65054b8122f35862846d5e166ff8c.jpeg
	  * http://10.0.2.35:8080/unbankImage/images/20150610/24d65054b8122f35862846d5e166ff8c.jpeg
	  * 并把它下载到本地的地址
	  * @param content
	  * @param path
	  * @return
	  * @throws IOException
	  */
	 	 public static String replaceImg(String content,String path,String url) throws IOException {
	 		  String searchImgReg = "(?x)(src|SRC|background|BACKGROUND)=('|\")(static.*?/)(.*?.(jpg|JPG|png|PNG|gif|GIF|jpeg|JPEG))('|\")";
	 		  //System.out.println(content);
	 		  // 下载图片
	 		  Pattern pattern = Pattern.compile(searchImgReg);
	 		  Matcher matcher = pattern.matcher(content);
	 		  while (matcher.find()) {
	 			  //System.out.println(matcher.group(3));http://static/
	 			  //System.out.println(matcher.group(4));unbankImage/images/20150610/5c938d709843986e016e487f1c47d48b.jpeg
	 			  getHtmlPicture(url, matcher.group(4),path+"\\ueditor\\jsp\\upload\\images");
	 		  }
	 		  // 修改图片链接地址
	 		  pattern = Pattern.compile(searchImgReg);
	 		  matcher = pattern.matcher(content);
	 		  StringBuffer replaceStr = new StringBuffer();
	 		  while (matcher.find()) {
	 			  //System.out.println(matcher.group(3));//http://y2.ifengimg.com/
	 			  //System.out.println(matcher.group(4));//cmpp/2014/08/15/10/ba0e0b67-f10f-4876-82e9-d37dfd18c43b.jpg
	 			  matcher.appendReplacement(replaceStr, matcher.group(1) + "='ueditor/jsp/upload/images/"
	 					  + matcher.group(4) + "'");//逐个动态替换图片链接地址
	 		  }
	 		  matcher.appendTail(replaceStr);//添加尾部
	 		  //System.out.println(replaceStr.toString());
	 		  return replaceStr.toString();
	 		 }
	 	/**
	 	 * 标题下面加分页符
	 	 * @param content
	 	 * @return
	 	 */
	 	public static String  getPageH1Str(String content){
	 		StringBuffer replaceStr = new StringBuffer();
	 		//String regex="(<h[1-6][^>]*?>)([^\t]*?)(</h[1-6]>)";
			String regex="(<h1[^>]*?>)([^\t]*?)(</h1>)";
			Pattern pattern = Pattern.compile(regex,Pattern.UNICODE_CASE+Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(content);
			String page ="<br clear=\"all\" style=\"page-break-before: always; mso-special-character: line-break \" />";
			int i=0;
			while(matcher.find()){//遍历节点
				if(i>0){
					matcher.appendReplacement(replaceStr, page+matcher.group(0));//逐个动态替换图片链接地址
				}
	 			i++;
			}//while
			matcher.appendTail(replaceStr);//添加尾部
			return replaceStr.toString();
	 	}
		//多个空格替换成一个空格，可以替换多次
		public static String replaceAllSpace(String str){
			str = str.replaceAll(" +", " ");//替换所有空格
			return str;
		}
		/**
		 * 处理空格,替换首尾空格,替换多个空格
		 * @return
		 */
		public static String replaceSpace(String str){
			str=str.trim().replaceAll(" +", " ");
			return str;
		}
		/**
		 * 替换高亮字符
		 * @param str
		 * @param regex
		 * @return
		 */
		public static String replaceHeight(String str,String regex){
			try{
				Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);  
		        Matcher m = p.matcher(str);  
		        StringBuffer sb = new StringBuffer();  
		        boolean result = m.find();  
		        while (result)  
		        {  
		            //System.out.println(m.group(0));
		        	m.appendReplacement(sb, "<span>"+m.group(0)+"</span>");  
		            result = m.find();  
		        }  
		        m.appendTail(sb);  
		        return sb.toString();  
			}catch(Exception e){
				//logger.info("e="+e.getMessage());
				return str;
			}

		}
		/**
		 * 字符转换成数组隔开
		 * @param str
		 * @return
		 */
		public static String [] returnArray(String str){
			String[] musts=new String[0];
			if("".equals(str)||str==null){
				
			}else{
				//str=str.toLowerCase();
				str=str.trim().replaceAll(" +", " ");
				musts=str.split(" ");
			}
			return musts;
		}
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		String s = "<p>\r\n\t\r\n\t\r\n\t</p>";

		String r = Function.removeNoneP(s);
		r = r.replaceAll("<p>\\s*</p>", "");
		System.out.println(r);

		String aaaa = "&nbsp;&nbsp;dd　&nbsp;　&nbsp;　";
		System.out.println(Function.removeNBSP(aaaa));

		// String rrr = Function.removeRN(s);
		// System.out.println(rrr);
		String html =
				"<p><img src='http://y2.ifengimg.com/cmpp/2014/08/15/10/ba0e0b67-f10f-4876-82e9-d37dfd18c43b.jpg'/><IMG src='http://y2.ifengimg.com/cmpp/2014/08/15/10/ba0e0b67-f10f-4876-82e9-d37dfd18c43b.jpg' width='300' height='221' border='0' hspace='0' vspace='0' title='' style='width: 300px; height: 221px;'/>4月16日，国家发改委会同有关部门和地方反复，公开征求社会意见。</p><p><img src='http://y2.ifengimg.com/cmpp/2014/08/15/10/ba0e0b67-f10f-4876-82e9-d37dfd18c43b.jpg' width='300' height='172' border='0' hspace='0' vspace='0' title='' style='width: 300px; height: 172px;'/>征求意见稿中除列出“国家现有产业目录中的鼓励类产业”外，还根据各省、自治</p><p>征求意见存在一定差异。</p><p>在这些生产”等项目。</p><p>国务</p>";
		try {
			System.out.println(getHtml(html,"d://"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
	}

}
