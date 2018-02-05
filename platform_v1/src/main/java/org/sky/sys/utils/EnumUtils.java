package org.sky.sys.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 枚举类型工具类
 * @author Administrator
 *
 */
public class EnumUtils {
	
	private static Map<String, String> keyCodeValues = new HashMap<>();

	public EnumUtils() {
		// TODO Auto-generated constructor stub
	}
	private static Properties prop = new Properties();
	static{
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("enum.properties"));
			
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
	public static String getEnums(String key) throws Exception{
		String dataOptions="";
		String values=prop.getProperty(key);
		String[] enums=values.split(",");
		for(int i=0;i<enums.length;i++){
			String em=enums[i];
			String code = em.split(":")[0];
			String name = em.split(":")[1];
			if(i==enums.length-1){
				dataOptions+="{\"code\":\""+code+"\",\"name\":\""+name+"\"}";
			}else{
				dataOptions+="{\"code\":\""+code+"\",\"name\":\""+name+"\"},";
			}
		}
		return "["+dataOptions+"]";
	}
	
	/**
	 * 根据key code，取得枚举类型对应的name
	 * 
	 * @param String
	 *            key
	 * @return String
	 */
	public static String getKeyNamebyKeyCode(String key, Object code) {
		if (code == null)
			code = "";
		if (key == null || prop.getProperty(key) == null) {
			return code.toString();
		}
		if (prop.getProperty(key).contains(":")) {
			if (keyCodeValues.containsKey(key + "-" + code)) {
				return keyCodeValues.get(key + "-" + code);
			}
			try {
				// List<Map> mapList = JsonUtils.json2list(getEnums(key),
				// Map.class);
				// for (Map map : mapList) {
				// if (map.get("code").equals(code)) {
				// String value = map.get("name").toString();
				// keyCodeValues.put(key + "-" + code, value);
				// return value;
				// }
				// }
				Map<String, String> enumMap = getEnumsMap(key);
				for (String mapcode : enumMap.keySet()) {
					if (mapcode.equals(code.toString())) {
						String value = enumMap.get(mapcode);
						keyCodeValues.put(key + "-" + code, value);
						return value;
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return code.toString();
	}
	
	public static Map<String, String> getEnumsMap(String key) {
		if (prop.getProperty(key) == null)
			return null;
		Map<String, String> enumMap = new HashMap<>();
		String values = prop.getProperty(key);
		String[] enums = values.split(",");
		for (int i = 0; i < enums.length; i++) {
			String em = enums[i];
			String code = em.split(":")[0];
			String name = em.split(":")[1];
			enumMap.put(code, name);
		}
		return enumMap;
	}
}
