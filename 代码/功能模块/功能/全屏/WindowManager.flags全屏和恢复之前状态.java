//方法一
        //全屏 划出后不会自动隐藏
        /*getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);*/

        //全屏 划出后会自动隐藏
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

				
//方法二

 /**
         * 取出全屏
         */
        private void cancelFullScreen() {
            int flags = mContent.getWindow().getDecorView().getSystemUiVisibility();
            int curApiVersion = Build.VERSION.SDK_INT;
            if (curApiVersion >= Build.VERSION_CODES.KITKAT) {
                flags &= ~(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN

                );
            } else {
                flags &= ~(View.SCROLLBAR_POSITION_DEFAULT | View.SYSTEM_UI_FLAG_FULLSCREEN);
            }
            mContent.getWindow().getDecorView().setSystemUiVisibility(flags);
        }
		
	 /**
     * 全屏
     *
     * @param context
     */
    private void fullScreen(Context context) {
        int flags = context.getWindow().getDecorView().getSystemUiVisibility();
        int curApiVersion = Build.VERSION.SDK_INT;
        if (curApiVersion >= Build.VERSION_CODES.KITKAT) {
            flags |= (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
            );
        } else {
            flags |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        }
        context.getWindow().getDecorView().setSystemUiVisibility(flags);
    }
	
//偏方
    private void hideNavigationBar() {
        int i = getWindow().getDecorView().getSystemUiVisibility();
        if (i == View.SYSTEM_UI_FLAG_VISIBLE) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
        }
    }