# canvas.drawArc 起始角度 #

	public void drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)

	oval :指定圆弧的外轮廓矩形区域。
	startAngle: 圆弧起始角度，单位为度。
	sweepAngle: 圆弧扫过的角度，顺时针方向，单位为度,从右中间开始为零度。
	useCenter: 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形。

一直不知道startAngle起始角度是从哪开始，今天试了下  

	

从图中可知，0度为水平右侧开始，角度是顺时针方向