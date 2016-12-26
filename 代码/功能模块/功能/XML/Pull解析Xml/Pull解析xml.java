public class PersonService{
	public List<Person> loadPersons(InputStream in) throws Exception{
		XmlPullParser parser = Xml.newPullParser();				//��ý�����
		parser.setInput(in, "UTF-8");							//������������ָ�����
		ArrayList<Person> persons = new ArrayList<Person>();
		Person p = null;
		for(int type=parser.getEventType();type!=XmlPullParser.END_DOCUMENT;type=parser.next()){
			if(type==XmlPullParser.START_TAG){							//��������˱�ǩ��ʼ�¼�
				if(parser.getName().equals("person")){					//�����ǩ��ΪPserson
					p = new Person();
					String id = parser.getAttributeValue(0);			//������ȡֵ
					//String id = (parser.getAttributeValue(namespace, name)) ��id��ȡֵ
					p.setId(Integer.parseInt(id));												
					persons.add(p);										//װ�뼯��
				}else if(parser.getName().equals("name")){				//�����ǩ��Ϊname
					//����name
					p.setName(parser.nextText());
				}else if(parser.getName().equals("age")){				//�����ǩ��Ϊage
					//����age
					p.setAge(Integer.parseInt(parser.nextText()));
				}
			}
		}
		return persons;
	}
}

//ģʽ
XmlPullparser.START_DOCUMENT 
XmlPullparser.START_TAG
XmlPullparser.TEXT
XmlPullparser.ENDTAG
XmlPullparser.END_DOCUMENT