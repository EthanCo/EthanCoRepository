# View的工作原理 #
## ViewRoot和DecorView ##
ViewRoot对应于ViewRootImpl类，它是连接WindowManager和DecorView的纽带，View的三大流程均通过ViewRoot来完成的。  

View的绘制流程是从ViewRoot的performTranversals开始的，经过measure -> layout -> draw，才能将一个View绘制出来

###如何得到DecorView的content
findViewById(android.R.id.content);
###如何得到setContentView的view呢
content.getChildAt(0);

## View工作流程 ##
### measure ###
- 原始View
	- 完成自己的测量
- ViewGroup
	- 完成自己的测量
	- 遍历去调用所有子元素的measure方法
### View的measure过程 ###

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//Z-setMeasuredDimension会设置View宽高的测量值
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

#### getDefaultSize ####
getDefaultSize在<lable>AT_MOST</lable>和EXACTLY时，返回specSize (View测量后的大小，[但不是最终大小，最终大小是在layout阶段确定的])  
UNSPECIFIED一般用于系统内部的测量过程，这种情况下，View的大小为getDefaultSize的第一个参数size(即宽高分别为getSuggestedMinimumWidth和getSuggestedMinimumHeight这连个方法的返回值)  

结论:直接继承View的自定义控件需要重写onMeasure方法并设置wrap_content时的自身大小，否则放在布局中使用wrap_content就相当于match_parent


	public static int getDefaultSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
        case MeasureSpec.UNSPECIFIED:
            result = size;
            break;
        case MeasureSpec.AT_MOST:
        case MeasureSpec.EXACTLY:
            result = specSize;
            break;
        }
        return result;
    }

#### getSuggestedMinimumWidth和getSuggestedMinimumHeight ####
	protected int getSuggestedMinimumWidth() {
        return (mBackground == null) ? mMinWidth : max(mMinWidth, mBackground.getMinimumWidth());
    }

	protected int getSuggestedMinimumHeight() {
        return (mBackground == null) ? mMinHeight : max(mMinHeight, mBackground.getMinimumHeight());
    }

- 如果没有设置背景，那么View的宽度为mMinWidth(android:minWidth，默认为0)
- 如果有指定背景，那么为mMinWidth和背景.getMinimumWidth中的最大值

#### Drawable的MinimumWidth ####
返回背景的最小宽度

	 public int getMinimumWidth() {
        final int intrinsicWidth = getIntrinsicWidth();
        return intrinsicWidth > 0 ? intrinsicWidth : 0;
    }

- 如果Drawable没有原始大小，返回0
- 如果Drawable有原始大小，则返回原始大小
	- 原始大小:比如ShapeDrawable无原始大小，而BitmapDrawable有原始大小


### ViewGroup的measure过程 ###
ViewGroup是一个抽象类，它没有重写View的onMeasure方法，但是它提供了一个叫measureChildren的方法

	protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
        final int size = mChildrenCount;
        final View[] children = mChildren;
        for (int i = 0; i < size; ++i) {
            final View child = children[i];
            if ((child.mViewFlags & VISIBILITY_MASK) != GONE) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
            }
        }
    }

measureChild对每一个子元素进行measure: 取出子元素的LayoutParams，然后再通过getChildMeasureSpec来创建子元素的MeasureSpec，接着讲MeasureSpec直接传递给View的measure方法来进行测量。

	protected void measureChild(View child, int parentWidthMeasureSpec,
            int parentHeightMeasureSpec) {
        final LayoutParams lp = child.getLayoutParams();

        final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                mPaddingLeft + mPaddingRight, lp.width);
        final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,
                mPaddingTop + mPaddingBottom, lp.height);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

### LinearLayout为例的measure过程 ###
其他的ViewGroup的实现方式是不同的，因为要实现不同的布局。  

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mOrientation == VERTICAL) {
            measureVertical(widthMeasureSpec, heightMeasureSpec);
        } else {
            measureHorizontal(widthMeasureSpec, heightMeasureSpec);
        }
    }


measureVertial -> 遍历子元素 -> measureChildBeforeLayout -> measure -> mTotalLength += mPaddingTop + mPaddingBottom + child.height -> 测量自己 -> setMeasuredDimension

