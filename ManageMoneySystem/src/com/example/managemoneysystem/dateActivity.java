package com.example.managemoneysystem;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.super8.biz.Common;

public class dateActivity extends Activity implements OnDateChangedListener {

	private TextView tvDate;
	private DatePicker dp01;
	private Button btnSure;
	Common common;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.putExtra("dateMsg", tvDate.getText());
			setResult(10, intent);
			finish();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date);
		common = new Common();
		btnSure = (Button) this.findViewById(R.id.btnSure);
		dp01 = (DatePicker) this.findViewById(R.id.dp01);
		tvDate = (TextView) this.findViewById(R.id.tvDate);
		//默认日期为当先系统日期
		dp01.init(common.getYear(), Integer.parseInt(common.getMONTH()) - 1,
				Integer.parseInt(common.getDayOfMonth()), this);
		onDateChanged(null, 0, 0, 0);
		btnSure.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("dateMsg", tvDate.getText());
				setResult(10, intent);
				finish();
			}
		});

	}

	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(dp01.getYear(), dp01.getMonth(), dp01.getDayOfMonth());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		tvDate.setText(sdf.format(calendar.getTime()));
	}

}
