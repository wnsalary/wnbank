package cn.com.pushworld.wn.to;

import java.text.SimpleDateFormat;
import java.util.Date;

import freemarker.template.SimpleDate;

/**
 * 威宁项目工具类
 * 
 */
public class WnUtils {
	/**
	 * 根据传入的日期获取到月末日期
	 * 
	 * @param date
	 *            (日期格式为:20190101或者2019-01-01或者2019年1月1日)
	 * @return
	 */
	public static void main(String[] args) {
		String monthEnd = getMonthEnd("2019-05-01");
		System.out.println(monthEnd);
	}

	public static String getMonthEnd(String date) {
		String result = "";
		try {
			SimpleDateFormat simple = null;
			if (date.contains("年")) {
				simple = new SimpleDateFormat("yyyy年MM月dd日");
			} else if (date.contains("-")) {
				simple = new SimpleDateFormat("yyyy-MM-dd");
			} else {
				simple = new SimpleDateFormat("yyyyMMdd");
			}
			Date parse = simple.parse(date);
			int year = Integer.parseInt(new SimpleDateFormat("yyyy")
					.format(parse));
			int month = Integer.parseInt(new SimpleDateFormat("MM")
					.format(parse));
			String day = "";
			switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				day = "31";
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				day = "30";
			case 2:
				if (year % 4 == 0 && year % 100 != 0) {
					day = "29";
				} else {
					day = "28";
				}
				break;
			}

			result = year
					+ "-"
					+ (month >= 10 ? String.valueOf(month) : String.valueOf("0"
							+ month)) + "-" + day;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//
	/**
	 * 获取到上一年的年初日期
	 * 
	 * @param date
	 *            ()
	 * @return
	 */
	public static String getYearEnd(String date) {
		String result = "";
		try {
			SimpleDateFormat simple = null;
			if (date.contains("年")) {
				simple = new SimpleDateFormat("yyyy年MM月dd日");
			} else if (date.contains("-")) {
				simple = new SimpleDateFormat("yyyy-MM-dd");
			} else {
				simple = new SimpleDateFormat("yyyyMMdd");
			}
			Date parse = simple.parse(date);
			int year = Integer.parseInt(new SimpleDateFormat("yyyy")
					.format(parse)) - 1;
			result = year + "-12-31";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 获取到考核年年初时间
	public static String getYearStart() {
		SimpleDateFormat simple = new SimpleDateFormat("yyyy");
		String yearStartDate = "";
		try {
			yearStartDate = simple.format(new Date()) + "-01-01";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return yearStartDate;
	}

	/*
	 * 根据考核日期获取到考核的年初时间
	 */
	public static String getYearStart(String date_time) {
		String[] yearMonthDay = getYearMonthDay(date_time);
		SimpleDateFormat simple = new SimpleDateFormat("yyyy");
		int currentYear = Integer.parseInt(simple.format(new Date()));
		int lastYear = Integer.parseInt(yearMonthDay[0]);
		if (currentYear > lastYear) {
			return lastYear + "-01" + "-01";
		} else {
			return currentYear + "-01" + "-01";
		}
	}

	// 获取到考核日期的年月日
	public static String[] getYearMonthDay(String date_time) {
		String[] result = new String[3];
		if (date_time.contains("年")) {
			date_time = date_time.replaceAll("年", "-").replaceAll("月", "-");
		}
		if (date_time.length() == 10) {
			result = date_time.split("-");
		} else {
			result = date_time.split("");
		}
		return result;
	}

	public static SimpleDateFormat formatDate(String date_time) {
		SimpleDateFormat simple = null;
		if (date_time.contains("年")) {
			simple = new SimpleDateFormat("yyyy年MM月dd日");
		} else if (date_time.contains("-")) {
			simple = new SimpleDateFormat("yyyy-MM-dd");
		} else {
			simple = new SimpleDateFormat("yyyyMMdd");
		}
		return simple;
	}
}
