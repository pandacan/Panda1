package com.super8.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.super8.entity.Budget;
import com.super8.entity.Record;

public class ManageMoneySystemDao {
	static SqlHelper sqh;

	public ManageMoneySystemDao(Context context) {
		sqh = new SqlHelper(context);
	}

	public List<Record> serach(int type) {
		SQLiteDatabase db = sqh.getWritableDatabase();
		Cursor cursor = db.query("user", null, "type=?", new String[] { type
				+ "" }, null, null, null);
		List<Record> records = new ArrayList<Record>();
		while (cursor.moveToNext()) {
			Record rd = new Record();
			rd.date = cursor.getString(cursor.getColumnIndex("date"));
			rd.money = cursor.getDouble(cursor.getColumnIndex("money"));
			rd.purpose = cursor.getString(cursor.getColumnIndex("purpose"));
			rd.note = cursor.getString(cursor.getColumnIndex("note"));
			records.add(rd);
		}
		cursor.close();
		db.close();
		return records;
	}

	public void addRecord(ContentValues values) {
		SQLiteDatabase db = sqh.getWritableDatabase();
		db.insert("user", null, values);
		db.close();

	}

	public Double searchbyYMD(int ymd, int type, int i) {
		double money = 0;
		String regex = "\\D+";
		SQLiteDatabase db = sqh.getWritableDatabase();
		Cursor cursor = db.query("user", null, "type=?", new String[] { type
				+ "" }, null, null, null);
		while (cursor.moveToNext()) {
			Record rd = new Record();
			rd.date = cursor.getString(cursor.getColumnIndex("date"));
			String date[] = rd.date.split(regex);
			if (Integer.parseInt(date[i]) == ymd) {
				money += cursor.getDouble(cursor.getColumnIndex("money"));
			}
		}
		return money;
	}

	// 查询某年某月的收支记录
	public Double searchbyYM(int year, int month, int type) {
		double money = 0;
		String regex = "\\D+";
		SQLiteDatabase db = sqh.getWritableDatabase();
		Cursor cursor = db.query("user", null, "type=?", new String[] { type
				+ "" }, null, null, null);
		while (cursor.moveToNext()) {
			Record rd = new Record();
			rd.date = cursor.getString(cursor.getColumnIndex("date"));
			String date[] = rd.date.split(regex);
			if (Integer.parseInt(date[0]) == year
					&& Integer.parseInt(date[1]) == month) {
				money += cursor.getDouble(cursor.getColumnIndex("money"));
			}
		}
		return money;
	}

	// 搜索某年某月某日的记录
	public Double searchbyYMD(int year, int month, int dayofmonth, int type) {
		double money = 0;
		String regex = "\\D+";
		SQLiteDatabase db = sqh.getWritableDatabase();
		Cursor cursor = db.query("user", null, "type=?", new String[] { type
				+ "" }, null, null, null);
		while (cursor.moveToNext()) {
			Record rd = new Record();
			rd.date = cursor.getString(cursor.getColumnIndex("date"));
			String date[] = rd.date.split(regex);
			if (Integer.parseInt(date[0]) == year
					&& Integer.parseInt(date[1]) == month
					&& Integer.parseInt(date[2]) == dayofmonth) {
				money += cursor.getDouble(cursor.getColumnIndex("money"));
			}
		}
		return money;
	}

	public List<Record> searchbyyear(int year, int type) {
		List<Record> rds = new ArrayList<Record>();
		String regex = "\\D+";
		SQLiteDatabase db = sqh.getWritableDatabase();
		Cursor cursor = db.query("user", null, "type=?", new String[] { type
				+ "" }, null, null, null);
		while (cursor.moveToNext()) {
			Record rd = new Record();
			rd.date = cursor.getString(cursor.getColumnIndex("date"));
			String date[] = rd.date.split(regex);
			if (Integer.parseInt(date[0]) == year) {
				rd.id = cursor.getInt(cursor.getColumnIndex("id"));
				rd.date = cursor.getString(cursor.getColumnIndex("date"));
				rd.money = cursor.getDouble(cursor.getColumnIndex("money"));
				rd.purpose = cursor.getString(cursor.getColumnIndex("purpose"));
				rd.note = cursor.getString(cursor.getColumnIndex("note"));
				rd.type = cursor.getInt(cursor.getColumnIndex("type"));
				rds.add(rd);
			}
		}
		cursor.close();
		db.close();
		return rds;
	}

