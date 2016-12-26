# 类加载器ClassLoader #
Java代码都是写在Class里面的，程序运行在虚拟机上时，虚拟机需要把需要的Class加载进来才能创建实例对象并工作，而完成这一个加载工作的角色就是ClassLoader。  

Android的Dalvik/ART虚拟机如同标准Java的JVM虚拟机一样，在运行程序时首先需要将对应的类加载到内存中，因此，我们可以利用这一点，在程序运行时手动加载Class，从而达到代码动态加载可执行文件的目的。Android的Dalvik/ART虚拟机虽然与标准Java的JVM虚拟机不一样，ClassLoader具体但是加载细节不一样，但是工作机制是类似的，也就是说在Android中同样可以采用类似的动态加载插件的功能，只是在Android应用中动态加载一个插件的工作要比Eclipse加载一个插件复杂许多。

## 有几个ClassLoader实例? ##
**动态加载的基础是ClassLoader**，从名字也可以看出，ClassLoader是专门用来处理类加载工作的，所以这货也叫类加载器，而且一个运行中的App不仅只有一个类加载器。  

其实，在Android系统启动的时候就会创建一个Boot类型的ClassLoader实例，用于加载一些系统Framework层级需要的类，我们的Android应用里也需要用到一些系统的类，所以App启动的时候也会把这个Boot类型的ClassLoader传进来。  

此外，App也有自己的类，这些类保存在Apk的dex文件里面，所以App启动的时候，也会创建一个自己的ClassLoader实例，用于加载自己dex文件中的类。  

一个运行的Android应用至少有2个ClassLoader。

## 创建自己的ClassLoader实例 ##
动态加载外部的dex文件的时候，我们也可以使用自己创建的ClassLoader实例来加载dex里面的Class，不过ClassLoader的创建方式有点特殊 ，我们先看看它的构造方法 

  	/*
     * constructor for the BootClassLoader which needs parent to be null.
     */
    ClassLoader(ClassLoader parentLoader, boolean nullAllowed) {
        if (parentLoader == null && !nullAllowed) {
            throw new NullPointerException("parentLoader == null && !nullAllowed");
        }
        parent = parentLoader;
    }

创建一个ClassLoader实例的时候，需要使用一个现有的ClassLoader实例作为新创建的实例的Parent。这样一来，一个Android应用，甚至真个Android系统里所有的ClassLoader实例都会被一棵树关联起来，这也是ClassLoader的双亲代理模型的特点。  

## ClassLoader双亲代理模型加载类的特点和作用 ##
JVM中ClassLoader通过defineClass方法加载jar里面的Class，而Android中这个方法被弃用了。  

取而代之的是loadClass方法  

	public Class<?> loadClass(String className) throws ClassNotFoundException {
        return loadClass(className, false);
    }

    protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {
        Class<?> clazz = findLoadedClass(className);

        if (clazz == null) {
            ClassNotFoundException suppressed = null;
            try {
                clazz = parent.loadClass(className, false);
            } catch (ClassNotFoundException e) {
                suppressed = e;
            }

            if (clazz == null) {
                try {
                    clazz = findClass(className);
                } catch (ClassNotFoundException e) {
                    e.addSuppressed(suppressed);
                    throw e;
                }
            }
        }

        return clazz;
    }

###特点

在源码中我们也可以看出，loadClass方法在加载一个类的实例的时候，  

		
- 查询当前ClassLoader实例是否加载过此类，有就返回  
- 如果没有，查询Parent是否已经加载过此类，如果已经加载过，就直接返回Parent加载类  
- 如果继承线路上的ClassLoader都没有记载，才由Child执行类的加载工作  

这样做有一个明显的特点，如果一个类被位于树根的ClassLoader加载过，那么在以后整个系统的生命周期内，这个类永远不会被重新加载。  

### 作用 ###
首先是共享功能，一些Framework层级的类一旦被顶层的CalssLoader加载过就缓存在内存里面，以后任何地方用到都不需要重新加载。  

