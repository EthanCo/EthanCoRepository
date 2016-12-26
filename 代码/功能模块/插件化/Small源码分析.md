## 初始化 ##
Small的初始化在Small#setUp()中完成。  
setUp()中，主要完成了一下几件事情: 

###获取已注册的Activity classes并缓存在 sActivityClasses (HashMap) 中  

	private static void saveActivityClasses(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_ACTIVITIES);
            ActivityInfo[] as = pi.activities;
            if (as != null) {
                sActivityClasses = new HashMap<String, Class<?>>();
                for (int i = 0; i < as.length; i++) {
                    ActivityInfo ai = as[i];
                    int dot = ai.name.lastIndexOf(".");
                    if (dot > 0) {
                        try {
                            Class<?> clazz = Class.forName(ai.name);
                            sActivityClasses.put(ai.name, clazz);
                        } catch (ClassNotFoundException e) {
                            // Ignored
                        }
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
    }  

###注册OpenUriReceiver  

	 LocalBroadcastManager.getInstance(appContext).registerReceiver(new OpenUriReceiver(),new IntentFilter(EVENT_OPENURI));  

OpenUriReceiver进行启动指定Activity的操作  

	private static class OpenUriReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String uri = intent.getStringExtra("uri");
            openUri(Uri.parse(uri), context);
        }
    }  

###比较版本  
若当前版本与之前版本不同，则保存新版本并清除App缓存  
若当前版本与之前版本相同，则

	//从Sp中获取版本
	int backupHostVersion = getHostVersionCode();
    int currHostVersion = 0;
    try {
		//获取当前App的版本
        PackageInfo pi = appContext.getPackageManager().getPackageInfo(
                appContext.getPackageName(), 0);
        currHostVersion = pi.versionCode;
    } catch (PackageManager.NameNotFoundException e) {
        e.printStackTrace();
    }
	//比较版本
    if (backupHostVersion != currHostVersion) {
		//版本不相同
		sIsNewHostApp = true;
        //保存到Sp
        setHostVersionCode(currHostVersion);
        //清除App缓存 (包括patch)
        clearAppCache(appContext);
	}else{
		//版本相同
		sIsNewHostApp = false;
	}  

###注册BundleLaunchers，并遍历BundleLauncher列表进行初始化  

	registerLauncher(new ActivityLauncher());
    registerLauncher(new ApkBundleLauncher());
    registerLauncher(new WebBundleLauncher());  

	//遍历 BundleLauncher列表 执行初始化
	Bundle.setupLaunchers(context);  

遍历 BundleLauncher列表 执行初始化中，只有 ApkBundleLauncher 替换mInstrumentation，其他的setUp() 都 doNothing  

Bundle#setupLaunchers()  

	public static void setupLaunchers(Context context) {
        if (sBundleLaunchers == null) return;
        for (BundleLauncher launcher : sBundleLaunchers) {
            launcher.setUp(context);
        }
    }  

ApkBundleLauncher#setUp()  

	@Override
    public void setUp(Context context) {
        super.setUp(context);
        // Inject instrumentation
        if (sHostInstrumentation == null) {
            try {

                //对ActivityThread的mInstrumentation进行替换
                final Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
                /**
                 *  public static ActivityThread currentActivityThread() {
                 *      return sCurrentActivityThread;
                 *  }
                 */
                final Method method = activityThreadClass.getMethod("currentActivityThread");
                Object thread = method.invoke(null, (Object[]) null); //ActivityThread
                Field field = activityThreadClass.getDeclaredField("mInstrumentation");
                field.setAccessible(true);
                sHostInstrumentation = (Instrumentation) field.get(thread);//Instrumentation
                Instrumentation wrapper = new InstrumentationWrapper(); //Instrumentation包装类
                field.set(thread, wrapper); //代替Instrumentation


                if (context instanceof Activity) {
                    //对Activity的mInstrumentation进行替换
                    field = Activity.class.getDeclaredField("mInstrumentation");
                    field.setAccessible(true);
                    field.set(context, wrapper);
                }
            } catch (Exception ignored) {
                ignored.printStackTrace();
                // Usually, cannot reach here
            }
        }
    }  

> Android activities 受 Instrumentation 监控。   
> 由startActivityForResult方法启动，通过instrumentation的execStartActivity方法激活生命周期。  (1)  
> 在ActivityThread的performLaunchActivity方法中通过instrumentation的newActivity方法实例化Activity。  (2)  
> 动态注册Activity，首先在宿主manifest中注册一个命名特殊的占坑Activity来欺骗(1)以获得生命周期，再欺骗(2)来获得插件Activity实例。  
> **要做的就是封装一个instrumentatino，替换掉宿主的。**在此处即 InstrumentationWrapper

### 加载Bundles ###

	Bundle.loadLaunchableBundles(listener);  

