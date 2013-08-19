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

	private Button btnBudgetMonth; // Ԥ���·�
	private Button btnAddBudget; // ���Ԥ��
	private TextView tvAllBudgetInfo; // ��Ԥ��
	private TextView tvBudgetPayoutInfo; // ����
	private TextView tvBudgetBalanceInfo;// ���
	private ImageView ivBudget; // Ԥ��ͼ����ʾ
	private ListView lvBudget; // ListView��ʾԤ����Ϣ
	private Boolean flag = true;
	Common common;
	private String dateMsg = "";// Ԥ���·���Ϣ
	private String[] date;
	private double onePay;// ĳ��Ԥ�������
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
		case 124:// ʱ������
			dateMsg = data.getStringExtra("dateMsg");
			date = dateMsg.split(regex);
			btnBudgetMonth.setText(date[0] + "-" + date[1]);
			break;
		default:
			break;
		}
	}

	// ʵʱ����
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
		// ��ID
		btnBudgetMonth = (Button) this.findViewById(R.id.btnBudgetMonth);
		btnAddBudget = (Button) this.findViewById(R.id.btnAddBudget);
		tvAllBudgetInfo = (TextView) this.findViewById(R.id.tvAllBudgetInfo);
		tvBudgetPayoutInfo = (TextView) this
				.findViewById(R.id.tvBudgetPayoutInfo);
		tvBudgetBalanceInfo = (TextView) this
				.findViewById(R.id.tvBudgetBalanceInfo);
		ivBudget = (ImageView) this.findViewById(R.id.ivBudget);
		lvBudget = (ListView) this.findViewById(R.id.lvBudget);

		btnBudgetMonth.setText(date[0] + "-" + date[1]);// Ĭ�ϵ�ǰ����
		// ����
		btnBudgetMonth.setOnClickListener(this);
		btnAddBudget.setOnClickListener(this);

		dao = new ManageMoneySystemDao(this);
		displayLvBudget(Integer.parseInt(date[0]), Integer.parseInt(date[1]));
		// ��ʾ��Ԥ�㣬���ѣ����,ͼƬ
		setThreeTV();

	}

	// ��ʾ��Ԥ�㣬���ѣ����
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

	// ListView��ʾ
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

	// ��ť�����¼�
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnAddBudget:
			// ��ת������Ԥ�����
			Intent intent = new Intent(BudgetActivity.this, SettingBudget.class);
			startActivityForResult(intent, 123);
			break;
		case R.id.btnBudgetMonth:
			// ��������ѡ�������Ԥ������
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
			// ��������
			tvBAllMoney.setText("Ԥ�㣺" + budget.money);
			onePay = dao.expensebyonepurpose(budget.purpose,
					Integer.parseInt(mmoo[0]), Integer.parseInt(mmoo[1]));
			tvBOneBalance.setText("��" + (budget.money - onePay));
			bar.setProgress((int) ((budget.money - onePay) / budget.money * 100));
			return view;
		}
	}

	// ���� ���/��Ԥ�㣬�ı�ͼƬ
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
			return true;// Ϊfalse��MENU����ʵ������Ӧ�еĴ��ݻ���Ч����Ϊtrue����MENU�����¼���ֹ������Ӧ���еĴ��ݣ�
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}
