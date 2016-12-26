//数据库Helper类

public class PersonDao {
	private DBOpenHelper helper;

	public PersonDao(Context context) {
		helper = new DBOpenHelper(context);
	}

	public void insert(PersonCn p) {

		SQLiteDatabase db = helper.getWritableDatabase(); // 获取数据库链接
		// db.execSQL("INSERT INTO person(name,balance) VALUES('"+p.getName()+"',"+p.getBanlance()+")");
		// 防止注入攻击
		db.execSQL("INSERT INTO person(name,balance) VALUES(?,?)", // 执行Sql语句,插入
				new Object[] { p.getName(), p.getBalance() });
		db.close();// 如果只有一个线程，可以不关数据库，还可以提高效率
	}

	public void delete(int id) {
		SQLiteDatabase db = helper.getWritableDatabase();// 获取数据库链接
		db.execSQL("DELETE FROM person WHERE id=?", new Object[] { id });
		db.close();// 如果只有一个线程，可以不关数据库，还可以提高效率
	}

	public void update(PersonCn p) {
		SQLiteDatabase db = helper.getWritableDatabase();// 获取数据库链接
		db.execSQL("UPDATE person SET name=?,balance=? WHERE id=?",
				new Object[] { p.getName(), p.getBalance(), p.getId() });
		db.close();// 如果只有一个线程，可以不关数据库，还可以提高效率
	}

	public PersonCn query(int id) {
		SQLiteDatabase db = helper.getReadableDatabase();// 用这个会先执行getWritableDatabase,如果出异常会再执行Read，更安全
		Cursor c = db.rawQuery("SELECT name,balance FROM person WHERE id=?",
				new String[] { id + "" });// 执行SQL语句,查询,得到游标
		PersonCn p = null;
		if (c.moveToNext()) {// 判断游标是否包含下一条记录，如果包含,将游标向后移动一位
			// //查询得到的cursor是指向第一条记录之前的,因此查询得到cursor后第一次调用moveToFirst或moveToNext都可以将cursor移动到第一条记录上
			String name = c.getString(c.getColumnIndex("name"));// 获取name字段的索引,然后根据索引获取数据,转为String
			int balance = c.getInt(1);// 获取1号索引上的数据,转为int
			p = new PersonCn(id, name, balance);// 创建person对象
		}
		c.close();
		db.close();// 如果只有一个线程，可以不关数据库，还可以提高效率
		return p;
	}

	// 查询所有
	public List<PersonCn> queryAll() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT id,name,balance FROM person", null);// 执行SQL语句,查询,得到游标
		List<PersonCn> persons = new ArrayList<PersonCn>();
		while (c.moveToNext()) {
			PersonCn p = new PersonCn(c.getInt(0), c.getString(1), c.getInt(2));
			persons.add(p);
		}
		c.close();
		db.close();
		return persons;
	}

	// 查询所有返回Cursor
	public Cursor queryAllCursor() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT id,name,balance FROM person", null);// 执行SQL语句,查询,得到游标
		return c;
	}

	// 查询记录(条数)
	public int queryCount() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT COUNT(*) FROM person", null);// 执行SQL语句,查询,得到游标
		c.moveToNext();
		int count = c.getInt(0);
		c.close();
		db.close();
		return count;
	}

	// 查询指定页
	public List<PersonCn> queryPage(int PageNum, int capacity) {
		String Count = (PageNum - 1) * capacity + ""; // Count开始索引 capacity 容量
		String len = capacity + "";
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("select * from person LIMIT ? offset ?",
				new String[] { len, Count });
		// db.rawQuery("SELECT id,name,balance FROM person LIMIT ？,？",
		// 理论上可以执行，不知原因用简化版的SQL语句查询出错
		// new String[] { Count, len });
		List<PersonCn> persons = new ArrayList<PersonCn>();
		while (c.moveToNext()) {
			PersonCn p = new PersonCn(c.getInt(0), c.getString(1), c.getInt(2));
			persons.add(p);
		}
		c.close();
		db.close();
		return persons;
	}

	// 事务----汇款
	// 发现如果id不存在，不会报错，另一个余额依旧减掉了，这是个问题！！！
	public void remit(int from, int to, int amount) {
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			db.beginTransaction(); // 开始事务
			db.execSQL("UPDATE person SET balance=balance-? WHERE id=?",
					new Object[] { amount, from });
			db.execSQL("UPDATE person SET balance=balance+? WHERE id=?",
					new Object[] { amount, to });
			db.setTransactionSuccessful();// 设置成功点,在事务结束前,成功点之前的操作会被提交
		} finally {
			db.endTransaction(); // 结束事务，将之前成功点之前的操作提交
			db.close();
		}
	}
}