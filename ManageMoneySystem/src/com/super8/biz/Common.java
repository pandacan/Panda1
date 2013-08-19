package com.super8.biz;

import java.util.Calendar;

public class Common {
	Calendar c = Calendar.getInstance();

	public String getDate() {
		String time = c.get(Calendar.YEAR) + "-" + // 得到年
				formatTime(c.get(Calendar.MONTH) + 1) + "-" + // month加一 //月
				formatTime(c.get(Calendar.DAY_OF_MONTH)) + " " + // 日
				formatTime(c.get(Calendar.HOUR_OF_DAY)) + ":" + // 时
				formatTime(c.get(Calendar.MINUTE)); // 分
		return time;
	}

	public int getYear() {
		int year = c.get(Calendar.YEAR);// 得到年
		return year;
	}

	public String getMONTH() {
		String month = formatTime(c.get(Calendar.MONTH) + 1);// month加一 //月
		return month;
	}

	public String getDayOfMonth() {
		String dayOfMonth = formatTime(c.get(Calendar.DAY_OF_MONTH));
		return dayOfMonth;
	}
	public String getYMD() {
		String time = c.get(Calendar.YEAR) + "-" + // 得到年
				formatTime(c.get(Calendar.MONTH) + 1) + "-" + // month加一 //月
				formatTime(c.get(Calendar.DAY_OF_MONTH));
		return time;
	}
	private String formatTime(int t) {
		return t >= 10 ? "" + t : "0" + t;// 三元运算符 t>10时取 ""+t
	}
}
