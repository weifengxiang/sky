package org.sky.sys.utils;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.springframework.security.crypto.codec.Hex;

/*
 * 对应配置文件 conf.properties
 */
public class ConfUtils{
	 
	private static Properties prop = new Properties();
	static{
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("conf.properties"));
			
			//转码处理
			Set<Object> keyset = prop.keySet();
			Iterator<Object> iter = keyset.iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				String newValue = null;
				try { 
					String propertiesFileEncode = "utf-8";
					newValue = new String(prop.getProperty(key).getBytes("ISO-8859-1"),propertiesFileEncode);
				} catch (UnsupportedEncodingException e) {
					newValue = prop.getProperty(key);
				}
				prop.setProperty(key, newValue);
			}
		} catch (Exception e) { 
		}
	}
	
	/**
	 * 根据key，取得对应的v
	 * @param String key
	 * @return String
	 */
	public static String getValue(String key){
		if(key == null){
			return null;
		}
		return prop.getProperty(key).trim();
	}
	
	
} 

