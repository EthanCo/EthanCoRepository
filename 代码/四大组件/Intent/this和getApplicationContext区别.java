this 是因为Activity 扩展了Context,其生命周期是Activity 创建到销毁;
getApplicationContext 取得的Context是和Application关联的生命周期是应用的创建到销毁. 
getBaseContext()  返回由构造函数指定或setBaseContext()设置的上下文
