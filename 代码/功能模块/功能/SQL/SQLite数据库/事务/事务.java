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
			db.setTransactionSuccessful();// 设置成功点,在事务结束前,成功点之前的操作会被提交 //可多次设置setTransactionSuccessful
		} finally {
			db.endTransaction(); // 结束事务，将之前成功点之前的操作提交
			db.close();
		}
	}