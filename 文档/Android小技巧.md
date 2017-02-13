# 小技巧 #
## 创建目录 ##
已创建缓存目录为例

 	File cacheFile = getDiskCacheDir(context, "bitmap");
    if (!cacheFile.exists()) {
        cacheFile.mkdir();
    }

	public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }

        return new File(cachePath + File.separator + uniqueName);
    }

## 加减操作-线程安全 ##

## 生成MD5 ##

	 private String hashKeyFormUrl(String url) {
	    String cacheKey;
	    try {
	        final MessageDigest mDigest = MessageDigest.getInstance("MD5");
	        mDigest.update(url.getBytes());
	        cacheKey = bytesToHexString(mDigest.digest());
	    } catch (NoSuchAlgorithmException e) {
	        cacheKey = String.valueOf(url.hashCode());
	    }
	    return cacheKey;
	}


#### SingleTon ####
可以设置为单例的统一父类
 	static public IActivityManager getDefault() {
        return gDefault.get();
    }

	private static final Singleton<IActivityManager> gDefault = new Singleton<IActivityManager>() {
        protected IActivityManager create() {
            IBinder b = ServiceManager.getService("activity");
            if (false) {
                Log.v("ActivityManager", "default service binder = " + b);
            }
            IActivityManager am = asInterface(b);
            if (false) {
                Log.v("ActivityManager", "default service = " + am);
            }
            return am;
        }
    };