	// 更新收支记录
	public void updata(Record record) {
		SQLiteDatabase db = sqh.getWritableDatabase();
		ContentValues valuse = new ContentValues();
		valuse.put("type", record.type);
		valuse.put("note", record.note);
		valuse.put("date", record.date);
		valuse.put("purpose", record.purpose);
		valuse.put("money", record.money);
		db.update("user", valuse, "id=?", new String[] { record.id + "" });
		db.close();
	}

	// 更新预算记录
	public void updata(Budget budget) {
		SQLiteDatabase db = sqh.getWritableDatabase();
		ContentValues valuse = new ContentValues();
		valuse.put("startyear", budget.startyear);
		valuse.put("endyear", budget.endyear);
		valuse.put("purpose", budget.purpose);
		valuse.put("money", budget.money);
		db.update("budget", valuse, "id=?", new String[] { budget.id + "" });
		db.close();
	}

	public void delete(int id) {
		SQLiteDatabase db = sqh.getWritableDatabase();
		db.delete("user", "id=?", new String[] { id + "" });
		db.close();
	}

	public List<Budget> searchBudget() {
		List<Budget> budgets = new ArrayList<Budget>();
		SQLiteDatabase db = sqh.getWritableDatabase();
		Cursor cursor = db.query("budget", null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			Budget budget = new Budget();
			budget.startyear = cursor.getString(cursor
					.getColumnIndex("startyear"));
			budget.endyear = cursor.getString(cursor.getColumnIndex("endyear"));
			budget.money = cursor.getDouble(cursor.getColumnIndex("money"));
			budget.purpose = cursor.getString(cursor.getColumnIndex("purpose"));
			budget.id = cursor.getInt(cursor.getColumnIndex("id"));
			budgets.add(budget);
		}
		return budgets;
	}

	public List<Budget> searchBudgetbydate(int year, int month) {
		List<Budget> budgets = new ArrayList<Budget>();
		SQLiteDatabase db = sqh.getWritableDatabase();
		Cursor cursor = db.query("budget", null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			Budget budget = new Budget();
			budget.startyear = cursor.getString(cursor
					.getColumnIndex("startyear"));
			budget.endyear = cursor.getString(cursor.getColumnIndex("endyear"));

			String startyear[] = budget.startyear.split("\\D+");
			String endyear[] = budget.endyear.split("\\D+");

			if (year >= Integer.parseInt(startyear[0])
					&& year <= Integer.parseInt(endyear[0])) {
				if (month >= Integer.parseInt(startyear[1])
						&& month <= Integer.parseInt(endyear[1])) {
					budget.money = cursor.getDouble(cursor
							.getColumnIndex("money"));
					budget.purpose = cursor.getString(cursor
							.getColumnIndex("purpose"));
					budget.id = cursor.getInt(cursor.getColumnIndex("id"));
					budgets.add(budget);
				}
			}

		}
		return budgets;
	}

	// 计算总的预算
	public double allbudget(int year, int month) {
		double money = 0;
		String regex = "\\D+";
		SQLiteDatabase db = sqh.getWritableDatabase();
		Cursor cursor = db.query("budget", null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			Budget bt = new Budget();
			bt.startyear = cursor.getString(cursor.getColumnIndex("startyear"));
			bt.endyear = cursor.getString(cursor.getColumnIndex("endyear"));
			String date[] = bt.startyear.split(regex);
			String dataend[] = bt.endyear.split(regex);
			if (Integer.parseInt(date[0]) == year) {
				if (Integer.parseInt(date[1]) <= month
						&& Integer.parseInt(dataend[1]) >= month) {
					money += cursor.getDouble(cursor.getColumnIndex("money"));
				}
			}

		}
		return money;
	}

	public void savebudget(Budget bt) {
		SQLiteDatabase db = sqh.getWritableDatabase();
		ContentValues valuse = new ContentValues();
		valuse.put("startyear", bt.startyear);
		valuse.put("endyear", bt.endyear);
		valuse.put("money", bt.money);
		valuse.put("purpose", bt.purpose);
		db.insert("budget", null, valuse);
		db.close();
	}