#### measureVertial ####
- 如果高度是match_parent或者具体数据，那么测量过程与View一致(即高度为specSize)
- 如果高度为wrap_content，那么高度为所有子元素所占用的高度总和+竖直方向padding的总和(不超过父容器)

#### measureHorizontal ####
- 水平方向的测量过程遵循View的测量过程。 

### Activity获取View的宽高 ###
 #### onWindowFocusChanged ####
View已初始化韩币，宽高已准备好  
但onWindowFocusChanged会被调用好多次  
当Activity继续执行和暂停时，onWindowFocusChanged均会调用  
#### view.post(runnable) ####
将一个runnable投递到消息队列的尾部，然后等Looper调用此runnable的时候，View也已经初始化好了
#### ViewTreeObserver ####
伴随着View树的状态改变等，onGlobalLayout会被调用多次(所以，一般被调用后就remove)

	view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
	    @Override
	    public void onGlobalLayout() {
	        view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
	        //view.getViewTreeObserver().removeOnGlobalLayoutListener(this); API16及以上
	        int width = view.getMeasuredWidth();
	        int height = view.getMeasuredHeight();
	    }
	});

#### view.measure(int widthMeasureSpec，int heightMeasureSpec) ####
比较复杂，需分情况根据View的LayoutParams来分

### Layout过程 ###
#### View的layout ####
public void layout(int l, int t, int r, int b) {
        if ((mPrivateFlags3 & PFLAG3_MEASURE_NEEDED_BEFORE_LAYOUT) != 0) {
            onMeasure(mOldWidthMeasureSpec, mOldHeightMeasureSpec);
            mPrivateFlags3 &= ~PFLAG3_MEASURE_NEEDED_BEFORE_LAYOUT;
        }

        int oldL = mLeft;
        int oldT = mTop;
        int oldB = mBottom;
        int oldR = mRight;

        boolean changed = isLayoutModeOptical(mParent) ?
                setOpticalFrame(l, t, r, b) : setFrame(l, t, r, b); //Z-setFrame 设置View的四个顶点的位置，(确定该view在父容器的位置)

        if (changed || (mPrivateFlags & PFLAG_LAYOUT_REQUIRED) == PFLAG_LAYOUT_REQUIRED) {
            onLayout(changed, l, t, r, b); //Z-调用onLayout方法(确定所有子元素的位置)
            mPrivateFlags &= ~PFLAG_LAYOUT_REQUIRED;

            ListenerInfo li = mListenerInfo;
            if (li != null && li.mOnLayoutChangeListeners != null) {
                ArrayList<OnLayoutChangeListener> listenersCopy =
                        (ArrayList<OnLayoutChangeListener>)li.mOnLayoutChangeListeners.clone();
                int numListeners = listenersCopy.size();
                for (int i = 0; i < numListeners; ++i) {
                    listenersCopy.get(i).onLayoutChange(this, l, t, r, b, oldL, oldT, oldR, oldB);
                }
            }
        }

        mPrivateFlags &= ~PFLAG_FORCE_LAYOUT;
        mPrivateFlags3 |= PFLAG3_IS_LAID_OUT;
    }

