# Material Design #
拟物设计和扁平设计的结合  
物体要体现
- 三维
- 大小
- 材质
- 轻重
- 弹性

# Material Design 动画 #
- 从静止开始要有加速过程，并且最终静止时要有减速过程
- (从屏幕外面)快速进场，则快速退场(到屏幕外面)
	- 从屏幕边缘进场，先是很快的，然后快到屏幕中央是速度慢
	- 退场到屏幕边缘，先是很慢的，然后到屏幕边缘是速度快
- 弧线转弯，而不是90度转弯
- 触控反应，涟漪、抬高
	- 抬高:用阴影体现
- 从接触点开始动画
- 视觉连续性，过度动画引导用户的关注点
- 层次动画的时间差
- 移动路径规划有序，随机的移动另用户分心

## Touch feedback ##
触摸反馈提供了用户与UI交互时可视化的确认接触点
RippleDrawable类做background，涟漪效果在两种不同的状态间过渡
	
	RippleDrawable(ColorStateList color,Drawable content，Drawable mask);

