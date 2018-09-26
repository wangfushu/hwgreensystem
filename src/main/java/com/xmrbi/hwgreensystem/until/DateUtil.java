package com.xmrbi.hwgreensystem.until;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class DateUtil {
	private DateUtil() {
	}

	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);
	private static final int ONE = 1;
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String LOG_DATE_PATTERN = "dd/MMM/yyyy";
	public static final String HOUR_PATTERN = "yyyy-MM-dd-HH";
	public static final String MIN_PATTERN = "yyyy-MM-dd-HH-mm";
	public static final String SIMPLE_SECOND_PATTERN = "yyyyMMddHHmmss";
	public static final String COMMON_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String TIME_PATTERN = "HH:mm:ss";
	public static final String TIME_MIN_PATTERN = "HH:mm";
	public static final Locale US = Locale.US;
	public static final String CHART_DATE_PATTERN = "%Y-%m-%d";
	public static final String CHART_HOUR_PATTERN = "%Y-%m-%d-%H";
	public static final String RFC3339 = "EEE MMM dd yyyy HH:mm:ss 'GMT'Z";
	public static final int MAX_HOUR = 23;
	public static final int MAX_MINUTE = 59;
	public static final int MAX_SECOND = 59;
	public static final int MAX_MSEC = 999;
	public static final int ONE_DAY_HOUR = 24;
	public static final int ONE_HOUR = 60;
	public static final int ONE_MINUTE = 60;
	public static final int ONE_MSEC = 1000;

	public static long dayBeginTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	public static long dayEndnTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	public static long yesterdayBeginTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	public static Date dayBeforeBeginTime(int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -num);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date nextDayBeginTime(int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, num);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date dayBeforeEndTime(int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -num);
		calendar.set(Calendar.HOUR_OF_DAY, MAX_HOUR);
		calendar.set(Calendar.MINUTE, MAX_MINUTE);
		calendar.set(Calendar.SECOND, MAX_SECOND);
		calendar.set(Calendar.MILLISECOND, MAX_MSEC);
		return calendar.getTime();
	}

	public static Date yesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Date(calendar.getTimeInMillis());
	}

	public static long todayBeginTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	public static Date hourEndTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, MAX_MINUTE);
		calendar.set(Calendar.SECOND, MAX_SECOND);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date hourStartTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static boolean isSameDay(Long start, Long end) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(start);
		int date = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.setTimeInMillis(end);
		return date == calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static boolean isSameDay(Date start, Date end) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		int date = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.setTime(end);
		return date == calendar.get(Calendar.DAY_OF_YEAR);
	}

	public static boolean isSameByDatePattern(Date start, Date end) {
		String formatDate = formatDate(start, DATE_PATTERN);
		String endDate = formatDate(end, DATE_PATTERN);
		return formatDate.equals(endDate);
	}

	public static boolean isSameWeek(Date start, Date end) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		int date = calendar.get(Calendar.WEEK_OF_YEAR);
		calendar.setTime(end);
		return date == calendar.get(Calendar.WEEK_OF_YEAR);
	}

	public static boolean isSameMonth(Date start, Date end) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		int date = calendar.get(Calendar.MONTH);
		calendar.setTime(end);
		return date == calendar.get(Calendar.MONTH);
	}

	public static long lastHour() {
		return lastHours(1);
	}

	public static long last2Hours() {
		return lastHours(2);
	}

	public static long lastHours(int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, (-1) * hours);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	public static long nextDays(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, days);
		return calendar.getTimeInMillis();
	}

	public static Date nextHours(int hours, Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hours);
		return calendar.getTime();
	}

	public static Date nextMinute(int minutes, Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minutes);
		return calendar.getTime();
	}

	public static Date nextDate(int days, Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}

	public static long lastHalfHours() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, -1);
		calendar.set(Calendar.MINUTE, ONE_MINUTE / 2);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	public static Date lastMonth(int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -month);
		return new Date(calendar.getTimeInMillis());
	}

	public static String formatDate(Date time, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern, US);
		return format.format(time);
	}

	public static String dayPattern(long time) {
		SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
		return format.format(new Date(time));
	}

	public static String logDayPattern(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(LOG_DATE_PATTERN, US);
		return format.format(date);
	}

	public static String hourPattern(long time) {
		SimpleDateFormat format = new SimpleDateFormat(HOUR_PATTERN);
		return format.format(new Date(time));
	}

	public static String minutePattern(long time) {
		SimpleDateFormat format = new SimpleDateFormat(MIN_PATTERN);
		return format.format(new Date(time));
	}

	public static String timeMinPattern(long time) {
		SimpleDateFormat format = new SimpleDateFormat(TIME_MIN_PATTERN);
		return format.format(new Date(time));
	}

	public static String simpleSecondPattern(long time) {
		SimpleDateFormat format = new SimpleDateFormat(SIMPLE_SECOND_PATTERN);
		return format.format(new Date(time));
	}

	public static String fiveMinutePattern(long time) {
		time = (time / (5 * ONE_MINUTE * ONE_MSEC)) * (5 * ONE_MINUTE * ONE_MSEC);
		SimpleDateFormat format = new SimpleDateFormat(MIN_PATTERN);
		return format.format(new Date(time));
	}

	public static String fiveMinutePattern(long time, String pattern) {
		time = (time / (5 * ONE_MINUTE * ONE_MSEC)) * (5 * ONE_MINUTE * ONE_MSEC);
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(new Date(time));
	}

	public static Date parseDate(String time, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			return format.parse(time);
		} catch (ParseException e) {
			logger.error("error : ", e);
		}
		return null;
	}

	public static Date parseDate(String time, String pattern, Locale local) {
		SimpleDateFormat format = new SimpleDateFormat(pattern, local);
		try {
			return format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String[] last2HourPattern() {
		return new String[] { hourPattern(System.currentTimeMillis()), hourPattern(lastHour()),
				hourPattern(last2Hours()) };
	}

	public static String[] last1HourPattern() {
		return new String[] { hourPattern(System.currentTimeMillis()), hourPattern(lastHour()) };
	}

	public static Date minutesBefore(int num) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, -num);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Date(calendar.getTimeInMillis());
	}

	/**
	 * 取指定日期的开始日期时间
	 *
	 * @param date
	 *            待处理的日期
	 * @return 传入日期的开始时间
	 */
