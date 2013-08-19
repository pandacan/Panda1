package com.example.managemoneysystem;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.super8.biz.Common;
import com.super8.biz.RecordBiz;
import com.super8.dao.ManageMoneySystemDao;
import com.super8.entity.Budget;
import com.super8.entity.Record;

public class BudgetActivity extends Activity implements OnClickListener {

	private Button btnBudgetMonth; // 预算月份
	private Button btnAddBudget; // 添加预算
	private TextView tvAllBudgetInfo; // 总预算
	private TextView tvBudgetPayoutInfo; // 消费
	private TextView tvBudgetBalanceInfo;// 余额
	private ImageView ivBudget; // 预算图像显示
	private ListView lvBudget; // ListView显示预算信息
	private Boolean flag = true;
	Common common;
	private String dateMsg = "";// 预算月份信息
	private String[] date;
	private double onePay;// 某条预算的消费
	String regex = "\\D+";
	private LayoutInflater inflater;
	RecordBiz biz;
	private List<Budget> budgets;
	ManageMoneySystemDao dao;
	private BudgetAdapter budgetadapter;
	private double all;
	private double pay;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 124:// 时间设置
			dateMsg = data.getStringExtra("dateMsg");
			date = dateMsg.split(regex);
			btnBudgetMonth.setText(date[0] + "-" + date[1]);
			break;
		default:
			break;
		}
	}

	// 实时更新
	@Override
	protected void onResume() {
		super.onResume();
		btnBudgetMonth.setText(date[0] + "-" + date[1]);
		displayLvBudget(Integer.parseInt(date[0]), Integer.parseInt(date[1]));
		setThreeTV();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_budget);
		common = new Common();
		date = new String[5];
		date[0] = common.getYear() + "";
		date[1] = common.getMONTH();
		// 绑定ID
		btnBudgetMonth = (Button) this.findViewById(R.id.btnBudgetMonth);
		btnAddBudget = (Button) this.findViewById(R.id.btnAddBudget);
		tvAllBudgetInfo = (TextView) this.findViewById(R.id.tvAllBudgetInfo);
		tvBudgetPayoutInfo = (TextView) this
				.findViewById(R.id.tvBudgetPayoutInfo);
		tvBudgetBalanceInfo = (TextView) this
				.findViewById(R.id.tvBudgetBalanceInfo);
		ivBudget = (ImageView) this.findViewById(R.id.ivBudget);
		lvBudget = (ListView) this.findViewById(R.id.lvBudget);

		btnBudgetMonth.setText(date[0] + "-" + date[1]);// 默认当前年月
		// 单击
		btnBudgetMonth.setOnClickListener(this);
		btnAddBudget.setOnClickListener(this);

		dao = new ManageMoneySystemDao(this);
		displayLvBudget(Integer.parseInt(date[0]), Integer.parseInt(date[1]));
		// 显示总预算，消费，余额,图片
		setThreeTV();

	}

	// 显示总预算，消费，余额
	private void setThreeTV() {

		String mo[] = btnBudgetMonth.getText().toString().split("\\D+");

		tvAllBudgetInfo.setText(""
				+ dao.allbudget(Integer.parseInt(mo[0]),
						Integer.parseInt(mo[1])));

		tvBudgetPayoutInfo.setText(dao.expensebypurpose(
				Integer.parseInt(mo[0]), Integer.parseInt(mo[1])) + "");

		all = Double.parseDouble(tvAllBudgetInfo.getText().toString());
		pay = Double.parseDouble(tvBudgetPayoutInfo.getText().toString());
		tvBudgetBalanceInfo.setText(all - pay + "");
		setIVBudget(all, pay);
	}

	// ListView显示
	private void displayLvBudget(int year, int month) {
		budgets = dao.searchBudgetbydate(year, month);
		inflater = this.getLayoutInflater();
		lvBudget = (ListView) this.findViewById(R.id.lvBudget);
		budgetadapter = new BudgetAdapter(budgets, this);
		lvBudget.setAdapter(budgetadapter);
		lvBudget.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Budget budget = budgets.get(arg2);
				Intent intent = new Intent(BudgetActivity.this,
						SettingBudget.class);
				intent.putExtra("id", budget.id);
				intent.putExtra("startyear", budget.startyear);
				intent.putExtra("endyear", budget.endyear);
				intent.putExtra("money", budget.money);
				intent.putExtra("purpose", budget.purpose);
				intent.putExtra("flag", flag);
				intent.putExtra("onePay", onePay);
				startActivity(intent);
			}
		});
	}

	// 按钮单击事件
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnAddBudget:
			// 跳转到设置预算界面
			Intent intent = new Intent(BudgetActivity.this, SettingBudget.class);
			startActivityForResult(intent, 123);
			break;
		case R.id.btnBudgetMonth:
			// 弹出日期选择框，设置预算日期
			Intent intent2 = new Intent(BudgetActivity.this, dateActivity.class);
			startActivityForResult(intent2, 124);
			break;

		default:
			break;
		}

	}

	private class BudgetAdapter extends BaseAdapter {
		List<Budget> budgets;
		Context context;
		String mmoo[] = btnBudgetMonth.getText().toString().split("\\D+");

		public BudgetAdapter(List<Budget> budgets, Context context) {
			this.budgets = budgets;
			this.context = context;
		}

		public int getCount() {
			return budgets.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LinearLayout view = (LinearLayout) inflater.inflate(
					R.layout.budget_template, null);
			final Budget budget = budgets.get(position);
			TextView tvpurpose = (TextView) view.findViewById(R.id.tvBApurpose);
			TextView tvBAllMoney = (TextView) view
					.findViewById(R.id.tvBAllMoney);
			TextView tvBOneBalance = (TextView) view
					.findViewById(R.id.tvBOneBalance);

			ProgressBar bar = (ProgressBar) view
					.findViewById(R.id.progressBar1);
			tvpurpose.setText(budget.purpose);
			// 测试数据
			tvBAllMoney.setText("预算：" + budget.money);
			onePay = dao.expensebyonepurpose(budget.purpose,
					Integer.parseInt(mmoo[0]), Integer.parseInt(mmoo[1]));
			tvBOneBalance.setText("余额：" + (budget.money - onePay));
			bar.setProgress((int) ((budget.money - onePay) / budget.money * 100));
			return view;
		}
	}

	// 根据 余额/总预算，改变图片
	private void setIVBudget(double all, double pay) {
		if (pay >= all) {
			ivBudget.setImageResource(R.drawable.budget_empty);
		} else {
			double i = (all - pay) / all * 100;
			if (i > 0 && i < 30) {
				ivBudget.setImageResource(R.drawable.budget_leave_low_30);
			} else if (i >= 30 && i < 60) {
				ivBudget.setImageResource(R.drawable.budget_leave_between_30_60);
			} else if (i >= 60 && i < 100) {
				ivBudget.setImageResource(R.drawable.budget_leave_between_60_100);
			} else if (i == 100) {
				ivBudget.setImageResource(R.drawable.budget_leave_full);
			}
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;// 为false则MENU按键实践在响应中的传递还有效，若为true，则MENU按键事件终止了在响应链中的传递！
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}
