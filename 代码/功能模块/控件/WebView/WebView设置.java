 private void initWebViewProperty(WebView webView) {
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // ���γ������³��ָ���ճ��
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);

        // ��������
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        webView.getSettings().setDefaultTextEncodingName("UTF-8");//Ĭ�ϱ����ʽ
        webView.getSettings().setJavaScriptEnabled(true);//֧��JavaScript
        webView.getSettings().setSupportZoom(false);
        // ����������
        if (Build.VERSION.SDK_INT >= 11) {
            webView.getSettings().setDisplayZoomControls(false);
        }
        webView.getSettings().setBuiltInZoomControls(false);
        // �Զ�����ͼƬ
        webView.getSettings().setLoadsImagesAutomatically(true);
        // ��ͼ����Ӧ
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);

        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            webView.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
        }
    }