//MainActivity中: service.savePersons(persons, new FileOutputStream("/mnt/sdcard/persons.xml"));
public List<Person> loadPersons(InputStream in) throws Exception{
	public void savePersons(List<Person> persons,OutputStream out) throws Exception{
		XmlSerializer serializer = Xml.newSerializer();			//获取序列化工具
		serializer.setOutput(out,"UTF-8");						//设置输出流，指定码表 
		
		serializer.startDocument("UTF-8", true);				//开始文档
		serializer.startTag(null, "persons");					//开始标签 
		
		for(Person p:persons)
		{
			serializer.startTag(null, "person");			
			serializer.attribute(null, "id", p.getId().toString());		//设置属性
			
			serializer.startTag(null, "name");							
			serializer.text(p.getName());								//获取文本
			serializer.endTag(null, "name");
			
			serializer.startTag(null, "age");
			serializer.text(p.getAge().toString());
			serializer.endTag(null, "age");
			
			serializer.endTag(null, "person");
		}
		serializer.endTag(null, "persons");								//结束标签
		serializer.endDocument();										//结束文档
	}
}