Bundle#loadLaunchableBundles()  

	/**
     * Load bundles from manifest
     * 读取Bundles.json
     *      1.如果存在于context.getFilesDir()路径中，则直接读取
     *      2.如果不存在，则读取asserts的bundles.json
     * 读取之后获得 Bundles.json的内容 : manifestJson
     * 接着解析manifestJson
     * 最后，如果version==1.0.0，则加载Bundles
     */
    public static void loadLaunchableBundles(OnLoadListener listener) {
        mListener = listener;
        Context context = Small.getContext();
        // Read manifest file
        // 读取bundles.json
        File manifestFile = new File(context.getFilesDir(), BUNDLE_MANIFEST_NAME); //BUNDLE_MANIFEST_NAME = "bundles.json";
        //删除bundles.json (在Small更高版本中，该操作取消)
        manifestFile.delete();
        String manifestJson;
        if (!manifestFile.exists()) {
            //如果在context.getFilesDir()路径中不存在，从asset中读取bundle.json
            // Copy asset to files
            try {
                InputStream is = context.getAssets().open(BUNDLE_MANIFEST_NAME);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();

                manifestFile.createNewFile();
                FileOutputStream os = new FileOutputStream(manifestFile);
                os.write(buffer);
                os.close();

                manifestJson = new String(buffer, 0, size);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        } else {
            //如果在context.getFilesDir()路径中存在，则读取
            try {
                BufferedReader br = new BufferedReader(new FileReader(manifestFile));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                manifestJson = sb.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        // Parse manifest file
        //解析bundle.json
        try {
            JSONObject jsonObject = new JSONObject(manifestJson);
            String version = jsonObject.getString("version");
            //加载Bundles
            loadManifest(version, jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
    }

Bundle#loadManifest()  

	private static boolean loadManifest(String version, JSONObject jsonObject) {
        if (version.equals("1.0.0")) {
            try {
                JSONArray bundles = jsonObject.getJSONArray(BUNDLES_KEY);
                //加载Bundles
                loadBundles(bundles);
                return true;
            } catch (JSONException e) {
                return false;
            }
        }

        throw new UnsupportedOperationException("Unknown version " + version);
    }  

在loadBundles()中，如果mListener(即Small.setUp中传入的Listener)不为null，则通过new LoadBundleThread()，异步加载所有的Bundle (包括从网络中下载)  
如果为null，则同步加载所有的Bundle

## 启动插件Activity ##
启动插件Activity通过Small#openUri()完成  
启动插件Activity其实就是用隐式Intent启动Activity

	Small.openUri("main", LaunchActivity.this);  

Small#openUri  

	public static void openUri(String uriString, Context context) {
        openUri(makeUri(uriString), context);
    }  

	public static void openUri(Uri uri, Context context) {
        // System url schemes
		//判断uri是否能匹配到相关的Activity
        if (!uri.getScheme().equals("http")
                && !uri.getScheme().equals("https")
                && !uri.getScheme().equals("file")
                && ApplicationUtils.canOpenUri(uri, context)) {
            ApplicationUtils.openUri(uri, context);
            return;
        }

        // Small url schemes
        //返回匹配到uri的bundle
        Bundle bundle = Bundle.getLaunchableBundle(uri);
        if (bundle != null) {

            //启动Activity
            bundle.launchFrom(context);
        }
    }  

ApplicationUtils.openUri();  
通过隐式Intent，启动Activity  

    public static void openUri(Uri uri, Context context) {
        //通过Intent 匹配Action 启动Activity
        Intent intent = getIntentOfUri(uri);
        context.startActivity(intent);
    }

	public static Intent getIntentOfUri(Uri uri) {
        return new Intent(Intent.ACTION_VIEW, uri);
    }

Bundle.getLaunchableBundle();  

	 public static Bundle getLaunchableBundle(Uri uri) {
        if (sPreloadBundles != null) {
            //遍历sPreloadBundles
            for (Bundle bundle : sPreloadBundles) {
                //如果匹配到uri，且enabled为true，返回该bundle
                if (bundle.matchesRule(uri)) {
                    if (!bundle.enabled) return null; // Illegal bundle (invalid signature, etc.)
                    return bundle;
                }
            }
        }

        //使用WebView进行显示，将mApplicableLauncher设为WebBundleLauncher
        // Downgrade to show webView
        if (uri.getScheme() != null) {
            Bundle bundle = new Bundle();
            try {
                bundle.url = new URL(uri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            //解析Bundle，做预加载处理
            bundle.prepareForLaunch();
            bundle.setQuery(uri.getEncodedQuery()); // Fix issue #6 from Spring-Xu.
            bundle.mApplicableLauncher = new WebBundleLauncher();
            bundle.mApplicableLauncher.prelaunchBundle(bundle);
            return bundle;
        }
        return null;
    }	

Bundle.launchFrom()直接调用了BundleLauncher的launchBundle()，我们直接看BundleLauncher#launchBundle()

	public void launchBundle(Bundle bundle, Context context) {
        if (!bundle.isLaunchable()) {
            // TODO: Exit app

            return;
        }

        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (shouldFinishPreviousActivity(activity)) {
                activity.finish();
            }
            activity.startActivityForResult(bundle.getIntent(), Small.REQUEST_CODE_DEFAULT);
        } else {
            context.startActivity(bundle.getIntent());
        }
    }

也是通过隐式Intent，启动Activity