# JavaDoc注释 #
## 类注释 ##

	/**
	 * 一句话功能描述
	 * 功能详细描述
	 * @author [作者] 必须
	 * @see [相关类/方法] (可选)
	 * @since [产品/模块版本] (必须)
	 * @deprecated 可选
	 */

## 注释的位置 ##
类成员变量、共有和保护方法需要些注释，写在被注释元素的上面，并与其上面的代码用空行隔开，注释与所描述内容进行同样的缩排。

	/**
	 * 一句话功能描述
	 * 功能详细描述
	 *
	 * @author [作者] 必须
	 * @see [相关类/方法] (可选)
	 * @since [产品/模块版本] (必须)
	 * @deprecated 可选
	 */
	public class Test {
	    /**
	     * 字段注释
	     */
	    private String logType;
	
	    /**
	     * 函数注解
	     */
	    public void write() {
	
	    }
	}

## 函数的注释 ##
公有和保护方法注释需要列出方法的一句话功能简述、功能详情描述、输入参数、输出参数、返回值、异常等。

	
    /**
     * 一句话功能描述
     * 功能详细描述
     * @param [参数1] 参数1说明
     * @param [参数2] 参数2说明
     * @return [返回类型说明]
     * @exception/throws [异常类型] [异常说明]
     * @see [类、类#方法、类#成员]
     * @since [起始版本]
     * @deprecated (可选)
     */


示例

	
    /**
     * 登陆
     * 账号的登陆，登陆成功后保存token到本地。
     *
     * @param name 用户名
     * @param pwd  密码
     * @return 登陆是否成功
     * @since 1.0
     */
    public boolean login(String name, String pwd) {

    }

## 异常的注释 ##
对于方法内部用throws语句抛出的异常，必须在方法的注释中表明。  
异常注释用@exception或@throws表示，在JavaDoc中两者等价，但推荐用@exception表示Runtime异常，@throw标注非Runtime异常。

	
    /**
     * 关闭当前数据流
     * @throws IOException 当关闭流失败时抛出该异常
     */
    public void close() throws IOException {

    }
