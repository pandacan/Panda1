package com.example.managemoneysystem;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.Window;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends TabActivity implements
		OnMenuItemClickListener {
	Intent intent;

	private int back = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		intent = new Intent();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		intent = new Intent();

		TabHost tabHost = getTabHost();
		// LayoutInflater.from(this).inflate(R.layout.activity_main,
		// tabHost.getTabContentView(), true);

		tabHost.addTab(tabHost
				.newTabSpec("tab1")
				.setIndicator("账户",
						getResources().getDrawable(R.drawable.account))
				.setContent(new Intent(this, Account.class)));
		tabHost.addTab(tabHost
				.newTabSpec("tab2")
				.setIndicator("预算",
						getResources().getDrawable(R.drawable.budget))
				.setContent(new Intent(this, BudgetActivity.class)));
		tabHost.addTab(tabHost
				.newTabSpec("tab3")
				.setIndicator("报表",
						getResources().getDrawable(R.drawable.report))
				.setContent(new Intent(this, ReportActivity.class)));
		/*tabHost.addTab(tabHost
				.newTabSpec("tab4")
				.setIndicator("同步",
						getResources().getDrawable(R.drawable.synchro))
				.setContent(new Intent(this, Login.class)));*/

		tabHost.setCurrentTab(0);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			back++;
			switch (back) {
			case 1:
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_LONG).show();
				break;
			case 2:
				finish();
				break;
			}
			return true;// 为false则MENU按键实践在响应中的传递还有效，若为true，则MENU按键事件终止了在响应链中的传递！
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}// 菜单

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem item = menu.add(1, 1, 1, "退出");
		item.setOnMenuItemClickListener(this);
		return super.onCreateOptionsMenu(menu);
	}

	// 菜单单击事件
	public boolean onMenuItemClick(MenuItem item) {
		if (item.getItemId() == 1) {
			new AlertDialog.Builder(this)
					.setTitle("确认退出？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();

									finish();

								}
							}).setNegativeButton("取消", null).show();
		}
		return false;
	}
}
