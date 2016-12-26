��һ���� HTTP ����
����������ʽ ������������ �� �����С�ͷ��Ϣ�������� 

	1�������У������е�һ����Ϣ  ---- ������������ ����ʽ����Դ·����HTTPЭ��汾
		���磺 GET / HTTP/1.1 
		HTTP����ʽ�м��� �� POST��GET��HEAD��OPTIONS��DELETE��TRACE��PUT
		�������֣� GET �� POST 

		�ͻ�����ҳ��Щ��GET��ʽ���� ��Щ�� POST ����
		GET��ʽ ��1���û�ֱ������������ֶ�����url��ַ  2��<a href="url"></a>  3��<form method="get" > *form��Ĭ���ύ��ʽ����get 
		POST��ʽ�� 1��<form method="post" >

		GET�ύ��ʽ��POST�ύ��ʽ���� ��
		Get url��ַ��Я�������д�С���� 1K ,�ύ������url����ʾ 
		* http://localhost/day04/url?username=zhangsan ��ʽ url?name=value&name=value&name=value... 
		�ԣ��ָ�url�Ͳ������������ü�ֵ�Ը�ʽ���������ʹ��& ���Ϸָ�

		POST �������������У�����url����ʾ ��û�����ݴ�С���� 
		* username=zhangsan �������ͷ��Ϣֱ�Ӵ���һ������ 

	2��ͷ��Ϣ�� �ܶ�key -value 
		Accept: text/html,image/   ----- �ͻ��˿��Խ����ļ����� text/html HTML�ļ� image/ �����ʽͼƬ
		Accept-Charset: ISO-8859-1 ----- �ͻ��˿���ʶ������ַ���  
		[��Ҫ]Accept-Encoding: gzip -----  �ͻ��˿���ʶ��ѹ�����ݸ�ʽ gzip��һ��ѹ����ʽ 
		Accept-Language:zh-cn ----- �ͻ������������ 
		Host: www.itcast.com:80  ----- ���ʷ�������ַ 
		[��Ҫ]If-Modified-Since: Tue, 11 Jul 2000 18:23:51 GMT ----- ���������Դ�ڿͻ��˱���������ʱ�� �������йأ�
		[��Ҫ]Referer: http://www.itcast.com/index.jsp ---- ��һ���������ҳ���ַ 
		[�Ƚ���Ҫ]User-Agent: Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0) ---- �ͻ�����������Ͱ汾 
		Connection: close/Keep-Alive    ---- �ô��������Ӻ������Ǳ��ֻ��ǹر� 1.0 �ر� 1.1 ���� 
		Date: Tue, 11 Jul 2000 18:23:51 GMT ---- ����ʱ�� 

		/*Referer���� ��ֹ�ͻ��������� ��ͨ��google����ҳ��ֱ������ ������վ���� ��������*/

		ģ����������ʷ����� ��� refererServlet������Ϣ
		1) URL ���� ----���ڣ�HTTPЭ��
		2) Socket ���� -----���ڣ�TCPЭ�� 


	3�������壺 ��ſͻ����ύpost ���� ����
		key=value&key=value&key=value ....   
		���� �� username=sss 


