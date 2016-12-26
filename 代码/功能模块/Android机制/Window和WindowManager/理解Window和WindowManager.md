# 理解Window和WindowManager #
## Window和WindowManager ##
### Flags ###
<h4> FALG_NOT_FOCUSABLE </h4>
此标记会同时启用FLAG_NOT_TOUCH_MODEL  
表示Window不需要获取焦点，最终事件会直接传递给下层的具有焦点的Window
<h4> FLAG_NOT_TOUCH_MODEL </h4>
当前Window区域外的单击事件传递给底层的Window  
当前Window区域的单击事件则自己处理
<h4> FLAG_SHOW_WHEN_LOCKED </h4>
开启此模式可以让Window显示在锁屏的界面上  
### Type ###
#### 应用子View ####
对应一个Activity  
层级范围是1~999
#### 子Window ####
不能单独存在，它需要附属在特定的父Window之中 (比如Dialog)  
层级范围是1000~1999  
#### 系统Window ####
需要声明权限才能创建的Window (比如Toast和系统状态栏)  
层级范围是2000~2999
比如

	层级范围对应着WindowManager.LayoutParams的type参数

示例:  

        LinearLayout linearLayout = new LinearLayout(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(lp);
        linearLayout.setBackgroundColor(Color.RED);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams wlp = new WindowManager.LayoutParams();
        wlp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        wlp.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        wm.addView(linearLayout, wlp);

## Window的内部机制 ##
每一个Window都对应着一个View和一个ViewRootImpl  
Window和View通过ViewRootImpl来建立联系  

### Window的添加过程 ###
