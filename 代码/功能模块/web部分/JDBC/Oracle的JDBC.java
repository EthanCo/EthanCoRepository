//导入数据库驱动 Oracle驱动 ----- 在Oracle安装目录找到 C:\oraclexe\app\oracle\product\10.2.0\server\jdbc\lib\ojdbc14.jar

Class.forName("oracle.jdbc.driver.OraclerDriver");
Connection conn = DriverManager.getConnection(
	"jdbc:oracle:thin:@localhost:1521:xe","system","123");
	
....其他的都相同