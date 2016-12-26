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
	 * ��������
	 * @param values �������ֵ
	 * @return ���������
	 */
	public int insert(ContentValues values){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		/**
		 * insert(table, nullColumnHack, values)
		 * insert(�� , ���ֶλ��� , ��ֵ);
		 * ���ֶλ��� ���� : ��valuesΪ��ʱ������sql�����������insert t_book( ) values(null)
		 * ��nullColumnHack��Ϊnull����insert t_book(null) values(null)�����ᱨ��
		 * ContentValues ������Hashmap key:column��value:�ֶ�
		 */
		Long id = db.insert(TABLE, null, values);
		db.close();
		return id.intValue();
	};
	/**
	 * ɾ��
	 * @param id id
	 * @return Ӱ�������
	 */
	public int delete(int id){
		int affectNum;//Ӱ�������
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		/**
		 * delete(table, whereClause, whereArgs)
		 * delete(�� , ���� , ��������)
		 */
		affectNum = db.delete(TABLE, ID+"=?", new String[]{String.valueOf(id)});
		db.close();
		return affectNum;
	}
	/**
	 * 
	 * @param values [���嶨]����Ҫ��һ��id
	 * @return
	 */
	public int update(ContentValues values){
		String id = values.getAsString(ID);
		int affectNum;//Ӱ�������
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		affectNum = db.update(TABLE, values, ID+"=?", new String[]{id});
		db.close();
		return affectNum;
	}
	public Cursor queryForCursor(){
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		return db.query(TABLE, null, null, null, null, null, "price desc");
		//���ص����α꣬��������ر�db���������ʲ���
	}
	public List<Map<String,Object>> query(){
		List<Map<String,Object>> data =null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor =  db.query(TABLE, null, null, null, null, null, "price desc");
		//�����α�
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
