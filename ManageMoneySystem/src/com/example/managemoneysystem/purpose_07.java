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

public class purpose_07 extends Activity {
	private String[] data = { "��Ʊ", "��Ʊ", "ס��", "����" };
	private GridView gv_pp_07;
	private Button btnReturn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gd_pp_07);
		initControl();
		btnReturn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();

			}
		});
		gv_pp_07.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position) {
				case 0:
				case 1:
				case 2:
				case 3: {
					Intent intent = new Intent();
					intent.putExtra("pp_07", data[position]);
					setResult(77, intent);
					finish();
					break;
				}

				default :
					break;
				}

			}

		});
	}

	// ��ʼ�����ؿؼ�
	private void initControl() {
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.gd_item, new String[] { "tvItem01" },
				new int[] { R.id.tvItem01 });
		btnReturn = (Button) this.findViewById(R.id.btnReturn);
		gv_pp_07 = (GridView) this.findViewById(R.id.gv_pp_07);
		gv_pp_07.setAdapter(adapter);
	}

	// ����
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
			return true;// Ϊfalse��MENU����ʵ������Ӧ�еĴ��ݻ���Ч����Ϊtrue����MENU�����¼���ֹ������Ӧ���еĴ��ݣ�
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}