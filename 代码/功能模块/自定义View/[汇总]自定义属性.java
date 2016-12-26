//�Զ���ؼ�
		//����Զ��������,������Щ����ֵ�������ǵĿؼ�
		TypedArray ta =context.obtainStyledAttributes(attrs,R.styleable.Topbar);
		
		leftTextColor = ta.getColor(R.styleable.Topbar_leftTextColor,0); //Ĭ��ֵ 0
		leftBackground = ta.getDrawable(R.styleable.Topbar_leftBackground);
		leftText = ta.getString(R.styleable.Topbar_leftText);
		
		rightTextColor = ta.getColor(R.styleable.Topbar_rightTextColor,0);
		rightBackground = ta.getDrawable(R.styleable.Topbar_rightBackground);
		rightText = ta.getString(R.styleable.Topbar_rightText);
		
		titleTextColor = ta.getColor(R.styleable.Topbar_titleTextColor,0);
		titleTextSize = ta.getDimension(R.styleable.Topbar_titleTextSize, 0);
		titleText = ta.getString(R.styleable.Topbar_title);
		
		ta.recycle(); //����,�����˷���Դ���򻺴�����ִ���
		
		
//attrs.xml
		
		<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="Topbar">
        <attr name="title" format="string"/>
        <attr name="titleTextSize" format="dimension"/>
        <attr name="titleTextColor" format="color"/>
        
        <attr name="leftTextColor" format="color"/>
        <attr name="leftBackground" format="reference|color"/> <!-- ��Դ����ɫ -->
        <attr name="leftText" format="string"/>
        
        <attr name="rightTextColor" format="color"/>
        <attr name="rightBackground" format="reference|color"/>
        <attr name="rightText" format="string"/>
    </declare-styleable>
</resources>

//Layout.xml��
eclipse:xmlns:ethanco="http://schemas.android.com/apk/res/com.zhk.ui_template"
AS��xmlns:ethanco="http://schemas.android.com/apk/res-auto"


//�Զ������� ö�� 

attrs.xml

<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="custom_view">
        <attr name="checkType" format="string" />
        <attr name="type">
            <enum name="password" value="1" />
            <enum name="checkButton" value="2" />
            <enum name="phone" value="3" />
        </attr>
    </declare-styleable>
</resources>

���У�һ��������Ҫָ�� name �� format 
ö������ֻ��ָ�� name
Ȼ���� enum ��ǩ ָ�����п������Ե� name��value��ע��valueֻ��Ϊint�ͣ�
class��ȡ����ֵ

int type = array.getInt(R.styleable.custom_view_type, 0);
�˴�ȡֵΪint 
������ʵ���ˣ����������ô˿ؼ�ʱ��type���ԣ�ֻ��ʹ�����������3������ֵ��
