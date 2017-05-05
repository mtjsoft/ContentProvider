package com.huahan.universitylibrary.utils;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.huahan.universitylibrary.model.LoginModel;

public class MyProviderHelper {

	private static MyProviderHelper helper;
	private static Uri uri_item;
	private static Uri uri_all;
	// 参数
	public static final String LAST_LOGIN_NAME = "last_login_name";// 上次登录名
	public static final String LOGIN_NAME = "login_name";// 登录名
	public static final String USER_ID = "user_id";// 用户ID
	public static final String HEAD_IMAGE = "head_image";// 头像
	public static final String NICK_NAME = "nick_name";// 头像
	public static final String SCHOOL_NAME = "school_name";// 学校名
	public static final String SCHOOL_ID = "school_id";// 学校名
	public static final String CLIENT_ID = "client_id";
	public static final String USER_RONG_TOKEN = "user_rong_token";

	/**
	 * 获取实例
	 * 
	 * @return
	 */
	public static MyProviderHelper getInstance() {
		synchronized (MyProviderHelper.class) {
			if (helper == null) {
				helper = new MyProviderHelper();
				uri_item = Uri
						.parse("content://com.huahan.universitylibrary.provider/userinfo/1");
				uri_all = Uri
						.parse("content://com.huahan.universitylibrary.provider/userinfo");
			}
			return helper;
		}
	}

	/**
	 * 添加数据
	 * 
	 * @param context
	 * @param model
	 */
	public boolean insert(Context context, LoginModel model) {
		// 添加数据
		ContentValues values = new ContentValues();
		values.put("head_image", model.getHead_image());
		values.put("nick_name", model.getNick_name());
		values.put("user_id", model.getUser_id());
		values.put("login_name", model.getLogin_name());
		values.put("last_login_name", model.getLast_login_name());
		values.put("school_name", model.getSchool_name());
		values.put("user_rong_token", model.getUser_rong_token());
		values.put("school_id", model.getSchool_id());
		values.put("client_id", model.getClient_id());
		// 是否存在
		if (queryIsHave(context)) {
			context.getContentResolver().update(uri_item, values, null, null);
			return true;
		}
		Uri newUri = context.getContentResolver().insert(uri_all, values);// 插入数据
		List<String> ids = newUri.getPathSegments();
		if (ids != null && ids.size() > 1 && !TextUtils.isEmpty(ids.get(1))) {
			return true;
		}
		return false;
	}

	/**
	 * 查询是否存在数据
	 */
	public boolean queryIsHave(Context context) {
		Cursor cursor = context.getContentResolver().query(uri_all, null, null,
				null, null);
		if (cursor != null && cursor.getCount() > 0) {
			Log.i("mtj", "queryIsHave==true");
			cursor.close();
			return true;
		}
		Log.i("mtj", "queryIsHave==false");
		return false;
	}

	/**
	 * 单个查询指定key的值
	 * 
	 * @param context
	 * @param updata_key
	 * @return
	 */
	public String querySign(Context context, String updata_key) {
		String vol = "";
		if (queryIsHave(context)) {
			Cursor cursor = context.getContentResolver().query(uri_item, null,
					null, null, null);
			if (cursor != null && cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					vol = cursor.getString(cursor.getColumnIndex(updata_key));
				}
				cursor.close();
			}
			Log.i("mtj", "querySign== " + vol);
			return vol;
		}
		Log.i("mtj", "querySign== " + vol);
		return vol;
	}

	/**
	 * 是否登录
	 * 
	 * @param context
	 * @return
	 */
	public boolean isLogin(Context context) {
		String vol = "";
		if (queryIsHave(context)) {
			Cursor cursor = context.getContentResolver().query(uri_item, null,
					null, null, null);
			if (cursor != null) {
				while (cursor.moveToNext()) {
					vol = cursor.getString(cursor
							.getColumnIndex(MyProviderHelper.USER_ID));
				}
				cursor.close();
			}
			Log.i("mtj", "isLoginUid== " + vol);
			if (TextUtils.isEmpty(vol)) {
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * 单个修改
	 * 
	 * @param context
	 * @param updata_key
	 *            :必须与数据库一致
	 * @param vol
	 * @return
	 */
	public boolean update(Context context, String updata_key, String vol) {
		ContentValues values = new ContentValues();
		values.put(updata_key, vol);
		if (queryIsHave(context)) {
			context.getContentResolver().update(uri_item, values, null, null);
			return true;
		}
		Uri newUri = context.getContentResolver().insert(uri_all, values);// 插入数据
		List<String> ids = newUri.getPathSegments();
		if (ids != null && ids.size() > 1 && !TextUtils.isEmpty(ids.get(1))) {
			return true;
		}
		return false;
	}

	/**
	 * 修改指定
	 * 
	 * @param context
	 * @param values
	 * @return
	 */
	public boolean updateAll(Context context, ContentValues values) {
		if (queryIsHave(context)) {
			context.getContentResolver().update(uri_item, values, null, null);
			return true;
		}
		Uri newUri = context.getContentResolver().insert(uri_all, values);// 插入数据
		List<String> ids = newUri.getPathSegments();
		if (ids != null && ids.size() > 1 && !TextUtils.isEmpty(ids.get(1))) {
			return true;
		}
		return false;
	}

	/**
	 * 获取model
	 * 
	 * @param context
	 * @return
	 */
	public LoginModel getLoginModel(Context context) {
		LoginModel model = new LoginModel();
		Cursor cursor = context.getContentResolver().query(uri_item, null,
				null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				model.setUser_id(cursor.getString(cursor
						.getColumnIndex(MyProviderHelper.USER_ID)));
				model.setNick_name(cursor.getString(cursor
						.getColumnIndex(MyProviderHelper.NICK_NAME)));
				model.setHead_image(cursor.getString(cursor
						.getColumnIndex(MyProviderHelper.HEAD_IMAGE)));
			}
			cursor.close();
		}
		return model;
	}
}
