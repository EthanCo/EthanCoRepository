## Provider ##
### 作用 ###
	provider是提供给其他应用其他访问自己私有的数据的接口，增删改查可以选择性实现
-  exported 不要忘记
-  可以使用URLMather比较url
-  *和#分别代表字符串和数字，使一个urlMather可以匹配多个url

ContentResolver就是用来访问Provider的  
	ContentResolver cr = getContentResolver();
	cr.insert(url,values);