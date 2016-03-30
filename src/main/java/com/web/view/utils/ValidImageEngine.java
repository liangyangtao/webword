package com.web.view.utils;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

public class ValidImageEngine {
    private HttpServletResponse response = null;
    private static final int HEIGHT = 35;
    private static final int FONT_NUM = 4;
    private int width = 0;
    private boolean drawBgFlag = false;
    private int rBg = 0;
    private int gBg = 0;
    private int bBg = 0;
    public ValidImageEngine(HttpServletResponse response) {
        this.response = response;
        this.width = 13 * FONT_NUM + 12;
    }
    public String createRandImage(){
        BufferedImage image = new BufferedImage(width, HEIGHT,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random random = new Random();
        if ( drawBgFlag ){
            g.setColor(new Color(rBg,gBg,bBg));
            g.fillRect(0, 0, width, HEIGHT);
        }else{
            //g.setColor(getRandColor(200, 250));
            g.fillRect(0, 0, width, HEIGHT);
            for (int i = 0; i < 5; i++) {
                g.setColor(getRandColor(140, 200));
                int x = random.nextInt(width);
                int y = random.nextInt(HEIGHT);
                int xl = random.nextInt(12);
                int yl = random.nextInt(12);
                g.drawLine(x, y, x + xl, y + yl);
            }
        }
        g.setFont(new Font("宋体", Font.PLAIN, 15));
        String sRand="";
        // 定义数组存放加减乘除四个运算符   
        //char[] arr = { '+', '-', '*', '/' };   

        // 生成10以内的随机整数num1   
        int num1 = random.nextInt(10);   
        // 生成一个0-4之间的随机整数operate   
        int operate = random.nextInt(3);   
        // 生成10以内的随机整数num1   
        int num2 = random.nextInt(10);   
        
        //String[] num_str={"零","一","二","三","四","五","六","七","八","九"};
        
        // 运算结果   
        int result = 0;   
        // 假定position值0/1/2/3分别代表”+”,”-”,”*”,”/”，计算前面操作数的运算结果   
        switch (operate) {  
        // v1.6.7.1_u5 linux系统下中文乱码，改成运算符 zza 2014-04-09 start
 		case 0:
 			result = num1 + num2;
 			sRand = num1 + " + " + num2 + " = ";
 			break;
 		case 1:
 			if(num2>num1){
 				result = num2 - num1;
 	 			sRand = num2 + " - " + num1 + " = ";
 			}else{
 				result = num1 - num2;
 	 			sRand = num1 + " - " + num2 + " = ";
 			}
 			break;
 		case 2:
 			result = num1 * num2;
 			sRand = num1 + " x " + num2 + " = ";
 			break;
 		}
        // v1.6.7.1_u5 linux系统下中文乱码，改成运算符 zza 2014-04-09 end
        //g.setColor(new Color(0,0,0));
        g.setColor(getRandColor());
        g.drawString(sRand, 5, 25);
        g.dispose();
        try{
            ImageIO.write(image, "JPEG", response.getOutputStream());
        }catch(IOException e){
        }
        return result+"";
    }
    public void setBgColor(int r,int g,int b){
        drawBgFlag = true;
        this.rBg = r;
        this.gBg = g;
        this.bBg = b;
    }

    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    } 
    private Color getRandColor() {
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        while((r>=100&& g>=100)||(r>=100&& b>=100)||(g>=100&& b>=100)||g>=160||r>=200||b>=200){
        	r = random.nextInt(255);
            g = random.nextInt(255);
           b = random.nextInt(255);
        }
        return new Color(r, g, b);
    } 
}
