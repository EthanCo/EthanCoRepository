Intent��Ϊ��ϵ��Activity֮���Ŧ���������ò�������ֻ���ڼ򵥵����ݴ��ݡ�ͨ�����Դ������ԣ���ʵ���Է������ɺܶ��Ϊ���ӵĲ���������ֱ�ӵ��ò��Ź��ܡ�
ֱ���Զ����ú��ʵĳ���򿪲�ͬ���͵��ļ��ȵȡ�������࣬������ͨ������Intent��������ɡ�
 
����Intent��Ҫ�������ĸ���Ҫ���ԣ����Ƿֱ�Ϊ��
 
��������Action��Action���Ե�ֵΪһ���ַ�������������ϵͳ���Ѿ�������һϵ�г��õĶ�����ͨ��setAction()���������嵥�ļ�AndroidManifest.xml�����á�Ĭ��Ϊ��DEFAULT��
 
��������Data��Dataͨ����URI��ʽ����Ĳ������ݡ����磺tel:// ��ͨ��setData()�������á�
 
��������Category��Category��������ָ����ǰ������Action����ִ�еĻ�����ͨ��addCategory()���������嵥�ļ�AndroidManifest.xml�����á�Ĭ��Ϊ��CATEGORY_DEFAULT��
 
��������Extras��Extras������Ҫ���ڴ���Ŀ���������Ҫ�Ķ�������ݡ�ͨ��putExtras()�������á�
 
�����ĸ����Ը��Եĳ���ֵ������ʾ��
 
����Action��
 
������   ACTION_MAIN��Android Application����ڣ�ÿ��AndroidӦ�ñ�����ֻ�ܰ���һ�������͵�Action��������
 
��������ACTION_VIEW��ϵͳ���ݲ�ͬ��Data���ͣ�ͨ����ע��Ķ�ӦApplication��ʾ���ݡ�
 
��������ACTION_EDIT��ϵͳ���ݲ�ͬ��Data���ͣ�ͨ����ע��Ķ�ӦApplication�༭ʾ���ݡ���
 
��������ACTION_DIAL����ϵͳĬ�ϵĲ��ų������Data�������˵绰���룬���Զ��ڲ��ų���������˺��롣��
 
��������ACTION_CALL��ֱ�Ӻ���Data�������ĺ��롣��
 
��������ACTION_ANSWER���������硣��
 
��������ACTION_SEND�����û�ָ�����ͷ�ʽ�������ݷ��Ͳ�����
 
��������ACTION_SENDTO��ϵͳ���ݲ�ͬ��Data���ͣ�ͨ����ע��Ķ�ӦApplication�������ݷ��Ͳ�������
 
��������ACTION_BOOT_COMPLETED��Androidϵͳ��������Ϻ󷢳����д�Action�Ĺ㲥��Broadcast������
 
��������ACTION_TIME_CHANGED��Androidϵͳ��ʱ�䷢���ı�󷢳����д�Action�Ĺ㲥��Broadcast������
 
��������ACTION_PACKAGE_ADDED��Androidϵͳ��װ���µ�Application֮�󷢳����д�Action�Ĺ㲥��Broadcast������
 
��������ACTION_PACKAGE_CHANGED��Androidϵͳ���Ѵ��ڵ�Application�����ı�֮����Ӧ�ø��²������������д�Action�Ĺ㲥��Broadcast������
 
��������ACTION_PACKAGE_REMOVED��ж����Androidϵͳ�Ѵ��ڵ�Application֮�󷢳����д�Action�Ĺ㲥��Broadcast��������
 
����Category��
 
������   CATEGORY_DEFAULT��Androidϵͳ��Ĭ�ϵ�ִ�з�ʽ��������ͨActivity��ִ�з�ʽִ�С���
 
��������CATEGORY_HOME�����ø����ΪHome Activity��
 
��������CATEGORY_PREFERENCE�����ø����ΪPreference����
 
��������CATEGORY_LAUNCHER�����ø����Ϊ�ڵ�ǰӦ�ó��������������ȼ���ߵ�Activity��ͨ��Ϊ���ACTION_MAIN���ʹ�á���
 
��������CATEGORY_BROWSABLE�����ø��������ʹ���������������
 
��������CATEGORY_GADGET�����ø����������Ƕ�������Activity�С�
 
����Extras:
 
������   EXTRA_BCC������ʼ������˵�ַ���ַ������顣��
 
��������EXTRA_CC������ʼ������˵�ַ���ַ������顣
 
��������EXTRA_EMAIL������ʼ���ַ���ַ������顣��
 
��������EXTRA_SUBJECT������ʼ������ַ�������
 
��������EXTRA_TEXT������ʼ����ݡ���
 
��������EXTRA_KEY_EVENT����KeyEvent����ʽ��Ŵ���Intent�İ���������
 
��������EXTRA_PHONE_NUMBER����ŵ���ACTION_CALLʱ�ĵ绰���롣������
 
 ����Data:
 
������   tel://���������ݸ�ʽ������绰���롣��
 
��������mailto://���ʼ����ݸ�ʽ������ʼ��ռ��˵�ַ��
 
��������smsto://����Ϣ���ݸ�ʽ��������Ž��պ��롣
 
��������content://���������ݸ�ʽ�������Ҫ��ȡ�����ݡ���
 
��������file://���ļ����ݸ�ʽ������ļ�·����
 
��������market://search?q=pname:pkgname���г����ݸ�ʽ����Google Market����������Ϊpkgname��Ӧ�á�
 
��������geo://latitude, longitude����γ���ݸ�ʽ���ڵ�ͼ����ʾ��γ����ָ����λ�á�
