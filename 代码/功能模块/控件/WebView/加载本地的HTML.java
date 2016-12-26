//打开本包内asset目录下的index.html文件
wView.loadUrl(" file:///android_asset/index.html ");  

//WebView加载本地Html的URL时，路径为外部存储/Android/data/包名/cache时， URL要怎么表示?
String url = "file:///" + getExternalCacheDir() + "/demo.html";
webview.load(url);