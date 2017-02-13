# 获取View最终的大小及源码分析 #

在Activity#onCreate()中设置监听  
当View树的状态发生改变或者View的内部的View可见性发生改变时，onGlobalLayout方法会被调用  

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override 
                    public void onGlobalLayout() {
                        //可在此获取控件的宽高 -----<<<<<
                        decorView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
    }  

> 伴随着View数的状态改变等，onGlobalLayout会被调用多次。

## 具体测试 ##
MainActivity  
	
	public class MainActivity extends AppCompatActivity {
	
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	
	        final View decorView = getWindow().getDecorView();
	        decorView.getViewTreeObserver()
	                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
	                    @Override
	                    public void onGlobalLayout() {
	                        //可在此获取控件的宽高
	                        Log.i("Z-Main", "onGlobalLayout");
	                        decorView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
	                    }
	                });
	
	        decorView.post(new Runnable() {
	            @Override
	            public void run() {
	                Log.i("Z-Main", "decorView.post");
	            }
	        });
	    }
	
	    @Override
	    protected void onResume() {
	        super.onResume();
	        for (int i = 0; i < 10; i++) {
	            Log.i("Z-Main", "onResume" + i);
	            SystemClock.sleep(500);
	        }
	    }
	}

在activity_main布局中有一个RippleButton  

	public class RippleButton extends Button {
	    public RippleButton(Context context, AttributeSet attrs) {
	        super(context, attrs);
	    }
	
	    @Override
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	        Log.i("Z-Main", "RippleButton onMeasure");
	    }
	
	    @Override
	    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
	        super.onLayout(changed, left, top, right, bottom);
	        Log.i("Z-Main", "RippleButton onLayout");
	    }
	
	    @Override
	    protected void onDraw(Canvas canvas) {
	        super.onDraw(canvas);
	        Log.i("Z-Main", "RippleButton onDraw");
	    }
	}

执行后打印日志如下  

	01-12 01:32:12.357 23414-23414/? I/Z-Main: onResume0
	01-12 01:32:12.858 23414-23414/com.ethanco.rippledrawabletest I/Z-Main: onResume1
	01-12 01:32:13.358 23414-23414/com.ethanco.rippledrawabletest I/Z-Main: onResume2
	01-12 01:32:13.858 23414-23414/com.ethanco.rippledrawabletest I/Z-Main: onResume3
	01-12 01:32:14.359 23414-23414/com.ethanco.rippledrawabletest I/Z-Main: onResume4
	01-12 01:32:14.859 23414-23414/com.ethanco.rippledrawabletest I/Z-Main: onResume5
	01-12 01:32:15.360 23414-23414/com.ethanco.rippledrawabletest I/Z-Main: onResume6
	01-12 01:32:15.860 23414-23414/com.ethanco.rippledrawabletest I/Z-Main: onResume7
	01-12 01:32:16.362 23414-23414/com.ethanco.rippledrawabletest I/Z-Main: onResume8
	01-12 01:32:16.863 23414-23414/com.ethanco.rippledrawabletest I/Z-Main: onResume9
	01-12 01:32:17.449 23414-23414/com.ethanco.rippledrawabletest I/Z-Main: RippleButton onMeasure
	01-12 01:32:17.449 23414-23414/com.ethanco.rippledrawabletest I/Z-Main: RippleButton onMeasure
	01-12 01:32:17.486 23414-23414/com.ethanco.rippledrawabletest I/Z-Main: RippleButton onLayout
	01-12 01:32:17.491 23414-23414/com.ethanco.rippledrawabletest I/Z-Main: onGlobalLayout
	01-12 01:32:17.552 23414-23414/com.ethanco.rippledrawabletest I/Z-Main: RippleButton onDraw
	01-12 01:32:17.553 23414-23414/com.ethanco.rippledrawabletest I/Z-Main: RippleButton onDraw
	01-12 01:32:17.601 23414-23414/com.ethanco.rippledrawabletest I/Z-Main: RippleButton onMeasure
	01-12 01:32:17.601 23414-23414/com.ethanco.rippledrawabletest I/Z-Main: RippleButton onMeasure
	01-12 01:32:17.601 23414-23414/com.ethanco.rippledrawabletest I/Z-Main: RippleButton onLayout
	01-12 01:32:17.605 23414-23414/com.ethanco.rippledrawabletest I/Z-Main: RippleButton onDraw


从日志中可以发现onGlobalLayout在第一次onLayout之后调用，而在执行onLayout方法时，View的宽高一般情况下已是最终宽高了 (特殊情况除外，特殊情况会多次调用onDraw后，View宽高可能还会改变)。  

并且，可知View的三大流程 (onMeasure、onLayout、onDraw) 都是在onResume执行完毕之后才执行的，那么，为什么会这样呢 ？ 

