package com.export;

import java.io.*;
import java.awt.image.*;
import java.awt.*;

import javax.imageio.ImageIO;
//缩略图类，
//本java类能将jpg图片文件，进行等比或非等比的大小转换。
//具体使用方法
//s_pic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true))
public class DwindlePic {

    int OutputWidth = 600; //默认输出图片宽
    int OutputHeight = 600; //默认输出图片高
    boolean proportion = true; //是否等比缩放标记(默认为等比缩放)

//输出
    public static boolean s_pic(String inputPath,String outputPath,int width, int height, boolean gp,boolean flag) {
//      BufferedImage image;
//      String NewFileName;
//建立输出文件对象
      Image img = null;
      try {
    	//System.out.println("inputPath="+inputPath);
    	File inputFile = new File(inputPath);
    	if(inputFile.exists()){
    		img=ImageIO.read(inputFile);
    	}else{
    		return false;
    	}
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		//e1.printStackTrace();
		 return false;
	}
      if (img.getWidth(null) == -1) {
//          System.out.println(" can't read,retry!" + "<BR>");
          return false;
      } else if(img.getWidth(null)<=width){
      	//System.out.println("真实图片小于输出图片" + "<BR>");
      	return false;
      }else{
          int new_w;
          int new_h;
          if (gp == true) {//判断是否是等比缩放.
//为等比缩放计算输出的图片宽度及高度
        	  /*if(flag){
        		  new_w = img.getWidth(null);
        		  new_h = img.getHeight(null);
        	  }else{*/
	              double rate = ((double) img.getWidth(null)) / (double) width;
	              new_w = width;
	              new_h = (int) (((double) img.getHeight(null)) / rate);
//        	  }
          } else {
              new_w = 600; //默认输出宽度
              new_h = 600; //默认输出高度
          }
          BufferedImage buffImg = new BufferedImage(new_w, new_h,
                  BufferedImage.TYPE_USHORT_555_RGB);
          Graphics2D g2D=null;
          g2D = (Graphics2D) buffImg.getGraphics();
			//Image img1 = ImageIO.read(new File(inputPath));
	      g2D.drawImage(img.getScaledInstance(new_w, new_h,
	        	         Image.SCALE_SMOOTH), 0, 0,new_w, new_h,null);
          try {
			ImageIO.write(buffImg, "png", new File(outputPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			 return false;
		}

      }
      return true;
  }
    public static void main(String[] a) {
        //s_pic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度)
        DwindlePic mypic = new DwindlePic();
        /*System.out.println(
                mypic.s_pic("d:/",
                "d:/",
                "2.jpg", "2.jpg", 300, 300, true));
                */
        boolean  flag = mypic.s_pic("d://1.jpg","d://3.jpg",600,600,true,false);
        System.out.println(flag);
        flag = mypic.s_pic("d://1.png","d://3.png",600,600,true,false);
        System.out.println(flag);
        
    }
}