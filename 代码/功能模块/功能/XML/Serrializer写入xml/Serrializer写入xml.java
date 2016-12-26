//MainActivity��: service.savePersons(persons, new FileOutputStream("/mnt/sdcard/persons.xml"));
public List<Person> loadPersons(InputStream in) throws Exception{
	public void savePersons(List<Person> persons,OutputStream out) throws Exception{
		XmlSerializer serializer = Xml.newSerializer();			//��ȡ���л�����
		serializer.setOutput(out,"UTF-8");						//�����������ָ����� 
		
		serializer.startDocument("UTF-8", true);				//��ʼ�ĵ�
		serializer.startTag(null, "persons");					//��ʼ��ǩ 
		
		for(Person p:persons)
		{
			serializer.startTag(null, "person");			
			serializer.attribute(null, "id", p.getId().toString());		//��������
			
			serializer.startTag(null, "name");							
			serializer.text(p.getName());								//��ȡ�ı�
			serializer.endTag(null, "name");
			
			serializer.startTag(null, "age");
			serializer.text(p.getAge().toString());
			serializer.endTag(null, "age");
			
			serializer.endTag(null, "person");
		}
		serializer.endTag(null, "persons");								//������ǩ
		serializer.endDocument();										//�����ĵ�
	}
}