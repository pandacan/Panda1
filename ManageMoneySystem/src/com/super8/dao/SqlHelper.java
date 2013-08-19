package com.super8.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqlHelper extends SQLiteOpenHelper {

	public SqlHelper(Context context) {
		super(context, "MM.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		String sql = "create table user(id integer primary key AUTOINCREMENT, type integer, money double, purpose text,date text ,note text)";
		arg0.execSQL(sql);
		Log.d("xc", "create budget");
		String sqlbudget = "create table budget(id integer primary key AUTOINCREMENT,money double,purpose text,startyear text,endyear text)";
		arg0.execSQL(sqlbudget);
		Log.d("xc", "ok");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion != newVersion) {
			db = this.getReadableDatabase();
			db.execSQL("drop table if exists db");
			onCreate(db);
		}

	}

}