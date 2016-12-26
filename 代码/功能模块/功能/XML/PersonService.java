public class PersonService {
	/**
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public List<Person> loadPersons(InputStream in) throws Exception{
		XmlPullParser parser = Xml.newPullParser();	//��ý�����
		parser.setInput(in, "UTF-8");							//������������ָ�����
		ArrayList<Person> persons = new ArrayList<Person>();
		Person p = null;
		for(int type=parser.getEventType();type!=XmlPullParser.END_DOCUMENT;type=parser.next()){
			if(type==XmlPullParser.START_TAG){							//��������˱�ǩ��ʼ�¼�
				if(parser.getName().equals("person")){									//�����ǩ��ΪPserson
					p = new Person();
					String id = parser.getAttributeValue(0);							//������ȡֵ
					//String id = (parser.getAttributeValue(namespace, name)) ��id��ȡֵ
					p.setId(Integer.parseInt(id));												
					persons.add(p);																	//װ�뼯��
				}else if(parser.getName().equals("name")){								//�����ǩ��Ϊname
					//����name
					p.setName(parser.nextText());
				}else if(parser.getName().equals("age")){								//�����ǩ��Ϊage
					//����age
					p.setAge(Integer.parseInt(parser.nextText()));
				}
//				switch(parser.getName()){
//				case "person":
//					break;
//				case "name":
//					break;
//				case "age":
//					break;
//			}
			}
		}
		return persons;
	}
	
	public void savePersons(List<Person> persons,OutputStream out) throws Exception{
		XmlSerializer serializer = Xml.newSerializer();		//��ȡ���л�����
		serializer.setOutput(out,"UTF-8");						//�����������ָ�����
		
		serializer.startDocument("UTF-8", true);			//��ʼ�ĵ�
		serializer.startTag(null, "persons");						//��ʼ��ǩ 
		
		for(Person p:persons)
		{
			serializer.startTag(null, "person");			
			serializer.attribute(null, "id", p.getId().toString());	//��������
			
			serializer.startTag(null, "name");							
			serializer.text(p.getName());									//��ȡ�ı�
			serializer.endTag(null, "name");
			
			serializer.startTag(null, "age");
			serializer.text(p.getAge().toString());
			serializer.endTag(null, "age");
			
			serializer.endTag(null, "person");
		}
		serializer.endTag(null, "persons");									//������ǩ
		serializer.endDocument();											//�����ĵ�
		}
	}