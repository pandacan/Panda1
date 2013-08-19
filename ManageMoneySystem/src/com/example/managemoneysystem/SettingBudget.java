package com.example.managemoneysystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.super8.biz.Common;
import com.super8.biz.RecordBiz;
import com.super8.entity.Budget;

public class SettingBudget extends Activity implements OnClickListener {

	private Button btnBReturn;// ����
	private Button btnBudgetSave;// ����
	private EditText etBInitial;// Ԥ����
	private TextView tvBPurposeInfo;// ֧����;
	private TextView tvBStartTimeInfo;// ��ʼʱ��,�̶���ǰʱ��
	private TextView tvBEndTimeInfo;// ����ʱ�䣬��ѡ��
	private TextView tvBPayoutedInfo;// �Ѿ�����
	private String[] mppMsg = new String[2];// �����;��Ϣ��ppMsg[0]�������,ppMsg[1]���֧��
	private String mdateMsg;
	private Boolean flag = false;// falseΪ����,trueΪ���
	private Intent intent;
	Common common;
	RecordBiz biz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settingbudget);
		common = new Common();
		biz = new RecordBiz(this);
		intent = this.getIntent();

		btnBReturn = (Button) this.findViewById(R.id.btnBReturn);
		btnBudgetSave = (Button) this.findViewById(R.id.btnBudgetSave);
		etBInitial = (EditText) this.findViewById(R.id.etBInitial);
		tvBPurposeInfo = (TextView) this.findViewById(R.id.tvBPurposeInfo);
		tvBStartTimeInfo = (TextView) this.findViewById(R.id.tvBStartTimeInfo);
		tvBEndTimeInfo = (TextView) this.findViewById(R.id.tvBEndTimeInfo);
		tvBPayoutedInfo = (TextView) this.findViewById(R.id.tvBPayoutedInfo);

		tvBStartTimeInfo.setText(common.getYear() + "-" + common.getMONTH());
		tvBEndTimeInfo.setText(common.getYear() + "-" + common.getMONTH());

		btnBReturn.setOnClickListener(this);
		btnBudgetSave.setOnClickListener(this);
		tvBPurposeInfo.setOnClickListener(this);
		tvBEndTimeInfo.setOnClickListener(this);
		etBInitial.setOnClickListener(this);
		try {
			flag = intent.getExtras().getBoolean("flag");
		} catch (Exception e) {
			flag = false;
		}
		isModify(flag);
	}
//���Ǹ���Ԥ�㣬������������
	private void isModify(Boolean flag) {

		if (flag == true) {
			tvBPayoutedInfo.setText("��"
					+ intent.getExtras().getDouble("onePay"));
			etBInitial.setText(intent.getExtras().getDouble("money") + "");
			tvBPurposeInfo.setText(intent.getExtras().getString("purpose"));
			tvBStartTimeInfo.setText(intent.getExtras().getString("startyear"));
			tvBEndTimeInfo.setText(intent.getExtras().getString("endyear"));

		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnBReturn:
			finish();
			break;
		case R.id.etBInitial:
			etBInitial.setText("");
			break;
		case R.id.btnBudgetSave:

			// ����
			Budget bt = new Budget();
			
			bt.purpose = tvBPurposeInfo.getText().toString();

			if (etBInitial.getText().toString().equals("")) {
				Toast.makeText(this, "��������", Toast.LENGTH_SHORT).show();
			} else if (bt.purpose.equals("��ѡ��")) {
				Toast.makeText(this, "��ѡ����;", Toast.LENGTH_SHORT).show();
			} else {

				bt.money = Double.parseDouble(etBInitial.getText().toString());
				bt.endyear = tvBEndTimeInfo.getText().toString();
				bt.startyear = tvBStartTimeInfo.getText().toString();
				if (!flag) {
					if (biz.savebudget(bt)) {
						Toast.makeText(SettingBudget.this, "Ԥ����ӳɹ�",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(SettingBudget.this, "Ԥ�����ʧ��",
								Toast.LENGTH_SHORT).show();
					}
				} else{
					Log.i("setFlag", flag+"");
					bt.id = intent.getExtras().getInt("id");
					if (biz.updata(bt)) {
						Toast.makeText(SettingBudget.this, "����Ԥ��ɹ�",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(SettingBudget.this, "����Ԥ��ʧ��",
								Toast.LENGTH_SHORT).show();
					}
				}
				finish();
			}
			break;
		case R.id.tvBPurposeInfo:
			// ��ת��ѡ����;����
			Intent intent = new Intent(this, purpose.class);
			intent.putExtra("type", 1);
			startActivityForResult(intent, 134);
			break;
		case R.id.tvBEndTimeInfo:
			// ��ת��ѡ��ʱ�����
			Intent intent2 = new Intent(this, dateActivity.class);
			startActivityForResult(intent2, 135);
			break;

		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		reppMsg(requestCode, resultCode, 1, data);

	}

	// ��ѡ�����;��Ϣ���������ppMsg��
	private void reppMsg(int requestCode, int resultCode, int type, Intent data) {

		mppMsg[type] = "";
		switch (requestCode) {
		case 134:
			switch (resultCode) {
			case 111:
				mppMsg[type] = data.getStringExtra("p1");
				tvBPurposeInfo.setText(mppMsg[type]);
				break;
			case 222:
				mppMsg[type] = data.getStringExtra("p2");
				tvBPurposeInfo.setText(mppMsg[type]);
				break;
			case 333:
				mppMsg[type] = data.getStringExtra("p3");
				tvBPurposeInfo.setText(mppMsg[type]);
				break;
			case 444:
				mppMsg[type] = data.getStringExtra("p4");
				tvBPurposeInfo.setText(mppMsg[type]);
				break;
			case 555:
				mppMsg[type] = data.getStringExtra("p5");
				tvBPurposeInfo.setText(mppMsg[type]);
				break;
			case 666:
				mppMsg[type] = data.getStringExtra("p6");
				tvBPurposeInfo.setText(mppMsg[type]);
				break;
			case 777:
				mppMsg[type] = data.getStringExtra("p7");
				tvBPurposeInfo.setText(mppMsg[type]);
				break;
			case 888:
				mppMsg[type] = data.getStringExtra("p8");
				tvBPurposeInfo.setText(mppMsg[type]);
				break;
			default:
				break;
			}
			break;
		case 135:
			mdateMsg = data.getStringExtra("dateMsg");
			String regex = "\\D+";
			String date[] = mdateMsg.split(regex);
			tvBEndTimeInfo.setText(date[0] + "-" + date[1]);
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			flag = false;
			finish();
			return true;// Ϊfalse��MENU����ʵ������Ӧ�еĴ��ݻ���Ч����Ϊtrue����MENU�����¼���ֹ������Ӧ���еĴ��ݣ�
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}
