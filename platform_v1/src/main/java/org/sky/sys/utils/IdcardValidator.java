package org.sky.sys.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 */
@SuppressWarnings({ "unchecked", "unused", "all" })
public class IdcardValidator {

	


	private static String cityCode[] = { "11", "12", "13", "14", "15", "21", "22",
			"23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43",
			"44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63",
			"64", "65", "71", "81", "82", "91" };

	private static int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	private  static String verifyCode[] = { "1", "0", "X", "9", "8", "7", "6", "5",
			"4", "3", "2" };

	/**
	 * 
	 * @param idcard
	 * @return
	 */
	public  static boolean isValidatedAllIdcard(String idcard) {
		if (idcard.length() == 15) {
			idcard = convertIdcarBy15bit(idcard);
		}
		return isValidate18Idcard(idcard);
	}

	
	public  static boolean isValidate18Idcard(String idcard) {
		if (idcard.length() != 18) {
			return false;
		}
		String idcard17 = idcard.substring(0, 17);
		String idcard18Code = idcard.substring(17, 18);
		char c[] = null;
		String checkCode = "";
		if (isDigital(idcard17)) {
			c = idcard17.toCharArray();
		} else {
			return false;
		}

		if (null != c) {
			int bit[] = new int[idcard17.length()];

			bit = converCharToInt(c);

			int sum17 = 0;

			sum17 = getPowerSum(bit);

			checkCode = getCheckCodeBySum(sum17);
			if (null == checkCode) {
				return false;
			}
			if (!idcard18Code.equalsIgnoreCase(checkCode)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param idcard
	 * @return
	 */
	public  static boolean isValidate15Idcard(String idcard) {
		if (idcard.length() != 15) {
			return false;
		}

		if (isDigital(idcard)) {
			String provinceid = idcard.substring(0, 2);
			String birthday = idcard.substring(6, 12);
			int year = Integer.parseInt(idcard.substring(6, 8));
			int month = Integer.parseInt(idcard.substring(8, 10));
			int day = Integer.parseInt(idcard.substring(10, 12));

			boolean flag = false;
			for (String id : cityCode) {
				if (id.equals(provinceid)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				return false;
			}
			Date birthdate = null;
			try {
				birthdate = new SimpleDateFormat("yyMMdd").parse(birthday);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (birthdate == null || new Date().before(birthdate)) {
				return false;
			}

			GregorianCalendar curDay = new GregorianCalendar();
			int curYear = curDay.get(Calendar.YEAR);
			int year2bit = Integer.parseInt(String.valueOf(curYear)
					.substring(2));

			if ((year < 50 && year > year2bit)) {
				return false;
			}

			if (month < 1 || month > 12) {
				return false;
			}

			boolean mflag = false;
			curDay.setTime(birthdate); //
			switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				mflag = (day >= 1 && day <= 31);
				break;
			case 2: //
				if (curDay.isLeapYear(curDay.get(Calendar.YEAR))) {
					mflag = (day >= 1 && day <= 29);
				} else {
					mflag = (day >= 1 && day <= 28);
				}
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				mflag = (day >= 1 && day <= 30);
				break;
			}
			if (!mflag) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param idcard
	 * @return
	 */
	public  static String convertIdcarBy15bit(String idcard) {
		String idcard17 = null;
		if (idcard.length() != 15) {
			return null;
		}

		if (isDigital(idcard)) {
			String birthday = idcard.substring(6, 12);
			Date birthdate = null;
			try {
				birthdate = new SimpleDateFormat("yyMMdd").parse(birthday);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar cday = Calendar.getInstance();
			cday.setTime(birthdate);
			String year = String.valueOf(cday.get(Calendar.YEAR));

			idcard17 = idcard.substring(0, 6) + year + idcard.substring(8);

			char c[] = idcard17.toCharArray();
			String checkCode = "";

			if (null != c) {
				int bit[] = new int[idcard17.length()];

				bit = converCharToInt(c);
				int sum17 = 0;
				sum17 = getPowerSum(bit);

				checkCode = getCheckCodeBySum(sum17);
				if (null == checkCode) {
					return null;
				}

				idcard17 += checkCode;
			}
		} else { //
			return null;
		}
		return idcard17;
	}

	/**
	 * 
	 * @param idcard
	 * @return
	 */
	public  static boolean isIdcard(String idcard) {
		return idcard == null || "".equals(idcard) ? false : Pattern.matches(
				"(^//d{15}$)|(//d{17}(?://d|x|X)$)", idcard);
	}

	/**
	 * 
	 * @param idcard
	 * @return
	 */
	public  static boolean is15Idcard(String idcard) {
		return idcard == null || "".equals(idcard) ? false : Pattern.matches(
				"^[1-9]//d{7}((0//d)|(1[0-2]))(([0|1|2]//d)|3[0-1])//d{3}$",
				idcard);
	}

	/**
	 * 
	 * @param idcard
	 * @return
	 */
	public  static boolean is18Idcard(String idcard) {
		return Pattern
				.matches(
						"^[1-9]//d{5}[1-9]//d{3}((0//d)|(1[0-2]))(([0|1|2]//d)|3[0-1])//d{3}([//d|x|X]{1})$",
						idcard);
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public  static boolean isDigital(String str) {
		return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");
	}

	/**
	 * 
	 * @param bit
	 * @return
	 */
	public  static int getPowerSum(int[] bit) {

		int sum = 0;

		if (power.length != bit.length) {
			return sum;
		}

		for (int i = 0; i < bit.length; i++) {
			for (int j = 0; j < power.length; j++) {
				if (i == j) {
					sum = sum + bit[i] * power[j];
				}
			}
		}
		return sum;
	}

	/**
	 * 
	 * @param checkCode
	 * @param sum17
	 */
	public  static String getCheckCodeBySum(int sum17) {
		String checkCode = null;
		switch (sum17 % 11) {
		case 10:
			checkCode = "2";
			break;
		case 9:
			checkCode = "3";
			break;
		case 8:
			checkCode = "4";
			break;
		case 7:
			checkCode = "5";
			break;
		case 6:
			checkCode = "6";
			break;
		case 5:
			checkCode = "7";
			break;
		case 4:
			checkCode = "8";
			break;
		case 3:
			checkCode = "9";
			break;
		case 2:
			checkCode = "x";
			break;
		case 1:
			checkCode = "0";
			break;
		case 0:
			checkCode = "1";
			break;
		}
		return checkCode;
	}

	/**
	 * 
	 * @param c
	 * @return
	 * @throws NumberFormatException
	 */
	public  static int[] converCharToInt(char[] c) throws NumberFormatException {
		int[] a = new int[c.length];
		int k = 0;
		for (char temp : c) {
			a[k++] = Integer.parseInt(String.valueOf(temp));
		}
		return a;
	}

	public static void main(String[] args) throws Exception {
		String idcard15 = "";
		String idcard18 = "";
		boolean flag = false;
		flag = IdcardValidator.isValidate18Idcard(idcard18);
		flag = IdcardValidator.isValidate15Idcard(idcard15);
		flag = IdcardValidator.isValidate18Idcard(IdcardValidator.convertIdcarBy15bit(idcard15));
	}
}
