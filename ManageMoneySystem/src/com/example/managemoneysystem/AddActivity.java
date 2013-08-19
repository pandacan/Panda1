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

	private Button btnOutgoing;// ֧��
	private Button btnIncoming;// ����
	private Button btnReturn; // ����
	private Button btnSave; // ����
	private Button btnAddAgain; // �ټ�һ��
	private EditText etPurpose; // ��;
	private EditText etNote; // ��ע
	private EditText etDate; // ����
	private EditText etInitial; // ���
	private TextView tvPurpose;

	private String[] ppMsg = new String[2];// �����;��Ϣ��ppMsg[0]�������,ppMsg[1]���֧��
	private boolean checkOut; // ���֧����ť
	private boolean checkIn; // ������밴ť
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

	// ��ѡ�����;��Ϣ���������ppMsg��
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

		etPurpose.setText("��ѡ��");
		etPurpose.setInputType(InputType.TYPE_NULL);
		etNote.setText("�ޱ�ע");
		etDate.setInputType(InputType.TYPE_NULL);
		etDate.setText(common.getYMD());// ��ǰ�ֻ�ʱ��ΪĬ�ϱ���ʱ��

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

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnReturn:
			finish();
			break;
		case R.id.btnSave: {
			if (etInitial.getText().toString().equals("")) {
				Toast.makeText(AddActivity.this, "����д��", Toast.LENGTH_SHORT)
						.show();
			} else if (etPurpose.getText().toString().equals("��ѡ��")) {
				if (i == 0) {
					Toast.makeText(AddActivity.this, "��ѡ��������Դ��",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(AddActivity.this, "��ѡ��֧����;��",
							Toast.LENGTH_SHORT).show();
				}

			} else {
				// ���÷��������浽���ݿ���,����ת��Add����
				if (rbiz.add(getRecord())) {
					Toast.makeText(AddActivity.this, "����ɹ�", Toast.LENGTH_SHORT)
							.show();

				} else {
					Toast.makeText(AddActivity.this, "����ʧ��!",
							Toast.LENGTH_SHORT).show();
				}

				finish();
			}
			break;
		}
		case R.id.btnAddAgain: {
			if (etInitial.getText().toString().equals("")) {
				Toast.makeText(AddActivity.this, "�뽫��Ϣ��д������", Toast.LENGTH_SHORT)
						.show();
			} else if (etPurpose.getText().toString().equals("��ѡ��")) {
				Toast.makeText(AddActivity.this, "�뽫��Ϣ��д������", Toast.LENGTH_SHORT)
						.show();
			} else if (rbiz.add(getRecord())) {
				Toast.makeText(AddActivity.this, "����ɹ�������д�½���",
						Toast.LENGTH_LONG).show();
				etInitial.setText("");
				etPurpose.setText("��ѡ��");
			} else {
				Toast.makeText(AddActivity.this, "����ʧ��!", Toast.LENGTH_SHORT)
						.show();
			}

			break;
		}
		case R.id.btnOutgoing:
			// ������֧������˽�����Ϊ֧������
			tvPurpose.setText("��;");
			btnOutgoing.setBackgroundResource(R.drawable.btn_type_focus);
			btnIncoming.setBackgroundResource(R.drawable.btn_wtype_unfocus);
			etInitial.setText("");
			etPurpose.setText("��ѡ��");
			i = 1;
			checkOut = true;
			checkIn = false;
			break;
		case R.id.btnIncoming:
			// ���������룬��˽�����Ϊ�������
			tvPurpose.setText("��Դ");
			btnIncoming.setBackgroundResource(R.drawable.btn_type_focus);
			btnOutgoing.setBackgroundResource(R.drawable.btn_wtype_unfocus);
			etInitial.setText("");
			etPurpose.setText("��ѡ��");
			i = 0;
			checkIn = true;
			checkOut = false;
			break;

		case R.id.etPurpose: {
			// ��ת��ѡ����;����
			Intent intent = new Intent(this, purpose.class);
			intent.putExtra("type", i);
			startActivityForResult(intent, 0);
			break;
		}
		case R.id.etDate:
			// ��������ѡ�����
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
