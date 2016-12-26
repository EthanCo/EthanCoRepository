# Fragment的通信问题, 新建Fragment为何不要在构造方法中传递参数 #

最近有个新进来的同事做Android开发,因为之前都是做Java的,所以新建对象习惯在构造方法里面去传递参数回调方法什么的.  
于是当他在Activity中创建Fragment的时候,也使用了类似new TestFragment( "content" ) 这样的方式去将Activity中的参数传递到TestFragment中.  
 第一次用的时候没报错,然后进出几次TestFragment就报android.support.v4.app.Fragment$InstantiationException 错了. 然后满脸疑惑的问怎么了? 


如果将Fragment换做是普通的对象,比如是自己自定义的bean对象,在构造方法中传递参数是没问题的.  
但是Fragment和Activity一样,是有生命周期的组件,并不能看做是一般的类.于是我说你在构造方法中不要直接用类似的这种方式去写:  

	public static TestFragment getInstance(String tId) {
		 if (null == sInstance) { 
			synchronized (TestFragment.class) {
				 if (sInstance == null) { 
					sInstance = new TestFragment(); 
				 }
			}
		 } 
		tokenId = tId;  
		return sInstance;
	}
第一次传递tId这个参数可能在Fragment中是OK的.但到后面tokenId就可能为空了. 为什么呢?   


- Fragment的生命周期依附在Activity中,如果Activity为null,那么Fragment肯定要出事儿.   
- 手机屏幕竖屏横屏切换,导致Activity重建了,于是Fragment中的所有原先传递过去的值也会失去.也就是说tokenId这个时候是空的,或者变为原本的默认值.


这里就涉及了Activity和Fragment之间如何通信的情况.  
当然可以用接口什么的,但这里使用fragment.setArguments(bundle)去实现.   
Bundle是一个很好的用于传递参数的工具对象.并且在Fragment通过Bundle传递的参数,即使Fragment重建,这个Bundle中的参数也能被保存下来,在新的Fragment中继续用.

至于为什么是这样的呢?我也好奇,于是去看了下Fragment初始化的源码,有这么一段:

	public static Fragment instantiate(Context context, String fname, Bundle args) {
		 try {
			Class<?> clazz = sClassMap .get(fname);
			if (clazz == null) { // Class not found in the cache, see if it's real, and try to add it 
			clazz = context.getClassLoader().loadClass(fname);
			sClassMap .put(fname, clazz);
		 }
		/*获取Bundle原先的值,这样一开始利用Bundle传递进来的值,就放入f. mArguments. 
		只需要在Fragment中利用getArguments().getString("key");就能将参数取出来继续用 */
		Fragment f = (Fragment)clazz.newInstance(); 
		if (args != null) {
			args.setClassLoader(f.getClass().getClassLoader());
			f. mArguments = args; 
		}
		return f;
	 } catch (ClassNotFoundException e) {
		 throw new InstantiationException( "Unable to instantiate fragment " + fname + ": make sure class name exists, is public, and has an" + " empty constructor that is public" , e);
	 } catch (java.lang.InstantiationException e) {
		throw new InstantiationException( "Unable to insta ntiate fragment " + fname + ": make sure class name exists, is public, and has an" + " empty constructor that is public" , e); 
	 } catch (IllegalAccessException e) { 
		throw new InstantiationException("Unable to instantiate fragment " + fname + ": make sure class name exists, is public, and has an" + " empty constructor that is public" , e); 
	 } //...

整个过程中,Fragment的创建其实也是利用了无参数的构造方法去实例化.  
但关键的是,它将Bundle传类新建的Fragment,这样旧的Fragment和新的Fragment就能拥有一样的Bundle,从而达到利用Bundle传递参数的目的.

既然知道了原理,具体怎么用? 当然是利用Bundle和这个setArguments(bundle)方法,在构造Fragment的方法中加入:

	Bundle bundle = new Bundle(); bundle.putString("key", value); fragment.setArguments(bundle);


在Fragment的周期方法,比如onCreateView()中去取出Bundle就行喇.

	String value = getArguments().getString("key");

这样就大功告成了呢.