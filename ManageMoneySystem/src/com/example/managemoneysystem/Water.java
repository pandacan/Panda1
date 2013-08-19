package com.example.managemoneysystem;

import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.super8.biz.Common;
import com.super8.biz.RecordBiz;
import com.super8.dao.ManageMoneySystemDao;
import com.super8.entity.Record;

public class Water extends Activity implements OnClickListener {

	private Button btnWReturn;
	private Button btnAddYear;
	private Button btnReduceYear;
	private Button btnOutgoing;
	private Button btnIncoming;
	private TextView tvyear;
	private TextView tvYear;
	private ListView listview;

	private LayoutInflater inflater;
	private List<Record> records;
	private RecordBiz rbiz;
	private ContentResolver resolver;
	private ManageMoneySystemDao mdao;
	RecordAdapter readapter;
	private int year;
	private int type = 1;
	Common common;
	private double outmoney = 0;
	private double inmoney = 0;
	private double balance = 0;

	@Override
	protected void onResume() {
		super.onResume();
		display(type);
		tvyear.setText(setTextView1(year));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.run_water2);

		btnWReturn = (Button) this.findViewById(R.id.btnWReturn);
		btnAddYear = (Button) this.findViewById(R.id.btnAddYear);
		btnReduceYear = (Button) this.findViewById(R.id.btnReduceYear);
		btnOutgoing = (Button) this.findViewById(R.id.btnOutgoing);
		btnIncoming = (Button) this.findViewById(R.id.btnIncoming);
		tvyear = (TextView) this.findViewById(R.id.textView1);
		tvYear = (TextView) this.findViewById(R.id.textView2);
		listview = (ListView) this.findViewById(R.id.listView1);

		btnOutgoing.setBackgroundResource(R.drawable.btn_wtype_focus);
		btnIncoming.setBackgroundResource(R.drawable.btn_wtype_unfocus);

		btnWReturn.setOnClickListener(this);
		btnAddYear.setOnClickListener(this);
		btnReduceYear.setOnClickListener(this);
		btnOutgoing.setOnClickListener(this);
		btnIncoming.setOnClickListener(this);
		display(type);
		common = new Common();
		year = common.getYear();
		mdao = new ManageMoneySystemDao(this);
		tvyear.setText(setTextView1(year));

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnWReturn:
			finish();
			break;
		case R.id.btnAddYear:
			++year;
			display(type);
			tvyear.setText(setTextView1(year));
			break;
		case R.id.btnReduceYear:
			--year;
			display(type);
			tvyear.setText(setTextView1(year));
			break;

		case R.id.btnOutgoing:
			type = 1;
			btnOutgoing.setBackgroundResource(R.drawable.btn_wtype_focus);
			btnIncoming.setBackgroundResource(R.drawable.btn_wtype_unfocus);
			display(type);
			break;

		case R.id.btnIncoming:
			type = 0;
			btnIncoming.setBackgroundResource(R.drawable.btn_wtype_focus);
			btnOutgoing.setBackgroundResource(R.drawable.btn_wtype_unfocus);
			display(type);
			break;
		default:
			break;
		}
	}

	private class RecordAdapter extends BaseAdapter {
		List<Record> records;
		Context context;

		public RecordAdapter(List<Record> records, Context context) {
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

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			LinearLayout view = (LinearLayout) inflater.inflate(
					R.layout.template, null);
			final Record record = records.get(position);
			TextView tvdate = (TextView) view.findViewById(R.id.tvdate);
			TextView tvmoney = (TextView) view.findViewById(R.id.tvmoney);
			TextView tvpurpose = (TextView) view.findViewById(R.id.tvpurpose);
			tvdate.setText(record.date);
			tvmoney.setText("￥"+record.money);
			tvpurpose.setText(record.purpose);
			return view;
		}
	}

	// 当year变化时,更新ListView
	private void display(int type) {

		rbiz = new RecordBiz(this);
		mdao = new ManageMoneySystemDao(this);
		resolver = this.getContentResolver();
		inflater = this.getLayoutInflater();
		listview = (ListView) this.findViewById(R.id.listView1);
		records = mdao.searchbyyear(year, type);
		readapter = new RecordAdapter(records, this);
		listview.setAdapter(readapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Record rd = records.get(arg2);
				Intent intent = new Intent(Water.this, Particular.class);
				intent.putExtra("id", rd.id);
				intent.putExtra("data", rd.date);
				intent.putExtra("note", rd.note);
				intent.putExtra("money", rd.money);
				intent.putExtra("purpose", rd.purpose);
				intent.putExtra("type", rd.type);
				startActivity(intent);
			}
		});
	}

	// 走马灯显示
	private CharSequence setTextView1(int year) {
		outmoney = mdao.searchbyYMD(year, 1, 0);
		inmoney = mdao.searchbyYMD(year, 0, 0);
		balance = inmoney - outmoney;
		tvYear.setText(year + "");

		String str = "	<big>" + year + "</big>" + "<big>" + ": 收: " + "</big>"
				+ "<font color = '#0000FF00'>" + inmoney + "</font>" + "<big>"
				+ " 支: " + "</big>" + "<font color = '#00FF0000'>" + outmoney
				+ "</font>" + "<big>" + " 余: " + "</big>"
				+ "<font color = '#000000FF'>" + balance + "</font>";

		CharSequence charSequence = Html.fromHtml(str);
		return charSequence;
	}
}
