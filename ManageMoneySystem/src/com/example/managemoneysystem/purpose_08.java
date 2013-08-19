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
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class purpose_08 extends Activity {
	private String[] data = { "其他" };
	private GridView gv_pp_08;
	private Button btnReturn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gd_pp_08);
		initControl();
		btnReturn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();

			}
		});
		gv_pp_08.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position) {
				case 0: {
					Intent intent = new Intent();
					intent.putExtra("pp_08", data[position]);
					setResult(88, intent);
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
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.gd_item, new String[] { "tvItem01" },
				new int[] { R.id.tvItem01 });
		btnReturn = (Button) this.findViewById(R.id.btnReturn);
		gv_pp_08 = (GridView) this.findViewById(R.id.gv_pp_08);
		gv_pp_08.setAdapter(adapter);
	}

	// 数据
	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < data.length; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("tvItem01", data[i]);
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
