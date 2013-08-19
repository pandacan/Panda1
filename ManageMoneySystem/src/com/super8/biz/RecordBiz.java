package com.super8.biz;

import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.example.managemoneysystem.Constance;
import com.super8.dao.ManageMoneySystemDao;
import com.super8.entity.Budget;
import com.super8.entity.Record;

public class RecordBiz {
	ContentResolver cresolve;
	ManageMoneySystemDao mmsdao;

	public RecordBiz(Context context) {
		mmsdao = new ManageMoneySystemDao(context);
		cresolve = context.getContentResolver();
	}

	public List<Record> serach(int type) {
		return mmsdao.serach(type);
	}

	public boolean add(Record rc) {
		try {
			ContentValues valuse = new ContentValues();
			valuse.put("date", rc.date);
			valuse.put("money", rc.money);
			valuse.put("note", rc.note);
			valuse.put("purpose", rc.purpose);
			valuse.put("type", rc.type);
			cresolve.insert(
					Uri.parse("content://" + Constance.AUTHORIIY + "/"
							+ Constance.RESUME_ADD), valuse);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public boolean updata(Record rd) {
		try {
			mmsdao.updata(rd);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean updata(Budget budget) {
		try {
			mmsdao.updata(budget);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delete(int id) {
		try {
			mmsdao.delete(id);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public boolean savebudget(Budget bt) {
		try {
			mmsdao.savebudget(bt);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}
