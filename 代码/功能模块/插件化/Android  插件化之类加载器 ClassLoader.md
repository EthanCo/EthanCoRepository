# Android  插件化之类加载器 ClassLoader #

## 类加载器 ##
用来加载 Java 类到 Java 虚拟机  
根据一个指定的类名，找到或者生产对应的字节代码，生成一个Java类，  
除此之外，ClassLoader还负责加载Java应用所需的资源，如图像文件和配置文件等。  


### Java虚拟机使用Java类的方式 ###
java源程序(.java文件)经过编译器转换成Java字节代码(.class文件)。  
类加载器负责读取.class文件，并生成一个Java类的实例。每个这样的实例用来表示一个java类。通过此实例的newInstance()方法就可以创建出该类的一个对象。  

## 一个Android App有几个ClassLoader实例 ? ##

> 一个运行的Android应用至少有2个ClassLoader。  

在Android系统启动的时候回创建一个Boot类型的ClassLoader实例，用于加载一些系统Framework层级需要的类，我们的Android应用里也需要用到一些系统的类，所以App启动时也会把这个Boot类型的ClassLoader传进来。  

	getClassLoader().getParent();  //BootClassLoader

此外，App也有自己的类，这些类保存在APK的dex文件里面，所以App启动的时候，也会创建一个自己的ClassLoader实例，用于加载自己dex文件里的类。  

	getClassLoader(); //PathClassLoader（应用启动时创建的，用于加载“/data/app/me.kaede.anroidclassloadersample-1/base.apk”里面的类)  


### 创建自己的ClassLoader实例 ###
动态加载外部的dex文件的时候，我们也可以使用自己创建的ClassLoader实例来加载dex里面的Class。  

创建一个ClassLoader实例时，需要一个现有的ClassLoader实例作为新创建的实例的Parent。  
这样一来，整个Android系统里所有的ClassLoader实例都会被一颗树关联起来，这也就是ClassLoader的**双亲代理模型**  

	ClassLoader(ClassLoader parentLoader, boolean nullAllowed) {
        if (parentLoader == null && !nullAllowed) {
            throw new NullPointerException("parentLoader == null && !nullAllowed");
        }
        parent = parentLoader;
    }  

### ClassLoader双亲代理模型加载类的特点和作用 ###
JVM中ClassLoader通过defineClass()加载jar里面的Class，而在Android中这个方法被弃用了。取而代之的是loadClass()。  

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

loadClass方法在加载一个类的实例时

1. 会先查询当前ClassLoader实例是否加载过此类，有就直接返回  
2. 如果没有，查询Parent是否已经加载过此类，如果已经加载过，就返回Parent加载的类  
3. 如果继承线路上的ClassLoader都没有加载，再由child执行类的加载工作  

作用  

1. 如果一个类被位于树根的ClassLoader加载过，那么在以后整个系统的生命周期内，这个类永远不会被重新加载。
2. 不同继承路线上的ClassLoader加载的类肯定不是同一个类，这样的限制避免了用户自己的代码冒充核心类库的类访问核心类库包可见成员的情况。  

## 使用ClassLoader需要注意的问题 ##

如果你希望通过动态加载的方式，加载一个新版本的dex文件，使用里面的新类替换原有的旧类，从而修复原有类的BUG，那么你必须保证在加载新类的时候，旧类还没有被加载，因为如果已经加载过旧类，那么ClassLoader会一直优先使用旧类。  

如果旧类总是优先于新类被加载，我们也可以使用一个与加载旧类的ClassLoader没有树的继承关系的另一个ClassLoader来加载新类，因为ClassLoader只会检查其Parent有没有加载过当前要加载的类，如果两个ClassLoader没有继承关系，那么旧类和新类都能被加载。  

不过这样一来又有另一个问题了，在Java中，只有当两个实例的类名、包名以及加载其的ClassLoader都相同，才会被认为是同一种类型。上面分别加载的新类和旧类，虽然包名和类名都完全一样，但是由于加载的ClassLoader不同，所以并不是同一种类型，在实际使用中可能会出现类型不符异常。  