除此之外还有隔离功能，不同继承线路上的ClassLoader加载的类肯定不是同一个类，这样的限制避免了用户自己的代码冒充核心类库的类访问核心类库包可见成员的情况。这也好理解，一些系统层级的类会在系统初始化的时候被加载，比如java.lang.String，如果在一个应用里面能够简单地用自定义的String类把这个系统的String类给替换掉，那么会有严重的安全问题。  

### 使用ClassLoader一些需要注意的问题 ###
如果你希望通过动态加载的方式，加载一个新版本的dex文件，使用里面的新类代替原来收到旧类，从而修复原有类的Bug，那么你必须要保证在加载新类的时候，旧类还没有被加载。  

如果旧类总是优先于新类被加载，那么我们也可以使用一个与加载旧类的ClassLoader没有树的继承关系的另一个ClassLoader来加载新类，因为ClassLoader只会检查其Parent有没有加载过当前要加载的类，如果两个ClassLoader没有继承关系，那么旧类新类都能被加载。

不过这样一来，又有另一个问题了。再Java中，只有当两个实例的类名、包名已经加载其的ClassLoader都相同，才会被认为是同一种类型。上面分别加载撒的新类和旧类都会虽然包名和类名都完全一样，但是由于加载的ClassLoader不同，所以并不是同一种类型，在实际使用中可能会出现类型不符异常。  

	同一个Class = 相同的ClassName + PackageName + ClassLoader  

## DexClassLoader 和 PathClassLoader ##
在Android中，classLoader是一个抽象类，实际开发中，我们一般是使用其具体子类DexClassLoader、PathClassLoader这些类加载器来加载类的，他们的不同之处是:  
	
- DexClassLoader 可以加载jar/apk/dex，可以从SD卡中加载未安装的apk;
- PathClassLoader只能加载系统中已经安装过的apk;  

## DexClassLoader和PathClassLoader的区别 ##
DexClassLoader和PathClassLoader都继承自BaseDexClassLoader，都需要传入optimizedDirectory，DexClassLoader可以传入指定的optimizedDirectory，而PathClassLoader只能传入 null (即不能自己指定 optimizedDirectory);  
optimizedDirectory是用来缓存我们需要加载的dex文件的，并创建一个DexFile对象，如果它为null，那么会直接使用dex文件原有的路径来创建DexFile对象。
DexClassLoader可以指定自己的optimizedDirectory，所以它可以加载外部的dex，因为这个dex会被复制到内部路径的optimizedDirectory;  
PathClassLoader没有optimizedDirectory，所以它只能加载内部的dex，这些大都是存在系统中已经安装过的apk里面的。  

