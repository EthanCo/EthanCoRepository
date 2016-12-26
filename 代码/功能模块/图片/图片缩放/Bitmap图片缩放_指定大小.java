/**
     * 转换成bitmap,并指定大小
     *
     * @param path 路径
     * @param w    缩放后的宽度
     * @param h    缩放后的高度
     * @return bitmap
     */
    public static Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 使用BitmapFactory.Options设置inJustDecodeBounds为true后，再使用decodeFile()等方法，并不会真正的分配空间，即解码出来的Bitmap为null，但是可计算出原始图片的宽度和高度,即options.outWidth和 options.outHeight。通过这两个值，就可以知道图片是否过大了。
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 1f, scaleHeight = 1f;
        if (width > w || height > h) {
            // 缩放
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
		//在下次使用BitmapFactory的 decodeFile()等方法实例化Bitmap对象前，别忘记将opts.inJustDecodeBound设置回false。否则获取的 bitmap对象还是null。
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);//比较缩放的宽高，取最大值
        opts.inSampleSize = (int) scale; //即缩小为1/scale
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeStream(is, null, opts)); //先缩放为scale的比例
        return Bitmap.createScaledBitmap(weak.get(), w, h, true); //再缩小为宽为w,高为h的新位图
    }