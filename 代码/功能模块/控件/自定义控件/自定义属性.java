1.
TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyAttrs);
ta.getDimension(); 获得的是px单位的值
如果要setTextSize 需要转化为sp后使用
setWidth,setHeight都是px单位的


    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }