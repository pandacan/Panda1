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
import android.widget.TextView;

public class purpose_02 extends Activity {
	private String[] datas = null;
	private String[] datasIn = { "股票", "基金", "分红", "利息", "投资其他" };
	private String[] datasOut = { "打的", "公交大巴", "地铁", "加油", "停车费", "过桥路费",
			"罚款赔偿", "保养修理", "火车票", "车款车贷", "机票", "船票", "自行车", "交通其他" };

	private GridView gv_pp_02;
	private Button btnReturnP02;
	private TextView tvTransp;
	private int i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gd_pp_02);

		initControl();

		btnReturnP02.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();

			}
		});
		gv_pp_02.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
				case 9:
				case 10:
				case 11:
				case 12:
				case 13: {
					Intent intent = new Intent();
					intent.putExtra("pp_02", datas[position]);
					setResult(22, intent);
					finish();
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

		tvTransp = (TextView) this.findViewById(R.id.tvTransp);
		i = getIntent().getExtras().getInt("type");// 标识用途类型
		if (i == 0) {
			tvTransp.setText("投资");
			datas = datasIn;
		} else if (i == 1) {
			tvTransp.setText("交通");
			datas = datasOut;
		}

		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.gd_item, new String[] { "tvItem01" },
				new int[] { R.id.tvItem01 });
		btnReturnP02 = (Button) this.findViewById(R.id.btnReturnP02);
		gv_pp_02 = (GridView) this.findViewById(R.id.gv_pp_02);
		gv_pp_02.setAdapter(adapter);
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
