package com.web.view.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;

public class CiticBankUtils {
	
	public static void main(String[] args) {
		System.out.println("sign======"+sign(createLinkString(),CiticBankConfig.citickey,CiticBankConfig.input_charset));
	}
	
	/**
     * @DESC 签名字符串
     * @param text 需要签名的字符串 text=createLinkString ()
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String input_charset) {
    	text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }
	/**
	 * @DESC 验证传过来的签名数据
	 * @param text
	 * @param sign
	 * @param key
	 * @param input_charset
	 * @return
	 */
	
	 public static boolean verify(String text, String sign, String key, String input_charset) {
	    	text = text + key;
	    	String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
	    	if(mysign.equals(sign)) {
	    		return true;
	    	}
	    	else {
	    		return false;
	    	}
	    }
	 private static byte[] getContentBytes(String content, String charset) {
	        if (charset == null || "".equals(charset)) {
	            return content.getBytes();
	        }
	        try {
	            return content.getBytes(charset);
	        } catch (UnsupportedEncodingException e) {
	            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
	        }
	    }
	 /**
	  * @DESC 验签
	  * @param sign
	  * @return 
	  */
	 public static boolean citicverify(String sign){
		 
		 
		 return verify(createLinkString(),sign,CiticBankConfig.citickey,CiticBankConfig.input_charset);
	 }
	 /**
	  * @DESC 签名签参数格式
	  * @return
	  */
	  public static String createLinkString(){
		 
		  return CiticBankConfig.param+"="+CiticBankConfig.param;
	  }
}
