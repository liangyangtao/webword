package com.util;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;

/**
  * 数据加密/解密
  * @ClassName: DataEncryptUtil
  * @Description: TODO
  * @author zyh
  * @date 2014-12-16 下午5:03:26
  *
 */
public class Md5Util {
	
	/**
	 * 将字符串转换成Sha256算法加密
	 * encryptSha256
	 * @author zyh
	 * @date 2014-12-16 下午5:09:44
	 * @param inputStr
	 * @return
	 */
	public static synchronized String encryptSha256(String inputStr) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			byte digest[] = md.digest(inputStr.getBytes("UTF-8"));

			return new String(Base64.encodeBase64(digest));
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 将字符串转换成MD5算法加密
	 * encryptMd5
	 * @author zyh
	 * @date 2014-12-16 下午5:11:21
	 * @param inputStr
	 * @return
	 */
	public static synchronized String encryptMd5(String inputStr) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(inputStr.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(Integer.toHexString((int) (b & 0xff)));
			}

			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}
	 /** 
     * MD5 加密 
     */  
    public static String getMD5Str(String str) {  
        MessageDigest messageDigest = null;  
  
        try {  
            messageDigest = MessageDigest.getInstance("MD5");  
  
            messageDigest.reset();  
  
            messageDigest.update(str.getBytes("UTF-8"));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
        byte[] byteArray = messageDigest.digest();  
  
        StringBuffer md5StrBuff = new StringBuffer();  
  
        for (int i = 0; i < byteArray.length; i++) {              
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
            else  
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
        }  
  
        return md5StrBuff.toString();  
    }
    /*
     * 获取随机数
     */
    public static String getRandom(){
    	 int  random = (int)(Math.random()*1000000000);
    	 return Integer.toString(random);
    }
   
	// 测试
		public static void main(String[] args) throws Exception {

			String data = "123456";
			String bb =Md5Util.encryptMd5(data);
			System.out.println(bb);
			System.out.println(getMD5Str(data));

		}
}
