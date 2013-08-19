package com.example.managemoneysystem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashScreen extends Activity {
	private TextView tvScreen;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0) {
				Intent intent = new Intent(SplashScreen.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			}

		}
	};

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);

		setContentView(R.layout.splashscreen);
		tvScreen = (TextView) this.findViewById(R.id.tvScreen);
		new Thread(new LoadThread()).start();

	}

	class LoadThread implements Runnable {

		public void run() {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			handler.sendEmptyMessage(0);
		}

	}
}
