#快捷键
##有用的需要记住的  
shift+F6 重命名  
Ctrl+Alt+Space 类名或接口名提示，类名自动完成  
Ctrl＋Shift＋Space在很多时候都能够给出Smart提示
  
Ctrl＋Shift＋Backspace可以跳转到上次编辑的地方  
Ctrl＋Alt＋V 最近的复制  

Shift+Ctrl+F12 显示/隐藏所有的边框栏  
Alt+1 切换project布局可视性  

Ctrl＋E 可以显示最近编辑的文件列表  
Ctrl＋F12 可以显示当前文件的结构(显示该文件所有的类)

Ctrl+Alt+T可以把代码包在一块内，例如try/catch    
ctrl+g 定位到第几行  

Ctrl+Alt+F 将变量放到外面，可重命名  
ctrl+h 查看一个所选择的类的继承层次  

Ctrl + Shift + U 大小写转换  
##有用的已经记住的
Ctrl＋Alt＋B 跳转到抽象方法的实现  
Ctrl + Alt + M 快速提取方法  
shift+shift 全局搜索类 
alt+inset 生成get、set 构造方法等  
shift+F6 重命名  
Alt+回车 导入包,自动修正 
Ctrl+P 显示方法提示  
Ctrl+J 自动生成代码(for,switch等) Ctrl + D 粘贴当前行到下一行 Ctrl + O 快捷覆写方法  
Ctrl＋F7可以查询当前元素在当前文件中的引用，然后按F3可以选择  
ctrl+shift+enter 跳出括号并补全符号 ctrl+alt+空格 代码补全  
Ctrl + / "//"单行注释 Ctrl + Shift + / /* … */ 添加(取消)多行注释  
##移动代码
alt+上/下 上一个/下一个方法  
Shift + Enter 任意位置换行（往下添加空行）  
Ctrl + Enter 在当前行的上一行插入新行，光标在行首时有效  
Shift + Alt + Up/Down 当前行、选中行向上/向下移动  
Ctrl + Shift + Up/Down 当前行、选中行向上/向下移动,无法移出语句当前所在代码块，注释也一样  
Ctrl＋[或]可以跳到{大括号}的开头结尾  
Ctrl＋Shift＋J可以整合两行  
##运行
Ctrl + Shift + F10 运行当前项目  
Ctrl+F9 构建  
Ctrl+F10 构建并运行程序  
Shift+F9 Debug  
##搜索
shift+shift 搜索everywhere  
shift+n	查找类  
Ctrl+Shift+N 查找文件  
Ctrl+Shift+Alt+N 查找类中的方法或变量  
## 快捷生成 ##
List<String> mList = null;  
然后mList.for 即出现foreach循环  
mList.fori则出现for循环  
mList.forf则出现反向  
数字.for 也可以  
Object o;  
判空  
o.null  
判断非空  
o.notnull  
##其他
F2 或Shift+F2 高亮错误或警告快速定位    
Ctrl+W 选中代码，连续按会有其他效果  
Ctrl + Backspace 按单词删除  
Ctrl+Q 显示所选API的文档  
Ctrl + Left/Right快捷定位到单词首/尾  
Ctrl+B 快速打开光标处的类或方法  
Shift＋Click可以关闭文件  
##其他的其他
Ctrl + Alt + O 自动导入需要的包和删除多余的包  
Ctrl + Alt + H或者Alt+F7 查找调用的位置  

##设置
###自动悬浮提示  
Editor->soft warps-> show soft wraps for current line only
###修改模板注释
(新建class时自动生成的那个) 点击扳手(Setting)->File and Code Templates ->右侧点击Includes->修改
###代码优化
平时没事可以做做代码优化，在eclipse里面右击项目，Android Tools里面的Run Lint可以用来提示可以优化的地方，但是也不是每个都可以优化。Android Studio可以右击项目，然后Analyze-》Inspect Code达到同样效果