public class DBTest extends AndroidTestCase {
	public void testCreateDatabase() {
		DBOpenHelper helper = new DBOpenHelper(getContext());
		helper.getWritableDatabase();
		// ��ȡ��д�����ݿ�����
		// ��һ��ִ��ʱ,���ݿ��ļ�������ʱ,�ᴴ�����ݿ��ļ�,����ִ��onCreate(),[���԰Ѵ�����Ĵ���д��onCreate()��]
		// ���ݿ��ļ�����,���Ұ汾û�иı�ʱ,��ִ���κη���
		// ���ݿ��ļ�����,�汾����,ִ��onUpgade()[�����޸�һЩ������onUpgate��д]
	}