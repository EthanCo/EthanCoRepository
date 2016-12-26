1. java.io.BufferedReader和java.io.BufferedWriter类各拥有8192字符的缓冲区。当BufferedReader在读取文本文件时，会先尽量从文件中读入字符数据并置入缓冲区，
	而之后若使用read()方法，会先从缓冲区中进行读取。如果缓冲区数据不足，才会再从文件中读取，使用BufferedWriter时，写入的数据并不会先输出到目的地，而是
	先存储至缓冲区中。如果缓冲区中的数据满了，才会一次对目的地进行写出。
2. 从标准输入流System.in中直接读取使用者输入时，使用者每输入一个字符，System.in就读取一个字符。为了能一次读取一行使用者的输入，使用了BufferedReader来对
	使用者输入的字符进行缓冲。readLine()方法会在读取到使用者的换行字符时，再一次将整行字符串传入。
3. System.in是一个位流，为了转换为字符流，可使用InputStreamReader为其进行字符转换，然后再使用BufferedReader为其增加缓冲功能。例如：
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 

	//就是讲位流(010101001)转化为字节流
	
	//解决中文乱码
	InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
	BufferedReader read = new BufferedReader(isr);
	
	
	InputStream  此抽象类是表示字节输入流的所有类的超类

InputStreamReader 是字节流通向字符流的桥梁：它使用指定的 charset 读取字节并将其解码为字符搜索。它使用的字符集可以由名称指定或显式给定，否则可能接受平台默认的字符集。 

每次调用 InputStreamReader 中的一个 read() 方法都会导致从基础输入流读取一个或多个字节。要启用从字节到字符的有效转换，可以提前从基础流读取更多的字节，使其超过满足当前读取操作所需的字节
	
	//连续读取
	while ((lines = reader.readLine()) != null) {
		System.out.println(lines);
	}