> 同一个Class = 相同的 ClassName + PackageName + ClassLoader  

## DexClassLoader和PathClassLoader ##
在Android中，ClassLoader是一个抽象类，在实际开发中我们一般使用其子类DexClassLoader、PathClassLoader来。  

- DexClassLoader 可以加载jar、apk、dex，可以从SD卡中加载未安装的apk
- PathClassLoader 只能加载系统中已经安装过的apk  

### optimizedDirectory ###
optimizedDirectory是用来缓存我们需要加载的dex文件的，并创建一个DexFile对象，如果它为null，那么会直接使用dex文件原有的路径来创建DexFile对象象。

optimizedDirectory必须是一个内部存储路径，无论哪种动态加载，加载的可执行文件一定要存放在内部存储。DexClassLoader可以指定自己的optimizedDirectory，所以它可以加载外部的dex，因为这个dex会被复制到内部路径的optimizedDirectory；而PathClassLoader没有optimizedDirectory，所以它只能加载内部的dex，这些大都是存在系统中已经安装过的apk里面的。

## 加载类的过程 ##

ClassLoader.loadClass() -> ClassLoader.findClass() -> BaseDexClassLoader.findClass() -> 

	public Class findClass(String name) {
        for (Element element : dexElements) {
            DexFile dex = element.dexFile;
            if (dex != null) {
                Class clazz = dex.loadClassBinaryName(name, definingContext);
                if (clazz != null) {
                    return clazz;
                }
            }
        }
        return null;
    }

	public Class loadClassBinaryName(String name, ClassLoader loader) {
        return defineClass(name, loader, mCookie);
    }
    private native static Class defineClass(String name, ClassLoader loader, int cookie);

> 即遍历了之前存储的DexFile实例，也就是遍历了所有加载过的dex文件，再调用loadClassBinaryName方法一个个尝试能不能加载想要的类

## 自定义ClassLoader ##
平时进行动态加载开发的时候，使用DexClassLoader就够了。但我们也可以创建自己的类去继承ClassLoader，我们可以重载loadClass方法并改写类的加载逻辑。  

ClassLoader双亲代理的实现很大一部分就是在loadClass方法里，我们可以通过重写loadClass方法**避开双亲代理的框架**，这样一来就可以重新加载已经加载过的类，也可以在加载类的时候注入一些代码。这是一种Hack的开发方式，采用这种开发方式的程序稳定性可能比较差，但是却可以实现一些“黑科技”的功能。

## Android程序比起一般Java程序在使用动态加载时麻烦在哪里 ##

1. Android中许多组件类（如Activity、Service等）是需要在Manifest文件里面注册后才能工作的（系统会检查该组件有没有注册），所以即使动态加载了一个新的组件类进来，没有注册的话还是无法工作；
2. Res资源是Android开发中经常用到的，而Android是把这些资源用对应的R.id注册好，运行时通过这些ID从Resource实例中获取对应的资源。如果是运行时动态加载进来的新类，那类里面用到R.id的地方将会抛出找不到资源或者用错资源的异常，因为新类的资源ID根本和现有的Resource实例中保存的资源ID对不上；  

说到底，抛开虚拟机的差别不说，一个Android程序和标准的Java程序最大的区别就在于他们的上下文环境（Context）不同。Android中，这个环境可以给程序提供组件需要用到的功能，也可以提供一些主题、Res等资源，其实上面说到的两个问题都可以统一说是这个环境的问题，而现在的各种Android动态加载框架中，核心要解决的东西也正是“如何给外部的新类提供上下文环境”的问题。

> 参考  
> [https://segmentfault.com/a/1190000004062880](https://segmentfault.com/a/1190000004062880)  
> [https://www.ibm.com/developerworks/cn/java/j-lo-classloader/](https://www.ibm.com/developerworks/cn/java/j-lo-classloader/)  

