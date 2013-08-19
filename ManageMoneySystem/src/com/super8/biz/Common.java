package com.super8.biz;

import java.util.Calendar;

public class Common {
	Calendar c = Calendar.getInstance();

	public String getDate() {
		String time = c.get(Calendar.YEAR) + "-" + // �õ���
				formatTime(c.get(Calendar.MONTH) + 1) + "-" + // month��һ //��
				formatTime(c.get(Calendar.DAY_OF_MONTH)) + " " + // ��
				formatTime(c.get(Calendar.HOUR_OF_DAY)) + ":" + // ʱ
				formatTime(c.get(Calendar.MINUTE)); // ��
		return time;
	}

	public int getYear() {
		int year = c.get(Calendar.YEAR);// �õ���
		return year;
	}

	public String getMONTH() {
		String month = formatTime(c.get(Calendar.MONTH) + 1);// month��һ //��
		return month;
	}

	public String getDayOfMonth() {
		String dayOfMonth = formatTime(c.get(Calendar.DAY_OF_MONTH));
		return dayOfMonth;
	}
	public String getYMD() {
		String time = c.get(Calendar.YEAR) + "-" + // �õ���
				formatTime(c.get(Calendar.MONTH) + 1) + "-" + // month��һ //��
				formatTime(c.get(Calendar.DAY_OF_MONTH));
		return time;
	}
	private String formatTime(int t) {
		return t >= 10 ? "" + t : "0" + t;// ��Ԫ����� t>10ʱȡ ""+t
	}
}
