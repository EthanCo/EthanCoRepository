## 测量大小和布局 ##

marginLeftRight: 确定子View与left和right的间距  
gutterSize: 确定原始大小View与缩小View之间的距离  


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    //设置默认大小，让当前的ViewGroup大小为充满屏幕
	    setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
	            getDefaultSize(0, heightMeasureSpec));
	    int measuredWidth = getMeasuredWidth();
	    int measuredHeight = getMeasuredHeight();
	
	    int childCount = getChildCount();
	    //每个子child的宽度为屏幕的宽度减去与两边的间距
	    int width = measuredWidth - (int) (mMarginLeftRight * 2);
	    int height = measuredHeight;
	    int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
	    int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
	    for (int i = 0; i < childCount; i++) {
	        getChildAt(i).measure(childWidthMeasureSpec, childHeightMeasureSpec);
	    }
	  	//切换一个page需要移动的距离为一个page的宽度
	    mSwitchSize = width;
	    //确定缩放比例
	    confirmScaleRatio(width, mGutterSize);
	}


	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
	    int childCount = getChildCount();
	    int originLeft = (int) mMarginLeftRight;
	
	    for (int i = 0; i < childCount; i++) {
	        View child = getChildAt(i);
	        int left = originLeft + child.getMeasuredWidth() * i;
	        int right = originLeft + child.getMeasuredWidth() * (i + 1);
	        int bottom = child.getMeasuredHeight();
	        child.layout(left, 0, right, bottom);
	        if (i != 0) {
	            child.setScaleX(SCALE_RATIO);
	            child.setScaleY(SCALE_RATIO);
	        }
	    }
	}

	private void confirmScaleRatio(int width, float gutterSize) {
    	SCALE_RATIO = (width - gutterSize * 2) / width;
	}  

### 滑动 ###

	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    LogUtils.LogD(TAG, " onInterceptTouchEvent hit touch event");
	    final int actionIndex = MotionEventCompat.getActionIndex(event);
	    mActivePointerId = MotionEventCompat.getPointerId(event, 0);
	
	    if (mVelocityTracker == null) {
	        mVelocityTracker = VelocityTracker.obtain();
	    }
	    mVelocityTracker.addMovement(event);
	    switch (event.getAction() & MotionEvent.ACTION_MASK) {
	        case MotionEvent.ACTION_DOWN:
	            mDownX = event.getRawX();
	            if (mScroller != null && !mScroller.isFinished()) {
	                mScroller.abortAnimation();
	            }
	            break;
	        case MotionEvent.ACTION_MOVE:
	
	            //calculate moving distance
	            float distance = -(event.getRawX() - mDownX);
	            mDownX = event.getRawX();
	            LogUtils.LogD(TAG, " current distance == " + distance);
	            performDrag((int)distance);
	            break;
	        case MotionEvent.ACTION_UP:
	            releaseViewForTouchUp();
	            cancel();
	            break;
	    }
	    return true;
	}
	
	private void performDrag(int distance) {
	    if (mOnPagerChangeListener != null){
	        mOnPagerChangeListener.onPageScrollStateChanged(SCROLL_STATE_DRAGGING);
	    }
	    LogUtils.LogD(TAG, " perform drag distance == " + distance);
	    scrollBy(distance, 0);
	    if (distance < 0) {
	        dragScaleShrinkView(mCurrentPosition, LEFT_TO_RIGHT);
	    } else {
	        LogUtils.LogD(TAG, " current direction is right to left and current child position =  " + mCurrentPosition);
	        dragScaleShrinkView(mCurrentPosition, RIGHT_TO_LEFT);
	    }
	}