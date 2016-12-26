Intent作为联系各Activity之间的纽带，其作用并不仅仅只限于简单的数据传递。通过其自带的属性，其实可以方便的完成很多较为复杂的操作。例如直接调用拨号功能、
直接自动调用合适的程序打开不同类型的文件等等。诸如此类，都可以通过设置Intent属性来完成。
 
　　Intent主要有以下四个重要属性，它们分别为：
 
　　　　Action：Action属性的值为一个字符串，它代表了系统中已经定义了一系列常用的动作。通过setAction()方法或在清单文件AndroidManifest.xml中设置。默认为：DEFAULT。
 
　　　　Data：Data通常是URI格式定义的操作数据。例如：tel:// 。通过setData()方法设置。
 
　　　　Category：Category属性用于指定当前动作（Action）被执行的环境。通过addCategory()方法或在清单文件AndroidManifest.xml中设置。默认为：CATEGORY_DEFAULT。
 
　　　　Extras：Extras属性主要用于传递目标组件所需要的额外的数据。通过putExtras()方法设置。
 
　　四个属性各自的常用值如下所示：
 
　　Action：
 
　　　   ACTION_MAIN：Android Application的入口，每个Android应用必须且只能包含一个此类型的Action声明。　
 
　　　　ACTION_VIEW：系统根据不同的Data类型，通过已注册的对应Application显示数据。
 
　　　　ACTION_EDIT：系统根据不同的Data类型，通过已注册的对应Application编辑示数据。　
 
　　　　ACTION_DIAL：打开系统默认的拨号程序，如果Data中设置了电话号码，则自动在拨号程序中输入此号码。　
 
　　　　ACTION_CALL：直接呼叫Data中所带的号码。　
 
　　　　ACTION_ANSWER：接听来电。　
 
　　　　ACTION_SEND：由用户指定发送方式进行数据发送操作。
 
　　　　ACTION_SENDTO：系统根据不同的Data类型，通过已注册的对应Application进行数据发送操作。　
 
　　　　ACTION_BOOT_COMPLETED：Android系统在启动完毕后发出带有此Action的广播（Broadcast）。　
 
　　　　ACTION_TIME_CHANGED：Android系统的时间发生改变后发出带有此Action的广播（Broadcast）。　
 
　　　　ACTION_PACKAGE_ADDED：Android系统安装了新的Application之后发出带有此Action的广播（Broadcast）。　
 
　　　　ACTION_PACKAGE_CHANGED：Android系统中已存在的Application发生改变之后（如应用更新操作）发出带有此Action的广播（Broadcast）。　
 
　　　　ACTION_PACKAGE_REMOVED：卸载了Android系统已存在的Application之后发出带有此Action的广播（Broadcast）。　　
 
　　Category：
 
　　　   CATEGORY_DEFAULT：Android系统中默认的执行方式，按照普通Activity的执行方式执行。　
 
　　　　CATEGORY_HOME：设置该组件为Home Activity。
 
　　　　CATEGORY_PREFERENCE：设置该组件为Preference。　
 
　　　　CATEGORY_LAUNCHER：设置该组件为在当前应用程序启动器中优先级最高的Activity，通常为入口ACTION_MAIN配合使用。　
 
　　　　CATEGORY_BROWSABLE：设置该组件可以使用浏览器启动。　
 
　　　　CATEGORY_GADGET：设置该组件可以内嵌到另外的Activity中。
 
　　Extras:
 
　　　   EXTRA_BCC：存放邮件密送人地址的字符串数组。　
 
　　　　EXTRA_CC：存放邮件抄送人地址的字符串数组。
 
　　　　EXTRA_EMAIL：存放邮件地址的字符串数组。　
 
　　　　EXTRA_SUBJECT：存放邮件主题字符串。　
 
　　　　EXTRA_TEXT：存放邮件内容。　
 
　　　　EXTRA_KEY_EVENT：以KeyEvent对象方式存放触发Intent的按键。　　
 
　　　　EXTRA_PHONE_NUMBER：存放调用ACTION_CALL时的电话号码。　　　
 
 　　Data:
 
　　　   tel://：号码数据格式，后跟电话号码。　
 
　　　　mailto://：邮件数据格式，后跟邮件收件人地址。
 
　　　　smsto://：短息数据格式，后跟短信接收号码。
 
　　　　content://：内容数据格式，后跟需要读取的内容。　
 
　　　　file://：文件数据格式，后跟文件路径。
 
　　　　market://search?q=pname:pkgname：市场数据格式，在Google Market里搜索包名为pkgname的应用。
 
　　　　geo://latitude, longitude：经纬数据格式，在地图上显示经纬度所指定的位置。
