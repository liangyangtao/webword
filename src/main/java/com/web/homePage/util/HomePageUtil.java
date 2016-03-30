/*
 * <p>Title: 知识自动化平台</p>
 * <p>Description: HomePageUtil.java</p>
 * <p>Copyright: Copyright (c) 2015 北京银联信投资顾问有限责任公司，版权所有. </p>
 * <p>Company: 北京银联信投资顾问有限责任公司</p>
 * @author knight
 * @date 2015-5-5
 * @version 1.0
 */
package com.web.homePage.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.database.bean.Homepage;


/**
 * <p>Title: HomePageUtil</p>
 * <p>Description: 封面工具类</p>
 * @author knight
 * @date 2015-5-5
 */
public class HomePageUtil {

	private static final Logger logger = Logger.getLogger(HomePageUtil.class);
	
	/**
	 * 图片缩放
	 * <p>Title: resize</p> 
	 * <p>Description: TODO</p>
	 * @param source
	 * @param targetW
	 * @param targetH
	 * @param equalProportion
	 * @return
	 */
	public static BufferedImage resize(BufferedImage source,int targetW,int targetH,boolean equalProportion){  
        int type=source.getType();  
        BufferedImage target=null;  
        double sx=(double)targetW/source.getWidth();  
        double sy=(double)targetH/source.getHeight();  
        //这里想实现在targetW，targetH范围内实现等比例的缩放  
          //如果不需要等比例的缩放则下面的if else语句注释调即可  
        if(equalProportion){  
            if(sx>sy){  
                sx=sy;  
                targetW=(int)(sx*source.getWidth());  
            }else{  
                sy=sx;  
                targetH=(int)(sx*source.getHeight());  
            }  
        }  
        if(type==BufferedImage.TYPE_CUSTOM){  
            ColorModel cm=source.getColorModel();  
            WritableRaster raster=cm.createCompatibleWritableRaster(targetW,targetH);  
            boolean alphaPremultiplied=cm.isAlphaPremultiplied();  
            target=new BufferedImage(cm,raster,alphaPremultiplied,null);  
        }else{  
            target=new BufferedImage(targetW,targetH,type);  
            Graphics2D g=target.createGraphics();  
            g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);  
            g.drawRenderedImage(source,AffineTransform.getScaleInstance(sx,sy));  
            g.dispose();  
        }  
        return target;  
    }
	
	public static void getPicPath(String realPath, String basePath,Homepage bean){
		if(bean != null){
			String part = "";
			String partTwo = "";
			String industryName = "";
			String areaName = "";
			String industryNameTwo = "";
			if(bean.getTitle() != null && !"".equals(bean.getTitle())){
				industryName = bean.getTitle();
			}else {
				if(bean.getIndustryname() != null && !"".equals(bean.getIndustryname())) {
					industryName = bean.getIndustryname()+"行业金融季度研究报告";
				}
			}
			int len = industryName.length()*40;//标题长度
			int start = (595-len)/2;//标题开始位置
			
			int fontsize = 48;//标题字号
			if(industryName.contains("、")){
				if(len>500){
					fontsize = 47;
					start = 48;
				}
			}else{
				if(len>550){
					fontsize = 47;
					start = 48;
				}
			}
			try {
				if(bean.getAreaname() != null && !bean.getAreaname().equals("")){
					areaName = "["+bean.getAreaname()+"]";
				}
			} catch (Exception e) {
				e.getStackTrace();
			}
			if(bean.getSubtitle() != null && !"".equals(bean.getSubtitle())){
				part = bean.getSubtitle();
			}else {
				part = getParams(bean);
				if(part == null){
					part = "";
				}
			}
		    int partstart = 0;//副标题出入位置
		    if(!part.equals("")){
				int countC = getStringCount(part);//汉字个数
				int countE = part.length()-countC;//英文个数
				//重新计算副标题开始位置
				partstart = 567-countC*28-countE*15;
		    }
		    int length = getStringLength(industryName);//主标题长度（汉字算2，半角字符算1）
		    
		    if(length>56){
		    	fontsize = 41;
		    	industryNameTwo = industryName.substring(15);
		    	industryName = industryName.substring(0, 15);
		    }else{
		    	if(length>28){
		    		if(getEnglishCount(industryName) == industryName.length()){
		    			industryNameTwo = industryName.substring(25);
				    	industryName = industryName.substring(0, 25);
		    		}else{
		    			int count = getEnglishCount(industryName.substring(0,14));
		    			int leng = 0;
		    			if(count%2 == 1){//英文字符是单数
		    				leng = (int) (14+Math.floor(count/2));
		    			}else{
		    				leng = (int) (14+Math.floor(count/2))-1;
		    			}
				    	industryNameTwo = industryName.substring(leng);
				    	industryName = industryName.substring(0, leng);
		    		}
			    }
		    }
			try{
				File f1 = new File(realPath);
				BufferedImage image = ImageIO.read(f1);
			    Graphics g = image.getGraphics();
			    g.setFont(new Font("黑体",Font.BOLD,fontsize));
			    g.setColor(Color.black);
			    g.drawString(industryName, start, 240);
			    if(!"".equals(industryNameTwo)){
			    	g.drawString(industryNameTwo, start, 307);
			    }
			    g.setFont(new Font("Serif",Font.BOLD,38));
			    g.setColor(Color.black);
			    g.drawString(part, partstart, 375);
			    if(!"".equals(partTwo)){
			    	g.drawString(partTwo, partstart, 428);
			    }
			    g.setFont(new Font("Serif",Font.BOLD,38));
			    g.setColor(Color.black);
			    g.drawString(areaName, 80, 870);
			    File f2 = new File(basePath);
				ImageIO.write(image, "png", f2);
				
				}catch(Exception e){
					
				}
		}
	}
	
	/**
	 * 判断字符串中汉字个数
	 * <p>Title: getStringCount</p> 
	 * <p>Description: TODO</p>
	 * @param str
	 * @return
	 */
	public static int getStringCount(String str){
		int count = 0;
		char[] ch = str.toCharArray();
		for(int i=0; i<ch.length; i++){
			if(ch[i] >= 0x0391 && ch[i] <= 0xFFE5){
				count += 1;
			}
		}
		return count;
	}
	
	/**
	 * 获取英文字符个数
	 * <p>Title: getEnglishCount</p> 
	 * <p>Description: TODO</p>
	 * @param str
	 * @return
	 */
	public static int getEnglishCount(String str){
		int count = 0;
		char[] ch = str.toCharArray();
		for(int i=0; i<ch.length; i++){
			if(ch[i]>=0x0000 && ch[i]<=0x00FF){
				count += 1;
			}
		}
		return count;
	}
	
	/**
	 * 获取拼接的时间字符串
	 * <p>Title: getParams</p> 
	 * <p>Description: TODO</p>
	 * @param apBean
	 * @return
	 */
	public static String getParams(Homepage apBean){
		if(apBean != null && apBean.getEndTime() != null && !"".equals(apBean.getEndTime())){
			String y = apBean.getEndTime().substring(0, 4);
			String m = apBean.getEndTime().substring(5, 7);
			String pp = null;
			int p = (Integer.valueOf(m)-1)/3;
			if(p == 0){
				pp = "一";
			}else if(p == 1){
				pp = "二";
			}else if(p == 2){
				pp = "三";
			}else if(p == 3){
				pp = "四";
			}
			return y+"年第"+pp+"季度";
		}
		
		return null;
	}
	
	/**
	 * 计算字符串长度
	 * 一个汉字占2个长度，一个英文字母占一个长度
	 * if((c >= 0x0391 && c <= 0xFFE5)  //中文字符
 	 *|| (c>=0x0000 && c<=0x00FF))   //英文字符
	 * <p>Title: getStringLength</p> 
	 * <p>Description: TODO</p>
	 * @param str
	 * @return
	 */
	public static int getStringLength(String str){
		int length = 0;
		char[] ch = str.toCharArray();
		for(int i = 0; i<ch.length; i++){
			if(ch[i] >= 0x0391 && ch[i] <= 0xFFE5){
				length += 2;
			}else if(ch[i]>=0x0000 && ch[i]<=0x00FF){
				length +=1;
			}
		}
		return length;
	}
	
	public static String transfer(String str){
       StringBuffer html=new StringBuffer();
       if(str != null){
       for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if(c=='"'){
				html.append("”");
			}
			else if(c=='\''){
				html.append("’");
			}
			else html.append(c);
			}
       }
    	
	   return html.toString();
    	
    }
	
	public static Timestamp stringToTimeStamp(String time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if(time==null||time.equals("")||time.trim().length()==0){
				return null;
			}else{
				Date date = sdf.parse(time);
				Timestamp timestamp=new Timestamp(date.getTime());
				return timestamp;
			}
		} catch (ParseException e) {
			
		}
		return null;
	}
	//开始时间
	public static Timestamp stringToFormatTimeStampStart(String time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(time==null||time.equals("")&&time.trim().length()==0){
				return null;
			}else{
				Date date = sdf.parse(time);
				Timestamp timestamp=new Timestamp(date.getTime());
				return timestamp;
			}
	
			
		} catch (ParseException e) {
			
		}
		return null;
	}
	
	//结束时间
	public static Timestamp stringToFormatTimeStampEnd(String time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if(time==null||time.equals("")&&time.trim().length()==0){
				return null;
			}else{
				Date date = sdf.parse(time+" 23:59:59");
				Timestamp timestamp=new Timestamp(date.getTime());
				return timestamp;
			}
	
			
		} catch (ParseException e) {
			
		}
		return null;
	}
}
