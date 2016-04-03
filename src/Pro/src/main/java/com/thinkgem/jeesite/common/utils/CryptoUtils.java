package com.thinkgem.jeesite.common.utils;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;



public class CryptoUtils {
	
	public static class Hex{
		public static final char[] hexDigits="0123456789ABCDEF".toCharArray(); 
		
		public static String toHexString(byte[] byts){
			char[] chars = new char[byts.length * 2];
			for(int i =0, j= byts.length, k = 0; i < j; i++){
				byte byte0 = byts[i];
				chars[k++] = hexDigits[byte0 >>> 4 & 0xf];
				chars[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(chars);
		}
	}

	public static class Base64 {  
	    /** 
	     * 将原始数据编码为base64编码 
	     */  
	    static public char[] encode(byte[] data) {  
	        char[] out = new char[((data.length + 2) / 3) * 4];  
	        for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {  
	            boolean quad = false;  
	            boolean trip = false;  
	            int val = (0xFF & (int) data[i]);  
	            val <<= 8;  
	            if ((i + 1) < data.length) {  
	                val |= (0xFF & (int) data[i + 1]);  
	                trip = true;  
	            }  
	            val <<= 8;  
	            if ((i + 2) < data.length) {  
	                val |= (0xFF & (int) data[i + 2]);  
	                quad = true;  
	            }  
	            out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];  
	            val >>= 6;  
	            out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];  
	            val >>= 6;  
	            out[index + 1] = alphabet[val & 0x3F];  
	            val >>= 6;  
	            out[index + 0] = alphabet[val & 0x3F];  
	        }  
	        return out;  
	    }  
	    /** 
	     * 将base64编码的数据解码成原始数据 
	     */  
	    static public byte[] decode(char[] data) {  
	        int len = ((data.length + 3) / 4) * 3;  
	        if (data.length > 0 && data[data.length - 1] == '=')  
	            --len;  
	        if (data.length > 1 && data[data.length - 2] == '=')  
	            --len;  
	        byte[] out = new byte[len];  
	        int shift = 0;  
	        int accum = 0;  
	        int index = 0;  
	        for (int ix = 0; ix < data.length; ix++) {  
	            int value = codes[data[ix] & 0xFF];  
	            if (value >= 0) {  
	                accum <<= 6;  
	                shift += 6;  
	                accum |= value;  
	                if (shift >= 8) {  
	                    shift -= 8;  
	                    out[index++] = (byte) ((accum >> shift) & 0xff);  
	                }  
	            }  
	        }  
	        if (index != out.length)  
	            throw new Error("miscalculated data length!");  
	        return out;  
	    }  
	    static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="  
	            .toCharArray();  
	    static private byte[] codes = new byte[256];  
	    static {  
	        for (int i = 0; i < 256; i++)  
	            codes[i] = -1;  
	        for (int i = 'A'; i <= 'Z'; i++)  
	            codes[i] = (byte) (i - 'A');  
	        for (int i = 'a'; i <= 'z'; i++)  
	            codes[i] = (byte) (26 + i - 'a');  
	        for (int i = '0'; i <= '9'; i++)  
	            codes[i] = (byte) (52 + i - '0');  
	        codes['+'] = 62;  
	        codes['/'] = 63;  
	    }  
	    public static void main(String[] args) throws Exception {  
	        // 加密成base64  
	        String strSrc = "林";  
	        String strOut = new String(Base64.encode(strSrc.getBytes("GB18030")));  
	        System.out.println(strOut);  
	        String strOut2 = new String(Base64.decode(strOut.toCharArray()),  
	                "GB18030");  
	        System.out.println(strOut2);  
	    }  
	}  
	
	public static class Coder {
	     
	    public static final String KEY_SHA="SHA";
	    public static final String KEY_MD5="MD5";
	     
	    /**
	     * BASE64解密
	     * @param key
	     * @return
	     * @throws Exception
	     */
	    public static byte[] decryptBASE64(String key) throws Exception{
	        return Base64.decode(key.toCharArray());
	    }
	     
	    /**
	     * BASE64加密
	     * @param key
	     * @return
	     * @throws Exception
	     */
	    public static String encryptBASE64(byte[] key)throws Exception{
	        return new String(Base64.encode(key));
	    }
	     
	    /**
	     * MD5加密
	     * @param data
	     * @return
	     * @throws Exception
	     */
	    public static byte[] encryptMD5(byte[] data)throws Exception{
	        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
	        md5.update(data);
	        return md5.digest();
	    }
	     
	    /**
	     * SHA加密
	     * @param data
	     * @return
	     * @throws Exception
	     */
	    public static byte[] encryptSHA(byte[] data)throws Exception{
	        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
	        sha.update(data);
	        return sha.digest();
	    }
	}
	public static class RSATool {
		public static final String KEY_ALGORTHM="RSA";//
	    public static final String SIGNATURE_ALGORITHM="MD5withRSA";
	     
