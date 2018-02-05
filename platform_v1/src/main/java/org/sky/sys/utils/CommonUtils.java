package org.sky.sys.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.sky.sys.service.SysCommonService;
import org.springframework.web.context.ContextLoader;

public class CommonUtils {

	public static String getCurrentYear() {
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		return year;
	}

	public static String getUUID() {
		String s = UUID.randomUUID().toString().replace("-", "");
		return s;
	}
	public static String getUUID(int length) {
		String uuid="";
		for(int i=0;i<length;i++){
			uuid=uuid+UUID.randomUUID().toString().substring(0,1);
		}
		return uuid;
	}

	public static Date str2Date(String str, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String args[]) {
		System.out.println(getUUID());
	}

	public static List<Map<String, Object>> formListMapDate(
			List<Map<String, Object>> list, String formdat) {
		SimpleDateFormat sdf = new SimpleDateFormat(formdat);
		for (Map<String, Object> map : list) {
			for (String key : map.keySet()) {
				Object value = map.get(key);
				if (value instanceof Date) {
					Date vd = (Date) value;
					map.put(key, sdf.format(vd));
				}
			}
		}
		return list;
	}

	/**
	 * 
	 * @param date
	 * @param formate
	 * @return
	 */
	public static Date reverse2Date(String date, String formate) {
		SimpleDateFormat simple = new SimpleDateFormat(formate);
		try {
			return simple.parse(date.trim());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String formatDate(Date date) {
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simple.format(date);
	}

	public static String formatDate(Date date, String formate) {
		SimpleDateFormat simple = new SimpleDateFormat(formate);
		return simple.format(date);
	}

	/**
	 * 
	 * @param content
	 * @return
	 */
	public static String repContentCerNo(String content) {
		String res = "";
		if (null == content || "".equals(content.trim())) {
			return "";
		} else {
			res = content;
			for (int i = 0; i <= content.length() - 18; i++) {
				String tmp = content.substring(i, i + 18);
				if (IdcardValidator.isValidate18Idcard(tmp)) {
					String newstr = tmp.substring(0, 6) + "************";
					res = res.replace(tmp, newstr);
				}
			}
			for (int i = 0; i <= content.length() - 15; i++) {
				String tmp = content.substring(i, i + 15);
				if (IdcardValidator.isValidate15Idcard(tmp)) {
					String newstr = tmp.substring(0, 6) + "*********";
					res = res.replace(tmp, newstr);
				}
			}
			return res;
		}
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String replacePhone(String para) {
		para = para.trim();
		if (para == null || "".equals(para)) {
			return "";
		} else {
			String regex = "(\\d{8})|(\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(para);
			para = matcher.replaceAll("***********");
			return para;
		}
	}

	/**
	 * 
	 * @author ShelWee
	 * @param time
	 * @return
	 */
	public static String toHex(long time) {
		return Integer.toHexString((int) time);
	}

	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 */
	public static String toUtf8String(HttpServletRequest request, String s) {
		String agent = request.getHeader("User-agent");
		if (null != agent) {
			agent = agent.toLowerCase();
			try {
				if (agent.indexOf("firefox") != -1) {
					s = new String(s.getBytes(), "iso8859-1");
				} else if (agent.indexOf("msie") != -1) {
					s = java.net.URLEncoder.encode(s, "UTF-8");
				} else {
					s = java.net.URLEncoder.encode(s, "UTF-8");
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return s;
	}

	public static String clob2String(Clob clob) {
		String resString = "";
		if (clob == null) {
			return resString;
		}
		Reader is = null;
		try {
			is = clob.getCharacterStream();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(is);
		String s = null;
		try {
			s = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		while (s != null) {
			sb.append(s);
			try {
				s = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		resString = sb.toString();
		return resString;
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress = null;
		// ipAddress = this.getRequest().getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
	public static String getCurrentDate(String format) {
		if (null == format || "".equals(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		return new SimpleDateFormat(format).format(new Date());
	}

	public static String getCurrentDate() {
		String format = "yyyy-MM-dd HH:mm:ss";
		return getCurrentDate(format);
	}

	/**
	 * 获取当前数据库时间
	 * @return
	 */
	public static Date getCurrentDbDate() {
		return ContextLoader.getCurrentWebApplicationContext().getBean(SysCommonService.class).queryDbDate();
	}
	
	public static Timestamp getCurrentDbTimestamp() {
		return ContextLoader.getCurrentWebApplicationContext().getBean(SysCommonService.class).queryDbTimestamp();
	}

	public static String getCurrnetDbDateStr(String format) {
		if (null == format || "".equals(format)) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		return new SimpleDateFormat(format).format(getCurrentDbDate());
	}

	/**
	 * 格式化时间差
	 * 
	 * @param timespan
	 * @return
	 */
	public static String getFormattedTimestamp(long timespan) {
		return getFormattedTimestamp(timespan, "毫秒");
	}

	private static String getFormattedTimestamp(long timespan, String postfix) {
		String value = null;
		String nextpf = "";
		long divisor = Long.MAX_VALUE;
		switch (postfix) {
		case "毫秒":
			divisor = 1000;
			nextpf = "秒";
			break;
		case "秒":
			divisor = 60;
			nextpf = "分";
			break;
		case "分":
			divisor = 60;
			nextpf = "小时";
			break;
		case "小时":
			divisor = 24;
			nextpf = "天";
			break;
		case "天":
			divisor = 365;
			nextpf = "年";
			break;
		default:
			break;
		}
		value = timespan % divisor + postfix;
		return timespan / divisor == 0 ? value : getFormattedTimestamp(timespan / divisor, nextpf) + value;
	}
}
