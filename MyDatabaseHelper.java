package com.huahan.universitylibrary.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

	public static final String CREATE_BOOK = "create table userinfo ("
			+ "id integer primary key autoincrement, " + "user_id text, "
			+ "head_image text, " + "nick_name text, " + "school_id text, "
			+ "school_name text, " + "client_id text, "
			+ "user_rong_token text, " + "login_name text, "
			+ "la text, " + "lo text, " + "is_choose_ed text, "
			+ "last_login_name text)";

	public MyDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_BOOK);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		switch (oldVersion) {
	    case 1:
	        //db.execSQL("alter table userinfo add remark text");
	        break;
	    default:
	        break;
	    }
	}

}