	    public static final String PUBLIC_KEY = "RSAPublicKey";//公钥
	    public static final String PRIVATE_KEY = "RSAPrivateKey";//私钥
	    

		/**
	     * 初始化密钥
	     * @return
	     * @throws Exception
	     */
	    public static KeyPair initKey(int size)throws Exception{
	        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORTHM);
	        keyPairGenerator.initialize(size);
	        KeyPair keyPair = keyPairGenerator.generateKeyPair();
	         
//	        //公钥
//	        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//	        //私钥
//	        RSAPrivateKey privateKey =  (RSAPrivateKey) keyPair.getPrivate();
//	         
//	        Map<String,Object> keyMap = new HashMap<String, Object>(2);
//	        keyMap.put(PUBLIC_KEY, publicKey);
//	        keyMap.put(PRIVATE_KEY, privateKey);
	         
	        return keyPair;
	    }
	    
	    /**
	     * 取得公钥，并转化为String类型
	     * @param keyMap
	     * @return
	     * @throws Exception
	     */
	    public static String getPublicKey(KeyPair keyPair)throws Exception{
	        return Coder.encryptBASE64(keyPair.getPublic().getEncoded());     
	    }
	 
	    /**
	     * 取得私钥，并转化为String类型
	     * @param keyMap
	     * @return
	     * @throws Exception
	     */
	    public static String getPrivateKey(KeyPair keyPair) throws Exception{
	        return Coder.encryptBASE64(keyPair.getPrivate().getEncoded());     
	    }
		
	    /**
	     * 用私钥加密
	     * @param data   加密数据
	     * @param key    密钥
	     * @return
	     * @throws Exception
	     */
	    public static byte[] encryptByPrivateKey(byte[] data,String key)throws Exception{
	        //解密密钥
	        byte[] keyBytes = Coder.decryptBASE64(key);
	        //取私钥
	        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
	        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
	         
	        //对数据加密
	        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
	        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
	         
	        return cipher.doFinal(data);
	    }
	    
	    /**
	     * 用私钥解密 * @param data    加密数据
	     * @param key    密钥
	     * @return
	     * @throws Exception
	     */
	    public static byte[] decryptByPrivateKey(byte[] data,String key)throws Exception{
	        //对私钥解密
	        byte[] keyBytes = Coder.decryptBASE64(key);
	         
	        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
	        Key privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
	        //对数据解密
	        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
	        cipher.init(Cipher.DECRYPT_MODE, privateKey);
	         
	        return cipher.doFinal(data);
	    }
	    
	    /**
	     * 用公钥加密
	     * @param data   加密数据
	     * @param key    密钥
	     * @return
	     * @throws Exception
	     */
	    public static byte[] encryptByPublicKey(byte[] data,String key)throws Exception{
	        //对公钥解密
	        byte[] keyBytes = Coder.decryptBASE64(key);
	        //取公钥
	        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
	        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
	         
	        //对数据解密
	        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	         
	        return cipher.doFinal(data);
	    }
	    
	    /**
	     * 用公钥解密
	     * @param data   加密数据
	     * @param key    密钥
	     * @return
	     * @throws Exception
	     */
	    public static byte[] decryptByPublicKey(byte[] data,String key)throws Exception{
	        //对私钥解密
	        byte[] keyBytes = Coder.decryptBASE64(key);
	        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
	        Key publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
	         
	        //对数据解密
	        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
	        cipher.init(Cipher.DECRYPT_MODE, publicKey);
	        return cipher.doFinal(data);
	    }
	    
	    /**
	     * 用私钥对信息生成数字签名
	     * @param data   //加密数据
	     * @param privateKey //私钥
	     * @return
	     * @throws Exception
	     */
	    public static String sign(byte[] data,String privateKey)throws Exception{
	        //解密私钥
	        byte[] keyBytes = Coder.decryptBASE64(privateKey);
	        //构造PKCS8EncodedKeySpec对象
	        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
	        //指定加密算法
	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
	        //取私钥匙对象
	        PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
	        //用私钥对信息生成数字签名
	        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
	        signature.initSign(privateKey2);
	        signature.update(data);
	         
	        return Coder.encryptBASE64(signature.sign());
	    }
	    
	    /**
	     * 校验数字签名
	     * @param data   加密数据
	     * @param publicKey  公钥
	     * @param sign   数字签名
	     * @return
	     * @throws Exception
	     */
	    public static boolean verify(byte[] data,String publicKey,String sign)throws Exception{
	        //解密公钥
	        byte[] keyBytes = Coder.decryptBASE64(publicKey);
	        //构造X509EncodedKeySpec对象
	        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
	        //指定加密算法
	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORTHM);
	        //取公钥匙对象
	        PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);
	         
	        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
	        signature.initVerify(publicKey2);
	        signature.update(data);
	        //验证签名是否正常
	        return signature.verify(Coder.decryptBASE64(sign));
	    }
	}
}