View和ViewGroup都没有实现onLayout方法，来看看LinearLayout的OnLayout

	void layoutVertical(int left, int top, int right, int bottom) {
        final int paddingLeft = mPaddingLeft;

        int childTop;
        int childLeft;
        
        // Where right end of child should go
        final int width = right - left;
        int childRight = width - mPaddingRight;
        
        // Space available for child
        int childSpace = width - paddingLeft - mPaddingRight;
        
        final int count = getVirtualChildCount();

        final int majorGravity = mGravity & Gravity.VERTICAL_GRAVITY_MASK;
        final int minorGravity = mGravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK;

        switch (majorGravity) {
           case Gravity.BOTTOM:
               // mTotalLength contains the padding already
               childTop = mPaddingTop + bottom - top - mTotalLength;
               break;

               // mTotalLength contains the padding already
           case Gravity.CENTER_VERTICAL:
               childTop = mPaddingTop + (bottom - top - mTotalLength) / 2;
               break;

           case Gravity.TOP:
           default:
               childTop = mPaddingTop;
               break;
        }

        for (int i = 0; i < count; i++) { //遍历所有子元素
            final View child = getVirtualChildAt(i);
            if (child == null) {
                childTop += measureNullChild(i);
            } else if (child.getVisibility() != GONE) {
                final int childWidth = child.getMeasuredWidth();
                final int childHeight = child.getMeasuredHeight();
                
                final LinearLayout.LayoutParams lp =
                        (LinearLayout.LayoutParams) child.getLayoutParams();
                
                int gravity = lp.gravity;
                if (gravity < 0) {
                    gravity = minorGravity;
                }
                final int layoutDirection = getLayoutDirection();
                final int absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection);
                switch (absoluteGravity & Gravity.HORIZONTAL_GRAVITY_MASK) {
                    case Gravity.CENTER_HORIZONTAL:
                        childLeft = paddingLeft + ((childSpace - childWidth) / 2)
                                + lp.leftMargin - lp.rightMargin;
                        break;

                    case Gravity.RIGHT:
                        childLeft = childRight - childWidth - lp.rightMargin;
                        break;

                    case Gravity.LEFT:
                    default:
                        childLeft = paddingLeft + lp.leftMargin;
                        break;
                }

                if (hasDividerBeforeChildAt(i)) {
                    childTop += mDividerHeight;
                }

                childTop += lp.topMargin;
                //Z-setChildFrame 为子元素指定对应的位置(调用子元素的layout) childTop会之间增大，后面的子元素会被放置在靠下的位置
                setChildFrame(child, childLeft, childTop + getLocationOffset(child),
                        childWidth, childHeight);
                childTop += childHeight + lp.bottomMargin + getNextLocationOffset(child);

                i += getChildrenSkipCount(child, i);
            }
        }
    }

这样就形成了传递  
ViewGroupA.layout -> onLayout -> 循环遍历子元素 -> ViewGroupB.layout -> onLayout -> 循环遍历子元素 -> ViewA.layout

setChildFrame设置子元素的layout
  
	private void setChildFrame(View child, int left, int top, int width, int height) {        
        child.layout(left, top, left + width, top + height); //width/height 子元素的宽高 left,top是子元素的四个顶点的位置
    }

#### View的getMeasuredWidth和getWidth这两个方法有什么区别 ####
先来看看getwidth  
	public final int getWidth() {
        return mRight - mLeft;
    }

getMeasuredWidth
	public final int getMeasuredWidth() {
        return mMeasuredWidth & MEASURED_SIZE_MASK;
    }

在默认的实现中，View的测量宽/高和最终的宽/高是相等的，只不过  

- 测量宽高形成与View的measure过程 (稍早)
- 最终宽高形成与View的layout过程

在日常的开发中，我们可以认为View的测量宽高就等于最终宽高，但是的确在某些特殊的情况下会导致两者不一致。



- 如果重写了View的layout方法，修改了其super.layout传入的参数，那么测量宽高就会与最终宽高不相等了
- 如果View需要多次measure才能确定自己测量的宽高，在前几次的测量过程中，其得出的测量宽高可能和最终宽高不一致，但最终来讲，测量宽高还是和最终宽高相同。

#### 应用场景 ####
getMeasuredWidth:在自定义view重写onLayout时、在我们用layoutinflater动态加载view后想获得view的原始宽度时。  
getWidth:一般在view已经布局后呈现出来了，想获取宽度时  

### Draw过程 ###
view的绘制步骤  

- 绘制背景 background.draw(canvas)
- 绘制自己(onDraw)
- 绘制children (dispatchDraw)
	- View的绘制过程的传递是通过dispatchDraw来实现的，dispatchDraw会遍历调用所用子元素的draw方法，如此draw事件就传递下去了。
- 绘制装饰(onDrawForeground(onDrawScrollBar))

#### setWillNoteDraw ####
默认View不开启，ViewGroup开启  
如果设为true，该View将不具备绘制功能，然后系统会进行后续的(不用绘制的)优化  

### 自定义View ###
#### 须知 ####
- 让View支持wrap_content
- 如果有必要，让你的View支持padding
- 尽量不要在View中使用Handler
- View中如果有线程或者动画，需要及时停止(退出或remove或变成不可见时)
	- View#onDetachedFromWindow: 包含此View的Activity退出或者当前View被remove时
	- View#onAttachedToWindow: 当包含此View的Activity启动时
- View带有滑动嵌套时，需要处理好滑动冲突