	// 根据预算用途搜索消费，返回预算涉及到的消费总金额
	public double expensebypurpose(int year, int month) {
		double money = 0;
		SQLiteDatabase db = sqh.getWritableDatabase();
		String sql = "SELECT user.money,user.date FROM user,budget WHERE user.purpose=budget.purpose AND user.type=1";
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			String data[] = cursor.getString(cursor.getColumnIndex("date"))
					.split("\\D+");
			if (Integer.parseInt(data[1]) == month
					&& Integer.parseInt(data[0]) == year) {
				money += cursor.getDouble(cursor.getColumnIndex("money"));
			}
		}
		return money;
	}

	// 返回某条预算的已消费金额
	public double expensebyonepurpose(String purpose, int year, int month) {
		double money = 0;

		SQLiteDatabase db = sqh.getWritableDatabase();
		String sql = "SELECT user.money,user.date FROM user,budget WHERE user.purpose=?";
		Cursor cursor = db.rawQuery(sql, new String[] { purpose + "" });
		while (cursor.moveToNext()) {
			String data[] = cursor.getString(cursor.getColumnIndex("date"))
					.split("\\D+");
			if (Integer.parseInt(data[1]) == month
					&& Integer.parseInt(data[0]) == year) {
				money += cursor.getDouble(cursor.getColumnIndex("money"));
			}
		}
		return money;
	}

	// 在某个时间段范围内，某种类型的某条记录的money
	public double searchRecordByDate(int lastYear, int lastMonth, int nextYear,
			int nextMonth, int type) {
		double money = 0;
		SQLiteDatabase db = sqh.getWritableDatabase();
		Cursor cursor = db.query("user", null, "type=?", new String[] { type
				+ "" }, null, null, null);
		while (cursor.moveToNext()) {
			Record rd = new Record();
			rd.date = cursor.getString(cursor.getColumnIndex("date"));
			String date[] = rd.date.split("\\D+");
			if (nextYear >= Integer.parseInt(date[0])
					&& lastYear <= Integer.parseInt(date[0])) {
				if (nextMonth >= Integer.parseInt(date[1])
						&& lastMonth <= Integer.parseInt(date[1])) {
					money += cursor.getDouble(cursor.getColumnIndex("money"));
				}
			}

		}
		return money;
	}

	// 在某个时间段范围内，某种purpose的某条记录的money

	public double searchRecordByDateAndPP(int lastYear, int lastMonth,
			int nextYear, int nextMonth, String purpose) {
		double money = 0;
		SQLiteDatabase db = sqh.getWritableDatabase();
		Cursor cursor = db.query("user", null, "purpose=?",
				new String[] { purpose + "" }, null, null, null);
		while (cursor.moveToNext()) {
			Record record = new Record();
			record.date = cursor.getString(cursor.getColumnIndex("date"));
			String date[] = record.date.split("\\D+");
			if (nextYear >= Integer.parseInt(date[0])
					&& lastYear <= Integer.parseInt(date[0])) {
				if (nextMonth >= Integer.parseInt(date[1])
						&& lastMonth <= Integer.parseInt(date[1])) {
					money += cursor.getDouble(cursor.getColumnIndex("money"));
				}
			}

		}
		return money;
	}

	// 通过某条记录的日期，判断是否在某个时间段范围内，返回对象
	public List<Record> searchRecordByDate(int lastYear, int lastMonth,
			int nextYear, int nextMonth) {
		List<Record> records = new ArrayList<Record>();
		SQLiteDatabase db = sqh.getWritableDatabase();
		Cursor cursor = db.query("user", null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			Record record = new Record();
			record.date = cursor.getString(cursor.getColumnIndex("date"));

			String date[] = record.date.split("\\D+");

			if (nextYear >= Integer.parseInt(date[0])
					&& lastYear <= Integer.parseInt(date[0])) {
				if (nextMonth >= Integer.parseInt(date[1])
						&& lastMonth <= Integer.parseInt(date[1])) {
					record.money = cursor.getDouble(cursor
							.getColumnIndex("money"));
					record.purpose = cursor.getString(cursor
							.getColumnIndex("purpose"));
					record.id = cursor.getInt(cursor.getColumnIndex("id"));
					records.add(record);
				}
			}
		}
		return records;
	}

}
