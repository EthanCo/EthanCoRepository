//自定义控件
		//获得自定义的属性,并将这些属性值赋给我们的控件
		TypedArray ta =context.obtainStyledAttributes(attrs,R.styleable.Topbar);
		
		leftTextColor = ta.getColor(R.styleable.Topbar_leftTextColor,0); //默认值 0
		leftBackground = ta.getDrawable(R.styleable.Topbar_leftBackground);
		leftText = ta.getString(R.styleable.Topbar_leftText);
		
		rightTextColor = ta.getColor(R.styleable.Topbar_rightTextColor,0);
		rightBackground = ta.getDrawable(R.styleable.Topbar_rightBackground);
		rightText = ta.getString(R.styleable.Topbar_rightText);
		
		titleTextColor = ta.getColor(R.styleable.Topbar_titleTextColor,0);
		titleTextSize = ta.getDimension(R.styleable.Topbar_titleTextSize, 0);
		titleText = ta.getString(R.styleable.Topbar_title);
		
		ta.recycle(); //回收,避免浪费资源及因缓存而出现错误
		
		
//attrs.xml
		
		<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="Topbar">
        <attr name="title" format="string"/>
        <attr name="titleTextSize" format="dimension"/>
        <attr name="titleTextColor" format="color"/>
        
        <attr name="leftTextColor" format="color"/>
        <attr name="leftBackground" format="reference|color"/> <!-- 资源或颜色 -->
        <attr name="leftText" format="string"/>
        
        <attr name="rightTextColor" format="color"/>
        <attr name="rightBackground" format="reference|color"/>
        <attr name="rightText" format="string"/>
    </declare-styleable>
</resources>

//Layout.xml中
eclipse:xmlns:ethanco="http://schemas.android.com/apk/res/com.zhk.ui_template"
AS：xmlns:ethanco="http://schemas.android.com/apk/res-auto"


//自定义属性 枚举 

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

其中，一般属性需要指定 name 和 format 
枚举属性只需指定 name
然后用 enum 标签 指定所有可能属性的 name和value（注：value只能为int型）
class中取属性值

int type = array.getInt(R.styleable.custom_view_type, 0);
此处取值为int 
这样就实现了，当开发者用此控件时，type属性，只能使用这里给出的3个属性值。
