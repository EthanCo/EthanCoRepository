public class PersonService {
	/**
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public List<Person> loadPersons(InputStream in) throws Exception{
		XmlPullParser parser = Xml.newPullParser();	//获得解析器
		parser.setInput(in, "UTF-8");							//设置输入流，指定码表
		ArrayList<Person> persons = new ArrayList<Person>();
		Person p = null;
		for(int type=parser.getEventType();type!=XmlPullParser.END_DOCUMENT;type=parser.next()){
			if(type==XmlPullParser.START_TAG){							//如果遇到了标签开始事件
				if(parser.getName().equals("person")){									//如果标签名为Pserson
					p = new Person();
					String id = parser.getAttributeValue(0);							//按索引取值
					//String id = (parser.getAttributeValue(namespace, name)) 按id名取值
					p.setId(Integer.parseInt(id));												
					persons.add(p);																	//装入集合
				}else if(parser.getName().equals("name")){								//如果标签名为name
					//设置name
					p.setName(parser.nextText());
				}else if(parser.getName().equals("age")){								//如果标签名为age
					//设置age
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
		XmlSerializer serializer = Xml.newSerializer();		//获取序列化工具
		serializer.setOutput(out,"UTF-8");						//设置输出流，指定码表
		
		serializer.startDocument("UTF-8", true);			//开始文档
		serializer.startTag(null, "persons");						//开始标签 
		
		for(Person p:persons)
		{
			serializer.startTag(null, "person");			
			serializer.attribute(null, "id", p.getId().toString());	//设置属性
			
			serializer.startTag(null, "name");							
			serializer.text(p.getName());									//获取文本
			serializer.endTag(null, "name");
			
			serializer.startTag(null, "age");
			serializer.text(p.getAge().toString());
			serializer.endTag(null, "age");
			
			serializer.endTag(null, "person");
		}
		serializer.endTag(null, "persons");									//结束标签
		serializer.endDocument();											//结束文档
		}
	}