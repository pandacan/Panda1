package com.example.managemoneysystem;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.super8.biz.Common;
import com.super8.dao.ManageMoneySystemDao;
import com.super8.entity.Record;

public class ReportActivity extends Activity implements OnClickListener {

	private TextView tvFLastDate;// ��һ��ʱ��
	private TextView tvFNextDate;// ��һ��ʱ��
	private TextView tvFIN;// ��
	private TextView tvFOut;// ֧
	private TextView tvFTotal;// ��
	private ListView lvpro;// ��ʾ��״ͼ
	private ListView lvPPname;// ��ʾ��������
	private Button btnFReturn;
	private String dateMsg;
	private String regex = "\\D+";
	private ManageMoneySystemDao moneydao;
	private Common common;
	private LayoutInflater inflater;
	private List<Record> records;
	private List<Record> recordsA;
	private LvPPnameAdapter lvPPnameAdapter;
	private LvproAdapter lvproAdapter;
	private double moneyIn;// ��
	private double moneyOut;// ֧
	private double onePPMoney;// ĳ��purpose��money

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {

		case 235:
			dateMsg = data.getStringExtra("dateMsg");
			String date5[] = dateMsg.split(regex);
			tvFLastDate.setText(date5[0] + "-" + date5[1]);

			setIOT(tvFLastDate.getText() + "", tvFNextDate.getText() + "");// ��ʾ��Ļ�·�����֧����Ϣ

			displayLvPPname(tvFLastDate.getText() + "", tvFNextDate.getText()
					+ "");// ��ʾ���ListView
			displayLvpro(records);// ��ʾ�Ҳ���״ͼ
			break;
		case 236:
			dateMsg = data.getStringExtra("dateMsg");
			String date6[] = dateMsg.split(regex);
			tvFNextDate.setText(date6[0] + "-" + date6[1]);

			setIOT(tvFLastDate.getText() + "", tvFNextDate.getText() + "");// ��ʾ��Ļ�·�����֧����Ϣ
			displayLvPPname(tvFLastDate.getText() + "", tvFNextDate.getText()
					+ "");// ��ʾ���ListView
			displayLvpro(records);// ��ʾ�Ҳ���״ͼ
			break;
		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);

		common = new Common();
		moneydao = new ManageMoneySystemDao(this);

		records = new ArrayList<Record>();

		tvFLastDate = (TextView) this.findViewById(R.id.tvFLastDate);
		tvFNextDate = (TextView) this.findViewById(R.id.tvFNextDate);
		tvFIN = (TextView) this.findViewById(R.id.tvFIN);
		tvFOut = (TextView) this.findViewById(R.id.tvFOut);
		tvFTotal = (TextView) this.findViewById(R.id.tvFTotal);
		btnFReturn = (Button) this.findViewById(R.id.btnFReturn);
		lvpro = (ListView) this.findViewById(R.id.lvpro);
		lvPPname = (ListView) this.findViewById(R.id.lvppname);

		tvFLastDate.setText(common.getYear() + "-" + common.getMONTH());// Ĭ�ϵ�ǰ����
		tvFNextDate.setText(common.getYear() + "-" + common.getMONTH());// Ĭ�ϵ�ǰ����

		tvFLastDate.setOnClickListener(this);
		tvFNextDate.setOnClickListener(this);
		btnFReturn.setOnClickListener(this);

		setIOT(tvFLastDate.getText() + "", tvFNextDate.getText() + "");// ��ʾ��Ļ�·�����֧����Ϣ
		displayLvPPname(tvFLastDate.getText() + "", tvFNextDate.getText() + "");// ��ʾ���ListView

