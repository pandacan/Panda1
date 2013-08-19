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

	private Button btnBReturn;// 返回
	private Button btnBudgetSave;// 保存
	private EditText etBInitial;// 预算金额
	private TextView tvBPurposeInfo;// 支出用途
	private TextView tvBStartTimeInfo;// 开始时间,固定当前时间
	private TextView tvBEndTimeInfo;// 结束时间，可选择
	private TextView tvBPayoutedInfo;// 已经消费
	private String[] mppMsg = new String[2];// 存放用途信息，ppMsg[0]存放收入,ppMsg[1]存放支出
	private String mdateMsg;
	private Boolean flag = false;// false为更新,true为添加
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
//若是更新预算，则调用这个方法
	private void isModify(Boolean flag) {

		if (flag == true) {
			tvBPayoutedInfo.setText("￥"
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

			// 保存
			Budget bt = new Budget();
			
			bt.purpose = tvBPurposeInfo.getText().toString();

			if (etBInitial.getText().toString().equals("")) {
				Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
			} else if (bt.purpose.equals("请选择")) {
				Toast.makeText(this, "请选择用途", Toast.LENGTH_SHORT).show();
			} else {

				bt.money = Double.parseDouble(etBInitial.getText().toString());
				bt.endyear = tvBEndTimeInfo.getText().toString();
				bt.startyear = tvBStartTimeInfo.getText().toString();
				if (!flag) {
					if (biz.savebudget(bt)) {
						Toast.makeText(SettingBudget.this, "预算添加成功",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(SettingBudget.this, "预算添加失败",
								Toast.LENGTH_SHORT).show();
					}
				} else{
					Log.i("setFlag", flag+"");
					bt.id = intent.getExtras().getInt("id");
					if (biz.updata(bt)) {
						Toast.makeText(SettingBudget.this, "更新预算成功",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(SettingBudget.this, "更新预算失败",
								Toast.LENGTH_SHORT).show();
					}
				}
				finish();
			}
			break;
		case R.id.tvBPurposeInfo:
			// 跳转到选择用途界面
			Intent intent = new Intent(this, purpose.class);
			intent.putExtra("type", 1);
			startActivityForResult(intent, 134);
			break;
		case R.id.tvBEndTimeInfo:
			// 跳转到选择时间界面
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

	// 将选择的用途信息存放在数组ppMsg中
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
			return true;// 为false则MENU按键实践在响应中的传递还有效，若为true，则MENU按键事件终止了在响应链中的传递！
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}
