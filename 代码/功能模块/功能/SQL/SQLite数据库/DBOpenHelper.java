public class DBOpenHelper extends SQLiteOpenHelper { // ���幤����,�̳�SQLiteOpenHelper

	public DBOpenHelper(Context context) { // ���������ʱ��,��Ҫ���������Ļ���
		super(context, "zhk.db", null, 2);
		// ���ڸ���û���޲ι��캯��,������ʾ�����вεĹ��캯��
		// ����1�������Ļ���,����ȷ�����ݿ��ļ������Ŀ¼
		// ����2�����ݿ��ļ�������
		// ����3�������α�Ĺ���(����ָ�����ݿ������ĳһ��),��null����ʹ��Ĭ�ϵ�
		// ����4�����ݿ�İ汾,��1��ʼ
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// ����
		java.lang.System.out.println("����");
		db.execSQL("CREATE TABLE person(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(20))");// ִ��SQL���,������
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// ����
		java.lang.System.out.println("�޸�");
		db.execSQL("ALTER TABLE person ADD balance INTEGER"); // ������
	}
}