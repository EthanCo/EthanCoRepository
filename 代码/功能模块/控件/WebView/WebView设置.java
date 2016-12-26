 private void initWebViewProperty(WebView webView) {
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // 屏蔽长按导致出现复制粘贴
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);

        // 缓存设置
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        webView.getSettings().setDefaultTextEncodingName("UTF-8");//默认编码格式
        webView.getSettings().setJavaScriptEnabled(true);//支持JavaScript
        webView.getSettings().setSupportZoom(false);
        // 不允许缩放
        if (Build.VERSION.SDK_INT >= 11) {
            webView.getSettings().setDisplayZoomControls(false);
        }
        webView.getSettings().setBuiltInZoomControls(false);
        // 自动加载图片
        webView.getSettings().setLoadsImagesAutomatically(true);
        // 视图自适应
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            webView.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
        }
    }