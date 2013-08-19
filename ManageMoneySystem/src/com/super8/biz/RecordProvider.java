package com.super8.biz;


import com.example.managemoneysystem.Constance;
import com.super8.dao.ManageMoneySystemDao;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class RecordProvider extends ContentProvider {
 
	private Context context;
	private UriMatcher urimatcher;
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		switch(urimatcher.match(uri)){
		case Constance.RESUME_ADD_CODE:
			Log.d("TT","OK");
			new ManageMoneySystemDao(this.context).addRecord(values);
			break;
		}
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		urimatcher=new UriMatcher(UriMatcher.NO_MATCH);
		urimatcher.addURI(Constance.AUTHORIIY, Constance.RESUME_ADD, Constance.RESUME_ADD_CODE);
		context=this.getContext();
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
