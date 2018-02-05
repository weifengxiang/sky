package org.sky.sys.utils;

import java.util.Arrays;

public class StringUtils {
	public static boolean isNull(String str) {
		if (null == str || "".equals(str)) {
			return true;
		} else {
			return false;
		}

	}

	public static String changeTableColumn(String columnName) {
		StringBuffer sb = new StringBuffer();
		if (columnName != null) {
			for (int i = 0; i < columnName.length(); i++) {
				char c = columnName.charAt(i);
				// 判断是大写
				if (Character.isUpperCase(c)) {
					sb.append("_" + c);
				} else if (Character.isLowerCase(c)) {
					sb.append(Character.toUpperCase(c));
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * 功能描述：首字母大写 <br>
	 * 创 建 人：dufeng <br>
	 * 创建时间：2016年5月17日 下午3:13:59 <br>
	 * 
	 * @param str
	 * @return
	 */
	public static String upperFirstCase(String str) {
		if (isNull(str))
			return null;
		if (str.length() == 1) {
			return str.toUpperCase();
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	/**
	 * 
	 * 功能描述：表名转bean字段 <br>
	 * 创 建 人：dufeng <br>
	 * 创建时间：2016年5月17日 下午3:14:13 <br>
	 * 
	 * @param columnName
	 * @return
	 */
	public static String changetoBeanField(String columnName) {
		StringBuffer sb = new StringBuffer();
		if (columnName != null) {
			columnName = columnName.toLowerCase().trim();
			String[] charC = columnName.split("_");
			for (int i = 0; i < charC.length; i++) {
				sb.append(i == 0 ? charC[i] : upperFirstCase(charC[i]));
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * 功能描述：字段名转列名 <br>
	 * 创 建 人：dufeng <br>
	 * 创建时间：2016年5月17日 下午4:39:27 <br>
	 * 
	 * @param field
	 * @return
	 */
	public static String changetoTableColumn(String field) {
		StringBuffer sb = new StringBuffer();
		if (!isNull(field)) {
			for (int i = 0; i < field.length(); i++) {
				char c = field.charAt(i);
				if (i == 0) {
					sb.append(Character.toUpperCase(c));
				} else if (Character.isUpperCase(c)) {
					sb.append("_" + c);
				} else {
					sb.append(Character.toUpperCase(c));
				}
			}
		}
		return sb.toString();
	}

}