/*	@SuppressWarnings("deprecation")*/
/*	public static Date getStartDate(Date date) {
		try {
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			return date;
		} catch (Exception e) {
			logger.error("err", e, e);
			throw new RuntimeException(e);
		}
	}*/
/*	public static Date getEndDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 59);
		return calendar.getTime();
	}*/



	public static Date getFirstDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return calendar.getTime();
	}

	public static Date getNextWeekMonday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.add(Calendar.DAY_OF_YEAR, 7);
		return calendar.getTime();
	}

	public static Date getNextMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}

	public static int getDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_MONTH, ONE);
		return calendar.getTime();
	}

	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	public static long dateDiff(Date startTime, Date endTime, int type) {
		long result = 0;
		// 一天的毫秒数
		long nd = ONE_MSEC * ONE_MINUTE * ONE_HOUR * ONE_DAY_HOUR;
		// 一小时的毫秒数
		long nh = ONE_MSEC * ONE_MINUTE * ONE_HOUR;
		// 一分钟的毫秒数
		long nm = ONE_MSEC * ONE_MINUTE;
		// 一秒钟的毫秒数
		long ns = ONE_MSEC;
		long diff;
		try {
			// 获得两个时间的毫秒时间差异
			diff = endTime.getTime() - startTime.getTime();
			switch (type) {
			case Calendar.DATE:
				// 计算差多少天
				result = diff / nd;
				break;
			case Calendar.HOUR:
				// 计算差多少小时
				result = diff % nd / nh;
				break;
			case Calendar.MINUTE:
				// 计算差多少分钟
				result = diff % nd % nh / nm;
				break;
			case Calendar.SECOND:
				// 计算差多少秒
				result = diff % nd % nh % nm / ns;
				break;
			default:
				break;
			}
		} catch (Exception e) {
			logger.error("err", e, e);
		}
		return result;
	}

	/**
	 *
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static long getDiffsecend(Date startTime, Date endTime) {
		long diff=0;
		try {
			// 获得两个时间的毫秒时间差异
			diff = endTime.getTime() - startTime.getTime();
		}catch (Exception e) {
			logger.error("err", e, e);
		}
		return diff;
	}


	public static String toRFC3339(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
		return sdf.format(date).replaceAll("(\\d\\d)(\\d\\d)$", "$1:$2");
	}

	public static Date fromRFC3339(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
		date = date.substring(0, date.lastIndexOf(':')) + date.substring(date.lastIndexOf(":") + 1);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			logger.error("fromRFC3339 err", e, e);
		}
		return null;
	}

	public static int getDayOfWeek(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		return week - 1;
	}

	/**
	 * @param timeStr
	 *            必须符合 yyyy-MM-dd
	 * @return
	 */
	public static boolean isToday(String timeStr) {
		Date date = parseDate(timeStr, "yyyy-MM-dd");
		return date.getTime() == todayBeginTime();
	}

	/**
	 * @param timeStr
	 *            必须符合 HH:mm:ss
	 * @return
	 */
	public static int getDayTimes(String timeStr) {
		if (StringUtils.isBlank(timeStr)) {
			return 0;
		}
		String[] split = StringUtils.split(timeStr, ":");
		if (split.length != 3) {
			return 0;
		}
		return Integer.parseInt(split[0]) * 60 * 60 + Integer.parseInt(split[1]) * 60 + Integer.parseInt(split[2]);
	}

	/**
	 * @param times
	 *            当天时间的秒数 00:00:00开始算起
	 * @return
	 */
	public static String getDayTimeStr(int times) {
		if (times <= 0) {
			return "00:00:00";
		}
		int hour = times / 3600;
		int minute = times % 3600 / 60;
		int second = times % 3600 % 60;
		return int2Time(hour) + ":" + int2Time(minute) + ":" + int2Time(second);
	}

	// 就一位的前面补个0
	private static String int2Time(int i) {
		if (i < 10) {
			return "0" + i;
		}
		return i + "";
	}

	public static int getWeekOfYear(Date date) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		return cl.get(Calendar.WEEK_OF_YEAR);
	}

	public static Date fromDateStringToDate(String textTime) {
		if (StringUtil.isEmpty(textTime)) {
			return null;
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		try {
			date = df.parse(textTime);
			// 将字符型转换成日期型
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;

		// 返回转换后的时间
	}


	public static Date fromDateStringToYMDDate(String textTime) {
		if (StringUtil.isEmpty(textTime)) {
			return null;
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try {
			date = df.parse(textTime);
			// 将字符型转换成日期型
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;

		// 返回转换后的时间
	}

	public static long getDaySub(String beginDateStr, String endDateStr)
	{
		long day=0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate;
		Date endDate;
		try
		{
			beginDate = format.parse(beginDateStr);
			endDate= format.parse(endDateStr);
			day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
			//System.out.println("相隔的天数="+day);
		} catch (ParseException e)
		{
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return day;
	}


	/**
	 * 取指定日期的开始日期时间
	 *
	 * @param date
	 *            待处理的日期
	 * @return 传入日期的开始时间
	 */
	@SuppressWarnings("deprecation")
	public static Date getStartDate(Date date) {
		try {
			/*date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);*/
			Calendar day=Calendar.getInstance();
			day.setTime(date);
			day.set(Calendar.HOUR_OF_DAY,0);
			day.set(Calendar.MINUTE,0);
			day.set(Calendar.SECOND,0);
			day.set(Calendar.MILLISECOND,0);
			return day.getTime();
		} catch (Exception e) {
			logger.error("err", e, e);
			throw new RuntimeException(e);
		}
	}
	/**
	 * 取指定日期的截止日期时间
	 *
	 * @param date
	 *            待处理的日期
	 * @return 传入日期的截止时间
	 */
	@SuppressWarnings("deprecation")
	public static Date getEndDate(Date date) {
		try {
	/*		date.setHours(23);
			date.setMinutes(59);
			date.setSeconds(59);
			return date;*/
			Calendar day=Calendar.getInstance();
			day.setTime(date);
			day.set(Calendar.HOUR_OF_DAY,23);
			day.set(Calendar.MINUTE,59);
			day.set(Calendar.SECOND,59);
			day.set(Calendar.MILLISECOND,999);
			return day.getTime();
		} catch (Exception e) {
			logger.error("err", e, e);
			throw new RuntimeException(e);
		}
	}

	public static Date getYesterDayTime(Date date) {
		try {
	/*		date.setHours(23);
			date.setMinutes(59);
			date.setSeconds(59);
			return date;*/
			Calendar day=Calendar.getInstance();
			day.setTime(date);
			day.set(Calendar.DATE,-1);
			day.set(Calendar.HOUR_OF_DAY,0);
			day.set(Calendar.MINUTE,0);
			day.set(Calendar.SECOND,0);
			day.set(Calendar.MILLISECOND,0);
			return day.getTime();
		} catch (Exception e) {
			logger.error("err", e, e);
			throw new RuntimeException(e);
		}
	}


	/**
	 * 得到几天前的时间
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateBeforeDays(Date d, int day){
		Calendar now =Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE,now.get(Calendar.DATE)-day);
		return now.getTime();
	}

	/**
	 * 得到几天后的时间
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date getDateAfterDays(Date d, int day){
		Calendar now =Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE,now.get(Calendar.DATE)+day);
		return now.getTime();
	}


	/**
	 * 判断时间是否在时间段内
	 * @param nowTime
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}

}
