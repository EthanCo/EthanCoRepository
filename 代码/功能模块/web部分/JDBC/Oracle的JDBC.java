//�������ݿ����� Oracle���� ----- ��Oracle��װĿ¼�ҵ� C:\oraclexe\app\oracle\product\10.2.0\server\jdbc\lib\ojdbc14.jar

Class.forName("oracle.jdbc.driver.OraclerDriver");
Connection conn = DriverManager.getConnection(
	"jdbc:oracle:thin:@localhost:1521:xe","system","123");
	
....�����Ķ���ͬ