package com.example.managemoneysystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.super8.biz.RecordBiz;
import com.super8.entity.Record;

public class Particular extends Activity implements OnClickListener {
	private EditText etMMoney;
	private EditText etMNote;
	private EditText etMPurpose;
	private EditText etMDate;
	private Button btnMSave;
	private Button btnDelete;
	private Intent intent;
	private Button btnMReturn;
	private TextView tvMPurpose;
	private int type;
	RecordBiz biz;
	Record rd;
	private int id;

	private String[] mppMsg = new String[2];// �����;��Ϣ��ppMsg[0]�������,ppMsg[1]���֧��
	private String mdateMsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify);

		biz = new RecordBiz(this);

		etMMoney = (EditText) this.findViewById(R.id.etMMoney);
		etMNote = (EditText) this.findViewById(R.id.etMNote);
		etMPurpose = (EditText) this.findViewById(R.id.etMPurpose);
		etMDate = (EditText) this.findViewById(R.id.etMDate);
		btnMSave = (Button) this.findViewById(R.id.btnMSave);
		btnDelete = (Button) this.findViewById(R.id.btnDelete);
		btnMReturn = (Button) this.findViewById(R.id.btnMReturn);
		tvMPurpose = (TextView) this.findViewById(R.id.tvMPurpose);

		btnMSave.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		btnMReturn.setOnClickListener(this);
		etMPurpose.setOnClickListener(this);
		etMDate.setOnClickListener(this);
		intent = this.getIntent();

		etMMoney.setText(intent.getExtras().getDouble("money") + "");
		etMNote.setText(intent.getExtras().getString("note"));
		etMPurpose.setText(intent.getExtras().getString("purpose"));
		etMPurpose.setInputType(InputType.TYPE_NULL);// �����������
		etMDate.setText(intent.getExtras().getString("data"));
		etMDate.setInputType(InputType.TYPE_NULL);// �����������
		id = intent.getExtras().getInt("id");
		type = intent.getExtras().getInt("type");
		setInOROut(type);

	}

	private void setInOROut(int type) {
		if (type == 0) {
			tvMPurpose.setText("��Դ");
		} else {
			tvMPurpose.setText("��;");
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

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnMReturn:
			finish();
			break;
		case R.id.btnMSave:
			rd = new Record();
			rd.date = etMDate.getText().toString();
			rd.note = etMNote.getText().toString();
			rd.money = Double.parseDouble(etMMoney.getText().toString());
			rd.purpose = etMPurpose.getText().toString();
			rd.id = id;
			rd.type = type;
			if (biz.updata(rd)) {
				Toast.makeText(Particular.this, "���³ɹ���", Toast.LENGTH_LONG)
						.show();
			} else {
				Toast.makeText(Particular.this, "����ʧ�ܣ�", Toast.LENGTH_LONG)
						.show();
			}
			finish();
			break;

		case R.id.btnDelete:
			new AlertDialog.Builder(this)
					.setTitle("ȷ��ɾ����")
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									if (biz.delete(id)) {
										Toast.makeText(Particular.this,
												"ɾ���ɹ���", Toast.LENGTH_LONG)
												.show();
									} else {
										Toast.makeText(Particular.this,
												"ɾ��ʧ�ܣ�", Toast.LENGTH_LONG)
												.show();
									}

									finish();

								}
							}).setNegativeButton("ȡ��", null).show();

			break;
		case R.id.etMPurpose: {
			// ��ת��ѡ����;����
			Intent intent = new Intent(this, purpose.class);
			intent.putExtra("type", type);
			startActivityForResult(intent, 14);
			break;
		}
		case R.id.etMDate: {
			// ��������ѡ�����
			Intent intent = new Intent(this, dateActivity.class);
			startActivityForResult(intent, 15);
			break;
		}
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		reppMsg(requestCode, resultCode, type, data);

	}

	// ��ѡ�����;��Ϣ���������ppMsg��
	private void reppMsg(int requestCode, int resultCode, int type, Intent data) {

		mppMsg[type] = "";
		switch (requestCode) {
		case 14:
			switch (resultCode) {
			case 111:
				mppMsg[type] = data.getStringExtra("p1");
				etMPurpose.setText(mppMsg[type]);
				break;
			case 222:
				mppMsg[type] = data.getStringExtra("p2");
				etMPurpose.setText(mppMsg[type]);
				break;
			case 333:
				mppMsg[type] = data.getStringExtra("p3");
				etMPurpose.setText(mppMsg[type]);
				break;
			case 444:
				mppMsg[type] = data.getStringExtra("p4");
				etMPurpose.setText(mppMsg[type]);
				break;
			case 555:
				mppMsg[type] = data.getStringExtra("p5");
				etMPurpose.setText(mppMsg[type]);
				break;
			case 666:
				mppMsg[type] = data.getStringExtra("p6");
				etMPurpose.setText(mppMsg[type]);
				break;
			case 777:
				mppMsg[type] = data.getStringExtra("p7");
				etMPurpose.setText(mppMsg[type]);
				break;
			case 888:
				mppMsg[type] = data.getStringExtra("p8");
				etMPurpose.setText(mppMsg[type]);
				break;
			default:
				break;
			}
			break;
		case 15:
			mdateMsg = data.getStringExtra("dateMsg");
			etMDate.setText(mdateMsg);
			break;

		default:
			break;
		}
	}

}
