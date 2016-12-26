Fragment
作用
碎片 用于多屏显示 可以把activity里面的逻辑进行分离
可以看出view,依附于activity之上 android3.0出现(平板) android4.0手机和平板
必须有view才能被显示，否则会出错
生命周期
显示到前台：
MyFragment  onAttach()  粘贴到activity上
MyFragment  onCreate()  fragment创建
MyFragment  onCreateView()  fragment创建自己的视图

MainActivity  onCreate()
MyFragment   onActivityCreated()   可以处理fragment数据的初始化

MainActivity  onStart()
MyFragment    onStart()

MainActivity  onResume()
MyFragment    onResume()

按后退键
MyFragment    onPause()
MainActivity  onPause()

MyFragment    onStop()
MainActivity   onStop()

MyFragment    onDestoryView()  销毁掉自己的视图
MyFragment    onDestory()
MyFragment    onDetach()  解除和activity的关系

MainActivity   onDetory()