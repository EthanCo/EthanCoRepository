1. java.io.BufferedReader��java.io.BufferedWriter���ӵ��8192�ַ��Ļ���������BufferedReader�ڶ�ȡ�ı��ļ�ʱ�����Ⱦ������ļ��ж����ַ����ݲ����뻺������
	��֮����ʹ��read()���������ȴӻ������н��ж�ȡ��������������ݲ��㣬�Ż��ٴ��ļ��ж�ȡ��ʹ��BufferedWriterʱ��д������ݲ������������Ŀ�ĵأ�����
	�ȴ洢���������С�����������е��������ˣ��Ż�һ�ζ�Ŀ�ĵؽ���д����
2. �ӱ�׼������System.in��ֱ�Ӷ�ȡʹ��������ʱ��ʹ����ÿ����һ���ַ���System.in�Ͷ�ȡһ���ַ���Ϊ����һ�ζ�ȡһ��ʹ���ߵ����룬ʹ����BufferedReader����
	ʹ����������ַ����л��塣readLine()�������ڶ�ȡ��ʹ���ߵĻ����ַ�ʱ����һ�ν������ַ������롣
3. System.in��һ��λ����Ϊ��ת��Ϊ�ַ�������ʹ��InputStreamReaderΪ������ַ�ת����Ȼ����ʹ��BufferedReaderΪ�����ӻ��幦�ܡ����磺
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 

	//���ǽ�λ��(010101001)ת��Ϊ�ֽ���
	
	//�����������
	InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
	BufferedReader read = new BufferedReader(isr);
	
	
	InputStream  �˳������Ǳ�ʾ�ֽ���������������ĳ���

InputStreamReader ���ֽ���ͨ���ַ�������������ʹ��ָ���� charset ��ȡ�ֽڲ��������Ϊ�ַ���������ʹ�õ��ַ�������������ָ������ʽ������������ܽ���ƽ̨Ĭ�ϵ��ַ����� 

ÿ�ε��� InputStreamReader �е�һ�� read() �������ᵼ�´ӻ�����������ȡһ�������ֽڡ�Ҫ���ô��ֽڵ��ַ�����Чת����������ǰ�ӻ�������ȡ������ֽڣ�ʹ�䳬�����㵱ǰ��ȡ����������ֽ�
	
	//������ȡ
	while ((lines = reader.readLine()) != null) {
		System.out.println(lines);
	}