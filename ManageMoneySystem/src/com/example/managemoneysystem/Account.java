package com.example.managemoneysystem;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.super8.biz.Common;
import com.super8.dao.ManageMoneySystemDao;

public class Account extends Activity implements OnClickListener {

	private Button btnRecords;// ��ˮ
	private Button btnAdd; // ��һ��
	private TextView tvMonth;// ��ǰ�·�
	private TextView tvOutgoingInfo;// ֧�����
	private TextView tvIncomeInfo; // ����
	private TextView tvBalanceInfo; // ���

	private TextView tvTodayOutgoing;// ����֧��
	private TextView tvTodayIncome; // ��������
	private TextView tvTodayBalance; // ����ӯ��
	private double TodayOutgoing;// ����֧��
	private double TodayIncome; // ��������
	private double TodayBalance; // ����ӯ��
	private ManageMoneySystemDao moneydao;
	Common common;

	@Override
	protected void onResume() {
		super.onResume();
		setAllText();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);

		common = new Common();
		btnAdd = (Button) this.findViewById(R.id.btnAdd);
		btnRecords = (Button) this.findViewById(R.id.btnRecords);
		tvMonth = (TextView) this.findViewById(R.id.tvMonth);
		tvOutgoingInfo = (TextView) this.findViewById(R.id.tvOutgoingInfo);
		tvIncomeInfo = (TextView) this.findViewById(R.id.tvIncomeInfo);
		tvBalanceInfo = (TextView) this.findViewById(R.id.tvBalanceInfo);

		tvTodayOutgoing = (TextView) this.findViewById(R.id.tvTodayOutgoing);
		tvTodayIncome = (TextView) this.findViewById(R.id.tvTodayIncome);
		tvTodayBalance = (TextView) this.findViewById(R.id.tvTodayBalance);
		moneydao = new ManageMoneySystemDao(this);
		btnAdd.setOnClickListener(this);
		btnRecords.setOnClickListener(this);

		setAllText();

	}

	// �����¼�
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btnAdd: {
			intent = new Intent(this, AddActivity.class);
			startActivity(intent);
			break;
		}
		case R.id.btnRecords: {
			intent = new Intent(this, Water.class);
			startActivity(intent);
			break;
		}
		default:
			break;
		}
	}

	// ��������
	private CharSequence setIncome() {
		TodayIncome = moneydao.searchbyYMD(common.getYear(),
				Integer.parseInt(common.getMONTH()),
				Integer.parseInt(common.getDayOfMonth()), 0);

		String str = "<font color='#0000FF00'>" + "��������:" + "</font>"
				+ "						" + "<font color='#0000FF00'><big>" + "��"
				+ TodayIncome + "</big></font><br>" + "<small>" + "	"
				+ common.getYMD() + "</small>";
		CharSequence charSequence = Html.fromHtml(str);
		return charSequence;

	}

	// ����֧��
	private CharSequence setOutgoing() {
		TodayOutgoing = moneydao.searchbyYMD(common.getYear(),
				Integer.parseInt(common.getMONTH()),
				Integer.parseInt(common.getDayOfMonth()), 1);
		String str = "<font color='#00FF0000'>" + "����֧��:" + "</font>"
				+ "						" + "<font color='#00FF0000'><big>" + "��"
				+ TodayOutgoing + "</big></font><br>" + "<small>" + "	"
				+ common.getYMD() + "</small>";
		CharSequence charSequence = Html.fromHtml(str);
		return charSequence;
	}

	// ����ӯ��
	private CharSequence setBalance() {
		TodayBalance = TodayIncome - TodayOutgoing;
		String str = "<font color='#000000FF'>" + "����ӯ��:" + "</font>"
				+ "						" + "<font color='#000000FF'><big>" + "��"
				+ TodayBalance + "</big></font><br>" + "<small>" + "	"
				+ common.getYMD() + "</small>";
		CharSequence charSequence = Html.fromHtml(str);
		return charSequence;
	}

	private void setAllText() {
		Calendar c = Calendar.getInstance();
		tvMonth.setText(c.get(Calendar.MONTH) + 1 + "��");// ��ʾ�·�
		Double moneyIn = moneydao.searchbyYM(c.get(Calendar.YEAR),
				c.get(Calendar.MONTH) + 1, 0);
		tvIncomeInfo.setText("��" + moneyIn);// ��ʾ����
		Double moneyOut = moneydao.searchbyYM(c.get(Calendar.YEAR),
				c.get(Calendar.MONTH) + 1, 1);
		tvOutgoingInfo.setText("��" + moneyOut);// ��ʾ֧��
		tvBalanceInfo.setText("��" + (moneyIn - moneyOut));// ��ʾ���

		tvTodayOutgoing.setText(setOutgoing());
		tvTodayIncome.setText(setIncome());
		tvTodayBalance.setText(setBalance());
	}

}