## 源码分析 ##

我们都知道，通过ActivityThread#handleLaunchActivity()方法，会完成Activity的启动。  

ActivityThread.handleLaunchActivity()  

	private void handleLaunchActivity(ActivityClientRecord r, Intent customIntent) {
        //...
        Activity a = performLaunchActivity(r, customIntent);

        if (a != null) {
           	//...
			//内部调用Activity#onResume()
           	handleResumeActivity(r.token, false, r.isForward,
                    !r.activity.mFinished && !r.startsNotResumed);  
			//...
        } else {
			//finish activity
            ActivityManagerNative.getDefault()
                .finishActivity(r.token, Activity.RESULT_CANCELED, null, false);
        }
    }  

可以发现，handleLaunchActivity()中调用了performLaunchActivity  

performLaunchActivity具体做了哪些事可以看[ActivityThread.handleLaunchActivity方法分析](http://www.jianshu.com/p/98c98d1ae3c3)  

这里，我们只关心我们关心的内容。  

	private Activity performLaunchActivity(ActivityClientRecord r, Intent customIntent) {
      	//...
        //通过类加载器创建Activity的实例
        java.lang.ClassLoader cl = r.packageInfo.getClassLoader();
        activity = mInstrumentation.newActivity(
                cl, component.getClassName(), r.intent);
		//...

      	//如果Application没有被创建，那么创建它
        Application app = r.packageInfo.makeApplication(false, mInstrumentation);

       	//...
        //关联运行过程中所依赖的一系列上下文环境变量
        activity.attach(appContext, this, getInstrumentation(), r.token,
                r.ident, app, r.intent, r.activityInfo, title, r.parent,
                r.embeddedID, r.lastNonConfigurationInstances, config,
                r.voiceInteractor);

            
        //内部调用Activity#onCreate()
        mInstrumentation.callActivityOnCreate(activity, r.state, r.persistentState);
            
        //内部调用Activity#onCreate()
        activity.performStart();
        r.stopped = false;       

        //内部调用Activity#onRestoreInstanceState()
        mInstrumentation.callActivityOnRestoreInstanceState(activity, r.state,
                r.persistentState);
        }

        //内部调用Activity#onPostCreate()
        mInstrumentation.callActivityOnPostCreate(activity, r.state,
                r.persistentState);
           
        r.paused = true;
        mActivities.put(r.token, r);
        
        return activity;
    }  


可以看到，在初始化Activity后，执行了Activity的一系列生命周期  

执行完performLaunchActivity()后，在handleLaunchActivity()中调用了handleResumeActivity();  

	final void handleResumeActivity(IBinder token,
           boolean clearHide, boolean isForward, boolean reallyResume) {
		//...
        //内部调用Activity.performResume() -> Activity.onResume()
        ActivityClientRecord r = performResumeActivity(token, clearHide);
		//...
    	if (r.window == null && !a.mFinished && willBeVisible) {
			//...	
            r.window = r.activity.getWindow();
            View decor = r.window.getDecorView();
            //先将DecorView设为不可见
            decor.setVisibility(View.INVISIBLE);
            ViewManager wm = a.getWindowManager();
            WindowManager.LayoutParams l = r.window.getAttributes();
            a.mDecor = decor;
            l.type = WindowManager.LayoutParams.TYPE_BASE_APPLICATION;
            l.softInputMode |= forwardBit;
            if (a.mVisibleFromClient) {
                a.mWindowAdded = true;

                //将DecorView添加到Window
                wm.addView(decor, l);
            }
            //...
            //将DecorView添加到Window(如果还没有添加)，将Activity的显示置为Visible
            r.activity.makeVisible();
           //...
        } else {
			 //finish activity
             ActivityManagerNative.getDefault()
                    .finishActivity(token, Activity.RESULT_CANCELED, null, false);
        }
    }

