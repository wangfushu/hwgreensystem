package com.xmrbi.hwgreensystem.until;


import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class StringUtil {
	private static String _defDateFormat = "yyyy-MM-dd HH:mm:ss.SSS";
	/**
	 * 正则表达式：验证手机号
	 */
	public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

	/**
	 * 检测字符串是否不为空(null,"","null")
	 * 
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s) {
		return !isEmpty(s);
	}

	/**
	 * 检测字符串是否为空(null,"","null")
	 * 
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s) {
		return s == null || "".equals(s) || "null".equals(s);
	}

	private static String _plateBaseString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static String[] _plateProvinceString = { "闽", "京", "津", "冀", "黑", "吉", "辽", "蒙", "新", "藏", "青", "甘", "陕",
			"宁", "川", "渝", "云", "贵", "桂", "粤", "琼", "鄂", "湘", "赣", "苏", "浙", "皖", "沪", "晋", "鲁", "豫" };

	public static String timestampToString(long timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat(_defDateFormat);
		return sdf.format(new Date(timestamp));
	}

	/**
	 * 16进制的字符串表示转成字节数组
	 * 
	 * @param hexString
	 *            16进制格式的字符串
	 * @return 转换后的字节数组
	 **/
	public static byte[] toByteArray(String hexString) {
		if (StringUtils.isEmpty(hexString))
			throw new IllegalArgumentException("this string must not be empty");

		hexString = hexString.toLowerCase();
		if (hexString.length() % 2 != 0)
			hexString = "0" + hexString;
		final byte[] byteArray = new byte[hexString.length() / 2];
		int k = 0;
		for (int i = 0; i < byteArray.length; i++) {// 因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
			byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
			byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
			byteArray[i] = (byte) (high << 4 | low);
			k += 2;
		}
		return byteArray;
	}

	/**
	 * 10进制的字符串表示转成字节数组
	 * 
	 * @param decString
	 *            10进制格式的字符串
	 * @return 转换后的字节数组
	 **/
	public static byte[] decToByteArray(String decString) {
		return toByteArray(String.format("%02x", Integer.parseInt(decString, 10)));
	}

	/**
	 * 字节数组转成16进制表示格式的字符串
	 * 
	 * @param byteArray
	 *            需要转换的字节数组
	 * @return 16进制表示格式的字符串
	 **/
	public static String toHexString(byte[] byteArray) {
		if (byteArray == null || byteArray.length < 1)
			throw new IllegalArgumentException("this byteArray must not be null or empty");

		final StringBuilder hexString = new StringBuilder();
		for (int i = 0; i < byteArray.length; i++) {
			if ((byteArray[i] & 0xff) < 0x10)// 0~F前面不零
				hexString.append("0");
			hexString.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return hexString.toString().toLowerCase();
	}

	public static String toDecString(byte[] byteArray) {
		String hexString = toHexString(byteArray);
		return Integer.toString(Integer.parseInt(hexString, 16), 10);
	}

	/**
	 * 创建sessionId
	 * 
	 * @return
	 * @param id
	 *            String唯一标识
	 * @return rtobj BaseReturn 基本返回对象
	 * @变更记录 2015-5-19 下午05:41:54 陈上岳 创建
	 * 
	 */
	public static String newSessionID() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date now = new Date();
			Date ref = df.parse("2015-05-15 15:05:15");
			long diff = now.getTime() - ref.getTime();
			int rnd = (int) (Math.random() * 65535);
			return String.format("%012x%04x", diff, rnd);
		} catch (Exception e) {

		}
		return "";
	}

	public static String getMD5(String original) {
		String s = null;
		if (original == null) {
			return s;
		}
		byte[] source = original.getBytes();
		char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
			// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
			// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			int len = tmp.length;
			for (int i = 0; i < len; i++) { // 从第一个字节开始，对 MD5 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
				// >>> 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/***
	 * SHA加密 生成40位SHA码
	 * 
	 * @param 待加密字符串
	 * @return 返回40位SHA码
	 *//*
	public static String getSHA(String original) throws Exception {
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}

		byte[] byteArray = original.getBytes(Config.CHARSET);
		byte[] md5Bytes = sha.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
*/
	public static String strSort(String original) {
		char temp;
		char[] b = original.toCharArray();
		int len = b.length;
		int half = b.length / 2;
		for (int i = 0; i < half; i++) {
			temp = b[i];
			b[i] = b[len - i - 1];
			b[len - i - 1] = temp;
		}
		return String.copyValueOf(b);
	}

	/*public static String getPlateNumber() {
		int provinceIdx = NumberUtil.randomNextInt(200);
		if (provinceIdx > 30) {
			provinceIdx = 0;
		}
		int cityIdx = NumberUtil.randomNextInt(200);
		if (cityIdx > 25) {
			cityIdx = 3;
		}
		String res = _plateProvinceString[provinceIdx];
		res += _plateBaseString.charAt(cityIdx);
		for (int idx = 0; idx < 5; idx++) {
			int numIdx = NumberUtil.randomNextInt(_plateBaseString.length() - 1);
			res += _plateBaseString.charAt(numIdx);
		}
		return res;
	}
*/
	public static String getUUID() {
		return UUID.randomUUID().toString().toUpperCase();
	}

	public static String getRandomMobileNo(int len) {
		String mobileNoString = "";
		Random r = new Random();
		String intString = "";
		while (mobileNoString.length() < len) {
			intString = Integer.toString(r.nextInt());
			if (intString.startsWith("-")) {
				intString = intString.substring(1);
			}
			mobileNoString = mobileNoString + intString;
			while (!mobileNoString.startsWith("3") && !mobileNoString.startsWith("5")
					&& !mobileNoString.startsWith("8")) {
				if (mobileNoString.length() < 2)
					break;
				mobileNoString = mobileNoString.substring(1);
			}
		}
		mobileNoString = "1" + mobileNoString;
		return mobileNoString.substring(0, len);
	}

	public static String getRandomMobileNo() {
		return getRandomMobileNo(11);
	}

	/**
	 * 获取指定长度随机简体中文
	 * 
	 * @param len
	 *            int
	 * @return String
	 */
	private static String getRandomJianHan() {
		Random random = new Random();
		String str = null;
		int hightPos, lowPos; // 定义高低位
		hightPos = (176 + Math.abs(random.nextInt(39))); // 获取高位值
		lowPos = (161 + Math.abs(random.nextInt(93))); // 获取低位值
		byte[] b = new byte[2];
		b[0] = (new Integer(hightPos).byteValue());
		b[1] = (new Integer(lowPos).byteValue());
		try {
			str = new String(b, "GBk"); // 转成中文
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
		return str;
	}

	public static String getRandomChinese(boolean isTraditional) {
		if (isTraditional) {
			Random ran = new Random();
			int delta = 0x9fa5 - 0x4e00 + 1;
			StringBuilder sb = new StringBuilder();
			sb.append((char) (0x4e00 + ran.nextInt(delta)));
			return sb.toString();
		} else {
			return getRandomJianHan();
		}

	}

	public static String getRandomChinese() {
		return getRandomChinese(false);
	}

	public static String getRandomChinese(int len, boolean isTraditional) {
		StringBuilder sb = new StringBuilder();
		for (int idx = 0; idx < len; idx++) {
			sb.append(getRandomChinese(isTraditional));
		}
		return sb.toString();
	}

	public static String getRandomChinese(int len) {
		return getRandomChinese(len, false);
	}

	/*public static String getOrderNo() {
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Long randLong = 1000000000000000L + Math.abs(NumberUtil.randomNextLong());
		String randStr = randLong.toString();
		return sdf.format(curDate) + randStr.substring(randStr.length() - 15);
	}*/

	/*
	 * 生成16位序列号
	 */
	/*public static String getSerialNumber() {
		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmssSSS");
		Long randLong = 10000000L + Math.abs(NumberUtil.randomNextLong());
		String randStr = randLong.toString();
		return sdf.format(curDate) + randStr.substring(randStr.length() - 7);

	}
*/
	public static Double stringToDouble(String doubleStr, Double doubleDef) {
		if (StringUtils.isBlank(doubleStr)) {
			return doubleDef;
		}
		try {
			return Double.parseDouble(doubleStr);
		} catch (Exception ex) {

		}
		return doubleDef;
	}

	public static float stringToFloat(String floatStr) {
		Double d = stringToDouble(floatStr, -1.0);
		return d.floatValue();
	}

	public static int stringToInt(String intStr, int intDef) {
		try {
			Integer integerDef = new Integer(intDef);
			Double resDouble = stringToDouble(intStr, integerDef.doubleValue());
			return resDouble.intValue();
		} catch (Exception ex) {

		}
		return intDef;
	}

	public static int stringToInt(String intStr) {
		return stringToInt(intStr, -1);
	}

	public static long stringToLong(String longStr, long longDef) {
		try {
			Long inputDef = new Long(longDef);
			Double resDouble = stringToDouble(longStr, inputDef.doubleValue());
			return resDouble.longValue();
		} catch (Exception ex) {

		}
		return longDef;
	}

	public static long stringToLong(String longStr) {
		return stringToLong(longStr, -1);
	}

	/**
	 * 检测对象是否不为空
	 */
	public static boolean hasObj(Object obj) {
		return obj != null;
	}

	/**
	 * Judge the String if NULL
	 */
	public static boolean hasText(String str) {
		return str != null && str.trim().length() > 0 && !str.equalsIgnoreCase("null");
	}

	/**
	 * Change Model to Map<br>
	 * NO INCLUDE NULL FIELD.
	 * 
	 * @see objToMap();
	 */
	public static Map<String, Object> modelToMapNoNullValue(Object obj) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		if (obj == null)
			return null;
		Field[] fields = obj.getClass().getDeclaredFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				try {
					Field f = obj.getClass().getDeclaredField(fields[i].getName());
					f.setAccessible(true);
					Object o = f.get(obj);
					if (o != null && !"".equals(o)) {
						reMap.put(fields[i].getName(), o);
					}
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return reMap;
	}

	/**
	 * Change Model to Map<br>
	 * INCLUDE NULL FIELD.
	 * 
	 * @see objToMap();
	 */
	public static Map<String, Object> modelToMap(Object obj) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		if (obj == null)
			return null;
		Field[] fields = obj.getClass().getDeclaredFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				try {
					Field f = obj.getClass().getDeclaredField(fields[i].getName());
					f.setAccessible(true);
					Object o = f.get(obj);
					reMap.put(fields[i].getName(), o);
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return reMap;
	}

	/**
	 * Change JsonStr to Map<br>
	 */
	/*public static Map<String, Object> jsonStrToMap(String jsonStr) {
		Map<String, Object> data = new HashMap<String, Object>();
		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		Set<String> sets = jsonObject.keySet();
		Iterator<String> it = sets.iterator();
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			Object value = (Object) jsonObject.get(key);
			if (value == null) {
				continue;
			}
			data.put(key, value);
		}
		return data;
	}*/

	/**
	 * Data To String
	 * 
	 * @param formatStr
	 *            日期格式
	 * @param targetDate
	 *            日期
	 * @return
	 */
	public static String formatDateTime2Str(String formatStr, Date targetDate) {
		String dateString = "null";
		if (hasText(formatStr) && hasObj(targetDate)) {
			SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
			try {
				dateString = sdf.format(targetDate);
			} catch (Exception localException) {
				// e.printStackTrace();
				return dateString;
			}
		}
		return dateString;
	}

	public static String formatDateTime2Str(String formatStr) {
		return formatDateTime2Str(formatStr, new Date());
	}

	public static String formatDateTime2Str(Date targetDate) {
		return formatDateTime2Str("yyyy-MM-dd HH:mm:ss", targetDate);
	}

	public static String formatDateTime2Str() {
		return formatDateTime2Str("yyyy-MM-dd HH:mm:ss", new Date());
	}

	public static String formatLastDate2Str(String formatStr) {
		return StringUtil.formatDateTime2Str(formatStr, new Date(System.currentTimeMillis() - 86400000));
	}

	public static String formatLastDate2Str() {
		return formatLastDate2Str("yyyy-MM-dd");
	}

	/*
	 * 取得唯一的编码值
	 */
	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}

	/*
	 * 取得相应颜色车牌代码 01-黄 02-小轿车，蓝 03-黑
	 */
/*
	public static String getPlateColorCode(String platecolor) {
		String platecolorcode = "02";
		switch (platecolor) {
			case "黄":
				platecolorcode = "01";
				break;
			case "蓝":
				platecolorcode = "02";
				break;
			case "黑":
				platecolorcode = "03";
				break;
			default:
				platecolorcode = "02";
		}
		return platecolorcode;
	}
*/

	/*
	 * 取得相应颜色车牌代码 1-01 2-02 3-03
	 */
	public static String getPlateColorCodeByid(String platecolorid) {
		String platecolorcode = "02";
		if (platecolorid.getBytes().length == 1) {
			platecolorcode = "0" + platecolorid;
		} else {
			platecolorcode = platecolorid;
		}
		return platecolorcode;
	}

	/*
	 * 取得相应颜色车牌代码 01-黄 02-小轿车，蓝 03-黑
	 */
	/*public static String getPlateColor(String platecolorcode) {
		String platecolor = "蓝";
		switch (platecolorcode) {
			case "01":
				platecolor = "黄";
				break;
			case "02":
				platecolor = "蓝";
				break;
			case "03":
				platecolor = "黑";
				break;
			default:
				platecolor = "蓝";
		}
		return platecolor;
	}*/

	/**
	 * 校验是否为手机号
	 * 
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile) {
		return Pattern.matches(REGEX_MOBILE, mobile);
	}

	/**
	 * 时间戳转Date
	 * 
	 * @param timestamp
	 * @return
	 */
	public static Date timeStamp2Date(Long timestamp) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long time = (long) 1462343027.450718;
		String d = format.format(time);
		Date date = null;
		try {
			date = format.parse(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	public static Map<String, Object> paramStr2Map(String params) {

		String[] paramArray = params.split("&");
		Map<String, Object> map = new HashMap<String, Object>();
		for (String param : paramArray) {
			int split = param.indexOf("=");
			int end = param.length();
			String key = param.substring(0, split);
			String value = param.substring(split + 1, end);
			map.put(key, value);
		}
		return map;
	}

	public static String getTimeStamp() {
		Date curDate = new Date();
		return Long.toString(curDate.getTime() / 1000);

	}

	public static String genRandomString(int strLength) {
		int i;
		int count = 0;
		char[] str = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
				'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
				'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
				'z' };
		int maxNum = str.length;
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < strLength) {
			i = Math.abs(r.nextInt(maxNum));
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}

	public static String genRandomString() {
		return genRandomString(8);
	}

	/**
	 * 移除空白字符，如果为null则改为“”
	 *<b>Summary: </b>
	 * removeTrim()
	 * @param pwd
	 * @return
	 * @throws Exception
	 */
	public static String removeTrim(String pwd){
		if(pwd == null || "".equals(pwd.trim())){
			return "";
		}
		return pwd.replaceAll("\\s+", "");//替换所有空白字符
	}

	/**
	 *
	 * @param digits 格式化几位数
	 * @param initNumber 0前面数字
	 * @return
	 */
	public static String getFormat(int digits,int initNumber){
		String formatNum = String.format("%0"+digits+"d",initNumber);
		return formatNum;
	}

}
