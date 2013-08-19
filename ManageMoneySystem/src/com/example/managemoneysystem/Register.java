package com.example.managemoneysystem;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Register extends Activity implements OnClickListener{

	private Button btnRegReturn;// ����
	private Button btnRegCIFReg;// ȷ��ע��
	private EditText etRegAccountInfo;// Ҫע����ʺ�
	private EditText etRegPassWordInfo;// Ҫע�������
	private EditText etRegPassWordCIFInfo;// ȷ��ע������

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
			//�ύע����Ϣ
			
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
			return true;// Ϊfalse��MENU����ʵ������Ӧ�еĴ��ݻ���Ч����Ϊtrue����MENU�����¼���ֹ������Ӧ���еĴ��ݣ�
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}
