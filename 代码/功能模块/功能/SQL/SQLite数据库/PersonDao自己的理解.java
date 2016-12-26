DBOpenHelper helper= new DBOpenHelper(context);
SQLiteDatabase db = helper.getWritableDatabase(); // 获取数据库链接
插入：	db.execSQL("INSERT INTO person(name,balance) VALUES(?,?)",new Object[] { p.getName(), p.getBalance() });
删除：	db.execSQL("DELETE FROM person WHERE id=?", new Object[] { id });
更新：	db.execSQL("UPDATE person SET name=?,balance=? WHERE id=?",new Object[] { p.getName(), p.getBalance(), p.getId() });
db.close();// 如果只有一个线程，可以不关数据库，还可以提高效率