可以看到，在初始化DecorView之后，将DecorView添加到了window中，这将调用一系列方法，最终，会调用到ViewRootImpl的performTraversals()，在此方法中，完成View的三大流程，measure、layout、draw。

	private void performTraversals() {
        //...

        if (mFirst) {
            //...

            if (!mStopped) {
					//...
                    performMeasure(childWidthMeasureSpec, childHeightMeasureSpec);

                    //...

                    if (measureAgain) {
                        performMeasure(childWidthMeasureSpec, childHeightMeasureSpec);
                    }

                    layoutRequested = true;
                }
            }
        } else {
			desiredWindowWidth = frame.width();
            desiredWindowHeight = frame.height();
			//...
		}
		//..
        boolean triggerGlobalLayoutListener = didLayout
                || mAttachInfo.mRecomputeGlobalAttributes;
        if (didLayout) {
            performLayout(lp, desiredWindowWidth, desiredWindowHeight);
            //...
        }

        if (triggerGlobalLayoutListener) {
            mAttachInfo.mRecomputeGlobalAttributes = false;
            //调用ViewTreeObserver.OnGlobalLayoutListener.onGlobalLayout()
            //此方法在第一次onLayout之后执行，可设置addOnGlobalLayoutListener来获取布局宽高
            mAttachInfo.mTreeObserver.dispatchOnGlobalLayout();
        }

        //...

        boolean cancelDraw = mAttachInfo.mTreeObserver.dispatchOnPreDraw() ||
                viewVisibility != View.VISIBLE;

        if (!cancelDraw && !newSurface) {
            if (!skipDraw || mReportNextDraw) {
                //...
                performDraw();
            }
        } else {
            if (viewVisibility == View.VISIBLE) {
                // Try again ------ <<< 重新进行一次View三大流程
                scheduleTraversals();
            } else if (mPendingTransitions != null && mPendingTransitions.size() > 0) {
                for (int i = 0; i < mPendingTransitions.size(); ++i) {
                    mPendingTransitions.get(i).endChangingAnimations();
                }
                mPendingTransitions.clear();
            }
        }

        mIsInTraversal = false;
    }

可以看到，如果triggerGlobalLayoutListener为true，那么就会调用mAttachInfo.mTreeObserver.dispatchOnGlobalLayout()，从而调用我们设置的监听。  

为true的条件有两个需要layout或者View.AppInfo.mRecomputeGlobalAttributes为true  

        /**
         * Indicates that ViewAncestor should trigger a global layout change
         * the next time it performs a traversal
         */
        boolean mRecomputeGlobalAttributes;  

根据注释，可知为mRecomputeGlobalAttributes为下一次performTranversal是否引发全局layout的改变。  


根据打印的日志，我们发现View的三大流程 (onMeasure、onLayout、onDraw) 都是在onResume执行完毕之后才执行的。而通过上面的源码分析，印证了，**View的三大流程是在onResume之后进行调用的**。  

## 其他方式 ##
在OnCreate中调用  

	 decorView.post(new Runnable() {
            @Override
            public void run() {
                //获取View的宽高
            }
        });  

通过post可以将一个runnable投递到消息队列的尾部，然后等待Looper调用此runnalbe的时候，View也已经初始化好了。  

打印日志如下  

	01-12 03:01:21.851 27432-27432/com.ethanco.rippledrawabletest I/Z-Main: onResume0
	01-12 03:01:22.351 27432-27432/com.ethanco.rippledrawabletest I/Z-Main: onResume1
	01-12 03:01:22.852 27432-27432/com.ethanco.rippledrawabletest I/Z-Main: onResume2
	01-12 03:01:23.352 27432-27432/com.ethanco.rippledrawabletest I/Z-Main: onResume3
	01-12 03:01:23.853 27432-27432/com.ethanco.rippledrawabletest I/Z-Main: onResume4
	01-12 03:01:24.353 27432-27432/com.ethanco.rippledrawabletest I/Z-Main: onResume5
	01-12 03:01:24.854 27432-27432/com.ethanco.rippledrawabletest I/Z-Main: onResume6
	01-12 03:01:25.355 27432-27432/com.ethanco.rippledrawabletest I/Z-Main: onResume7
	01-12 03:01:25.855 27432-27432/com.ethanco.rippledrawabletest I/Z-Main: onResume8
	01-12 03:01:26.356 27432-27432/com.ethanco.rippledrawabletest I/Z-Main: onResume9
	01-12 03:01:26.944 27432-27432/com.ethanco.rippledrawabletest I/Z-Main: RippleButton onMeasure
	01-12 03:01:26.971 27432-27432/com.ethanco.rippledrawabletest I/Z-Main: RippleButton onLayout
	01-12 03:01:26.974 27432-27432/com.ethanco.rippledrawabletest I/Z-Main: onGlobalLayout
	01-12 03:01:27.019 27432-27432/com.ethanco.rippledrawabletest I/Z-Main: decorView.post
	01-12 03:01:27.021 27432-27432/com.ethanco.rippledrawabletest I/Z-Main: RippleButton onDraw
	01-12 03:01:27.022 27432-27432/com.ethanco.rippledrawabletest I/Z-Main: RippleButton onDraw
	01-12 03:01:27.118 27432-27432/com.ethanco.rippledrawabletest I/Z-Main: RippleButton onMeasure
	01-12 03:01:27.118 27432-27432/com.ethanco.rippledrawabletest I/Z-Main: RippleButton onLayout
	01-12 03:01:27.119 27432-27432/com.ethanco.rippledrawabletest I/Z-Main: RippleButton onDraw



