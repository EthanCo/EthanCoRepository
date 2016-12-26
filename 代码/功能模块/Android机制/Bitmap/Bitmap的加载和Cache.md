# Bitmap的加载和Cache #
## Bitmap的高效加载 ##
### 如何加载一个Bitmap ###
BitmapFactory类提供了四类方法

- decodeFile
	- 从文件系统加载
- decodeResource
	- 从资源加载
- decodeStream
	- 从输入流加载
- decodeByteArray
	- 从字节数组中加载

### 如何高效加载Bitmap ###
采用BitmapFactory.Options来加载所需尺寸的图片  

- inSampleSize
	- 采用率，缩小为原来的几倍，如果小于1则没有效果
	- 官方建议: inSampleSize的取值应该总是为2的指数

## Android中的缓存策略 ##
### LRU算法 ###
当缓存满时，会有先淘汰那些近期最少使用的缓存对象。
  
- LruCache
	- 用于实现内存缓存
- DisLruCache
	- 用于存储设备(磁盘)缓存

### LruCache ###
- LruCache是一个泛型类
- 内部采用一个LinkedHashMap以强引用的方式存储外界的缓存对象
- 提供get和set方法来完成缓存的获取和添加操作
- 当缓存满时，LruCache会移除较早使用的缓存对象，然后再添加新的缓存对象
- LruCache是线程安全的
#####  #####
	int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    int cacheSize = maxMemory / 8;
    mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
        @Override //计算缓存对象的大小，大小单位要和总容量的单位一致
        protected int sizeOf(String key, Bitmap value) { 
            return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
        }

		@Override //移除旧缓存时会调用，可以在这里完成一些资源回收工作
        protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
            super.entryRemoved(evicted, key, oldValue, newValue);
        }
    };

获取一个缓存对象

    mMemoryCache.get("key");

添加一个缓存对象  

    mMemoryCache.put("key",bitmap);

删除一个缓存对象

	mMemoryCache.remove("key");

### DishLruCache ###
#### 创建 ####
	File diskCacheDir = getExternalCacheDir();
    if (!diskCacheDir.exists()) { //如果文件夹不存在，则创建
        diskCacheDir.mkdir();
    }

    try {
        //参数一 缓存目录 参数二，参数三:一般设为1即可 参数四:缓存上限
        mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, 1024 * 1024 * 50);
    } catch (IOException e) {
        e.printStackTrace();
    }

#### 缓存添加 ####

