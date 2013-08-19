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

	private Button btnRecords;// 流水
	private Button btnAdd; // 记一笔
	private TextView tvMonth;// 当前月份
	private TextView tvOutgoingInfo;// 支出金额
	private TextView tvIncomeInfo; // 收入
	private TextView tvBalanceInfo; // 余额

	private TextView tvTodayOutgoing;// 今日支出
	private TextView tvTodayIncome; // 今日收入
	private TextView tvTodayBalance; // 今日盈余
	private double TodayOutgoing;// 今日支出
	private double TodayIncome; // 今日收入
	private double TodayBalance; // 今日盈余
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

	// 单击事件
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

	// 今日收入
	private CharSequence setIncome() {
		TodayIncome = moneydao.searchbyYMD(common.getYear(),
				Integer.parseInt(common.getMONTH()),
				Integer.parseInt(common.getDayOfMonth()), 0);

		String str = "<font color='#0000FF00'>" + "今日收入:" + "</font>"
				+ "						" + "<font color='#0000FF00'><big>" + "￥"
				+ TodayIncome + "</big></font><br>" + "<small>" + "	"
				+ common.getYMD() + "</small>";
		CharSequence charSequence = Html.fromHtml(str);
		return charSequence;

	}

	// 今日支出
	private CharSequence setOutgoing() {
		TodayOutgoing = moneydao.searchbyYMD(common.getYear(),
				Integer.parseInt(common.getMONTH()),
				Integer.parseInt(common.getDayOfMonth()), 1);
		String str = "<font color='#00FF0000'>" + "今日支出:" + "</font>"
				+ "						" + "<font color='#00FF0000'><big>" + "￥"
				+ TodayOutgoing + "</big></font><br>" + "<small>" + "	"
				+ common.getYMD() + "</small>";
		CharSequence charSequence = Html.fromHtml(str);
		return charSequence;
	}

	// 今日盈余
	private CharSequence setBalance() {
		TodayBalance = TodayIncome - TodayOutgoing;
		String str = "<font color='#000000FF'>" + "今日盈余:" + "</font>"
				+ "						" + "<font color='#000000FF'><big>" + "￥"
				+ TodayBalance + "</big></font><br>" + "<small>" + "	"
				+ common.getYMD() + "</small>";
		CharSequence charSequence = Html.fromHtml(str);
		return charSequence;
	}

	private void setAllText() {
		Calendar c = Calendar.getInstance();
		tvMonth.setText(c.get(Calendar.MONTH) + 1 + "月");// 显示月份
		Double moneyIn = moneydao.searchbyYM(c.get(Calendar.YEAR),
				c.get(Calendar.MONTH) + 1, 0);
		tvIncomeInfo.setText("￥" + moneyIn);// 显示收入
		Double moneyOut = moneydao.searchbyYM(c.get(Calendar.YEAR),
				c.get(Calendar.MONTH) + 1, 1);
		tvOutgoingInfo.setText("￥" + moneyOut);// 显示支出
		tvBalanceInfo.setText("￥" + (moneyIn - moneyOut));// 显示余额

		tvTodayOutgoing.setText(setOutgoing());
		tvTodayIncome.setText(setIncome());
		tvTodayBalance.setText(setBalance());
	}

}
