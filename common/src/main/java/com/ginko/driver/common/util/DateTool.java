package com.ginko.driver.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTool {

	private static final SimpleDateFormat shortFormat = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static final Logger log = LoggerFactory.getLogger(DateTool.class);
	public static final String getNowTime() {
		return new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:dd").format(new Date());
	}

	public static final String getTimeAdd30min() {
		Long time = System.currentTimeMillis();
		time +=24*60*60*1000;
		String x= new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:dd").format(time);
		return x;
	}
/**
 * 
 * <p>Title: getMonth </p>
 * <p>Description: 获取月份 </p>
 * @author ly
 * Date: 2018年12月27日 上午11:13:09
 * @param strMonth
 * @param rtnType
 * @return String 获取月份
 */
	public static String getMonth(String strMonth, String rtnType) {
		String tempstrMonth = "";
		if (((strMonth.length() <= 2) && ("MM".equalsIgnoreCase(rtnType)))
				|| ((strMonth.length() == 3) && ("MMM".equalsIgnoreCase(rtnType)))) {
			tempstrMonth = strMonth;
		} else {
			if ((strMonth.length() <= 2) && ("MMM".equalsIgnoreCase(rtnType))) {
				if (("01".equals(strMonth)) || ("1".equals(strMonth))) {
                    tempstrMonth = "Jan";
                }
				if (("02".equals(strMonth)) || ("2".equals(strMonth))) {
                    tempstrMonth = "Feb";
                }
				if (("03".equals(strMonth)) || ("3".equals(strMonth))) {
                    tempstrMonth = "Mar";
                }
				if (("04".equals(strMonth)) || ("4".equals(strMonth))) {
                    tempstrMonth = "Apr";
                }
				if (("05".equals(strMonth)) || ("5".equals(strMonth))) {
                    tempstrMonth = "May";
                }
				if (("06".equals(strMonth)) || ("6".equals(strMonth))) {
                    tempstrMonth = "Jun";
                }
				if (("07".equals(strMonth)) || ("7".equals(strMonth))) {
                    tempstrMonth = "Jul";
                }
				if (("08".equals(strMonth)) || ("8".equals(strMonth))) {
                    tempstrMonth = "Aug";
                }
				if (("09".equals(strMonth)) || ("9".equals(strMonth))) {
                    tempstrMonth = "Sep";
                }
				if ("10".equals(strMonth)) {
                    tempstrMonth = "Oct";
                }
				if ("11".equals(strMonth)) {
                    tempstrMonth = "Nov";
                }
				if ("12".equals(strMonth)) {
                    tempstrMonth = "Dec";
                }
			}
			if ((strMonth.length() == 3) && ("MM".equalsIgnoreCase(rtnType))) {
				if ("Jan".equalsIgnoreCase(strMonth)) {
                    tempstrMonth = "01";
                }
				if ("Feb".equalsIgnoreCase(strMonth)) {
                    tempstrMonth = "02";
                }
				if ("Mar".equalsIgnoreCase(strMonth)) {
                    tempstrMonth = "03";
                }
				if ("Apr".equalsIgnoreCase(strMonth)) {
                    tempstrMonth = "04";
                }
				if ("May".equalsIgnoreCase(strMonth)) {
                    tempstrMonth = "05";
                }
				if ("Jun".equalsIgnoreCase(strMonth)) {
                    tempstrMonth = "06";
                }
				if ("Jul".equalsIgnoreCase(strMonth)) {
                    tempstrMonth = "07";
                }
				if ("Aug".equalsIgnoreCase(strMonth)) {
                    tempstrMonth = "08";
                }
				if ("Sep".equalsIgnoreCase(strMonth)) {
                    tempstrMonth = "09";
                }
				if ("Oct".equalsIgnoreCase(strMonth)) {
                    tempstrMonth = "10";
                }
				if ("Nov".equalsIgnoreCase(strMonth)) {
                    tempstrMonth = "11";
                }
				if ("Dec".equalsIgnoreCase(strMonth)) {
                    tempstrMonth = "12";
                }
			}
		}
		return tempstrMonth.toUpperCase();
	}

	public static String getDataBaseFormatData(String str) {
		String[] strs = str.split("-");

		if (strs[2].length() == 1) {
			strs[2] = ("0" + strs[2]);
		}
		if (strs[1].length() == 1) {
			strs[1] = ("0" + strs[1]);
		}
		StringBuffer sb = new StringBuffer();
		sb.append(strs[0]);
		sb.append("-");
		sb.append(strs[1]);
		sb.append("-");
		sb.append(strs[2]);
		return sb.toString();
	}
/**
 * 
 * <p>Title: getHostFormatDate </p>
 * <p>Description: 获取主机日期 </p>
 * @author ly
 * Date: 2018年12月27日 上午11:14:21
 * @param str
 * @return  String 获取主机日期
 */
	public static String getHostFormatDate(String str) {
		String[] strs = str.split("-");

		if (strs[2].length() == 1) {
			strs[2] = ("0" + strs[2]);
		}
		if (strs[1].length() == 1) {
			strs[1] = ("0" + strs[1]);
		}
		String month = getMonth(strs[1], "MMM");
		String year = strs[0].substring(2, 4);
		StringBuffer sb = new StringBuffer();
		sb.append(strs[2]);
		sb.append(month);
		sb.append(year);
		return sb.toString();
	}
/**
 * 
 * <p>Title: getCommonFormateDate </p>
 * <p>Description: 获取普通日期 </p>
 * @author ly
 * Date: 2018年12月27日 上午11:15:40
 * @param str
 * @return  String
 */
	public static String getCommonFormateDate(String str) {
		String date = str.substring(0, 2);
		String month = getMonth(str.substring(2, 5), "MM");
		String year = str.substring(5, 7);
		StringBuffer sb = new StringBuffer();
		sb.append("20").append(year).append("-").append(month).append("-");

		sb.append(date);
		return sb.toString();
	}
/**
 * 
 * <p>Title: getDayOfWeekCN </p>
 * <p>Description: 获取一周中的天数 </p>
 * @author ly
 * Date: 2018年12月27日 上午11:38:38
 * @param str
 * @return String
 * @throws ParseException Exception
 */
	public static String getDayOfWeekCN(String str) throws ParseException {
		Calendar c = Calendar.getInstance();
		Date date = new SimpleDateFormat(
				"yyyy-MM-dd").parse(str);
		c.setTime(date);
		switch (c.get(7)) {
		case 1:
			return "星期日";
		case 2:
			return "星期一";
		case 3:
			return "星期二";
		case 4:
			return "星期三";
		case 5:
			return "星期四";
		case 6:
			return "星期五";
		case 7:
			return "星期六";
		}
		return null;
	}

	public static String getDayOfWeekENS(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		switch (c.get(7)) {
		case 1:
			return "SUN";
		case 2:
			return "MON";
		case 3:
			return "TUR";
		case 4:
			return "WED";
		case 5:
			return "THU";
		case 6:
			return "FRI";
		case 7:
			return "SAT";
		}
		return null;
	}

	public static int getDayOfWeek(String str) throws ParseException {
		Date date = new SimpleDateFormat(
				"yyyy-MM-dd").parse(str);

		return getDayOfWeek(date);
	}

	public static int getDayOfWeek(Date date) throws ParseException {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(7);

		return dayOfWeek;
	}

	public static int getDayOfMonth(String str) throws ParseException {
		Date date = new SimpleDateFormat(
				"yyyy-MM-dd").parse(str);

		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c.get(5);
	}

	public static int getDayOfYear(String str) throws ParseException {
		Date date = new SimpleDateFormat(
				"yyyy-MM-dd").parse(str);

		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c.get(6);
	}

	public static int getMonthOfYear(String str) throws ParseException {
		Date date = new SimpleDateFormat(
				"yyyy-MM-dd").parse(str);

		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c.get(2) + 1;
	}

	public static Date getBeforeDate(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(6, calendar.get(6) - days);
		return calendar.getTime();
	}

	public static Date getAfterDate(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(6, calendar.get(6) + days);
		return calendar.getTime();
	}

	public static String getBeforeDate(String str, int days) {
		try {
			Date d = new SimpleDateFormat(
					"yyyy-MM-dd").parse(str);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			calendar.set(6, calendar.get(6) - days);
			return new SimpleDateFormat(
					"yyyy-MM-dd").format(calendar.getTime());
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		return "";
	}

	public static String getAfterDate(String str, int days) {
		try {
			Date d = new SimpleDateFormat(
					"yyyy-MM-dd").parse(str);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(d);
			calendar.set(6, calendar.get(6) + days);
			return new SimpleDateFormat(
					"yyyy-MM-dd").format(calendar.getTime());
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		return "";
	}

	public static Date getDateByStr(String date) throws ParseException {
		return new SimpleDateFormat(
				"yyyy-MM-dd").parse(date);
	}

	public static String getDateStrToString(String date) {
		//StringBuffer s = new StringBuffer();
		return date.substring(0, 4) + date.substring(5, 7) + date.substring(8);
	}

	public static String getDateStrByFormatStr(Date date, String str)
			throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(str);
		String sdate = df.format(date);
		return sdate;
	}

	public static String getDateStrByFormatStr(String dateStr,
			String inputFormat, String outputFormat) {
		String sdate = null;
		try {
			SimpleDateFormat indf = new SimpleDateFormat(inputFormat);
			SimpleDateFormat outdf = new SimpleDateFormat(outputFormat);
			Date date = indf.parse(dateStr);
			sdate = outdf.format(date);
		} catch (ParseException e) {
				log.error(e.getMessage());
		}

		return sdate;
	}

	public static Date getDateByFormatStr(String sdate, String formatstr)
			throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(formatstr);
		Date date = df.parse(sdate);
		return date;
	}

	public static String getTwoDay(String begin, String end) {
		long day = 0L;
		try {
			Date date = new SimpleDateFormat(
					"yyyy-MM-dd").parse(begin);
			Date mydate = new SimpleDateFormat(
					"yyyy-MM-dd").parse(end);
			day = (date.getTime() - mydate.getTime()) / 86400000L;
		} catch (Exception e) {
			return "";
		}
		return String.valueOf(day);
	}

	public static int getTwoHours(String begin, String end) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("hh:mm");
		int hours = 0;
		try {
			Date date = myFormatter.parse(begin);
			Date mydate = myFormatter.parse(end);
			hours = date.getHours() - mydate.getHours();
		} catch (Exception e) {
			return 0;
		}
		return hours;
	}

	public static String getSpecifiedDayBefore() {
		Date date = new Date();
		String specifiedDay = new SimpleDateFormat(
				"yyyy-MM-dd").format(date);
		String dayBefore = "";
		try {
			Calendar c = Calendar.getInstance();
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
			c.setTime(date);
			int day = c.get(5);
			c.set(5, day - 1);
			dayBefore = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
		} catch (ParseException e) {
				log.error(e.getMessage());
		}
		return dayBefore;
	}

	public static Date addMinutes(Date date, long sleepMinutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(12, (int) sleepMinutes);
		Date next = cal.getTime();

		return next;
	}

	public static String getReturnDayBefore(String putdate) {
		Date date = null;
		String dayBefore = "";
		try {
			Calendar c = Calendar.getInstance();
			date = new SimpleDateFormat("yy-MM-dd").parse(putdate);
			c.setTime(date);
			dayBefore = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
		} catch (ParseException e) {
				log.error(e.getMessage());
		}
		return dayBefore;
	}

	public static String getReturnDayAfter(String iputdate) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(iputdate);
		} catch (ParseException e) {
				log.error(e.getMessage());
		}
		c.setTime(date);
		int day = c.get(5);
		c.set(5, day + 1);
		String dayAfter = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
		return dayAfter;
	}

	public static String getFirstDayAtWeek(String date) {
		Date d = null;
		try {
			d = new SimpleDateFormat(
					"yyyy-MM-dd").parse(date);
		} catch (Exception e) {
				log.error(e.getMessage());
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(7, 2);
		return new SimpleDateFormat(
				"yyyy-MM-dd").format(cal.getTime());
	}

	public static String[] getSEDaysOfWeek(String date) throws ParseException {
		String[] seDays = new String[2];

		Date d = new SimpleDateFormat(
				"yyyy-MM-dd").parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(2);
		cal.setTime(d);

		cal.set(7, 2);
		Date monday = cal.getTime();
		seDays[0] = new SimpleDateFormat(
				"yyyy-MM-dd").format(monday);

		Date sunday = getAfterDate(monday, 6);
		seDays[1] = new SimpleDateFormat(
				"yyyy-MM-dd").format(sunday);

		return seDays;
	}

	public static String[] getSEDaysOfMonth(String date) throws ParseException {
		String[] seDays = new String[2];

		Date d = new SimpleDateFormat(
				"yyyy-MM-dd").parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);

		int year = cal.get(1);
		int month = cal.get(2);

		cal.set(year, month, 1);
		seDays[0] = new SimpleDateFormat(
				"yyyy-MM-dd").format(cal.getTime());

		cal.set(year, month + 1, 0);
		seDays[1] = new SimpleDateFormat(
				"yyyy-MM-dd").format(cal.getTime());

		return seDays;
	}
	//begin 20160707获取上月日期信息
	public static String[] getlastOfMonth(String date) throws ParseException {
		String[] seDays = new String[2];

		Date d = new SimpleDateFormat(
				"yyyy-MM-dd").parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);

		int year = cal.get(1);
		int month = cal.get(2);

		cal.set(year, month-1, 1);
		seDays[0] = new SimpleDateFormat(
				"yyyy-MM-dd").format(cal.getTime());

		cal.set(year, month, 0);
		seDays[1] = new SimpleDateFormat(
				"yyyy-MM-dd").format(cal.getTime());

		return seDays;
	}//end 20160707

	public static String[] getSEDaysOfQuarter(String str) throws ParseException {
		String[] seDays = new String[2];

		Date d = new SimpleDateFormat(
				"yyyy-MM-dd").parse(str);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);

		int year = cal.get(1);
		int month = cal.get(2);

		while ((month != 0) && (month != 3) && (month != 6) && (month != 9)) {
			cal.set(2, cal.get(2) - 1);
			month = cal.get(2);
		}

		cal.set(year, month, 1);
		seDays[0] = new SimpleDateFormat(
				"yyyy-MM-dd").format(cal.getTime());

		cal.set(year, month + 3, 0);
		seDays[1] = new SimpleDateFormat(
				"yyyy-MM-dd").format(cal.getTime());

		return seDays;
	}

	public static String[] getSEDaysOfYear(String date) throws ParseException {
		String[] seDays = new String[2];

		Date d = new SimpleDateFormat(
				"yyyy-MM-dd").parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);

		int year = cal.get(1);

		cal.set(year, 0, 1);
		seDays[0] = new SimpleDateFormat(
				"yyyy-MM-dd").format(cal.getTime());

		cal.set(year, 11, 0);
		seDays[1] = new SimpleDateFormat(
				"yyyy-MM-dd").format(cal.getTime());

		return seDays;
	}

	public static int compare_date(String DATE1, String DATE2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			}
			if (dt1.getTime() < dt2.getTime()) {
				return -1;
			}
			return 0;
		} catch (Exception exception) {
			log.error(exception.getMessage());
		}
		return 0;
	}
	public static String compare_datetime(String DATE2) {
		if (DATE2==null||DATE2==""){
			return "success";
		}
		else{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			try {
				Date dt1 = df.parse(getNowTime());
				Date dt2 = df.parse(DATE2);
				if (dt1.getTime() > dt2.getTime()) {
					return "success";
				}
				else if (dt1.getTime() < dt2.getTime()) {
					return "fail";
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
		return "";
	}

	public static long diffTime(String date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long minutes = 0;
		long hours = 0;
		try {
			Date d2 = df.parse(getNowTime());
			Date d1 = df.parse(date);
			long diff = d1.getTime() - d2.getTime();//这样得到的差值是毫秒级别
			hours = (diff/(1000 * 60 * 60));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return hours;
	}
	public static long diffTimeMin(String date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long minutes = 0;
		long hours = 0;
		try {
			Date d2 = df.parse(getNowTime());
			Date d1 = df.parse(date);
			long diff = d2.getTime() - d1.getTime();//这样得到的差值是毫秒级别
			minutes = (diff/(1000 * 60));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return minutes;
	}

		public static String getCurrentDate() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(dt);
	}

	private static String getTicketNumEnd(String ticketNumStr) {
		String ticketNumEnd = "";
		if(ticketNumStr!=null){
			ticketNumEnd = ticketNumStr.substring(3);
		if (ticketNumStr.indexOf("-") > 0) {
			int ticketLen = ticketNumStr.split("-")[1].length();
			String ticketNumPrefixStr = ticketNumStr.substring(3).split("-")[0];
			String ticketNumEndStr = ticketNumStr.substring(ticketLen).split(
					"-")[1];
			if (Integer.parseInt(ticketNumEndStr) < Integer
					.parseInt(ticketNumPrefixStr.substring(10 - ticketLen))) {
				String ticketNumTemp = ticketNumPrefixStr.substring(0,
						10 - ticketLen);
				ticketNumEnd = String
						.valueOf(Long.parseLong(ticketNumTemp) + 1L)
						+ ticketNumEndStr;
				return ticketNumEnd;
			}

			String str = ticketNumStr.substring(3);
			String str2 = "";
			str2 = str.split("-")[0];
			ticketNumEnd = str2.substring(0, str2.length() - ticketLen)
					+ ticketNumStr.split("-")[1];
			return ticketNumEnd;
		}
		}
		return ticketNumEnd;
	}

	public static Long getDaysBetween(Date startDate, Date endDate) {
		Calendar fromCalendar = Calendar.getInstance();
		fromCalendar.setTime(startDate);
		fromCalendar.set(11, 0);
		fromCalendar.set(12, 0);
		fromCalendar.set(13, 0);
		fromCalendar.set(14, 0);

		Calendar toCalendar = Calendar.getInstance();
		toCalendar.setTime(endDate);
		toCalendar.set(11, 0);
		toCalendar.set(12, 0);
		toCalendar.set(13, 0);
		toCalendar.set(14, 0);

		return Long.valueOf((toCalendar.getTime().getTime() - fromCalendar
				.getTime().getTime()) / 86400000L);
	}

	public static void main(String[] args) throws ParseException {
		System.out.println(getNowTime());
	}
	
	public static SimpleDateFormat getSimpleDateFormat(){
		return shortFormat;
		
	}
}
