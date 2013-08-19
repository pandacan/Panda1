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

public class purpose_01 extends Activity {

	private String[] datas = null;
	private String[] datasIn = { "����", "��ְ", "����", "����", "��������" };
	private String[] datasOut = { "���", "���", "���", "ҹ��", "����ˮ��", "��ʳ", "���",
			"���ν���", "��������" };
	private GridView gv_pp_01;
	private Button btnReturnP01;
	private int i;

	private TextView tvFood;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gd_pp_01);

		initControl();

		btnReturnP01.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				finish();
			}
		});
		gv_pp_01.setOnItemClickListener(new OnItemClickListener() {

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
				case 8: {
					Intent intent = new Intent();
					intent.putExtra("pp_01", datas[position]);
					setResult(11, intent);
					finish();
					break;
				}
				default:
					break;
				}
			}

		});
	}

	// ��ʼ�����ؿؼ�
	private void initControl() {

		tvFood = (TextView) this.findViewById(R.id.tvFood);
		i = getIntent().getExtras().getInt("type");// ��ʶ��;����
		if (i == 0) {
			datas = datasIn;
			tvFood.setText("����");
		} else if (i == 1) {
			datas = datasOut;
			tvFood.setText("����");
		}
		SimpleAdapter adapter = new SimpleAdapter(this, getData(),
				R.layout.gd_item, new String[] { "tvItem01" },
				new int[] { R.id.tvItem01 });

		btnReturnP01 = (Button) this.findViewById(R.id.btnReturnP01);
		gv_pp_01 = (GridView) this.findViewById(R.id.gv_pp_01);
		gv_pp_01.setAdapter(adapter);
	}

	// ����
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
			return true;// Ϊfalse��MENU����ʵ������Ӧ�еĴ��ݻ���Ч����Ϊtrue����MENU�����¼���ֹ������Ӧ���еĴ��ݣ�
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}
