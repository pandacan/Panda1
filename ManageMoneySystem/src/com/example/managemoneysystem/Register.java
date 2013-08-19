package com.example.managemoneysystem;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Register extends Activity implements OnClickListener{

	private Button btnRegReturn;// 返回
	private Button btnRegCIFReg;// 确认注册
	private EditText etRegAccountInfo;// 要注册的帐号
	private EditText etRegPassWordInfo;// 要注册的密码
	private EditText etRegPassWordCIFInfo;// 确认注册密码

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		btnRegReturn = (Button) this.findViewById(R.id.btnRegReturn);
		btnRegCIFReg = (Button) this.findViewById(R.id.btnRegCIFReg);
		etRegAccountInfo = (EditText) this.findViewById(R.id.etRegAccountInfo);
		etRegPassWordInfo = (EditText) this
				.findViewById(R.id.etRegPassWordInfo);
		etRegPassWordCIFInfo = (EditText) this
				.findViewById(R.id.etRegPassWordCIFInfo);
		
		btnRegReturn.setOnClickListener(this);
		btnRegCIFReg.setOnClickListener(this);
		
		
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnRegReturn:
			
			finish();
			break;
		case R.id.btnRegCIFReg:
			//提交注册信息
			
			finish();
			break;
		
		default:
			break;
		}
		
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
