package com.example.managemoneysystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class purpose extends Activity {
	private String[] datas = null;
	private String[] datasIn = { "工作", "投资" };
	private String[] datasOut = { "餐饮", "交通", "购物", "娱乐", "医教", "居家", "假期",
			"其他" };

	private GridView gv_purpose;
	public String msg;
	private Button btnReturn;
	private int i;// 标识用途类型

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		msg = "";

		switch (requestCode) {
		case 1:
			switch (resultCode) {
			case 11:
				msg = datas[0] + "->" + data.getStringExtra("pp_01");
				Intent intent = new Intent();
				intent.putExtra("p1", msg);
				setResult(111, intent);
				finish();
				break;
			default:
				break;
			}
			break;
		case 2:
			switch (resultCode) {
			case 22:
				msg = datas[1] + "->" + data.getStringExtra("pp_02");
				Intent intent = new Intent();
				intent.putExtra("p2", msg);
				setResult(222, intent);
				finish();
				break;
			default:
				break;
			}
			break;
		case 3:
			switch (resultCode) {
			case 33:
				msg = datas[2] + "->" + data.getStringExtra("pp_03");
				Intent intent = new Intent();
				intent.putExtra("p3", msg);
				setResult(333, intent);
				finish();
				break;
			default:
				break;
			}
			break;
		case 4:
			switch (resultCode) {
			case 44:
				msg = datas[3] + "->" + data.getStringExtra("pp_04");
				Intent intent = new Intent();
				intent.putExtra("p4", msg);
				setResult(444, intent);
				finish();
				break;
			default:
				break;
			}
			break;
		case 5:
			switch (resultCode) {
			case 55:
				msg = datas[4] + "->" + data.getStringExtra("pp_05");
				Intent intent = new Intent();
				intent.putExtra("p5", msg);
				setResult(555, intent);
				finish();
				break;
			default:
				break;
			}
			break;
		case 6:
			switch (resultCode) {
			case 66:
				msg += datas[5] + "->" + data.getStringExtra("pp_06");
				Intent intent = new Intent();
				intent.putExtra("p6", msg);
				setResult(666, intent);
				finish();
				break;
			default:
				break;
			}
			break;
		case 7:
			switch (resultCode) {
			case 77:
				msg = datas[6] + "->" + data.getStringExtra("pp_07");
				Intent intent = new Intent();
				intent.putExtra("p7", msg);
				setResult(777, intent);
				finish();
				break;
			default:
				break;
			}
			break;
		case 8:
			msg = datas[7] + "->" + "其他";
			Intent intent = new Intent();
			intent.putExtra("p8", msg);
			setResult(888, intent);
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gd_purpose);
		i = getIntent().getExtras().getInt("type");// 标识用途类型
		if (i == 0) {
			datas = datasIn;
		} else if (i == 1) {
			datas = datasOut;
		}
		initControl(); // 初始化加载控件

		btnReturn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});

		gv_purpose.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position) {
				case 0: {
					Intent intent = new Intent(purpose.this, purpose_01.class);
					intent.putExtra("type", i);
					startActivityForResult(intent, 1);
					break;
				}
				case 1: {
					Intent intent = new Intent(purpose.this, purpose_02.class);
					intent.putExtra("type", i);
					startActivityForResult(intent, 2);
					break;
				}
				case 2: {
					Intent intent = new Intent(purpose.this, purpose_03.class);
					intent.putExtra("type", i);
					startActivityForResult(intent, 3);
					break;
				}
				case 3: {
					Intent intent = new Intent(purpose.this, purpose_04.class);
					intent.putExtra("type", i);
					startActivityForResult(intent, 4);
					break;
				}
				case 4: {
					Intent intent = new Intent(purpose.this, purpose_05.class);
					intent.putExtra("type", i);
					startActivityForResult(intent, 5);
					break;
				}
				case 5: {
					Intent intent = new Intent(purpose.this, purpose_06.class);
					intent.putExtra("type", i);
					startActivityForResult(intent, 6);
					break;
				}
				case 6: {
					Intent intent = new Intent(purpose.this, purpose_07.class);
					intent.putExtra("type", i);
					startActivityForResult(intent, 7);
					break;
				}
				case 7: {
					Intent intent = new Intent(purpose.this, purpose_08.class);
					intent.putExtra("type", i);
					startActivityForResult(intent, 8);
					break;
				}
				default:
					break;
				}
			}

		});
	}

	// 初始化加载控件
	private void initControl() {
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.gd_item, new String[] { "tvItem01" },
				new int[] { R.id.tvItem01 });
		gv_purpose = (GridView) this.findViewById(R.id.gv_purpose);
		btnReturn = (Button) this.findViewById(R.id.btnReturn);
		gv_purpose.setAdapter(adapter);
	}

	// 数据
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

		for (int j = 0; j < datas.length; j++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("tvItem01", datas[j]);
			items.add(item);
		}
		return items;
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
