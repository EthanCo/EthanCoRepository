1.
TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyAttrs);
ta.getDimension(); ��õ���px��λ��ֵ
���ҪsetTextSize ��Ҫת��Ϊsp��ʹ��
setWidth,setHeight����px��λ��


    /**
     * ��pxֵת��Ϊspֵ����֤���ִ�С����
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }