
//类要实现Parcelable接口
public class Person implements Parcelable {
	private int id;
	private String name;
	private int age;

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeInt(age);
	}

	public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {

		@Override
		public Person createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Person(source);
		}

		@Override
		public Person[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Person[size];
		}
	};
	public Person(Parcel in) { // 与上面write中的顺序一定要一致
		id = in.readInt();
		name = in.readString();
		age = in.readInt();
	}
}


在Activity1中
		Intent intent = new Intent();
		intent.setAction("zzz");
		Person person = new Person(100, "普朗克", 39);
		intent.putExtra("person", person);
		startActivity(intent);
在Activity2中获取
		Intent intent = getIntent();
		Person person = intent.getParcelableExtra("person");
		resultTV.setText("对象：" + person.toString());
		
       
	<activity
        android:name=".Main2Activity"
        android:label="@string/app_name" >
        <intent-filter>
            <action android:name="zzz"/>
            <category android:name="android.intent.category.DEFAULT"></category>
        </intent-filter>
    </activity>