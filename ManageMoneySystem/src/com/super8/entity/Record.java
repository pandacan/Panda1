package com.super8.entity;

import java.io.Serializable;

public class Record implements Serializable {
	public int id;
	public int type; // 0��ʾ���룬1��ʾ֧��
	public double money; // ���
	public String purpose; // ��;
	public String date; // ����
	public String note; // ��ע
}
