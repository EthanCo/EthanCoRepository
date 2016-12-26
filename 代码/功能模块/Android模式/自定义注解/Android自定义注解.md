# 自定义注解 #
## @Target ##
代表着注解的修饰范围  

	@Target(ElementType.METHOD) //修饰范围为参数

### 多个修饰范围 ###

	@Target({ElementType.FIELD, ElementType.METHOD})

### ElementType ###
	public enum ElementType {
	    /**
	     * 类，接口或枚举的声明
	     */
	    TYPE,
	    /**
	     * 变量的声明
	     */
	    FIELD,
	    /**
	     * 方法的声明
	     */
	    METHOD,
	    /**
	     * 参数的声明
	     */
	    PARAMETER,
	    /**
	     * 构造方法的声明
	     */
	    CONSTRUCTOR,
	    /**
	     * 局部变量声明
	     */
	    LOCAL_VARIABLE,
	    /**
	     * 注释类型声明
	     */
	    ANNOTATION_TYPE,
	    /**
	     * 包声明
	     */
	    PACKAGE
	}

## @Retention ##
表示需要在什么级别保存该注解信息
	
	@Retention(RetentionPolicy.RUNTIME) //注释在源代码，类文件，运行时 时可用。

### RetentionPolicy ###
	public enum RetentionPolicy {
	    /* 注解只在源代码中可用
	     * Annotation is only available in the source code.
	     */
	    SOURCE,
	    /* 注解在源代码，类文件 时可用，但在运行时不可用。 默认使用这个
	     * Annotation is available in the source code and in the class file, but not
	     * at runtime. This is the default policy.
	     */
	    CLASS,
	    /* 注释在源代码，类文件，运行时 时可用。
	     * Annotation is available in the source code, the class file and is
	     * available at runtime.
	     */
	    RUNTIME
	}


	[具体的ElementTyle大全](http://man.lupaworld.com/content/develop/JDK_6.0_API_html_zh_CN/html/zh_CN/api/java/lang/annotation/ElementType.html)