## 加载类的过程 ##
在Android中，调用loadClass来加载我们需要的类  
### ClassLoader#loadClass ###
	
	protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {
		//找到类
        Class<?> clazz = findLoadedClass(className);

		//如果clazz为null
        if (clazz == null) {
            ClassNotFoundException suppressed = null;
            try {
				//尝试错父类中查找
                clazz = parent.loadClass(className, false);
            } catch (ClassNotFoundException e) {
                suppressed = e;
            }

			//如果clazz依旧为null
            if (clazz == null) {
                try {
					//调用findClass()进行查找
                    clazz = findClass(className);
                } catch (ClassNotFoundException e) {
                    e.addSuppressed(suppressed);
                    throw e;
                }
            }
        }

        return clazz;
    }

	//loadClass方法调用了findClass方法，而BaseDexClassLoader重载了这个方法
	//BaseDexClassLoader.java#findClass 
	@Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        List<Throwable> suppressedExceptions = new ArrayList<Throwable>();
        
		//调用pathList.findClass进行查找
		Class c = pathList.findClass(name, suppressedExceptions);
        if (c == null) {
            ClassNotFoundException cnfe = new ClassNotFoundException("Didn't find class \"" + name + "\" on path: " + pathList);
            for (Throwable t : suppressedExceptions) {
                cnfe.addSuppressed(t);
            }
            throw cnfe;
        }
        return c;
    }

	//DexPathList.java#findClass
    public Class findClass(String name, List<Throwable> suppressed) {
		//遍历了之前所有的DexFile实例，其实也就是遍历了所有加载过的dex文件
        for (Element element : dexElements) {
            DexFile dex = element.dexFile;

            if (dex != null) {
				//调用loadClassBinaryName方法一个个尝试能不能加载想要的类，简单粗暴
                Class clazz = dex.loadClassBinaryName(name, definingContext, suppressed);
                if (clazz != null) {
                    return clazz;
                }
            }
        }
        if (dexElementsSuppressedExceptions != null) {
            suppressed.addAll(Arrays.asList(dexElementsSuppressedExceptions));
        }
        return null;
    }

	//DexFile.java#loadClassBinaryName
	public Class loadClassBinaryName(String name, ClassLoader loader, List<Throwable> suppressed) {
        return defineClass(name, loader, mCookie, suppressed);
    }

    private static Class defineClass(String name, ClassLoader loader, long cookie,List<Throwable> suppressed) {
        Class result = null;
        try {
			
			//调用native方法
            result = defineClassNative(name, loader, cookie);
        } catch (NoClassDefFoundError e) {
            if (suppressed != null) {
                suppressed.add(e);
            }
        } catch (ClassNotFoundException e) {
            if (suppressed != null) {
                suppressed.add(e);
            }
        }
        return result;
    }

	//native方法
	private static native Class defineClassNative(String name, ClassLoader loader, long cookie) throws ClassNotFoundException, NoClassDefFoundError;

loadClassBinaryName中调用了Native方法defineClass加载类  

> 标准JVM中，ClassLoader是用defineClass加载类的，而Android中defineClass被弃用了，改用了loadClass方法，而且加载类的过程也挪到了DexFile中，在DexFile中加载类的具体方法也叫defineClass，不知道是Google故意写成这样的还是巧合。  

## 自定义ClassLoader ##
平时进行动态加载开发的时候，使用DexClassLoader就够了。但我们也可以创建自己的类去继承ClassLoader，需要注意的是loadClass方法并不是final类型的，所以我们可以重载loadClass方法并改写类的加载逻辑。

通过前面我们分析知道，ClassLoader双亲代理的实现很大一部分就是在loadClass方法里，**我们可以通过重写loadClass方法避开双亲代理的框架**，这样一来就可以在重新加载已经加载过的类，也可以在加载类的时候注入一些代码。这是一种Hack的开发方式，采用这种开发方式的程序稳定性可能比较差，但是却可以实现一些“黑科技”的功能。 

## Android程序比起一般Java程序在使用动态加载时麻烦在哪里 ##
通过上面的分析，我们知道使用ClassLoader动态加载一个外部的类是非常容易的事情，所以很容易就能实现动态加载新的执行代码的功能，但是比起一般的Java程序，在Android程序中使用动态代理加载主要由两个麻烦但是问题:  
	
- Android中许多组件类 (如Activity，Service等) 是需要在Manifest文件里面注册后才能工作的(系统会检查该组件有没有注册)，所以即使动态加载了一个新的组件类进来，没有祖册的话还是无法工作。  
- Res资源是Android开发中经常用到的，而Android是把这些资源用对应的R.id注册好，运行时通过这些ID从Resource实例中获取对应对应的资源，如果是运行时动态加载进来的新类，那类里面用到R.id的地方会抛出找不到资源或者用错资源的异常，因为新类的资源ID根本和现有的Resource实例中保存的资源Id对不上。  

说到底，抛开虚拟机的差别不说，**一个Android程序和标准的Java程序最大的区别就在于他们的上下文环境(Context)不同，**Android中，这个环境可以给程序提供组件需要用到的功能，也可以提供一些主题、Res等资源，其实上面说到的两个问题都可以统一说是这个环境的问题，而现在的各种Android动态加载框架中，核心要解决的问题也正是"如何给外部的新类提供上下文环境"的问题。


###  ###
## 参考 ##
[Android动态加载基础 ClassLoader工作机制](https://segmentfault.com/a/1190000004062880)