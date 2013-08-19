package com.example.managemoneysystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity implements OnClickListener {
	private Button btnLReturn;
	private Button btnLLogin;
	private Button btnLRegister;
	private EditText etLAccountInfo;
	private EditText etLPassWordInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		btnLReturn = (Button) this.findViewById(R.id.btnLReturn);
		btnLLogin = (Button) this.findViewById(R.id.btnLLogin);
		btnLRegister = (Button) this.findViewById(R.id.btnLRegister);
		etLAccountInfo = (EditText) this.findViewById(R.id.etLAccountInfo);
		etLPassWordInfo = (EditText) this.findViewById(R.id.etLPassWordInfo);

		btnLReturn.setOnClickListener(this);
		btnLLogin.setOnClickListener(this);
		btnLRegister.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLReturn:
			finish();
			break;
		case R.id.btnLRegister://��ת��ע�����
			Intent intent = new Intent(Login.this, Register.class);
			startActivity(intent);
			break;
		case R.id.btnLLogin:
			//��¼�ɹ���ִ�б���
			break;

		default:
			break;
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

}
