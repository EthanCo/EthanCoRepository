package com.zhk.sqlitetest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBUtils {
	private static final String TABLE="t_book";
	private static final String ID ="_id";
	private static final String NAME ="name";
	private static final String PRICE ="price";
	
	private DBHelper dbHelper;
	public DBUtils(Context context){
		dbHelper = new DBHelper(context);
	}
	/**
	 * 插入数据
	 * @param values 插入的列值
	 * @return 插入的行数
	 */
	public int insert(ContentValues values){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		/**
		 * insert(table, nullColumnHack, values)
		 * insert(表 , 空字段回填 , 列值);
		 * 空字段回填 作用 : 当values为空时，避免sql语句错误而报错，insert t_book( ) values(null)
		 * 把nullColumnHack设为null，则insert t_book(null) values(null)，不会报错
		 * ContentValues 就像是Hashmap key:column，value:字段
		 */
		Long id = db.insert(TABLE, null, values);
		db.close();
		return id.intValue();
	};
	/**
	 * 删除
	 * @param id id
	 * @return 影响的行数
	 */
	public int delete(int id){
		int affectNum;//影响的行数
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		/**
		 * delete(table, whereClause, whereArgs)
		 * delete(表 , 条件 , 条件参数)
		 */
		affectNum = db.delete(TABLE, ID+"=?", new String[]{String.valueOf(id)});
		db.close();
		return affectNum;
	}
	/**
	 * 
	 * @param values [自义定]必须要传一个id
	 * @return
	 */
	public int update(ContentValues values){
		String id = values.getAsString(ID);
		int affectNum;//影响的行数
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		affectNum = db.update(TABLE, values, ID+"=?", new String[]{id});
		db.close();
		return affectNum;
	}
	public Cursor queryForCursor(){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		return db.query(TABLE, null, null, null, null, null, "price desc");
		//返回的是游标，不能在这关闭db，否则会访问不到
	}
	public List<Map<String,Object>> query(){
		List<Map<String,Object>> data =null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor =  db.query(TABLE, null, null, null, null, null, "price desc");
		//遍历游标
		if(cursor.getCount()>0){
			data = new ArrayList<Map<String,Object>>();
		}
		while (cursor.moveToNext()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put(ID, cursor.getInt(cursor.getColumnIndex(ID)));
			map.put(NAME, cursor.getInt(cursor.getColumnIndex(NAME)));
			map.put(PRICE, cursor.getInt(cursor.getColumnIndex(PRICE)));
			data.add(map);
		}
		return data;
	}
}