�ڶ����� HTTP��Ӧ
��Ӧ���� ��Ϊ�������� ��״̬�С�ͷ��Ϣ����Ӧ�� 
	1��״̬�� ��Ϊ�������֣� HTTPЭ��汾��״̬�롢������Ϣ
		����: HTTP/1.1 200 OK 
		100��199 Informational  ������Ϣ���� ������Ҫ������Ϣ���ܴ���
		200��299 Success  ������ɹ�
		300��399 Redirection �������Ѿ�������ϣ��ͻ��˻���Ҫ��һ������
		400��499 Client Error  �ͻ��˴���
		500��599 Server Error  �������˴���

		200 ������ɹ� 302 �ͻ��˽����ض��� 304 �ͻ��˷�����Դû�б��޸ĺ󣬿ͻ��˷��ʱ��ػ��� 404 ������Դ������ 500 �������ڲ�����
		��Ǯ
		200  A �� B ��Ǯ B ��Ǯ ���A 
		302  A �� B ��Ǯ B ֪ͨ A ��C ��Ǯ --- A ��C ��Ǯ
		304  A �� B ��Ǯ �� B��A ˵ ����� --- A ʹ�� ֮ǰ��B ���Ǯ
		404  ûǮ
		505  B �������� 

	2����Ӧͷ 
		Location: http://www.it315.org/index.jsp ---- �ض����ַ ��� 302 һ��ʹ�� 
		* �ض��� �������� ������Ӧ 
		Server:apache tomcat ----- ����������
		[��Ҫ]Content-Encoding: gzip ---- ������Ӧ����ѹ����ʽ   ---- ������ Accept-Encoding ��Ӧ
		* �����Ӧ���ݾ���ѹ�� ���������С�����ܸ��� 

		û��ѹ�������  13605�ֽ�  http://localhost/docs/
		�������tomcatѹ�� 
		* ͨ������ tomcat/conf/server.xml ������Ӧ����ѹ�� 
		<Connector port="80" protocol="HTTP/1.1" 
					   connectionTimeout="20000" 
					   redirectPort="8443" compressableMimeType="text/html,text/xml,text/plain" compression="on"/>
		* �����������棬����tomcat 
		4244�ֽ� http://localhost/docs/

		Content-Length: 80  ----- ��Ӧ���ݳ���
		Content-Language: zh-cn  ----- ��Ӧ��������
		Content-Type: text/html; charset=GB2312  ----- ��Ӧ�������� 
		* �������Ͳ���MimeЭ��涨���� html�ļ� ---- text/html�� jpg�ļ�---- image/jpeg
		* �� tomcat/conf/web.xml �в鿴�ļ�mime���� 

		[��Ҫ]Last-Modified: Tue, 11 Jul 2000 18:23:51 GMT  ----- ������ͷ��Ϣ If-Modified-Sinceһ��ʹ�ã�[���Ʒ���������]
		* ETag: W/"13397-1184876416000" tomcat����Etag 13397�ļ���С 1184876416000�ļ�����޸�ʱ�䣨��ȷ���룩
		* �ͻ����´�����ʱͨ��If-None-Match Я���ո� Etag��Ϣ 

		Refresh: 1;url=http://www.it315.org ----- ��ҳ�Զ���ת 
		Content-Disposition: attachment; filename=aaa.zip ----- �ļ�����ʱָ���ļ���������

		���Ƹó����ڿͻ��˲�����
		Expires: -1 
		Cache-Control: no-cache  
		Pragma: no-cache   
		* ���ڶ�̬���򣬾����޸ģ� ��ֹ�������������
		/*ʹ����������Ϊ���������������
		response.setDateHeader("expires",-1);
		response.setDateHeader("Cache-Control","no-cache ");
		response.setDateHeader("Pragma","no-cache");
		*/

		Connection: close/Keep-Alive   ---- ��Ӧ�������Ƿ�ر�
		Date: Tue, 11 Jul 2000 18:23:51 GMT --- ��Ӧʱ�� 

		�ص㣺302���Location�����ض��� ����tomcat����gzipѹ�� ��tomcat������� ����ֹ��������� 

	3����Ӧ��
		ͨ��HTML �ļ�����  ---- ���������Ӧ�����gzipѹ��������gzipѹ������

		-------------------------------------------------------------------------------------------------
		ͨ��HTTPЭ��Rangeͷ��Ϣ ʵ�ֶϵ����ع��� 
		�����ӷ�����Ŀ����Դ�������ع����У�����Rangeͷ��Ϣ�� ָ������Ŀ����Դ�������� ---- ʵ�ֶϵ����ع��� 
		 
		ʹ��URL�� ģ��ͻ��˷��� WebRoot/info.txt �����ļ�������



[HTTP �� HTML ��ϵ ��]
	HTTP ͨ��Э�� �涨���ݴ����ʽ
	HTML ��ҳ������ԣ���̬��ҳ���� 
	HTTP �����ʽ ��HTML ��������

HTTP/1.0��HTTP/1.1 ����
	1.0 һ�� ����������� ֻ�ܻ��һ����Դ 
	1.1 һ������������� ��������ö����Դ 

˼���⣺
	һ��webҳ���У�ʹ��img��ǩ����������ͼƬ�����ͻ��˷��ʷ������е����webҳ��ʱ���ͻ����ܹ�����ʼ��η���������������������˼���HTTP����
	* ����ͼƬ��ַ�Ƿ���ͬ 
	�������ͼƬ��ַ������ͬ --- 4������
