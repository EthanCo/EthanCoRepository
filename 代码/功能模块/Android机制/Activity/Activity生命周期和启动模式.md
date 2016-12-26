# Activity生命周期和启动模式 #
- onCreate
	- Activity正在被创建
	- 初始化工作
- onRestart
	- Activity正在重新启动
- onStart
	- Activity正在启动
	- 可见但不显示
- onResume
	- Activity已可见，出现在前台并开始活动。
- onPause
	- Activity正在停止
	- 不做耗时操作
- onStop
	- Activity即将停止
	- 不做耗时操作
- onDestory
	- Activity即将销毁

## IntentFilter的匹配规则 ##
### action的匹配规则 ###
Intent中的action必须能和过滤规则中的任意一个action匹配
### category的匹配规则 ###
Intent中的category必须包含滤规则中的每一个category  
为了能过接收隐式调用，就必须在intent-filter中指定"android.intent.category.DEFAULT"这个category
### data ###
data的匹配规则和action类似，如果过滤规则中定义了data，那么Intent中必须也要定义可匹配的data

