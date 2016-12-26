##位运算符比&级别要高
位运算符的级别要比&高  

##ANR异常 查看Log
出现ANR时Android会保存日志到 /data/anr  
可通过一下adb命令获取:

	db pull /data/anr trances.txt .  

##finished with non-zero exit value 3  

jar包冲突   
1.lib自动导入的jar包与compare的jiar包  
2.可能有的jar包里已经包括了需要的jar包  

> 推荐采用排除法, 删除代码都，只剩compare，然后继续注释一部分运行进行排除  

## Android SerialPort  ##
Android SerialPort 串口打开时会阻塞，需放到子线程中  

## android设备连接电脑后没反应 ##
1.是否打开了usb调试  
2.在我的电脑—设备管理里有没有感叹号的设备  
3.adb 无线连接进行调试，需android于电脑处于同一局域网，android需安装adbwireless.app，然后点击灰色按钮，让其变为绿色。
  输入以下adb命令  

	adb connect android的IP地址  

## 为什么在服务中要添加FLAG_ACTIVITY_NEW_TASK标记来启动Activity？ ##

因为standard模式的Activity默认会进入启动它的Activity所属的任务栈中，但由于非Activity类型的Context并没有所谓的任务栈，所以要添加这个标记，以singleTask模式启动  