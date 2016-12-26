//���ݿ�Helper��

public class PersonDao {
	private DBOpenHelper helper;

	public PersonDao(Context context) {
		helper = new DBOpenHelper(context);
	}

	public void insert(PersonCn p) {

		SQLiteDatabase db = helper.getWritableDatabase(); // ��ȡ���ݿ�����
		// db.execSQL("INSERT INTO person(name,balance) VALUES('"+p.getName()+"',"+p.getBanlance()+")");
		// ��ֹע�빥��
		db.execSQL("INSERT INTO person(name,balance) VALUES(?,?)", // ִ��Sql���,����
				new Object[] { p.getName(), p.getBalance() });
		db.close();// ���ֻ��һ���̣߳����Բ������ݿ⣬���������Ч��
	}

	public void delete(int id) {
		SQLiteDatabase db = helper.getWritableDatabase();// ��ȡ���ݿ�����
		db.execSQL("DELETE FROM person WHERE id=?", new Object[] { id });
		db.close();// ���ֻ��һ���̣߳����Բ������ݿ⣬���������Ч��
	}

	public void update(PersonCn p) {
		SQLiteDatabase db = helper.getWritableDatabase();// ��ȡ���ݿ�����
		db.execSQL("UPDATE person SET name=?,balance=? WHERE id=?",
				new Object[] { p.getName(), p.getBalance(), p.getId() });
		db.close();// ���ֻ��һ���̣߳����Բ������ݿ⣬���������Ч��
	}

	public PersonCn query(int id) {
		SQLiteDatabase db = helper.getReadableDatabase();// ���������ִ��getWritableDatabase,������쳣����ִ��Read������ȫ
		Cursor c = db.rawQuery("SELECT name,balance FROM person WHERE id=?",
				new String[] { id + "" });// ִ��SQL���,��ѯ,�õ��α�
		PersonCn p = null;
		if (c.moveToNext()) {// �ж��α��Ƿ������һ����¼���������,���α�����ƶ�һλ
			// //��ѯ�õ���cursor��ָ���һ����¼֮ǰ��,��˲�ѯ�õ�cursor���һ�ε���moveToFirst��moveToNext�����Խ�cursor�ƶ�����һ����¼��
			String name = c.getString(c.getColumnIndex("name"));// ��ȡname�ֶε�����,Ȼ�����������ȡ����,תΪString
			int balance = c.getInt(1);// ��ȡ1�������ϵ�����,תΪint
			p = new PersonCn(id, name, balance);// ����person����
		}
		c.close();
		db.close();// ���ֻ��һ���̣߳����Բ������ݿ⣬���������Ч��
		return p;
	}

	// ��ѯ����
	public List<PersonCn> queryAll() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT id,name,balance FROM person", null);// ִ��SQL���,��ѯ,�õ��α�
		List<PersonCn> persons = new ArrayList<PersonCn>();
		while (c.moveToNext()) {
			PersonCn p = new PersonCn(c.getInt(0), c.getString(1), c.getInt(2));
			persons.add(p);
		}
		c.close();
		db.close();
		return persons;
	}

	// ��ѯ���з���Cursor
	public Cursor queryAllCursor() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT id,name,balance FROM person", null);// ִ��SQL���,��ѯ,�õ��α�
		return c;
	}

	// ��ѯ��¼(����)
	public int queryCount() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT COUNT(*) FROM person", null);// ִ��SQL���,��ѯ,�õ��α�
		c.moveToNext();
		int count = c.getInt(0);
		c.close();
		db.close();
		return count;
	}

	// ��ѯָ��ҳ
	public List<PersonCn> queryPage(int PageNum, int capacity) {
		String Count = (PageNum - 1) * capacity + ""; // Count��ʼ���� capacity ����
		String len = capacity + "";
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.rawQuery("select * from person LIMIT ? offset ?",
				new String[] { len, Count });
		// db.rawQuery("SELECT id,name,balance FROM person LIMIT ��,��",
		// �����Ͽ���ִ�У���֪ԭ���ü򻯰��SQL����ѯ����
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
			db.setTransactionSuccessful();// ���óɹ���,���������ǰ,�ɹ���֮ǰ�Ĳ����ᱻ�ύ
		} finally {
			db.endTransaction(); // �������񣬽�֮ǰ�ɹ���֮ǰ�Ĳ����ύ
			db.close();
		}
	}
}