		displayLvpro(records);// ��ʾ�Ҳ���״ͼ

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnFReturn:
			finish();
			break;
		case R.id.tvFLastDate:
			// ��������ѡ�����
			Intent intent5 = new Intent(this, dateActivity.class);
			startActivityForResult(intent5, 235);
			break;
		case R.id.tvFNextDate:
			// ��������ѡ�����
			Intent intent6 = new Intent(this, dateActivity.class);
			startActivityForResult(intent6, 236);
			break;
		default:
			break;
		}
	}

	// ˢ��
	@Override
	protected void onResume() {
		super.onResume();

		setIOT(tvFLastDate.getText() + "", tvFNextDate.getText() + "");// ��ʾ��Ļ�·�����֧����Ϣ
		displayLvPPname(tvFLastDate.getText() + "", tvFNextDate.getText() + "");// ��ʾ���ListView
		displayLvpro(records);// ��ʾ�Ҳ���״ͼ
	}

	// ������֧��¼��date,��ʾ��Ļ�·�����֧����Ϣ
	private void setIOT(String lastDateMsg, String nextDateMsg) {
		String[] lastDate = lastDateMsg.split(regex);
		String[] nextDate = nextDateMsg.split(regex);
		moneyIn = moneydao.searchRecordByDate(Integer.parseInt(lastDate[0]),
				Integer.parseInt(lastDate[1]), Integer.parseInt(nextDate[0]),
				Integer.parseInt(nextDate[1]), 0);
		tvFIN.setText("�գ�" + moneyIn);// ��
		moneyOut = moneydao.searchRecordByDate(Integer.parseInt(lastDate[0]),
				Integer.parseInt(lastDate[1]), Integer.parseInt(nextDate[0]),
				Integer.parseInt(nextDate[1]), 1);
		tvFOut.setText("֧��" + moneyOut);// ֧
		tvFTotal.setText("�ϣ�" + (moneyIn - moneyOut) + "");// ��
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

	private List<Record> getRecords(List<Record> recordsA) {
		List<Record> records = new ArrayList<Record>();

		List<String> strPurpose = new ArrayList<String>();

		for (int i = 0; i < recordsA.size(); i++) {
			Log.i("recordsA.get(i)",recordsA.get(i).purpose);
			strPurpose.add(recordsA.get(i).purpose);
		}

		// ȥ���ظ�Ԫ��
		Set<String> set = new HashSet<String>(strPurpose);

		// ���list�е�����
		strPurpose.clear();
		// �����������
		strPurpose.addAll(set);

		String str;
		Record rdB;
		
		for (int i = 0; i < strPurpose.size(); i++) {
			str = strPurpose.get(i);
			rdB = new Record();
			rdB.purpose = str;
			for (int j = 0; j < recordsA.size(); j++) {
				Record rdA = recordsA.get(j);
				if (str.equals(rdA.purpose)) {
					rdB.money += rdA.money;
				}
			}
			if ((int) rdB.money != 0) {
				records.add(rdB);
			}
		}
		return records;
	}

	// lvPPname���������ʾ
	private void displayLvPPname(String lastDateMsg, String nextDateMsg) {
		String[] lastDate = lastDateMsg.split(regex);
		String[] nextDate = nextDateMsg.split(regex);
		recordsA = moneydao.searchRecordByDate(Integer.parseInt(lastDate[0]),
				Integer.parseInt(lastDate[1]), Integer.parseInt(nextDate[0]),
				Integer.parseInt(nextDate[1]));

		if (recordsA.size() != 0) {
			records = getRecords(recordsA);
			inflater = this.getLayoutInflater();
			lvPPname = (ListView) this.findViewById(R.id.lvppname);
			lvPPnameAdapter = new LvPPnameAdapter(records, this);
			lvPPname.setAdapter(lvPPnameAdapter);
		}

	}

	private class LvPPnameAdapter extends BaseAdapter {
		List<Record> records;
		Context context;

		public LvPPnameAdapter(List<Record> records, Context context) {
			this.records = records;
			this.context = context;
		}

		public int getCount() {
			return records.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout view = (LinearLayout) inflater.inflate(
					R.layout.report_lvppname, null);
			final Record rd = records.get(position);
			TextView tvRlvPP = (TextView) view.findViewById(R.id.tvRlvPP);
			TextView tvRlvPPNum = (TextView) view.findViewById(R.id.tvRlvPPNum);

			String oo[] = rd.purpose.split(">");
			tvRlvPP.setText(oo[1]);
			double yy = rd.money / (moneyIn + moneyOut);

			// ��ȡ��ʽ������
			NumberFormat nt = NumberFormat.getPercentInstance();
			// ���ðٷ�����ȷ��2��������λС��
			nt.setMinimumFractionDigits(2);
			tvRlvPPNum.setText(nt.format(yy));// �������� �ĳɶ�̬����

			return view;
		}
	}

	// lvpro�Ҳ���״ͼ��ʾ
	private void displayLvpro(List<Record> records) {
		if (records.size() != 0) {
			inflater = this.getLayoutInflater();
			lvpro = (ListView) this.findViewById(R.id.lvpro);
			lvproAdapter = new LvproAdapter(records, this);
			lvpro.setAdapter(lvproAdapter);
		}

	}

	private class LvproAdapter extends BaseAdapter {
		List<Record> records;
		Context context;

		String[] lastDate = ("" + tvFLastDate.getText()).split("\\D+");
		String[] nextDate = ("" + tvFNextDate.getText()).split("\\D+");

		public LvproAdapter(List<Record> records, Context context) {
			this.records = records;
			this.context = context;
		}

		public int getCount() {
			return records.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			LinearLayout view = (LinearLayout) inflater.inflate(
					R.layout.report_lvpro, null);

			TextView tvReportMoney = (TextView) view
					.findViewById(R.id.tvReportMoney);
			ProgressBar pbReport = (ProgressBar) view
					.findViewById(R.id.pbReport);
			onePPMoney = records.get(position).money;
			tvReportMoney.setText(onePPMoney + "");
			pbReport.setProgress((int) (onePPMoney / (moneyIn + moneyOut) * 10000));
			return view;
		}
	}

}
