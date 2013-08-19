package com.super8.entity;

import java.io.Serializable;

public class Record implements Serializable {
	public int id;
	public int type; // 0表示收入，1表示支出
	public double money; // 金额
	public String purpose; // 用途
	public String date; // 日期
	public String note; // 备注
}
