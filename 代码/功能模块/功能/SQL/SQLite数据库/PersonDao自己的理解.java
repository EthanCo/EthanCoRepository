DBOpenHelper helper= new DBOpenHelper(context);
SQLiteDatabase db = helper.getWritableDatabase(); // ��ȡ���ݿ�����
���룺	db.execSQL("INSERT INTO person(name,balance) VALUES(?,?)",new Object[] { p.getName(), p.getBalance() });
ɾ����	db.execSQL("DELETE FROM person WHERE id=?", new Object[] { id });
���£�	db.execSQL("UPDATE person SET name=?,balance=? WHERE id=?",new Object[] { p.getName(), p.getBalance(), p.getId() });
db.close();// ���ֻ��һ���̣߳����Բ������ݿ⣬���������Ч��