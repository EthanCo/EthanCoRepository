public class DBTest extends AndroidTestCase {
	public void testCreateDatabase() {
		DBOpenHelper helper = new DBOpenHelper(getContext());
		helper.getWritableDatabase();
		// 获取可写的数据库链接
		// 第一次执行时,数据库文件不存在时,会创建数据库文件,并且执行onCreate(),[所以把创建表的代码写在onCreate()中]
		// 数据库文件存在,并且版本没有改变时,不执行任何方法
		// 数据库文件存在,版本提升,执行onUpgade()[所以修改一些表、列在onUpgate中写]
	}