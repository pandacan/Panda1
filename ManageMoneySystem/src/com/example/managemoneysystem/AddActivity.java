package com.example.managemoneysystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.super8.biz.Common;
import com.super8.biz.RecordBiz;
import com.super8.entity.Record;

public class AddActivity extends Activity implements OnClickListener {

	private Common common;

	private Button btnOutgoing;// 支出
	private Button btnIncoming;// 收入
	private Button btnReturn; // 返回
	private Button btnSave; // 保存
	private Button btnAddAgain; // 再记一笔
	private EditText etPurpose; // 用途
	private EditText etNote; // 备注
	private EditText etDate; // 日期
	private EditText etInitial; // 金额
	private TextView tvPurpose;

	private String[] ppMsg = new String[2];// 存放用途信息，ppMsg[0]存放收入,ppMsg[1]存放支出
	private boolean checkOut; // 标记支出按钮
	private boolean checkIn; // 标记收入按钮
	private int i; // type
	private String dateMsg;
	private RecordBiz rbiz;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (checkOut == true) {
			i = 1;
			reppMsg(requestCode, resultCode, i, data);
		}
		if (checkIn == true) {
			i = 0;
			reppMsg(requestCode, resultCode, i, data);
		}
	}

	// 将选择的用途信息存放在数组ppMsg中
	private void reppMsg(int requestCode, int resultCode, int i, Intent data) {

		ppMsg[i] = "";
		switch (requestCode) {
		case 0:
			switch (resultCode) {
			case 111:
				ppMsg[i] = data.getStringExtra("p1");
				etPurpose.setText(ppMsg[i]);
				break;
			case 222:
				ppMsg[i] = data.getStringExtra("p2");
				etPurpose.setText(ppMsg[i]);
				break;
			case 333:
				ppMsg[i] = data.getStringExtra("p3");
				etPurpose.setText(ppMsg[i]);
				break;
			case 444:
				ppMsg[i] = data.getStringExtra("p4");
				etPurpose.setText(ppMsg[i]);
				break;
			case 555:
				ppMsg[i] = data.getStringExtra("p5");
				etPurpose.setText(ppMsg[i]);
				break;
			case 666:
				ppMsg[i] = data.getStringExtra("p6");
				etPurpose.setText(ppMsg[i]);
				break;
			case 777:
				ppMsg[i] = data.getStringExtra("p7");
				etPurpose.setText(ppMsg[i]);
				break;
			case 888:
				ppMsg[i] = data.getStringExtra("p8");
				etPurpose.setText(ppMsg[i]);
				break;
			default:
				break;
			}
			break;
		case 1:
			dateMsg = data.getStringExtra("dateMsg");
			etDate.setText(dateMsg);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		i = 1;
		checkOut = true;
		checkIn = false;
		common = new Common();
		rbiz = new RecordBiz(this);

		etPurpose = (EditText) this.findViewById(R.id.etPurpose);
		etNote = (EditText) this.findViewById(R.id.etNote);
		etDate = (EditText) this.findViewById(R.id.etDate);
		etInitial = (EditText) this.findViewById(R.id.etInitial);
		tvPurpose = (TextView) this.findViewById(R.id.tvPurpose);

		btnReturn = (Button) this.findViewById(R.id.btnReturn);
		btnSave = (Button) this.findViewById(R.id.btnSave);
		btnAddAgain = (Button) this.findViewById(R.id.btnAddAgain);
		btnIncoming = (Button) this.findViewById(R.id.btnIncoming);
		btnOutgoing = (Button) this.findViewById(R.id.btnOutgoing);

		btnReturn.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		btnAddAgain.setOnClickListener(this);
		btnIncoming.setOnClickListener(this);
		btnOutgoing.setOnClickListener(this);

		etPurpose.setOnClickListener(this);
		etDate.setOnClickListener(this);

		etPurpose.setText("请选择");
		etPurpose.setInputType(InputType.TYPE_NULL);
		etNote.setText("无备注");
		etDate.setInputType(InputType.TYPE_NULL);
		etDate.setText(common.getYMD());// 当前手机时间为默认保存时间

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

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnReturn:
			finish();
			break;
		case R.id.btnSave: {
			if (etInitial.getText().toString().equals("")) {
				Toast.makeText(AddActivity.this, "请填写金额！", Toast.LENGTH_SHORT)
						.show();
			} else if (etPurpose.getText().toString().equals("请选择")) {
				if (i == 0) {
					Toast.makeText(AddActivity.this, "请选择收入来源！",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(AddActivity.this, "请选择支出用途！",
							Toast.LENGTH_SHORT).show();
				}

			} else {
				// 调用方法，保存到数据库中,并跳转到Add界面
				if (rbiz.add(getRecord())) {
					Toast.makeText(AddActivity.this, "保存成功", Toast.LENGTH_SHORT)
							.show();

				} else {
					Toast.makeText(AddActivity.this, "保存失败!",
							Toast.LENGTH_SHORT).show();
				}

				finish();
			}
			break;
		}
		case R.id.btnAddAgain: {
			if (etInitial.getText().toString().equals("")) {
				Toast.makeText(AddActivity.this, "请将信息填写完整！", Toast.LENGTH_SHORT)
						.show();
			} else if (etPurpose.getText().toString().equals("请选择")) {
				Toast.makeText(AddActivity.this, "请将信息填写完整！", Toast.LENGTH_SHORT)
						.show();
			} else if (rbiz.add(getRecord())) {
				Toast.makeText(AddActivity.this, "保存成功，请填写新交易",
						Toast.LENGTH_LONG).show();
				etInitial.setText("");
				etPurpose.setText("请选择");
			} else {
				Toast.makeText(AddActivity.this, "保存失败!", Toast.LENGTH_SHORT)
						.show();
			}

			break;
		}
		case R.id.btnOutgoing:
			// 若单击支出，则此界面作为支出界面
			tvPurpose.setText("用途");
			btnOutgoing.setBackgroundResource(R.drawable.btn_type_focus);
			btnIncoming.setBackgroundResource(R.drawable.btn_wtype_unfocus);
			etInitial.setText("");
			etPurpose.setText("请选择");
			i = 1;
			checkOut = true;
			checkIn = false;
			break;
		case R.id.btnIncoming:
			// 若单击收入，则此界面作为收入界面
			tvPurpose.setText("来源");
			btnIncoming.setBackgroundResource(R.drawable.btn_type_focus);
			btnOutgoing.setBackgroundResource(R.drawable.btn_wtype_unfocus);
			etInitial.setText("");
			etPurpose.setText("请选择");
			i = 0;
			checkIn = true;
			checkOut = false;
			break;

		case R.id.etPurpose: {
			// 跳转到选择用途界面
			Intent intent = new Intent(this, purpose.class);
			intent.putExtra("type", i);
			startActivityForResult(intent, 0);
			break;
		}
		case R.id.etDate:
			// 弹出日期选择界面
			Intent intent = new Intent(this, dateActivity.class);
			startActivityForResult(intent, 1);
			break;
		}
	}

	private Record getRecord() {
		Record record = new Record();
		record.type = i;
		record.money = Double.parseDouble(etInitial.getText().toString());
		record.purpose = etPurpose.getText().toString();
		record.date = etDate.getText().toString();
		record.note = etNote.getText().toString();
		return record;
	}

}
