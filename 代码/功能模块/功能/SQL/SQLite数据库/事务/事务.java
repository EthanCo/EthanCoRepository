// ����----���
// �������id�����ڣ����ᱨ����һ��������ɼ����ˣ����Ǹ����⣡����
	public void remit(int from, int to, int amount) {
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			db.beginTransaction(); // ��ʼ����
			db.execSQL("UPDATE person SET balance=balance-? WHERE id=?",
					new Object[] { amount, from });
			db.execSQL("UPDATE person SET balance=balance+? WHERE id=?",
					new Object[] { amount, to });
			db.setTransactionSuccessful();// ���óɹ���,���������ǰ,�ɹ���֮ǰ�Ĳ����ᱻ�ύ //�ɶ������setTransactionSuccessful
		} finally {
			db.endTransaction(); // �������񣬽�֮ǰ�ɹ���֮ǰ�Ĳ����ύ
			db.close();
		}
	}